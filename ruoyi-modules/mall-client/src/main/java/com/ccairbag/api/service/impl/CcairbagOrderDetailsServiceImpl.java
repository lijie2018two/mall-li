package com.ccairbag.api.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.shaded.com.google.gson.Gson;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.ccairbag.api.config.VisapayConfig;
import com.ccairbag.api.domain.dto.PayPalConfig;
import com.ccairbag.api.mapper.*;
import com.ccairbag.api.service.ICcairbagOrderDetailsService;
import com.ccairbag.api.utils.AlipayUtil;
import com.ccairbag.api.utils.EmailUtils;
import com.ddty.common.mq.service.MessageSender;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ijpay.core.IJPayHttpResponse;
import com.ijpay.paypal.PayPalApi;
import com.ijpay.paypal.PayPalApiConfig;
import com.ijpay.paypal.PayPalApiConfigKit;
import com.ruoyi.common.core.domain.MQProperties;
import com.ruoyi.common.core.domain.MqMessage;
import com.ruoyi.common.core.utils.HttpUtils;
import com.ruoyi.common.core.utils.SslUtils;
import com.ruoyi.common.core.utils.oConvertUtils;
import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.common.redis.service.OrderIdUtil;
import com.ruoyi.common.security.utils.DictUtils;
import com.ruoyi.system.api.domain.SysDictData;
import com.ruoyi.system.api.domain.ccairbag.*;
import com.ruoyi.system.api.domain.ccairbag.dto.RefundOrderDTO;
import com.ruoyi.system.domain.CcairbagIncomeRecords;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Refund;
import com.stripe.param.RefundCreateParams;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.amqp.AmqpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * 订单详情Service业务层处理
 *
 * @author lidabai
 * @date 2025-02-20
 */
@Service
@Slf4j
public class CcairbagOrderDetailsServiceImpl implements ICcairbagOrderDetailsService
{
    @Resource
    private CcairbagOrderDetailsMapper ccairbagOrderDetailsMapper;
    @Resource
    private CcairbagOrdersMapper ccairbagOrdersMapper;
    @Resource
    private CcairbagUserAddrMapper ccairbagUserAddrMapper;
    @Resource
    private CcairbagUsersMapper ccairbagUsersMapper;
    @Resource
    private CcairbagOrderPaymentMapper ccairbagOrderPaymentMapper;
    @Resource
    private CcairbagShopsMapper ccairbagShopsMapper;
    @Resource
    private CcairbagIncomeRecordsMapper ccairbagIncomeRecordsMapper;
    @Resource
    private MessageSender messageSender;
    @Autowired
    private OrderIdUtil orderIdUtil;
    @Autowired
    private PayPalConfig paypalConfig;
    @Resource
    private CcairbagNotificationMapper ccairbagNotificationMapper;
    @Resource
    private CcairbagNotificationUserMapper ccairbagNotificationUserMapper;
    @Resource
    private CcairbagOrderRefundMapper ccairbagOrderRefundMapper;
    @Resource
    private CcairbagOrdersTotalMapper ccairbagOrdersTotalMapper;
    @Resource
    private CcairbagProductsMapper ccairbagProductsMapper;
    @Autowired
    private AlipayUtil alipayUtil;
    @Autowired
    private VisapayConfig visapayConfig;
    @Autowired
    private EmailUtils emailUtils;

    @Override
    public AppResult setDvyFlowNum(Long detailId, String dvyFlowNum, String  courierCode, String  logisticsName) throws Exception {
        CcairbagOrderDetails ccairbagOrderDetails = new CcairbagOrderDetails();
        ccairbagOrderDetails.setDetailId(detailId);
        ccairbagOrderDetails.setDvyFlowNum(dvyFlowNum);
        ccairbagOrderDetails.setLogisticsCode(courierCode);
        ccairbagOrderDetails.setLogisticsName(logisticsName);
        ccairbagOrderDetails.setDvyTime(new Date());
        ccairbagOrderDetails.setOrderStatus(2);
        ccairbagOrderDetailsMapper.updateCcairbagOrderDetails(ccairbagOrderDetails);
        CcairbagOrderDetails details = ccairbagOrderDetailsMapper.selectCcairbagOrderDetailsByDetailId(detailId);
        CcairbagOrders orders = ccairbagOrdersMapper.selectCcairbagOrdersByOrderId(details.getOrderId());
        addMessage(orders,"The merchant has shipped the order","The merchant has shipped the order",1,"7");
        CcairbagUsers users = ccairbagUsersMapper.selectCcairbagUsersByUserId(orders.getUserId());
        //发送邮箱通知买家
        if (oConvertUtils.isNotEmpty(users)){
            emailUtils.send("The merchant has shipped the order","The merchant has shipped the order. Tracking Number: "+dvyFlowNum+", Courier Service:"+logisticsName,users.getEmail());
        }
//        createTrack(dvyFlowNum,courierCode,detailId.toString());
        // 设置mq  7天后自动收货
        systemSignOrderTask(String.valueOf(detailId));
        createTrack(dvyFlowNum,courierCode,detailId.toString());
        return new AppResult("设置物流单号成功");
    }

