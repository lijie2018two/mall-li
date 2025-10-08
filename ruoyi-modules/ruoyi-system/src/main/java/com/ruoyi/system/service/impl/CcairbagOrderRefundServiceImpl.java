package com.ruoyi.system.service.impl;

import cn.hutool.json.JSONUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.ijpay.core.IJPayHttpResponse;
import com.ijpay.paypal.PayPalApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ijpay.paypal.PayPalApiConfig;
import com.ijpay.paypal.PayPalApiConfigKit;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.system.api.domain.ccairbag.*;
import com.ruoyi.system.domain.PayPalConfig;
import com.ruoyi.system.mapper.*;
import com.ruoyi.system.service.ICcairbagOrderRefundService;
import com.ruoyi.system.utils.AlipayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单退款Service业务层处理
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Service
@Slf4j
public class CcairbagOrderRefundServiceImpl implements ICcairbagOrderRefundService 
{
    @Resource
    private CcairbagOrderRefundMapper ccairbagOrderRefundMapper;
    @Resource
    private CcairbagOrderDetailsMapper ccairbagOrderDetailsMapper;
    @Resource
    private CcairbagOrderPaymentMapper ccairbagOrderPaymentMapper;
    @Autowired
    private PayPalConfig paypalConfig;
    @Autowired
    private AlipayUtil alipayUtil;
    @Resource
    private CcairbagOrdersTotalMapper ccairbagOrdersTotalMapper;
    @Resource
    private CcairbagOrdersMapper ccairbagOrdersMapper;
    @Resource
    private CcairbagProductsMapper ccairbagProductsMapper;
    @Resource
    private CcairbagShopsMapper ccairbagShopsMapper;

    @Override
    public List<CcairbagOrderRefund> selectCcairbagOrderRefundList(CcairbagOrderRefund ccairbagOrderRefund)
    {
        return ccairbagOrderRefundMapper.selectCcairbagOrderRefundList(ccairbagOrderRefund);
    }



    @Override
    public AjaxResult handleSellerProblem(Long refundId) {
        CcairbagOrderRefund refund = ccairbagOrderRefundMapper.selectCcairbagOrderRefundByRefundId(refundId);
        CcairbagOrderDetails details = ccairbagOrderDetailsMapper.selectCcairbagOrderDetailsByDetailId(refund.getOrderItemId());
        CcairbagOrders orders = ccairbagOrdersMapper.selectCcairbagOrdersByOrderId(details.getOrderId());
        //走第三方退款接口
        CcairbagOrderPayment payment = ccairbagOrderPaymentMapper.selectCcairbagOrderPaymentByOrderId(orders.getOrderTotalId());
        refundPaypalMoeny(details,refund,payment);
        return AjaxResult.success("平台介入，已强制退款");
    }

    @Override
    public AjaxResult handleBuyerProblem(Long refundId) {
        CcairbagOrderRefund refund = ccairbagOrderRefundMapper.selectCcairbagOrderRefundByRefundId(refundId);
        CcairbagOrderDetails details = ccairbagOrderDetailsMapper.selectCcairbagOrderDetailsByDetailId(refund.getOrderItemId());
        int old = details.getOldStatus();
        int nowStatus = details.getOrderStatus();
        if (old==7){
            details.setOrderStatus(7);
        }else if (old==2){
            details.setOrderStatus(2);
        }else {
            details.setOrderStatus(7);
        }
        details.setOldStatus(nowStatus);
        ccairbagOrderDetailsMapper.updateCcairbagOrderDetails(details);
        refund.setRefundSts(9);
        refund.setRefundTime(new Date());
        ccairbagOrderRefundMapper.updateCcairbagOrderRefund(refund);
        return AjaxResult.success("平台介入，订单状态已退回");
    }

    @Transactional
    public void refundPaypalMoeny(CcairbagOrderDetails details, CcairbagOrderRefund refund, CcairbagOrderPayment payment) {

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


//                JSONObject json = new JSONObject();
//                // 传入退款金额
//                JSONObject amount = new JSONObject();
//                amount.put("value",refundtotal.toString());
//                amount.put("currency_code","USD");
//                json.put("amount",amount);

                Map<String, Object> map = new HashMap<>();
                map.put("invoice_id", details.getDetailId().toString());

                Map<String, String> amount = new HashMap<>();
                amount.put("value", refundtotal.toString());

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

        }

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

        refund.setRefundSts(9);
        refund.setReturnMoneySts(1);
        refund.setRefundTime(new Date());
        ccairbagOrderRefundMapper.updateCcairbagOrderRefund(refund);
        //加回库存 数据库
        ccairbagProductsMapper.updateStock(details.getProdId(), details.getQuantity());
        //加回库存 redis
//        redisStockService.releaseStock(details.getProdId(), details.getQuantity());
        // 支付成功，收货前的钱
        CcairbagOrders orders = ccairbagOrdersMapper.selectCcairbagOrdersByOrderId(details.getOrderId());
        CcairbagShops shops = ccairbagShopsMapper.selectCcairbagShopsByShopId(orders.getShopId());
        shops.setWalletOrderIng(shops.getWalletOrderIng().subtract(details.getSubtotal()));
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
}