    @Override
    public AppResult signOrder(Long detailId) {
        /**
         * 签收 改detailId的订单状态
         */
        CcairbagOrderDetails details = ccairbagOrderDetailsMapper.selectCcairbagOrderDetailsByDetailId(detailId);
        if (oConvertUtils.isEmpty(details)) {
            throw new RuntimeException("订单不存在");
        }
        if (details.getOrderStatus() != 2) {
            throw new RuntimeException("订单不是未发货状态，不能设置收货");
        }
        int oldstauts = details.getOrderStatus();
        //正常履约链路（无任何退款 / 售后）：0-待付款 → 1-待发货（支付成功） → 2-待收货（卖家填运单号） → 7-已收货（买家确认） → 8-已完成（订单闭环）
        details.setOldStatus(oldstauts);

        //已收货， 后续3天内 把状态更改成 8 已完成
        details.setOrderStatus(7);//7-已收货（买家确认）
        ccairbagOrderDetailsMapper.updateCcairbagOrderDetails(details);
        // 加个队列 或者定时器  5天后  走一下的逻辑 ， 并把订单改成已完成
        systemCanceOrder5DayTask(String.valueOf(detailId));

        return new AppResult("success");
    }

    @Override
    public AppResult refundOrderIng(Long detailId) {
        CcairbagOrderDetails details = ccairbagOrderDetailsMapper.selectCcairbagOrderDetailsByDetailId(detailId);
        List<SysDictData> reList = new ArrayList<>();
        if (details.getOrderStatus()== 1 ) {
             reList = DictUtils.getDictCache("refund_info1");
        }else {
            reList = DictUtils.getDictCache("refund_info3");
        }
        return new AppResult(reList);
    }



    @Override
    public AppResult shopRefundOrderIng(Long detailId) {
        CcairbagOrderDetails details = ccairbagOrderDetailsMapper.selectCcairbagOrderDetailsByDetailId(detailId);

        if (details.getOrderStatus() != 1) {
            return AppResult.error("订单状态不是待发货，不能取消订单");
        }
        List<SysDictData> reList = DictUtils.getDictCache("refund_info2");
        return new AppResult(reList);
    }

    @Override
    @Transactional
    public AppResult refundOrder(RefundOrderDTO refundOrderDTO) {
        CcairbagOrderDetails details = ccairbagOrderDetailsMapper.selectCcairbagOrderDetailsByDetailId(refundOrderDTO.getDetailId());
        if (details.getOrderStatus()==8 || details.getOrderStatus()==10){
            return AppResult.error("订单状态有误，订单已完成，无法退款");
        }
        if (details.getOrderStatus()==0 ){
            return AppResult.error("订单状态有误，订单未支付");
        }
        int statusOld = details.getOrderStatus();
        details.setOldStatus(statusOld);
        details.setOrderStatus(3);
        details.setUpdateTime(new Date());
        ccairbagOrderDetailsMapper.updateCcairbagOrderDetails(details);

        CcairbagOrders orders = ccairbagOrdersMapper.selectCcairbagOrdersByOrderId(details.getOrderId());
        CcairbagOrderRefund refunde =ccairbagOrderRefundMapper.selectRefundByOrderItemId(details.getDetailId());

        if (oConvertUtils.isNotEmpty(refunde)){
            if (refunde.getRefundSts()==1){
                return AppResult.error("A refund has been applied for this order and is pending review. No further refund applications can be submitted.");
            }else {
                //加判断xx
                if (refunde.getRefundSts()==3 || refunde.getRefundSts()==7){
                    return AppResult.error("The order request has been rejected, and no further applications can be submitted. For inquiries, please request platform intervention.");
                }

                refunde.setBuyerType(refundOrderDTO.getBuyerType());
                refunde.setBuyerValue(refundOrderDTO.getBuyerValue());
                refunde.setBuyerMsg(refundOrderDTO.getBuyerMsg());
                refunde.setPhotoFiles(refundOrderDTO.getPhotoFiles());
                refunde.setRefundSts(1);
                refunde.setApplyTime(new Date());
                ccairbagOrderRefundMapper.updateCcairbagOrderRefund(refunde);
                return new AppResult(refunde);
            }
        }else {
            CcairbagOrderRefund refund = new CcairbagOrderRefund();
            refund.setOrderItemId(details.getDetailId());
            refund.setRefundSn(orderIdUtil.genWithdrId());
            refund.setBuyerType(refundOrderDTO.getBuyerType());
            refund.setBuyerValue(refundOrderDTO.getBuyerValue());
            refund.setBuyerMsg(refundOrderDTO.getBuyerMsg());
            refund.setPhotoFiles(refundOrderDTO.getPhotoFiles());
            refund.setOrderAmount(details.getSubtotal());
            refund.setRefundAmount(details.getSubtotal());
            refund.setPayType(0);
            refund.setDeleted(0);
            refund.setUserId(orders.getUserId());
            refund.setApplyType(refundOrderDTO.getApplyType());
            refund.setRefundSts(1);
            refund.setReturnMoneySts(0);
            refund.setApplyTime(new Date());
            refund.setShopId(orders.getShopId());
            ccairbagOrderRefundMapper.insertCcairbagOrderRefund(refund);

            //如果订单状态 为1 则 直接退款  2 已发货 需要商家审批
            if (statusOld == 1) {
                CcairbagOrderPayment payment = ccairbagOrderPaymentMapper.selectCcairbagOrderPaymentByOrderId(orders.getOrderTotalId());
                refundPaypalMoeny(details,refund,payment);
            }
            //发送通知 商家
            addMessage(orders,"The user has applied for a refund.","The user has applied for a refund. Please go to the order list to check.",0,"6");
            return new AppResult(refund);
        }

    }

    @Override
    public AppResult refundOrderExt(RefundOrderDTO refundOrderDTO) {
        CcairbagOrderDetails details = ccairbagOrderDetailsMapper.selectCcairbagOrderDetailsByDetailId(refundOrderDTO.getDetailId());
        if (details.getOrderStatus()!=4 ){
            return AppResult.error("订单状态有误，订单只能是待退货");
        }
        CcairbagOrders orders = ccairbagOrdersMapper.selectCcairbagOrdersByOrderId(details.getOrderId());
        CcairbagOrderRefund refunde =ccairbagOrderRefundMapper.selectRefundByOrderItemId(details.getDetailId());
        if (oConvertUtils.isEmpty(refunde)){
            return AppResult.error("退款流程有误，无法找到退款申请信息");
        }

        if (refunde.getRefundSts()!=2){
            return AppResult.error("初次审核有误，无法提交退货运单号");
        }else {
            refunde.setRefundDvyFlowNum(refundOrderDTO.getRefundDvyFlowNum());
            refunde.setRefundSts(5);
            refunde.setApplyTime(new Date());
            refunde.setUpdateTime(new Date());
            ccairbagOrderRefundMapper.updateCcairbagOrderRefund(refunde);
            details.setOldStatus(details.getOrderStatus());
            details.setOrderStatus(5);
            details.setUpdateTime(new Date());
            ccairbagOrderDetailsMapper.updateCcairbagOrderDetails(details);

            addMessage(orders,"The user has submitted the return tracking number. ",
                    "The user has submitted the return tracking number. Please go to the order list to check.",
                    0,"8");

            return new AppResult(refunde);
        }

    }

    @Override
    public AppResult getRefundOrderType() {

        List<SysDictData> reList1 = DictUtils.getDictCache("refund_info1");
        List<SysDictData> reList2 = DictUtils.getDictCache("refund_info2");
        List<SysDictData> reList3 = DictUtils.getDictCache("refund_info3");

        Map<String, Object> mapList = new HashMap<>();
        mapList.put("refund_info1",reList1);
        mapList.put("refund_info2",reList2);
        mapList.put("refund_info3",reList3);

        return new AppResult(mapList);

    }

    @Transactional
    @Override
    public AppResult refundShopOrderSign(RefundOrderDTO refundOrderDTO) {
        /**
         *  同意 就退款
         */
        CcairbagOrderDetails details = ccairbagOrderDetailsMapper.selectCcairbagOrderDetailsByDetailId(refundOrderDTO.getDetailId());
        if (details.getOrderStatus()==8 || details.getOrderStatus()==10){
            return AppResult.error("订单状态有误，订单已完成，无法退款");
        }
        CcairbagOrderRefund refunde =ccairbagOrderRefundMapper.selectRefundByOrderItemId(details.getDetailId());
        CcairbagOrders orders = ccairbagOrdersMapper.selectCcairbagOrdersByOrderId(details.getOrderId());
        if (refundOrderDTO.getRefundSts()==2){
            details.setOldStatus(details.getOrderStatus());
            //同意 把订单状态改成  4-待退货
            details.setOrderStatus(4);
            details.setUpdateTime(new Date());
            ccairbagOrderDetailsMapper.updateCcairbagOrderDetails(details);
            refunde.setReturnMoneySts(2);
            refunde.setHandelTime(new Date());
            refunde.setRefundSts(2);
            refunde.setSellerMsg(refundOrderDTO.getSellerMsg());
            refunde.setUpdateTime(new Date());
            ccairbagOrderRefundMapper.updateCcairbagOrderRefund(refunde);
            //通知 买家 商家同意退货，让买家填写退货信息（退货运单信息）
            addMessage(orders,"the merchant has agreed to the refund.",
                    "For the product'"+orders.getShopName()+
                            "'the merchant has agreed to the refund. Please go to the order list to check and upload the return tracking number.",
                    1,"4");


        }else  if( refundOrderDTO.getRefundSts()==3){
            //拒绝
//            if (oConvertUtils.isEmpty(refundOrderDTO.getSellerMsg())){
//                return AppResult.error("拒绝退款必须填写拒绝理由");
//            }
            refunde.setHandelTime(new Date());
            refunde.setRefundSts(3);
            refunde.setSellerMsg(refundOrderDTO.getSellerMsg());
            refunde.setUpdateTime(new Date());
            ccairbagOrderRefundMapper.updateCcairbagOrderRefund(refunde);
            int old = details.getOrderStatus();
            if (details.getOldStatus()==7){
                details.setOrderStatus(7);
            } else if (details.getOldStatus()==2) {
                details.setOrderStatus(2);
            }
            details.setOldStatus(old);

            details.setUpdateTime(new Date());
            ccairbagOrderDetailsMapper.updateCcairbagOrderDetails(details);

            addMessage(orders,
                    "The merchant has rejected the refund",
                    "For the product'"+orders.getShopName()+"'The merchant rejected the refund. Please go to the order list to check.",
                    1,"5");

        }

        return AppResult.success("审批成功");
    }

    @Transactional
    @Override
    public AppResult refundShopOrderSignExt(RefundOrderDTO refundOrderDTO) {
        CcairbagOrderDetails details = ccairbagOrderDetailsMapper.selectCcairbagOrderDetailsByDetailId(refundOrderDTO.getDetailId());
        if (details.getOrderStatus()==8 || details.getOrderStatus()==10){
            return AppResult.error("订单状态有误，订单已完成，无法退款");
        }
        CcairbagOrderRefund refunde =ccairbagOrderRefundMapper.selectRefundByOrderItemId(details.getDetailId());
        CcairbagOrders orders = ccairbagOrdersMapper.selectCcairbagOrdersByOrderId(details.getOrderId());
        if (oConvertUtils.isEmpty(refunde)){
            return AppResult.error("退款流程有误，无法找到退款申请信息");
        }
        if (refundOrderDTO.getRefundSts()==6){
            //同意

            //通知 买家 商家同意退货，让买家填写退货信息（退货运单信息）
            addMessage(orders,"the merchant has agreed to the refund.",
                    "For the product'"+orders.getShopName()+
                            "'the merchant has agreed to the refund. Please go to the order list to check and upload the return tracking number.",
                    1,"4");
            refunde.setHandelTime(new Date());
            CcairbagOrderPayment payment = ccairbagOrderPaymentMapper.selectCcairbagOrderPaymentByOrderId(orders.getOrderTotalId());
            refundPaypalMoeny(details,refunde,payment);

        }else  if( refundOrderDTO.getRefundSts()==7){
            //拒绝
            refunde.setHandelTime(new Date());
            refunde.setRefundSts(7);
            refunde.setSellerMsg(refundOrderDTO.getSellerMsg());
            refunde.setUpdateTime(new Date());
            ccairbagOrderRefundMapper.updateCcairbagOrderRefund(refunde);
            details.setOldStatus(details.getOrderStatus());
            //拒绝 退回 已收货状态
            details.setOrderStatus(7);
            details.setUpdateTime(new Date());
            ccairbagOrderDetailsMapper.updateCcairbagOrderDetails(details);

            addMessage(orders,
                    "The merchant has rejected the refund",
                    "For the product'"+orders.getShopName()+"'The merchant rejected the refund. Please go to the order list to check.",
                    1,"5");
        }

        return AppResult.success("审批成功");

    }

    @Override
    public AppResult manualRefund(RefundOrderDTO refundOrderDTO) {
        CcairbagOrderDetails details = ccairbagOrderDetailsMapper.selectCcairbagOrderDetailsByDetailId(refundOrderDTO.getDetailId());
        if (details.getOrderStatus()!=6 ){
            return AppResult.error("订单状态有误，只能是平台介入状态");
        }
        CcairbagOrderRefund refunde =ccairbagOrderRefundMapper.selectRefundByOrderItemId(details.getDetailId());
        CcairbagOrders orders = ccairbagOrdersMapper.selectCcairbagOrdersByOrderId(details.getOrderId());
        if (oConvertUtils.isEmpty(refunde)){
            return AppResult.error("退款流程有误，无法找到退款申请信息");
        }
        refunde.setRefundAmount(refundOrderDTO.getRefundAmount());
        CcairbagOrderPayment payment = ccairbagOrderPaymentMapper.selectCcairbagOrderPaymentByOrderId(orders.getOrderTotalId());
        refundPaypalMoeny(details,refunde,payment);

        return new AppResult("平台介入，退款成功");
    }

    @Override
    public AppResult platformIntervention(RefundOrderDTO refundOrderDTO) {
        CcairbagOrderDetails details = ccairbagOrderDetailsMapper.selectCcairbagOrderDetailsByDetailId(refundOrderDTO.getDetailId());
        CcairbagOrderRefund refunde =ccairbagOrderRefundMapper.selectRefundByOrderItemId(details.getDetailId());
        if (oConvertUtils.isEmpty(refunde)){
            return AppResult.error("There is an error in the refund process, and the refund application information cannot be found.");
        }
        if (refunde.getRefundSts()==9){
            return AppResult.error("The platform has already intervened, so no further applications can be submitted.");
        }
        if (refunde.getRefundSts()!=7 && refunde.getRefundSts()!=3){
            return AppResult.error("There is an error in the refund process, and the merchant has not rejected (the refund application).");
        }
        details.setOldStatus(details.getOrderStatus());
        details.setOrderStatus(6);
        details.setUpdateTime(new Date());
        ccairbagOrderDetailsMapper.updateCcairbagOrderDetails(details);
        refunde.setRefundSts(8);
        refunde.setBuyerMsgExt(refundOrderDTO.getBuyerMsgExt());
        refunde.setPhotoFilesExt(refundOrderDTO.getPhotoFilesExt());
        refunde.setUpdateTime(new Date());
        ccairbagOrderRefundMapper.updateCcairbagOrderRefund(refunde);
        return new AppResult("The platform intervention submission has been successful");
    }

    @Override
    @Transactional
    public AppResult refundShopOrder(RefundOrderDTO refundOrderDTO) {
        //商家取消订单 这是直接退款
        CcairbagOrderDetails details = ccairbagOrderDetailsMapper.selectCcairbagOrderDetailsByDetailId(refundOrderDTO.getDetailId());
        if (details.getOrderStatus()==8 || details.getOrderStatus()==10){
            return AppResult.error("订单状态有误，订单已完成，无法退款");
        }
        if (details.getOrderStatus()==2 ){
            return AppResult.error("已发货商品，无法退款");
        }
//        details.setOrderStatus(10);
//        details.setUpdateTime(new Date());
//        ccairbagOrderDetailsMapper.updateCcairbagOrderDetails(details);

        CcairbagOrders orders = ccairbagOrdersMapper.selectCcairbagOrdersByOrderId(details.getOrderId());
        CcairbagOrderRefund refunde =ccairbagOrderRefundMapper.selectRefundByOrderItemId(details.getDetailId());

        CcairbagOrderPayment payment = ccairbagOrderPaymentMapper.selectCcairbagOrderPaymentByOrderId(orders.getOrderTotalId());

        if (oConvertUtils.isNotEmpty(refunde)){
            return AppResult.error("该订单已申请退款,商家不可再次提交退款申请");
        }else {
            CcairbagOrderRefund refund = new CcairbagOrderRefund();
            refund.setOrderItemId(details.getDetailId());
            refund.setRefundSn(orderIdUtil.genWithdrId());
            refund.setBuyerType(refundOrderDTO.getBuyerType());
            refund.setBuyerValue(refundOrderDTO.getBuyerValue());
            refund.setSellerMsg(refundOrderDTO.getSellerMsg());
            refund.setPhotoFiles(refundOrderDTO.getPhotoFiles());
            refund.setOrderAmount(details.getSubtotal());
            refund.setPayType(0);
            refund.setDeleted(0);
            refund.setRefundAmount(details.getSubtotal());
            refund.setUserId(orders.getUserId());
            refund.setApplyType(refundOrderDTO.getApplyType());
            refund.setRefundSts(4);
            refund.setReturnMoneySts(0);
            refund.setApplyTime(new Date());
            refund.setShopId(orders.getShopId());
            ccairbagOrderRefundMapper.insertCcairbagOrderRefund(refund);
            // 调用退款
            refundPaypalMoenyExt(details,refund,payment);

            //发送通知 买家
            addMessage(orders,"The merchant has applied for a refund",
                    "For the product'"+orders.getShopName()+"'The merchant has initiated a refund. Please go to the order list to check.",
                    1,"9");
            return new AppResult(refund);
        }
    }
    private void refundPaypalMoenyExt(CcairbagOrderDetails details, CcairbagOrderRefund refund, CcairbagOrderPayment payment) {
        if (payment.getPayType()==0){
            PayPalApiConfig config = getConfig();
            IJPayHttpResponse captureQueryResponse = PayPalApi.queryOrder(config,payment.getBizPayNo());
            log.info("refund data：" + captureQueryResponse);
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                JsonNode event = objectMapper.readTree(captureQueryResponse.getBody());
                String captureId = event.path("purchase_units")
                        .get(0).path("payments").path("captures")
                        .get(0).path("id").asText();

                Map<String, Object> map = new HashMap<>();
                map.put("invoice_id", details.getDetailId());
                map.put("note_to_payer", refund.getSellerMsg());

                Map<String, String> amount = new HashMap<>();
                amount.put("value", refund.getRefundAmount().toPlainString());

                amount.put("value", refund.getRefundAmount().toPlainString());
                amount.put("currency_code", "USD");

                map.put("amount", amount);

                String data = JSONUtil.toJsonStr(map);
                log.info("refund data：" + data);
                // paypal调用退款
                IJPayHttpResponse resData = PayPalApi.refund(config, captureId, data);

                JsonNode eventx = objectMapper.readTree(resData.getBody());
                log.info("eventx data：" + eventx);

                if ("COMPLETED".equals(eventx.path("status").asText())){
                    refundSuccessExt(details,refund,payment);

                }

            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }else if (payment.getPayType()==1){
            //支付宝 退款
            try {
                AlipayClient alipayClient = alipayUtil.buildAliClient();
                AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
                AlipayTradeRefundModel model=new AlipayTradeRefundModel();
                // 设置商户订单号
                model.setOutRequestNo(String.valueOf(details.getDetailId()));
                // 设置支付宝交易号
                model.setTradeNo(payment.getBizPayNo());
                model.setRefundAmount(refund.getRefundAmount().toPlainString());
                model.setRefundReason("平台介入退款");
                request.setBizModel(model);
                AlipayTradeRefundResponse response = alipayClient.certificateExecute(request);
                if (response.isSuccess()) {
                    System.out.println("调用成功");
                } else {
                    System.out.println("调用失败");
                    log.info("调用失败,原因:" + response.getSubMsg());
                }

            }catch (AlipayApiException e){
                throw new RuntimeException(e);
            }

        }else if (payment.getPayType()==2){

            Stripe.apiKey = visapayConfig.getApiKey();
            BigDecimal moeny = refund.getRefundAmount();
            RefundCreateParams params = RefundCreateParams.builder().setPaymentIntent(payment.getBizPayNo())
                    .setAmount(moeny.longValue()).build();
            try {
                Refund visaRefund = Refund.create(params);
                String json = JSON.toJSONString(visaRefund);
                log.info("visa退款返回结果：" + json);
            } catch (StripeException e) {
                throw new RuntimeException(e);
            }
        }

    }
    private void refundPaypalMoeny(CcairbagOrderDetails details, CcairbagOrderRefund refund, CcairbagOrderPayment payment) {
        CcairbagOrdersTotal total = ccairbagOrdersTotalMapper.queryById(payment.getOrderId());
        CcairbagOrders orders = ccairbagOrdersMapper.selectCcairbagOrdersByOrderId(details.getOrderId());
        /**
         * 100（店铺订单总额）/150（支付订单总额）*9.5（手续费）=6.3333  payFee 是这笔退款订单的手续费  要加进去 退款金额中
         */
        BigDecimal payFee = orders.getFinalAmount().divide(total.getTotalAmount(), 2, BigDecimal.ROUND_HALF_UP).multiply(total.getPaymentFee());
        payFee = payFee.setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal  refundtotal =  details.getSubtotal().add(payFee);
        if (payment.getPayType()==0){

            PayPalApiConfig config = getConfig();
            IJPayHttpResponse captureQueryResponse = PayPalApi.queryOrder(config,payment.getBizPayNo());
            log.info("refund data：" + captureQueryResponse);
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                JsonNode event = objectMapper.readTree(captureQueryResponse.getBody());
                String captureId = event.path("purchase_units")
                        .get(0).path("payments").path("captures")
                        .get(0).path("id").asText();

                Map<String, Object> map = new HashMap<>();
                map.put("invoice_id", details.getDetailId());

                Map<String, String> amount = new HashMap<>();

                amount.put("value", String.valueOf(refundtotal));
                amount.put("currency_code", "USD");

                map.put("amount", amount);

                String data = JSONUtil.toJsonStr(map);
                log.info("refund data：" + data);
                // paypal调用退款
                IJPayHttpResponse resData = PayPalApi.refund(config, captureId, data);

                JsonNode eventx = objectMapper.readTree(resData.getBody());
                log.info("eventx data：" + eventx);

                if ("COMPLETED".equals(eventx.path("status").asText())){
                    refundSuccess(details,refund,payment);

                }

            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }else if (payment.getPayType()==1){
            //支付宝 退款
            try {
                AlipayClient alipayClient = alipayUtil.buildAliClient();
                AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();

                AlipayTradeRefundModel model=new AlipayTradeRefundModel();


                // 设置商户订单号
                model.setOutRequestNo(String.valueOf(details.getDetailId()));
                // 设置支付宝交易号
                model.setTradeNo(payment.getBizPayNo());

                model.setRefundAmount(String.valueOf(refundtotal));



                // 设置退款原因说明
                model.setRefundReason("正常退款");


                request.setBizModel(model);

                AlipayTradeRefundResponse response = alipayClient.certificateExecute(request);

                if (response.isSuccess()) {
                    if ("Y".equals(response.getFundChange())){
                        refundSuccess(details,refund,payment);
                    }
                    System.out.println("调用成功");
                } else {
                    System.out.println("调用失败");
                    log.info("调用失败,原因:" + response.getSubMsg());

                }

            }catch (AlipayApiException e){
                throw new RuntimeException(e);
            }

        }else if (payment.getPayType()==2){
            //visa 退款

            Stripe.apiKey = visapayConfig.getApiKey();
            BigDecimal moeny = details.getSubtotal().multiply(new BigDecimal("100"));
//            refundtotal = details.getSubtotal().add(payFee);
//                model.setRefundAmount(String.valueOf(refundtotal));
            RefundCreateParams params = RefundCreateParams.builder().setPaymentIntent(payment.getBizPayNo())
                    .setAmount(moeny.longValue()).build();
            try {
                Refund visaRefund = Refund.create(params);
                String json = JSON.toJSONString(visaRefund);
                log.info("visa退款返回结果：" + json);
            } catch (StripeException e) {
                throw new RuntimeException(e);
            }
        }




    }


    /**
     *
     * @param orders
     * @param title
     * @param content
     * @param type 0 : 发送给商家 1：发送给买家
     * @param isAgree 4 退款通知 商家同意 ，5 退款通知 商家拒绝 6 退款通知 买家申请  ,7 商家填写运单号 通知 买家,8:用户提交退货运单号
     */
    private void addMessage(CcairbagOrders orders,String title,String content,Integer type,String isAgree){
        CcairbagNotification ccairbagNotification = new CcairbagNotification();
        Map<String,Object> params = new HashMap<>();
        params.put("content",content);
        Gson gson = new Gson();
        String contentJson  = gson.toJson(params);
        ccairbagNotification.setContent(contentJson);
        ccairbagNotification.setTitle(title);
        ccairbagNotification.setNotificationType(isAgree);
        ccairbagNotification.setIsBroadcast(1);
        ccairbagNotification.setDeleted(0);
        ccairbagNotificationMapper.insertCcairbagNotification(ccairbagNotification);

        CcairbagNotificationUser ccairbagNotificationUser = new CcairbagNotificationUser();
        ccairbagNotificationUser.setNotificationId(ccairbagNotification.getNotificationId());
        if (type==0){
            ccairbagNotificationUser.setUserId(orders.getShopUserId());
        }else {
            ccairbagNotificationUser.setUserId(orders.getUserId());
        }
        ccairbagNotificationUser.setReadStatus(0);
        ccairbagNotificationUser.setDeleted(0);
        ccairbagNotificationUserMapper.insertCcairbagNotificationUser(ccairbagNotificationUser);
    }

    private void refundSuccess(CcairbagOrderDetails details, CcairbagOrderRefund refund, CcairbagOrderPayment payment) {
        //退款成功 后 payment 减去退款的金额 还有减去 商品数量  还有改子订单本身状态
        BigDecimal payMoney =  payment.getPayAmount().subtract(refund.getRefundAmount());
        payment.setPayAmount(payMoney);
        payment.setPayStatus(2);
        payment.setProductNums(payment.getProductNums()- details.getQuantity());
        ccairbagOrderPaymentMapper.updateCcairbagOrderPayment(payment);
        details.setOldStatus(details.getOrderStatus());
        details.setOrderStatus(10);
        ccairbagOrderDetailsMapper.updateCcairbagOrderDetails(details);

        refund.setRefundSts(6);
        refund.setReturnMoneySts(1);
        refund.setRefundTime(new Date());
        ccairbagOrderRefundMapper.updateCcairbagOrderRefund(refund);
        //加回库存 数据库
        ccairbagProductsMapper.updateStockExt(details.getProdId(), details.getQuantity());
        //加回库存 redis
//        redisStockService.releaseStock(details.getProdId(), details.getQuantity());
        // 支付成功，收货前的钱
        CcairbagOrders orders = ccairbagOrdersMapper.selectCcairbagOrdersByOrderId(details.getOrderId());
        CcairbagShops shops = ccairbagShopsMapper.selectCcairbagShopsByShopId(orders.getShopId());
        shops.setWalletOrderIng(shops.getWalletOrderIng().subtract(details.getSubtotal()));
        ccairbagShopsMapper.updateCcairbagShops(shops);
    }

    private void refundSuccessExt(CcairbagOrderDetails details, CcairbagOrderRefund refund, CcairbagOrderPayment payment) {
        //退款成功 后 payment 减去退款的金额 还有减去 商品数量  还有改子订单本身状态
        BigDecimal payMoney =  payment.getPayAmount().subtract(refund.getRefundAmount());
        payment.setPayAmount(payMoney);
        payment.setPayStatus(2);
        payment.setProductNums(payment.getProductNums()- details.getQuantity());
        ccairbagOrderPaymentMapper.updateCcairbagOrderPayment(payment);
        details.setOrderStatus(10);
        ccairbagOrderDetailsMapper.updateCcairbagOrderDetails(details);

        refund.setRefundSts(8);
        refund.setReturnMoneySts(1);
        refund.setRefundTime(new Date());
        ccairbagOrderRefundMapper.updateCcairbagOrderRefund(refund);
        //加回库存 数据库
        ccairbagProductsMapper.updateStockExt(details.getProdId(), details.getQuantity());
        //加回库存 redis
//        redisStockService.releaseStock(details.getProdId(), details.getQuantity());
        // 支付成功，收货前的钱
        CcairbagOrders orders = ccairbagOrdersMapper.selectCcairbagOrdersByOrderId(details.getOrderId());
        CcairbagShops shops = ccairbagShopsMapper.selectCcairbagShopsByShopId(orders.getShopId());
        shops.setWalletOrderIng(shops.getWalletOrderIng().subtract(refund.getOrderAmount()));
        ccairbagShopsMapper.updateCcairbagShops(shops);
    }


    public PayPalApiConfig getConfig() {
        PayPalApiConfig config = new PayPalApiConfig();
        config.setClientId(paypalConfig.getClientId());
        config.setSecret(paypalConfig.getSecret());
        config.setSandBox(paypalConfig.isSandBox());
        config.setDomain(paypalConfig.getDomain());
        PayPalApiConfigKit.setThreadLocalApiConfig(config);
        return config;
    }


    public AppResult createTrack(String trackingNumber, String courierCode,String orderNumber) {
        OkHttpClient client = new OkHttpClient();
        Map<String, String> params = new HashMap<>();
        params.put("tracking_number", trackingNumber);
        params.put("courier_code", courierCode);
        params.put("order_number", orderNumber);
        Gson gson = new Gson ();
        String json = gson.toJson (params);

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType,json );
        Request request = new Request.Builder()
                .url("https://api.51tracking.com/v4/trackings/create")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Tracking-Api-Key", visapayConfig.getTrackingApi())
                .build();

        try {
            Response response = client.newCall(request).execute();
            return new AppResult(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public AppResult getDvyFlowInfo(Long detailId) {
        CcairbagOrderDetails details = ccairbagOrderDetailsMapper.selectCcairbagOrderDetailsByDetailId(detailId);
        if (oConvertUtils.isEmpty(details)) {
            throw new RuntimeException("订单不存在");
        }
        if (oConvertUtils.isEmpty(details.getDvyFlowNum())){
            throw new RuntimeException("物流单号不存在");
        }
        try {
            SslUtils.ignoreSsl();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        CcairbagOrders orders = ccairbagOrdersMapper.selectCcairbagOrdersByOrderId(details.getOrderId());
        CcairbagUserAddr addr = ccairbagUserAddrMapper.selectCcairbagUserAddrByAddrId(orders.getAddrId());
        String lang = "en";
        if ("China".equals(addr.getCountry())){
            lang = "zh";
        }
        String url = "https://api.51Tracking.com/v4/trackings/get?tracking_numbers="+details.getDvyFlowNum()
                +"&courier_code="+details.getLogisticsCode()+"&lang="+lang;
        Map<String, String> header = new HashMap<>(4);
        header.put("Tracking-Api-Key", visapayConfig.getTrackingApi());
        header.put("Content-Type", "application/json");
        String cc = HttpUtils.get(url, header, null);
        JSONObject jsonObject = JSON.parseObject(cc);

        return new AppResult(jsonObject);
    }

    @Override
    public AppResult getDvyCompanyList() {

        try {
            SslUtils.ignoreSsl();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String url = "https://api.51Tracking.com/v4/couriers/all";
        Map<String, String> header = new HashMap<>(4);
        header.put("Tracking-Api-Key", visapayConfig.getTrackingApi());
        header.put("Content-Type", "application/json");
        String cc = HttpUtils.get(url, header, null);
        JSONObject jsonObject = JSON.parseObject(cc);
        return new AppResult(jsonObject);
    }

    @Override
    public AppResult signOrder7Day(String detailId) {
        CcairbagOrderDetails details = ccairbagOrderDetailsMapper.selectCcairbagOrderDetailsByDetailId(Long.valueOf(detailId));
        if (oConvertUtils.isEmpty(details)) {
            return AppResult.error("订单不存在");
        }

        if (details.getOrderStatus() != 2) {
            return AppResult.error("订单不是未发货状态，不能设置收货");
        }
        //正常履约链路（无任何退款 / 售后）：0-待付款 → 1-待发货（支付成功） → 2-待收货（卖家填运单号） → 7-已收货（买家确认） → 8-已完成（订单闭环）

        //已收货， 后续3天内 把状态更改成 8 已完成
        details.setOrderStatus(7);//7-已收货（买家确认）
        details.setUpdateTime(new Date());
        ccairbagOrderDetailsMapper.updateCcairbagOrderDetails(details);
        // 加个队列 或者定时器  5天后  走一下的逻辑 ， 并把订单改成已完成
        systemCanceOrder5DayTask(detailId);


        return new AppResult("success");
    }

    @Override
    public AppResult cancelorder5Day(String detailId) {
        CcairbagOrderDetails details = ccairbagOrderDetailsMapper.selectCcairbagOrderDetailsByDetailId(Long.valueOf(detailId));
        details.setOrderStatus(8);
        ccairbagOrderDetailsMapper.updateCcairbagOrderDetails(details);

        //给商家店铺 钱包添加 对应金额
        CcairbagOrders orders = ccairbagOrdersMapper.selectCcairbagOrdersByOrderId(details.getOrderId());
        CcairbagShops shops = ccairbagShopsMapper.selectCcairbagShopsByShopId(orders.getShopId());
        CcairbagIncomeRecords records = new CcairbagIncomeRecords();
        records.setOrderId(details.getDetailId());
        if (oConvertUtils.isNotEmpty(details.getFreightAmount()) ) {
            records.setMoney(orders.getIncomeMoney().add(details.getFreightAmount()));
        }else {
            records.setMoney(orders.getIncomeMoney());
        }
        records.setShopId(shops.getShopId());
        ccairbagIncomeRecordsMapper.insertCcairbagIncomeRecords(records);
        //walletBalance 加金额  walletOrderIng 减金额
        shops.setWalletBalance(shops.getWalletBalance().add(records.getMoney()));
        shops.setWalletOrderIng(shops.getWalletOrderIng().subtract(records.getMoney()));
        ccairbagShopsMapper.updateCcairbagShops(shops);

        CcairbagProducts products = ccairbagProductsMapper.selectCcairbagProductsByProductId(details.getProdId());
        products.setSalesVolume(products.getSalesVolume()+details.getQuantity());
        return AppResult.error("订单流程已结束");

    }


    private void systemCanceOrder5DayTask(String detailId) throws AmqpException {
        MqMessage<String> m = new MqMessage<>();
        m.setQueueType(MQProperties.order_sign3Day_EXCHANGE_NAME);
        m.setBody(detailId);
        messageSender.sendMessageWithDelay(MQProperties.order_sign3Day_EXCHANGE_NAME,
                MQProperties.order_sign3Day_ROUTE_KEY,m , Long.valueOf(5*24*60*60*1000));


    }
    private void systemSignOrderTask(String detailId) throws AmqpException {
        MqMessage<String> m = new MqMessage<>();
        m.setQueueType(MQProperties.order_signOrder_EXCHANGE_NAME);
        m.setBody(detailId);
        messageSender.sendMessageWithDelay(MQProperties.order_signOrder_EXCHANGE_NAME,
                MQProperties.order_signOrder_ROUTE_KEY,m , Long.valueOf(7*24*60*60*1000));

//        messageSender.sendMessageWithDelay(MQProperties.order_signOrder_EXCHANGE_NAME,
//                MQProperties.order_signOrder_ROUTE_KEY,m , Long.valueOf(10*1000));

    }




}
