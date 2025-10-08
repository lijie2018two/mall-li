package com.ccairbag.api.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.shaded.com.google.gson.Gson;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.ccairbag.api.config.AlipayCertConfig;
import com.ccairbag.api.config.VisapayConfig;
import com.ccairbag.api.controller.WebSocketMessageServer;
import com.ccairbag.api.domain.dto.CreateOrderDto;
import com.ccairbag.api.domain.dto.PayPalConfig;
import com.ccairbag.api.domain.dto.ShopCartDto;
import com.ccairbag.api.domain.dto.ShopCartDtoExt;
import com.ccairbag.api.mapper.*;
import com.ccairbag.api.service.ICcairbagOrdersService;
import com.ccairbag.api.utils.AlipayUtil;
import com.ccairbag.api.utils.EmailUtils;
import com.ccairbag.api.utils.RedisStockService;
import com.ddty.common.mq.service.MessageSender;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ijpay.core.IJPayHttpResponse;
import com.ijpay.paypal.PayPalApi;
import com.ijpay.paypal.PayPalApiConfig;
import com.ijpay.paypal.PayPalApiConfigKit;
import com.ruoyi.common.core.domain.MQProperties;
import com.ruoyi.common.core.domain.MqMessage;
import com.ruoyi.common.core.utils.PageUtils;
import com.ruoyi.common.core.utils.oConvertUtils;
import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.common.core.web.page.PageDataInfo;
import com.ruoyi.common.redis.service.OrderIdUtil;
import com.ruoyi.common.redis.service.RedisService;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.domain.ccairbag.*;
import com.ruoyi.system.api.domain.ccairbag.dto.LogisticsInfoDTO;
import com.ruoyi.system.api.model.LoginAppUser;
import com.ruoyi.system.api.util.date.DateUtils;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 订单Service业务层处理
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Slf4j
@Service
public class CcairbagOrdersServiceImpl implements ICcairbagOrdersService {
    @Resource
    private CcairbagOrdersMapper ccairbagOrdersMapper;
    @Resource
    private CcairbagProductsMapper ccairbagProductsMapper;
    @Resource
    private CcairbagUserAddrMapper ccairbagUserAddrMapper;
    @Resource
    private CcairbagUsersMapper ccairbagUsersMapper;
    @Autowired
    private OrderIdUtil orderIdUtil;
    @Autowired
    private RedisStockService redisStockService;
    @Autowired
    private RedisService redisService;
    @Resource
    private CcairbagShopsMapper ccairbagShopsMapper;
    @Resource
    private CcairbagOrderDetailsMapper ccairbagOrderDetailsMapper;

    @Autowired
    private PayPalConfig paypalConfig;
    @Resource
    private CcairbagOrderPaymentMapper ccairbagOrderPaymentMapper;
    @Resource
    private MessageSender messageSender;

    @Autowired
    private EmailUtils emailUtils;
    @Resource
    private CcairbagPromotionActivitiesMapper ccairbagPromotionActivitiesMapper;
    @Resource
    private CcairbagOrdersTotalMapper ccairbagOrdersTotalMapper;

    @Resource
    private CcairbagSetscaleMapper ccairbagSetscaleMapper;

    @Resource
    private CcairbagOrderRefundMapper ccairbagOrderRefundMapper;
    @Resource
    private CcairbagIncomeRecordsMapper ccairbagIncomeRecordsMapper;
    @Resource
    private CcairbagNegotiationRecordMapper ccairbagNegotiationRecordMapper;
    @Autowired
    private AlipayUtil alipayUtil;

    @Autowired
    private AlipayCertConfig alipayCertConfig;

    @Resource
    private CcairbagNotificationMapper ccairbagNotificationMapper;
    @Resource
    private CcairbagNotificationUserMapper ccairbagNotificationUserMapper;

    @Autowired
    private VisapayConfig visapayConfig;
    @Resource
    private CcairbagConversationsMapper ccairbagConversationsMapper;

    //发送消息给卖家 有新订单
    @Transactional
    public void sendMessagetToSeller(CcairbagOrders orders) {
        CcairbagOrderDetails details = new CcairbagOrderDetails();
        details.setOrderId(orders.getOrderId());
        List<CcairbagOrderDetails> ccairbagOrderDetails = ccairbagOrderDetailsMapper.selectCcairbagOrderDetailsList(details);
        CcairbagUserAddr addr = ccairbagUserAddrMapper.selectCcairbagUserAddrByAddrId(orders.getAddrId());

        for (CcairbagOrderDetails ccairbagOrderDetail : ccairbagOrderDetails){
            CcairbagNotification ccairbagNotification = new CcairbagNotification();
            Map<String,Object> params = new HashMap<>();
            params.put("productName",ccairbagOrderDetail.getProdName());
            params.put("houseNumber",addr.getHouseNumber());
            params.put("getStreet",addr.getStreet());
            params.put("city",addr.getCity());
            params.put("postCode",addr.getPostCode());

            params.put("stateProvince",addr.getStateProvince());
            params.put("country",addr.getCountry());
            Gson gson = new Gson();
            String content  = gson.toJson(params);
            ccairbagNotification.setContent(content);
            ccairbagNotification.setTitle("You have a new order.");
            ccairbagNotification.setNotificationType("3");
            ccairbagNotification.setIsBroadcast(1);
            ccairbagNotification.setDeleted(0);
            ccairbagNotificationMapper.insertCcairbagNotification(ccairbagNotification);

            CcairbagNotificationUser ccairbagNotificationUser = new CcairbagNotificationUser();
            ccairbagNotificationUser.setNotificationId(ccairbagNotification.getNotificationId());
            ccairbagNotificationUser.setUserId(orders.getShopUserId());
            ccairbagNotificationUser.setReadStatus(0);
            ccairbagNotificationUser.setDeleted(0);
            ccairbagNotificationUserMapper.insertCcairbagNotificationUser(ccairbagNotificationUser);
        }

        ConcurrentHashMap<String, WebSocketMessageServer> map = WebSocketMessageServer.getWebSocketMap();
        Gson gson = new Gson();
        List<Map<String, Object>> maps =ccairbagConversationsMapper.getUserUnreadMessageCount(orders.getShopUserId());
        int i = ccairbagNotificationMapper.getUserNotificationUnreadCount(orders.getShopUserId());
        Map<String,Object> map1 = new HashMap<>();
        map1.put("user_id",orders.getShopUserId());
        map1.put("user_type","notification");
        map1.put("unread_count",i);
        maps.add(map1);
        WebSocketMessageServer webSocket = map.get(String.valueOf(orders.getShopUserId()));
        if (webSocket != null) {
            webSocket.sendMessage(gson.toJson(maps));
        } else {
            log.warn("卖家 WebSocket 连接不存在，用户ID: {}", orders.getShopUserId());
        }

    }
    @Override
    @Transactional
    public AppResult createOrder(CreateOrderDto createOrderDto) {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        Long userId = loginAppUser.getCcairbagUsers().getUserId();
        List<CcairbagUserAddr> addrList = ccairbagUserAddrMapper.selectCcairbagUserAddrByUserIdExt(userId);
        if (addrList.isEmpty()) {
            throw new RuntimeException("Please add a shipping address first.");
        }
        CcairbagProducts products = ccairbagProductsMapper.selectCcairbagProductsByProductIdExt(createOrderDto.getProductId());
        if (oConvertUtils.isEmpty(products)) {
            throw new RuntimeException("Product does not exist.");
        }



        CcairbagShops shops = ccairbagShopsMapper.selectCcairbagShopsByShopId(products.getShopId());
        if (oConvertUtils.isEmpty(shops)) {
            throw new RuntimeException("Seller does not exist.");
        }



        CcairbagOrders orders = new CcairbagOrders();
        //先算出 订单表 和订单详情表的 金额，小计
        BigDecimal totalAmount = new BigDecimal(BigInteger.ZERO);
        orders.setOrderNumber(orderIdUtil.genOrderId());

        orders.setReduceAmount(BigDecimal.ZERO);

        orders.setTotalAmount(new BigDecimal(0));
        orders.setFinalAmount(new BigDecimal(0));
        orders.setAddrId(addrList.get(0).getAddrId());
        orders.setOrderStatus(0);
        orders.setUserId(userId);
        orders.setUserDelete(0);
        orders.setShopDelete(0);
        orders.setShopId(shops.getShopId());
        orders.setShopName(shops.getShopName());
        orders.setShopUserId(shops.getUserId());
        orders.setRemarks(createOrderDto.getRemarks());
        orders.setProductNums(createOrderDto.getQuantity());
        orders.setDeleted(0);
        ccairbagOrdersMapper.insertCcairbagOrders(orders);

        // 订单详情表
        CcairbagOrderDetails orderDetails = new CcairbagOrderDetails();
        orderDetails.setOrderId(orders.getOrderId());
        orderDetails.setOrderNumber(orders.getOrderNumber());
        if (oConvertUtils.isNotEmpty(createOrderDto.getNegotiablePrice())){
            totalAmount = createOrderDto.getNegotiablePrice().multiply(BigDecimal.valueOf(createOrderDto.getQuantity()));
            orderDetails.setNegotiablePrice(createOrderDto.getNegotiablePrice());
        }else {
            BigDecimal price = BigDecimal.ZERO;
            if (oConvertUtils.isNotEmpty(products.getDiscount())) {
                // 如果存在折扣，使用折扣价：原价 * (discount / 100)
                BigDecimal discountRate = new BigDecimal(products.getDiscount()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                price = products.getPrice().multiply(discountRate).setScale(2, RoundingMode.HALF_UP);
                price = products.getPrice().subtract(price);
            } else {
                // 如果没有折扣，直接使用原价
                price = products.getPrice();
            }
            orderDetails.setUnitPrice(price);
            totalAmount =price.multiply(BigDecimal.valueOf(createOrderDto.getQuantity()));
        }

        orderDetails.setDeleted(0);
        orderDetails.setPic(products.getPic());
        orderDetails.setQuantity(createOrderDto.getQuantity());
        orderDetails.setProdId(products.getProductId());
        orderDetails.setProdName(products.getProductName());
        orderDetails.setSubtotal(totalAmount);
        orderDetails.setUserId(userId);
        orderDetails.setCommSts(0);
        orderDetails.setUserDelete(0);
        orderDetails.setShopDelete(0);

        ccairbagOrderDetailsMapper.insertCcairbagOrderDetails(orderDetails);
        List<CcairbagPromotionActivities> activities = ccairbagPromotionActivitiesMapper.selectCcairbagPromotionActivitiesListByProductId(products.getProductId());
        BigDecimal relationMoney = new BigDecimal(0);
        for (CcairbagPromotionActivities activities1 : activities){
            BigDecimal relaMoneyx = activities1.getCommissionRate().multiply(totalAmount).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
            relationMoney = relationMoney.add(relaMoneyx);
        }
        CcairbagSetscale setscale = ccairbagSetscaleMapper.selectCcairbagSetscaleList(new CcairbagSetscale()).get(0);
        orders.setScale(setscale.getScale());
        orders.setDealScale(setscale.getDealScale());
        BigDecimal scaleMoney = totalAmount.multiply(setscale.getScale()).
                divide(new BigDecimal(100)).
                setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal dealMoney = totalAmount.multiply(setscale.getDealScale()).
                divide(new BigDecimal(100)).
                setScale(2, BigDecimal.ROUND_HALF_UP);
        orders.setScaleMoney(scaleMoney);
        orders.setDealMoney(dealMoney);
        orders.setCommissionSumMoney(relationMoney);
        BigDecimal incomeMoney = totalAmount.subtract(dealMoney).subtract(relationMoney);
        orders.setIncomeMoney(incomeMoney);
        orders.setTotalAmount(totalAmount);

        orders.setFinalAmount(totalAmount);
        ccairbagOrdersMapper.updateCcairbagOrders(orders);


        orderDetails.setScale(setscale.getScale());
        orderDetails.setDealScale(setscale.getDealScale());
        orderDetails.setScaleMoney(scaleMoney);
        orderDetails.setDealMoney(dealMoney);
        orderDetails.setIncomeMoney(incomeMoney);
        ccairbagOrderDetailsMapper.updateCcairbagOrderDetails(orderDetails);


        List<CcairbagOrderDetails> ccairbagOrderDetails = new ArrayList<>();
        ccairbagOrderDetails.add(orderDetails);
        orders.setOrderDetails(ccairbagOrderDetails);
        //添加mq队列
        systemcloseOrderTask(String.valueOf(orders.getOrderId()));

        sendMessagetToSeller(orders);
        return new AppResult(orders.getOrderId());
    }

    @Override
    @Transactional
    public AppResult createOrderExt(CreateOrderDto createOrderDto) {

        CcairbagProducts products = ccairbagProductsMapper.selectCcairbagProductsByProductIdExt(createOrderDto.getProductId());
        if (oConvertUtils.isEmpty(products)) {
            throw new RuntimeException("Product does not exist.");
        }



        CcairbagShops shops = ccairbagShopsMapper.selectCcairbagShopsByShopId(products.getShopId());
        if (oConvertUtils.isEmpty(shops)) {
            throw new RuntimeException("Seller does not exist.");
        }

        CcairbagOrders orders = new CcairbagOrders();
        //先算出 订单表 和订单详情表的 金额，小计
        BigDecimal totalAmount = new BigDecimal(BigInteger.ZERO);
        orders.setOrderNumber(orderIdUtil.genOrderId());

        orders.setReduceAmount(BigDecimal.ZERO);

        orders.setTotalAmount(new BigDecimal(0));
        orders.setFinalAmount(new BigDecimal(0));
        orders.setAddrId(-99L);
        orders.setOrderStatus(0);
        orders.setUserId(-99L);
        orders.setUserDelete(0);
        orders.setShopDelete(0);
        orders.setShopId(shops.getShopId());
        orders.setShopName(shops.getShopName());
        orders.setShopUserId(shops.getUserId());
        orders.setRemarks(createOrderDto.getRemarks());
        orders.setProductNums(createOrderDto.getQuantity());
        orders.setDeleted(0);
        ccairbagOrdersMapper.insertCcairbagOrders(orders);

        // 订单详情表
        CcairbagOrderDetails orderDetails = new CcairbagOrderDetails();
        orderDetails.setOrderId(orders.getOrderId());
        orderDetails.setOrderNumber(orders.getOrderNumber());
        if (oConvertUtils.isNotEmpty(createOrderDto.getNegotiablePrice())){
            totalAmount = createOrderDto.getNegotiablePrice().multiply(BigDecimal.valueOf(createOrderDto.getQuantity()));
            orderDetails.setNegotiablePrice(createOrderDto.getNegotiablePrice());
        }else {
            BigDecimal price = BigDecimal.ZERO;
            if (oConvertUtils.isNotEmpty(products.getDiscount())) {
                // 如果存在折扣，使用折扣价：原价 * (discount / 100)
                BigDecimal discountRate = new BigDecimal(products.getDiscount()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                price = products.getPrice().multiply(discountRate).setScale(2, RoundingMode.HALF_UP);
                price = products.getPrice().subtract(price);
            } else {
                // 如果没有折扣，直接使用原价
                price = products.getPrice();
            }
            orderDetails.setUnitPrice(price);
            totalAmount =price.multiply(BigDecimal.valueOf(createOrderDto.getQuantity()));
        }

        orderDetails.setDeleted(0);
        orderDetails.setPic(products.getPic());
        orderDetails.setQuantity(createOrderDto.getQuantity());
        orderDetails.setProdId(products.getProductId());
        orderDetails.setProdName(products.getProductName());
        orderDetails.setSubtotal(totalAmount);
        orderDetails.setUserId(-99L);
        orderDetails.setCommSts(0);
        orderDetails.setUserDelete(0);
        orderDetails.setShopDelete(0);

        ccairbagOrderDetailsMapper.insertCcairbagOrderDetails(orderDetails);
        List<CcairbagPromotionActivities> activities = ccairbagPromotionActivitiesMapper.selectCcairbagPromotionActivitiesListByProductId(products.getProductId());
        BigDecimal relationMoney = new BigDecimal(0);
        for (CcairbagPromotionActivities activities1 : activities){
            BigDecimal relaMoneyx = activities1.getCommissionRate().multiply(totalAmount).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
            relationMoney = relationMoney.add(relaMoneyx);
        }
        CcairbagSetscale setscale = ccairbagSetscaleMapper.selectCcairbagSetscaleList(new CcairbagSetscale()).get(0);
        orders.setScale(setscale.getScale());
        orders.setDealScale(setscale.getDealScale());
        BigDecimal scaleMoney = totalAmount.multiply(setscale.getScale()).
                divide(new BigDecimal(100)).
                setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal dealMoney = totalAmount.multiply(setscale.getDealScale()).
                divide(new BigDecimal(100)).
                setScale(2, BigDecimal.ROUND_HALF_UP);
        orders.setScaleMoney(scaleMoney);
        orders.setDealMoney(dealMoney);
        orders.setCommissionSumMoney(relationMoney);
        BigDecimal incomeMoney = totalAmount.subtract(dealMoney).subtract(relationMoney);
        orders.setIncomeMoney(incomeMoney);
        orders.setTotalAmount(totalAmount);

        orders.setFinalAmount(totalAmount);
        ccairbagOrdersMapper.updateCcairbagOrders(orders);


        orderDetails.setScale(setscale.getScale());
        orderDetails.setDealScale(setscale.getDealScale());
        orderDetails.setScaleMoney(scaleMoney);
        orderDetails.setDealMoney(dealMoney);
        orderDetails.setIncomeMoney(incomeMoney);
        ccairbagOrderDetailsMapper.updateCcairbagOrderDetails(orderDetails);


        List<CcairbagOrderDetails> ccairbagOrderDetails = new ArrayList<>();
        ccairbagOrderDetails.add(orderDetails);
        orders.setOrderDetails(ccairbagOrderDetails);
        //添加mq队列
        systemcloseOrderTask(String.valueOf(orders.getOrderId()));

        String key = "user:visitor:" + createOrderDto.getRandomNum();
        redisService.setCacheObject(key,String.valueOf(orders.getOrderId()), 10L, TimeUnit.DAYS);

//        sendMessagetToSeller(orders);
        return new AppResult(orders.getOrderId());
    }

    @Override
    @Transactional
    public AppResult updateOrderExt(CcairbagUserAddr ccairbagUserAddr) {
        if (oConvertUtils.isEmpty(ccairbagUserAddr.getCountry())){
            return AppResult.error("The country cannot be empty.");
        }
        if (oConvertUtils.isEmpty(ccairbagUserAddr.getCity())){
            return AppResult.error("The city cannot be empty.");
        }
        if (oConvertUtils.isEmpty(ccairbagUserAddr.getStreet())){
            return AppResult.error("The street cannot be empty.");
        }

        if (oConvertUtils.isEmpty(ccairbagUserAddr.getPostCode())){
            return AppResult.error("The post code cannot be empty.");
        }
        if (oConvertUtils.isEmpty(ccairbagUserAddr.getStateProvince())){
            return AppResult.error("The stateProvince cannot be empty.");
        }
        if (oConvertUtils.isEmpty(ccairbagUserAddr.getReceiver())){
            return AppResult.error("The receiver cannot be empty.");
        }

        String key = "user:visitor:" + ccairbagUserAddr.getRandomNum();
        String visitor = redisService.getCacheObject(key);
        if (oConvertUtils.isNotEmpty(visitor) && ccairbagUserAddr.getOrderId().equals(visitor)){
            if (oConvertUtils.isEmpty(ccairbagUserAddr.getPhone())){
                return AppResult.error("The phone number cannot be empty.");
            }
            ccairbagUserAddr.setStatus(1);
            ccairbagUserAddr.setUserId(-99L);
            ccairbagUserAddr.setAddrType(4);
            ccairbagUserAddr.setCreateTime(com.ruoyi.common.core.utils.DateUtils.getNowDate());
            ccairbagUserAddr.setDeleted(0);
            ccairbagUserAddr.setCommonAddr(1);
            ccairbagUserAddrMapper.insertCcairbagUserAddr(ccairbagUserAddr);

            CcairbagOrders orders = ccairbagOrdersMapper.selectCcairbagOrdersByOrderId(Long.valueOf(ccairbagUserAddr.getOrderId()));
            orders.setAddrId(ccairbagUserAddr.getAddrId());
            ccairbagOrdersMapper.updateCcairbagOrders(orders);
            sendMessagetToSeller(orders);
            return AppResult.success("Update successful.");
        }else {
            return AppResult.error("Failed to update data.");
        }


    }

    @Override
    public AppResult getOrderInfoExt(String orderId,String randomNum) {
        // 查询主订单
        CcairbagOrders order = ccairbagOrdersMapper.selectCcairbagOrdersByOrderId(Long.valueOf(orderId));
        if (order == null) {
            throw new RuntimeException("Order does not exist.");
        }

        // 查询订单明细
        CcairbagOrderDetails detailsQuery = new CcairbagOrderDetails();
        detailsQuery.setOrderId(Long.valueOf(orderId));
        List<CcairbagOrderDetails> detailsList = ccairbagOrderDetailsMapper.selectCcairbagOrderDetailsList(detailsQuery);

        // 状态优先级映射（值越小优先级越高）
        Map<Integer, Integer> statusPriority = new HashMap<>();
        {
            statusPriority.put(0, 1);   // 待付款
            statusPriority.put(1, 2);   // 待发货
            statusPriority.put(2, 3);   // 待收货
            statusPriority.put(3, 4);   // 售后中
            statusPriority.put(4, 5);   // 已收货
            statusPriority.put(5, 6);   // 已完成
            statusPriority.put(6, 7);   // 已取消
            statusPriority.put(7, 8);   // 退款成功
            statusPriority.put(99, 0);  // 已关单（最高优先级）
        }

        if (!detailsList.isEmpty()) {
            int highestPriority = Integer.MAX_VALUE;
            Integer finalStatus = null;

            // 1. 遍历获取最高优先级状态
            for (CcairbagOrderDetails details : detailsList) {
                // 关联退款信息
                CcairbagOrderRefund refund = ccairbagOrderRefundMapper.selectRefundByOrderItemId(details.getDetailId());
                if (oConvertUtils.isNotEmpty(refund)) {
                    details.setCcairbagOrderRefund(refund);
                }

                // 获取当前状态优先级
                Integer currentPriority = statusPriority.get(details.getOrderStatus());
                if (currentPriority == null) continue;

                // 保留最高优先级状态
                if (currentPriority < highestPriority) {
                    highestPriority = currentPriority;
                    finalStatus = details.getOrderStatus();
                }
            }

            // 2. 更新主订单状态
            if (finalStatus != null && !finalStatus.equals(order.getOrderStatus())) {
                order.setOrderStatus(finalStatus);
                ccairbagOrdersMapper.updateCcairbagOrders(order);
            }
        }

        // 组合数据
        order.setOrderDetails(detailsList);
        CcairbagOrdersTotal total = ccairbagOrdersTotalMapper.queryById(order.getOrderTotalId());
        CcairbagOrderPayment payment = null;


        if (oConvertUtils.isNotEmpty(total)) {
            /**
             * 100（店铺订单总额）/150（支付订单总额）*9.5（手续费）=6.3333
             */
            BigDecimal payFee = order.getFinalAmount().divide(total.getTotalAmount(), 2, BigDecimal.ROUND_HALF_UP).multiply(total.getPaymentFee());
            payFee = payFee.setScale(2, BigDecimal.ROUND_HALF_UP);
            order.setPaymentFee(payFee);

            payment = ccairbagOrderPaymentMapper.selectCcairbagOrderPaymentByOrderId(total.getOrderTotalId());
            if (oConvertUtils.isNotEmpty(payment)){
                payment.setPayAmount(order.getFinalAmount());
                payment.setProductNums(order.getProductNums());
                order.setPayment(payment);
            }

        }

        List<LogisticsInfoDTO> logisticsInfoDTOList = new ArrayList<>();

        List<CcairbagUserAddr> addrList = new ArrayList<>();

        Map<String, LogisticsInfoDTO> logisticsMap = new HashMap<>();

        Map<Long, CcairbagUserAddr> addrMap = new HashMap<>();

        /***
         * 如果 物流单号不为空 就添加 物流对象LogisticsInfoDTO 到logisticsInfoDTOList
         * 如果物流对象LogisticsInfoDTO 相同就合并 不加进去logisticsInfoDTOList
         * details.getProdId() 详情中的物流id ，如果相同就合并 不然就加到addrList 中
         */
        for (CcairbagOrderDetails details : detailsList) {
            String logisticsNumber = details.getDvyFlowNum();

            if (oConvertUtils.isNotEmpty(logisticsNumber)) {
                if (!logisticsMap.containsKey(logisticsNumber)) {
                    LogisticsInfoDTO dto = new LogisticsInfoDTO();
                    dto.setLogisticsNumber(logisticsNumber);
                    dto.setLogisticsPlatformName(details.getLogisticsName());
                    dto.setLogisticsCode(details.getLogisticsCode());
                    dto.setCompletionTime(details.getFinallyTime());
                    if (oConvertUtils.isNotEmpty(payment)){
                        dto.setPaymentTime(payment.getCreateTime());
                    }
                    dto.setWaybillGenerationTime(details.getDvyTime());
                    logisticsMap.put(logisticsNumber, dto);
                    logisticsInfoDTOList.add(dto);
                }
            }
            if (logisticsInfoDTOList.isEmpty()){
                LogisticsInfoDTO dto1 = new LogisticsInfoDTO();
                if (oConvertUtils.isNotEmpty(payment)){
                    dto1.setPaymentTime(payment.getCreateTime());
                }
                logisticsInfoDTOList.add(dto1);
            }
            Long prodId = details.getProdId();



            if (!addrMap.containsKey(prodId)) {
                CcairbagOrders orders = ccairbagOrdersMapper.selectCcairbagOrdersByOrderId(details.getOrderId());
                CcairbagUserAddr addr = ccairbagUserAddrMapper.selectCcairbagUserAddrByAddrId(orders.getAddrId());
                addrMap.put(prodId, addr);
                addrList.add(addr);

            }
        }

        order.setAddrList(addrList);
        order.setLogisticsInfoDTOList(logisticsInfoDTOList);
        return new AppResult<>(order);

    }

    @Override
    public AppResult<CcairbagOrders> createOrderByNegotiationId(Long id) {
        CcairbagNegotiationRecord negotiations = ccairbagNegotiationRecordMapper.selectCcairbagNegotiationRecordById(id);
        if (oConvertUtils.isEmpty(negotiations)){
            return  AppResult.error("协商记录不存在");
        }
        if (negotiations.getStatus() != 1){
            return  AppResult.error("协商记录状态不是已同意");
        }
        CreateOrderDto createOrderDto = new CreateOrderDto();
        createOrderDto.setProductId(negotiations.getProductId());
        createOrderDto.setQuantity(1);
        createOrderDto.setNegotiablePrice(negotiations.getOfferPrice());
        return this.createOrder(createOrderDto);
    }


    @Override
    @Transactional
    public AppResult createOrderByCart(ShopCartDtoExt shopCartDtoExt) {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        Long userId = loginAppUser.getCcairbagUsers().getUserId();
        List<CcairbagUserAddr> addrList = ccairbagUserAddrMapper.selectCcairbagUserAddrByUserIdExt(userId);
        if (addrList.isEmpty()) {
            throw new RuntimeException("Please add a shipping address first.");
        }

        List<CcairbagOrders> ordersList = new ArrayList<>();

        BigDecimal orderSumMoney = new BigDecimal(0);
        int productNumsExt = 0;
        String orderids = "";
        List<Long> orderidsLists = new ArrayList<>();
        for (ShopCartDto shopCartDto : shopCartDtoExt.getShopCartDtoList()) {
            CcairbagShops shops = ccairbagShopsMapper.selectCcairbagShopsByShopId(shopCartDto.getShopId());
            if (oConvertUtils.isEmpty(shops)) {
                throw new RuntimeException("Seller does not exist.");
            }
            //先创建一个总订单
            CcairbagOrders orders = new CcairbagOrders();
            //先算出 订单表 和订单详情表的 金额，小计
            orders.setOrderNumber(orderIdUtil.genOrderId());
            orders.setAddrId(addrList.get(0).getAddrId());
            orders.setShopId(shops.getShopId());
            orders.setShopName(shops.getShopName());
            orders.setShopUserId(shops.getUserId());
            orders.setOrderStatus(0);
            orders.setUserId(userId);
            orders.setUserDelete(0);
            orders.setShopDelete(0);
            orders.setRemarks(shopCartDtoExt.getRemarks());
            orders.setDeleted(0);
            ccairbagOrdersMapper.insertCcairbagOrders(orders);
            int productNums = 0;
            //初始化订单总金额
            BigDecimal orderDetailSumMoney = new BigDecimal(0);

            BigDecimal commissionDetailSumMoney = new BigDecimal(0);

            List<CcairbagOrderDetails> detailsList = new ArrayList<>();

            CcairbagSetscale setscale = ccairbagSetscaleMapper.selectCcairbagSetscaleList(new CcairbagSetscale()).get(0);

            for (CcairbagShoppingCarts cart : shopCartDto.getProducts()) {
                CcairbagProducts products = ccairbagProductsMapper.selectCcairbagProductsByProductIdExt(cart.getProdId());
                if (oConvertUtils.isEmpty(products)) {
                    throw new RuntimeException("商品不存在");
                }
                // 扣减 Redis 库存
//                boolean success = redisStockService.reduceStock(cart.getProdId(), cart.getQuantity());
//                if (!success) {
//                    return AppResult.error("Insufficient stock.");
//                }

                BigDecimal price = BigDecimal.ZERO;
                if (oConvertUtils.isNotEmpty(products.getDiscount())) {
                    // 如果存在折扣，使用折扣价：原价 * (discount / 100)
                    BigDecimal discountRate = new BigDecimal(products.getDiscount()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                    price = products.getPrice().multiply(discountRate).setScale(2, RoundingMode.HALF_UP);
                    price = products.getPrice().subtract(price);
                } else {
                    // 如果没有折扣，直接使用原价
                    price = products.getPrice();
                }

                //创建子订单
                // 订单详情表
                BigDecimal detailMoney = price.multiply(BigDecimal.valueOf(cart.getQuantity()));
                List<CcairbagPromotionActivities> activities = ccairbagPromotionActivitiesMapper.selectCcairbagPromotionActivitiesListByProductId(cart.getProdId());
                BigDecimal relationMoney = new BigDecimal(0);
                for (CcairbagPromotionActivities activities1 : activities){
                    BigDecimal relaMoneyx = activities1.getCommissionRate().multiply(detailMoney).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                    relationMoney = relationMoney.add(relaMoneyx);
                }
                CcairbagOrderDetails orderDetails = new CcairbagOrderDetails();
                orderDetails.setOrderId(orders.getOrderId());
                orderDetails.setOrderNumber(orders.getOrderNumber());
                orderDetails.setDeleted(0);
                orderDetails.setPic(products.getPic());
                orderDetails.setQuantity(cart.getQuantity());
                orderDetails.setProdId(products.getProductId());
                orderDetails.setProdName(products.getProductName());
                orderDetails.setUnitPrice(price);
                orderDetails.setUserDelete(0);
                orderDetails.setShopDelete(0);

                orderDetails.setSubtotal(detailMoney);
                orderDetails.setUserId(userId);
                orderDetails.setCommSts(0);

                orderDetails.setScale(setscale.getScale());
                orderDetails.setDealScale(setscale.getDealScale());
                BigDecimal dealMoneyx = detailMoney.multiply(setscale.getDealScale()).
                        divide(new BigDecimal(100)).
                        setScale(2, BigDecimal.ROUND_HALF_UP);
                orderDetails.setDealMoney(dealMoneyx);
                orderDetails.setScaleMoney(BigDecimal.ZERO);


                orderDetails.setIncomeMoney(detailMoney.subtract(dealMoneyx).subtract(relationMoney));

                ccairbagOrderDetailsMapper.insertCcairbagOrderDetails(orderDetails);


                detailsList.add(orderDetails);
                productNums++;
                orderDetailSumMoney = orderDetailSumMoney.add(detailMoney);
                commissionDetailSumMoney = commissionDetailSumMoney.add(relationMoney);


            }

            //销售税
//                BigDecimal scaleMoney = detailMoney.multiply(setscale.getScale()).
//                        divide(new BigDecimal(100)).
//                        setScale(2, BigDecimal.ROUND_HALF_UP);
            //交易费用税
            BigDecimal dealMoney = orderDetailSumMoney.multiply(setscale.getDealScale()).
                    divide(new BigDecimal(100)).
                    setScale(2, BigDecimal.ROUND_HALF_UP);


            orders.setScale(setscale.getScale());
            orders.setDealScale(setscale.getDealScale());
            orders.setDealMoney(dealMoney);
            orders.setCommissionSumMoney(commissionDetailSumMoney);
            orders.setScaleMoney(BigDecimal.ZERO);
            orders.setIncomeMoney(orderDetailSumMoney.subtract(dealMoney).subtract(commissionDetailSumMoney));

            orders.setTotalAmount(orderDetailSumMoney);
            orders.setFinalAmount(orderDetailSumMoney);
            orders.setReduceAmount(BigDecimal.ZERO);
            orders.setProductNums(productNums);
            orders.setOrderDetails(detailsList);
            ccairbagOrdersMapper.updateCcairbagOrders(orders);

            orderSumMoney = orderSumMoney.add(orderDetailSumMoney);
            ordersList.add(orders);

            productNumsExt++;
            if (oConvertUtils.isEmpty(orderids)){
                orderids = String.valueOf(orders.getOrderId());
            }else {
                orderids =  orderids+ ","+ orders.getOrderId();
            }
            orderidsLists.add(orders.getOrderId());
            //发送消息给卖家 提醒有订单
            sendMessagetToSeller(orders);
        }

        //添加mq队列
        systemcloseOrderTask(orderids);
//        total.setOrdersList(ordersList);
        return new AppResult(orderidsLists);
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


    public AppResult generatePaymentLink(List<Long> orderIds) {
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        BigDecimal paymentFee = new BigDecimal(0);
        BigDecimal orderMoney = new BigDecimal(0);

        int productNums = 0;
        Long userId = null;
        String remarks = "";
        Long addrId = null;

        for (Long orderId : orderIds) {
            CcairbagOrders ccairbagOrders = ccairbagOrdersMapper.selectCcairbagOrdersByOrderId(orderId);
            if (ccairbagOrders == null) {
                throw new RuntimeException("Order does not exist.");
            }
            if (ccairbagOrders.getOrderStatus() != 0){
                return AppResult.error("The order status has been modified, so payment cannot be made.");
            }

            log.info("ccairbagOrders.getOrderStatus():"+ccairbagOrders.getOrderStatus());
            if (ccairbagOrders.getOrderStatus() != 0) {
                throw new RuntimeException("The order is not pending payment.");
            }
            if (ccairbagOrders.getAddrId().equals(-99L)){
                throw new RuntimeException("Please add your address.");
            }


            if (oConvertUtils.isEmpty(userId)) {
                userId = ccairbagOrders.getUserId();
            }
            if (oConvertUtils.isEmpty(remarks)) {
                remarks = ccairbagOrders.getRemarks();
            }
            if (oConvertUtils.isEmpty(addrId)) {
                addrId = ccairbagOrders.getAddrId();
            }

            BigDecimal finalAmount = ccairbagOrders.getFinalAmount();
            BigDecimal paymentFeeitem = finalAmount.divide(BigDecimal.valueOf(100))
                    .multiply(BigDecimal.valueOf(5)).setScale(2, BigDecimal.ROUND_HALF_UP);
            paymentFee = paymentFee.add(paymentFeeitem);
            orderMoney = orderMoney.add(finalAmount);
            log.info("订单金额="+ finalAmount +",手续费="+paymentFeeitem+",总订单金额="+orderMoney+",子订单id="+orderId);
            productNums = productNums + ccairbagOrders.getProductNums();

        }



        CcairbagOrdersTotal total = new CcairbagOrdersTotal();
        total.setUserId(userId);
        total.setDeleted(0);
        total.setCreateTime(new Date());
        total.setOrderNumber(orderIdUtil.genOrderId());
        total.setTotalAmount(orderMoney);
        paymentFee = paymentFee.add(BigDecimal.valueOf(1));
        total.setFinalAmount(orderMoney.add(paymentFee));
        total.setPaymentFee(paymentFee);
        total.setAddrId(addrId);
        total.setPayType(0);
        total.setRemarks(remarks);
        total.setProductNums(productNums);
        ccairbagOrdersTotalMapper.insert(total);



        for (Long orderId : orderIds) {
            CcairbagOrders ccairbagOrders = ccairbagOrdersMapper.selectCcairbagOrdersByOrderId(orderId);
            ccairbagOrders.setOrderTotalId(total.getOrderTotalId());
            ccairbagOrdersMapper.updateCcairbagOrders(ccairbagOrders);
        }
        BigDecimal money = total.getFinalAmount().setScale(2, BigDecimal.ROUND_HALF_UP);
        Map<String, Object> amount = new HashMap<>();
        amount.put("currency_code", "USD");
        amount.put("value", money);
        Map<String, Object> itemMap = new HashMap<>();
        itemMap.put("amount", amount);
        itemMap.put("reference_id", total.getOrderTotalId());
        list.add(itemMap);
        PayPalApiConfig config = getConfig();
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("intent", "CAPTURE");
        dataMap.put("purchase_units", list);

        Map<String, Object> applicationMap = new HashMap<>();
        applicationMap.put("return_url", "https://www.ccairbag.com");
        applicationMap.put("cancel_url", "https://www.ccairbag.com");
        itemMap.put("amount", amount);
        itemMap.put("reference_id", total.getOrderTotalId());
        dataMap.put("application_context", applicationMap);

        String data = JSONUtil.toJsonStr(dataMap);
        log.info(data);
        IJPayHttpResponse resData = PayPalApi.createOrder(config, data);
        log.info("resData:"+resData);
        if (resData.getStatus() != 200 && resData.getStatus() != 201) {
            return new AppResult("Failed to create order.");
        }
        JSONObject jsonObject = JSON.parseObject(resData.getBody());

        return new AppResult(jsonObject);

    }





    @Override
    public AppResult generateAliPayLink(List<Long> orderIds) throws AlipayApiException {
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        AlipayClient alipayClient = alipayUtil.buildAliClient();

        BigDecimal paymentFee = new BigDecimal(0);
        BigDecimal orderMoney = new BigDecimal(0);

        int productNums = 0;
        Long userId = null;
        String remarks = "";
        Long addrId = null;
        String subject = "";
        for (Long orderId : orderIds) {
            CcairbagOrders ccairbagOrders = ccairbagOrdersMapper.selectCcairbagOrdersByOrderId(orderId);
            if (ccairbagOrders == null) {
                throw new RuntimeException("Order does not exist.");
            }
            if (ccairbagOrders.getOrderStatus() != 0) {
                throw new RuntimeException("The order is not pending payment.");
            }
            if (ccairbagOrders.getAddrId().equals(-99L)){
                throw new RuntimeException("Please add your address.");
            }
            if (oConvertUtils.isEmpty(userId)) {
                userId = ccairbagOrders.getUserId();
            }
            if (oConvertUtils.isEmpty(remarks)) {
                remarks = ccairbagOrders.getRemarks();
            }
            if (oConvertUtils.isEmpty(addrId)) {
                addrId = ccairbagOrders.getAddrId();
            }

            BigDecimal finalAmount = ccairbagOrders.getFinalAmount();
            BigDecimal paymentFeeitem = finalAmount.divide(BigDecimal.valueOf(100))
                    .multiply(BigDecimal.valueOf(1));
            paymentFee = paymentFee.add(paymentFeeitem);
            log.info("订单金额="+ finalAmount +",手续费="+paymentFeeitem+",总订单金额="+orderMoney+",子订单id="+orderId);
            productNums = productNums + ccairbagOrders.getProductNums();
            orderMoney = orderMoney.add(finalAmount);
            subject = ccairbagOrders.getShopName();
        }


        CcairbagOrdersTotal total = new CcairbagOrdersTotal();
        total.setUserId(userId);
        total.setDeleted(0);
        total.setCreateTime(new Date());
        total.setOrderNumber(orderIdUtil.genOrderId());
        total.setTotalAmount(orderMoney);
        total.setFinalAmount(orderMoney.add(paymentFee));
        total.setPaymentFee(paymentFee);
        total.setAddrId(addrId);
        total.setPayType(0);
        total.setRemarks(remarks);
        total.setProductNums(productNums);
        ccairbagOrdersTotalMapper.insert(total);
        for (Long orderId : orderIds) {
            CcairbagOrders ccairbagOrders = ccairbagOrdersMapper.selectCcairbagOrdersByOrderId(orderId);
            ccairbagOrders.setOrderTotalId(total.getOrderTotalId());
            ccairbagOrdersMapper.updateCcairbagOrders(ccairbagOrders);
        }

        BigDecimal money = total.getFinalAmount().setScale(2, BigDecimal.ROUND_HALF_UP);

        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        // 设置商户订单号
        model.setOutTradeNo(String.valueOf(total.getOrderTotalId()));
        // 设置订单总金额
        model.setTotalAmount(String.valueOf(money));
        // 设置订单标题
        model.setSubject(subject);
        // 设置产品码
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        // 设置PC扫码支付的方式
        model.setQrPayMode("2");
        // 设置商户自定义二维码宽度
        model.setQrcodeWidth(100L);
        // 设置订单绝对超时时间
        //当前时间 往后15小时
        model.setTimeExpire(DateUtil.format(DateUtil.offset(new Date(), DateField.HOUR, 15), DatePattern.NORM_DATETIME_PATTERN));
        // 设置商户传入业务信息
//        model.setBusinessParams("{orderTotalId:"+total.getOrderTotalId()+"}");
        // 设置优惠参数
//        model.setPromoParams("{\"storeIdType\":\"1\"}");

        // 设置请求后页面的集成方式
        model.setIntegrationType("PCWEB");
        // 设置请求来源地址
        model.setRequestFromUrl("https://");
        request.setBizModel(model);
//        request.setBizContent(model.toJSONString());
        request.setReturnUrl(alipayCertConfig.getReturnUrl());
        //异步通知
        request.setNotifyUrl(alipayCertConfig.getNotifyUrl());
        AlipayTradePagePayResponse response = alipayClient.pageExecute(request);
        // 如果需要返回GET请求，请使用
        // AlipayTradePagePayResponse response = alipayClient.pageExecute(request, "GET");
        String pageRedirectionData = response.getBody();
        System.out.println(pageRedirectionData);
        log.info("支付宝返回数据：{}", pageRedirectionData);
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");

        }
        return new AppResult(pageRedirectionData);


    }



    @Override
    public AppResult generateStripePayLink(List<Long> orderIds) {
        BigDecimal paymentFee = new BigDecimal(0);
        BigDecimal orderMoney = new BigDecimal(0);

        int productNums = 0;
        Long userId = null;
        String remarks = "";
        Long addrId = null;

        for (Long orderId : orderIds) {
            CcairbagOrders ccairbagOrders = ccairbagOrdersMapper.selectCcairbagOrdersByOrderId(orderId);
            if (ccairbagOrders == null) {
                throw new RuntimeException("Order does not exist.");
            }
            if (ccairbagOrders.getOrderStatus() != 0) {
                throw new RuntimeException("The order is not pending payment.");
            }
            if (oConvertUtils.isEmpty(userId)) {
                userId = ccairbagOrders.getUserId();
            }
            if (oConvertUtils.isEmpty(remarks)) {
                remarks = ccairbagOrders.getRemarks();
            }
            if (oConvertUtils.isEmpty(addrId)) {
                addrId = ccairbagOrders.getAddrId();
            }

            BigDecimal finalAmount = ccairbagOrders.getFinalAmount();
            orderMoney = orderMoney.add(finalAmount);

            productNums = productNums + ccairbagOrders.getProductNums();

        }


        CcairbagOrdersTotal total = new CcairbagOrdersTotal();
        total.setUserId(userId);
        total.setDeleted(0);
        total.setCreateTime(new Date());
        total.setOrderNumber(orderIdUtil.genOrderId());
        total.setTotalAmount(orderMoney);
        total.setFinalAmount(orderMoney.add(paymentFee));
        total.setPaymentFee(paymentFee);
        total.setAddrId(addrId);
        total.setPayType(0);
        total.setRemarks(remarks);
        total.setProductNums(productNums);
        ccairbagOrdersTotalMapper.insert(total);
        for (Long orderId : orderIds) {
            CcairbagOrders ccairbagOrders = ccairbagOrdersMapper.selectCcairbagOrdersByOrderId(orderId);
            ccairbagOrders.setOrderTotalId(total.getOrderTotalId());
            ccairbagOrdersMapper.updateCcairbagOrders(ccairbagOrders);
        }

        PaymentIntent paymentIntent;
        String json;
        try {
            Stripe.apiKey = visapayConfig.getStripeApiKey();



            // 创建支付订单
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    // 支付金额
                    .setAmount(total.getFinalAmount().multiply(new BigDecimal("100")).longValue())
//                    .setAmount(100L)
                    // 货币类型 美元 usd 港币 hkd
                    .setCurrency("usd")
                    // 支付方式 card 信用卡或借记卡支付 alipay：支付宝支付，适用于中国地区的用户 wechat_pay：微信支付，适用于中国地区的用户
//                    .addPaymentMethodType("card")
                    // 存储自定义数据的属性
                    .putMetadata("orderId", String.valueOf(total.getOrderTotalId()))
                    // 客户id
//                    .setCustomer(customerId)
                    // 描述
                    .setDescription("测试购买商品").build();
            paymentIntent = PaymentIntent.create(params);
            System.out.println("stripe卡支付下单返回结果：" + paymentIntent);
            json = JSON.toJSONString(paymentIntent);
            log.info("stripe卡支付下单返回结果：" + json);
        } catch (StripeException e) {
            System.out.println("visa卡支付下单异常：" + e.getMessage());
            throw new RuntimeException(e);
        }
        // 前端根据客户端密钥发起支付


        return new AppResult(json);
    }

    @Override
    public AppResult generateStripePayoutExt() {
        return null;
    }

    @Override
    public AppResult generateVisaPay(List<Long> orderIds) {
        BigDecimal paymentFee = new BigDecimal(0);
        BigDecimal orderMoney = new BigDecimal(0);

        int productNums = 0;
        Long userId = null;
        String remarks = "";
        Long addrId = null;

        for (Long orderId : orderIds) {
            CcairbagOrders ccairbagOrders = ccairbagOrdersMapper.selectCcairbagOrdersByOrderId(orderId);
            if (ccairbagOrders == null) {
                throw new RuntimeException("Order does not exist.");
            }
            if (ccairbagOrders.getOrderStatus() != 0) {
                throw new RuntimeException("The order is not pending payment.");
            }
            if (oConvertUtils.isEmpty(userId)) {
                userId = ccairbagOrders.getUserId();
            }
            if (oConvertUtils.isEmpty(remarks)) {
                remarks = ccairbagOrders.getRemarks();
            }
            if (oConvertUtils.isEmpty(addrId)) {
                addrId = ccairbagOrders.getAddrId();
            }

            BigDecimal finalAmount = ccairbagOrders.getFinalAmount();
            orderMoney = orderMoney.add(finalAmount);

            productNums = productNums + ccairbagOrders.getProductNums();

        }


        CcairbagOrdersTotal total = new CcairbagOrdersTotal();
        total.setUserId(userId);
        total.setDeleted(0);
        total.setCreateTime(new Date());
        total.setOrderNumber(orderIdUtil.genOrderId());
        total.setTotalAmount(orderMoney);
        total.setFinalAmount(orderMoney.add(paymentFee));
        total.setPaymentFee(paymentFee);
        total.setAddrId(addrId);
        total.setPayType(0);
        total.setRemarks(remarks);
        total.setProductNums(productNums);
        ccairbagOrdersTotalMapper.insert(total);
        for (Long orderId : orderIds) {
            CcairbagOrders ccairbagOrders = ccairbagOrdersMapper.selectCcairbagOrdersByOrderId(orderId);
            ccairbagOrders.setOrderTotalId(total.getOrderTotalId());
            ccairbagOrdersMapper.updateCcairbagOrders(ccairbagOrders);
        }


        // 走visa 的支付



        return new AppResult();
    }

    @Override
    public AppResult getOrderAmount(List<Long> orderIds,int payType) {
        BigDecimal paymentFee = new BigDecimal(0);
        BigDecimal orderSumMoney = new BigDecimal(0);
        int productNums = 0;
        Long userId = null;
        String remarks = "";
        Long addrId = null;
        for (Long orderId : orderIds) {
            CcairbagOrders ccairbagOrders = ccairbagOrdersMapper.selectCcairbagOrdersByOrderId(orderId);

            if (ccairbagOrders == null) {
                throw new RuntimeException("Order does not exist.");
            }
            if (ccairbagOrders.getOrderStatus() != 0) {
                throw new RuntimeException("The order is not pending payment.");
            }
            if (oConvertUtils.isEmpty(userId)) {
                userId = ccairbagOrders.getUserId();
            }
            if (oConvertUtils.isEmpty(remarks)) {
                remarks = ccairbagOrders.getRemarks();
            }
            if (oConvertUtils.isEmpty(addrId)) {
                addrId = ccairbagOrders.getAddrId();
            }
            BigDecimal finalAmount = ccairbagOrders.getFinalAmount();
            if (payType==0){

                BigDecimal paymentFeeitem = finalAmount.divide(BigDecimal.valueOf(100))
                        .multiply(BigDecimal.valueOf(5))
                        .setScale(2, RoundingMode.HALF_UP);
                paymentFee = paymentFee.add(paymentFeeitem);
                log.info("订单金额="+ finalAmount +",手续费="+paymentFeeitem+",总订单金额="+orderSumMoney+",子订单id="+orderId);

            }else if (payType==1){
                BigDecimal paymentFeeitem = finalAmount.divide(BigDecimal.valueOf(100))
                        .multiply(BigDecimal.valueOf(1)).setScale(2, RoundingMode.HALF_UP);
                paymentFee = paymentFee.add(paymentFeeitem);
                log.info("订单金额="+ finalAmount +",手续费="+paymentFeeitem+",总订单金额="+orderSumMoney+",子订单id="+orderId);

            }

            orderSumMoney = orderSumMoney.add(finalAmount);
            productNums = productNums + ccairbagOrders.getProductNums();

        }
        if (payType==0){
            paymentFee = paymentFee.add(BigDecimal.valueOf(1));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("paymentFee", paymentFee);
        map.put("orderFinalSumMoney", orderSumMoney.add(paymentFee));

        map.put("orderSumMoney", orderSumMoney);
        map.put("productNums", productNums);
        map.put("couponMoney", 0);

        return new AppResult(map);

    }

    @Override
    @Transactional
    public String alinotify(HttpServletRequest request) {
        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
            System.out.println("=========支付宝异步回调========");

            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
                // System.out.println(name + " = " + request.getParameter(name));
            }

            String tradeNo = params.get("out_trade_no");
            String alipayTradeNo = params.get("trade_no");


            boolean checkSignature = false;
            try {
                checkSignature = alipayUtil.rsaCheckV1(request);
            } catch (AlipayApiException e) {
                throw new RuntimeException(e);
            }
//            String sign = params.get("sign");
//            String content = AlipaySignature.getSignCheckContentV1(params);
//            boolean checkSignature = AlipaySignature.rsa256CheckContent(content, sign, alipayCertConfig.getAlipayPublicKey(), "UTF-8"); // 验证签名
            // 支付宝验签
            if (checkSignature) {
                // 验签通过
                System.out.println("交易名称: " + params.get("subject"));
                System.out.println("交易状态: " + params.get("trade_status"));
                System.out.println("支付宝交易凭证号: " + params.get("trade_no"));
                System.out.println("商户订单号: " + params.get("out_trade_no"));
                System.out.println("交易金额: " + params.get("total_amount"));
                System.out.println("买家在支付宝唯一id: " + params.get("buyer_id"));
                System.out.println("买家付款时间: " + params.get("gmt_payment"));
                System.out.println("买家付款金额: " + params.get("buyer_pay_amount"));

                // 处理每个 purchaseUnit
                String localOrderId = tradeNo;
                List<CcairbagOrders> ordersListx = ccairbagOrdersMapper.selectOrdersTotalList(Long.valueOf(localOrderId));
                for (CcairbagOrders orders : ordersListx){
                    CcairbagOrders orderx = ccairbagOrdersMapper.selectOrderBySeqForUpdate(orders.getOrderId());
                    if (orderx == null) {
                        return "fail";
                    }
                }

                CcairbagOrdersTotal orders = ccairbagOrdersTotalMapper.queryById(Long.valueOf(localOrderId));
                String value = params.get("buyer_pay_amount");
                String payTimeString= params.get("gmt_payment");

                BigDecimal finalAmount = orders.getFinalAmount();
                if (value.equals(finalAmount.toString())){
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = null;
                    try {
                        date = dateFormat.parse(payTimeString);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    orders.setOrderStatus(1);
                    orders.setPayTime(date);
                    orders.setUpdateTime(new Date());
                    //更新订单表 添加订单支付表
                    ccairbagOrdersTotalMapper.update(orders);
                    List<CcairbagOrders> ordersList = ccairbagOrdersMapper.selectOrdersTotalList(Long.valueOf(localOrderId));
                    ccairbagOrdersMapper.updateOrderByTotalId(orders.getOrderTotalId());
                    for (CcairbagOrders orders1 : ordersList){
                        //更新订单明细表
                        ccairbagOrderDetailsMapper.updateOrderDetailsByOrderId(orders1.getOrderId());
                    }


                    CcairbagOrderPayment ccairbagOrderPayment = new CcairbagOrderPayment();
                    ccairbagOrderPayment.setBizPayNo(alipayTradeNo);
                    ccairbagOrderPayment.setPayerAccount(params.get("buyer_id"));
                    ccairbagOrderPayment.setPayerName("支付宝用户");
                    ccairbagOrderPayment.setTotalFreightAmount(new BigDecimal("0.00"));
                    ccairbagOrderPayment.setProductNums(orders.getProductNums());

                    ccairbagOrderPayment.setPayNo(orders.getOrderNumber());
                    ccairbagOrderPayment.setCreateTime(date);
                    ccairbagOrderPayment.setOrderId(Long.valueOf(localOrderId));
                    ccairbagOrderPayment.setPayStatus(0);
                    ccairbagOrderPayment.setPayType(1);
                    String currency = "CNY";
                    ccairbagOrderPayment.setCreateTime(date);
                    ccairbagOrderPayment.setCurrency(currency);
                    ccairbagOrderPayment.setPayAmount(new BigDecimal(value));
                    ccairbagOrderPayment.setUserId(orders.getUserId());
                    ccairbagOrderPayment.setDeleted(0);
                    ccairbagOrderPaymentMapper.insertCcairbagOrderPayment(ccairbagOrderPayment);
                    // 减商品库存   添加 消息会话表 + 发一条消息记录 （确认订单位置）
                    updateRedisStock(Long.valueOf(localOrderId));
                    addMessage(orders);
                    //给商家钱包 添加对应金额
                    for (CcairbagOrders orderx : ordersList){
                        CcairbagShops shops = ccairbagShopsMapper.selectCcairbagShopsByShopId( orderx.getShopId());
                        shops.setWalletOrderIng(shops.getWalletOrderIng().add(orderx.getFinalAmount()));
                        ccairbagShopsMapper.updateCcairbagShops(shops);
                    }
                }


            }

        }
        return "success";
    }




    @Override
    @Transactional
    public void stripeNotify(HttpServletRequest request, HttpServletResponse response) {
        Stripe.apiKey = visapayConfig.getStripeApiKey();
        String requestId = UUID.randomUUID().toString();
        try {


            String payload = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode event = objectMapper.readTree(payload);
//            JSONObject jsonObject = JSON.parseObject(payload);
            log.info("event:{}",event);
            if ("payment_intent.succeeded".equals(event.path("type").asText())){
                //支付成功的
                String status = event.path("data").path("object").path("status").asText();
                if ("succeeded".equals(status)){
                    String localOrderId = event.path("data").path("object")
                            .path("metadata").path("orderId").asText();

                    List<CcairbagOrders> ordersListx = ccairbagOrdersMapper.selectOrdersTotalList(Long.valueOf(localOrderId));
                    for (CcairbagOrders orders : ordersListx){
                        CcairbagOrders orderx = ccairbagOrdersMapper.selectOrderBySeqForUpdate(orders.getOrderId());
                        if (orderx == null) {
                            return;
                        }
                    }


                    CcairbagOrdersTotal orders = ccairbagOrdersTotalMapper.queryById(Long.valueOf(localOrderId));
                    String id = event.path("data").path("object").path("id").asText();
                    String value = event.path("data").path("object")
                            .path("amount_received").asText();
                    String payTimeString= event.path("created").asText();
                    String currency = event.path("data").path("object")
                            .path("currency").asText();
                    BigDecimal bdValue = new BigDecimal(value);

                    // 除以100
                    BigDecimal divisor = new BigDecimal("100");
                    BigDecimal result = bdValue.divide(divisor, 2, RoundingMode.HALF_UP);

                    BigDecimal finalAmount = orders.getFinalAmount();
                    if (result.equals(finalAmount)){
                        String payTime = DateUtils.long2strTime(Long.parseLong(payTimeString));
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = null;
                        try {
                            date = dateFormat.parse(payTime);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        orders.setOrderStatus(1);
                        orders.setPayTime(date);
                        orders.setUpdateTime(new Date());
                        //更新订单表 添加订单支付表
                        ccairbagOrdersTotalMapper.update(orders);
                        List<CcairbagOrders> ordersList = ccairbagOrdersMapper.selectOrdersTotalList(Long.valueOf(localOrderId));
                        ccairbagOrdersMapper.updateOrderByTotalId(orders.getOrderTotalId());
                        for (CcairbagOrders orders1 : ordersList){
                            //更新订单明细表
                            ccairbagOrderDetailsMapper.updateOrderDetailsByOrderId(orders1.getOrderId());
                        }


                        CcairbagOrderPayment ccairbagOrderPayment = new CcairbagOrderPayment();
                        ccairbagOrderPayment.setBizPayNo(id);
                        ccairbagOrderPayment.setPayerAccount("visa用户id");
                        ccairbagOrderPayment.setPayerName("visa用户");
                        ccairbagOrderPayment.setTotalFreightAmount(new BigDecimal("0.00"));
                        ccairbagOrderPayment.setProductNums(orders.getProductNums());

                        ccairbagOrderPayment.setPayNo(orders.getOrderNumber());
                        ccairbagOrderPayment.setCreateTime(date);
                        ccairbagOrderPayment.setOrderId(Long.valueOf(localOrderId));
                        ccairbagOrderPayment.setPayStatus(0);
                        ccairbagOrderPayment.setPayType(2);
                        ccairbagOrderPayment.setCreateTime(date);
                        ccairbagOrderPayment.setCurrency(currency);
                        ccairbagOrderPayment.setPayAmount(new BigDecimal(value));
                        ccairbagOrderPayment.setUserId(orders.getUserId());
                        ccairbagOrderPayment.setDeleted(0);
                        ccairbagOrderPaymentMapper.insertCcairbagOrderPayment(ccairbagOrderPayment);
                        // 减商品库存   添加 消息会话表 + 发一条消息记录 （确认订单位置）
                        updateRedisStock(Long.valueOf(localOrderId));
                        addMessage(orders);
                        //给商家钱包 添加对应金额
                        for (CcairbagOrders orderx : ordersList){
                            CcairbagShops shops = ccairbagShopsMapper.selectCcairbagShopsByShopId( orderx.getShopId());
                            shops.setWalletOrderIng(shops.getWalletOrderIng().add(orderx.getFinalAmount()));
                            ccairbagShopsMapper.updateCcairbagShops(shops);
                        }
                    }

                }
            }



        } catch (Exception e) {
            response.setStatus(500);
            log.error(e.getMessage(), e);
        }


    }


    @Override
    public AppResult getOrderInfo(String id) {
        try {
            PayPalApiConfig config = getConfig();
            IJPayHttpResponse response = PayPalApi.queryOrder(config, id);
            log.info(response.toString());
            JSONObject jsonObject = JSON.parseObject(response.getBody());
            log.info("订单结果："+jsonObject.toString());
            if (response.getStatus() == 200) {

                return new AppResult(jsonObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AppResult.error();

    }


    //根据订单id 查出商品 并 减 数据库库存
    private void updateRedisStock(Long localOrderId){
        List<CcairbagOrders> ordersList = ccairbagOrdersMapper.selectOrdersTotalList(Long.valueOf(localOrderId));
        for (CcairbagOrders orders : ordersList) {
            CcairbagOrderDetails detailsQuery = new CcairbagOrderDetails();
            detailsQuery.setOrderId(orders.getOrderId());
            List<CcairbagOrderDetails> detailsList = ccairbagOrderDetailsMapper.selectCcairbagOrderDetailsList(detailsQuery);
            for (CcairbagOrderDetails details : detailsList) {
                Long productId = details.getProdId();
                log.info("开始减库存操作：商品id："+productId+"，数量："+details.getQuantity());
                ccairbagProductsMapper.updateStock(productId, details.getQuantity());
                redisStockService.releaseStock(details.getProdId(),details.getQuantity());
            }
        }


    }


    private void addMessage(CcairbagOrdersTotal orders){
        List<CcairbagOrders> ordersList = ccairbagOrdersMapper.selectOrdersTotalList(orders.getOrderTotalId());
        for (CcairbagOrders orders1 : ordersList){
            CcairbagOrderDetails details = new CcairbagOrderDetails();
            details.setOrderId(orders1.getOrderId());
            List<CcairbagOrderDetails> ccairbagOrderDetails = ccairbagOrderDetailsMapper.selectCcairbagOrderDetailsList(details);
            CcairbagUserAddr addr = ccairbagUserAddrMapper.selectCcairbagUserAddrByAddrId(orders.getAddrId());
            CcairbagUsers users = ccairbagUsersMapper.selectCcairbagUsersByUserId(orders1.getUserId());
            CcairbagUsers shopUsers = ccairbagUsersMapper.selectCcairbagUsersByUserId(orders1.getShopUserId());

            for (CcairbagOrderDetails ccairbagOrderDetail : ccairbagOrderDetails){
                //给买家发送确认收货地址的 站内通知
                CcairbagNotification ccairbagNotification = new CcairbagNotification();
                Map<String,Object> params = new HashMap<>();
                params.put("productName",ccairbagOrderDetail.getProdName());
                params.put("houseNumber",addr.getHouseNumber());
                params.put("postCode",addr.getPostCode());
                params.put("getStreet",addr.getStreet());
                params.put("city",addr.getCity());
                params.put("stateProvince",addr.getStateProvince());
                params.put("country",addr.getCountry());
                Gson gson = new Gson();
                String content  = gson.toJson(params);
                ccairbagNotification.setContent(content);
                ccairbagNotification.setTitle("Order Address Confirmation");
                ccairbagNotification.setNotificationType("2");
                ccairbagNotification.setIsBroadcast(1);
                ccairbagNotification.setDeleted(0);
                ccairbagNotificationMapper.insertCcairbagNotification(ccairbagNotification);

                CcairbagNotificationUser ccairbagNotificationUser = new CcairbagNotificationUser();
                ccairbagNotificationUser.setNotificationId(ccairbagNotification.getNotificationId());
                ccairbagNotificationUser.setUserId(orders.getUserId());
                ccairbagNotificationUser.setReadStatus(0);
                ccairbagNotificationUser.setDeleted(0);
                ccairbagNotificationUserMapper.insertCcairbagNotificationUser(ccairbagNotificationUser);

                //给卖家发送有新订单 提醒发货的 站内通知 + 邮件
                CcairbagNotification ccairbagNotificationShop = new CcairbagNotification();
                Map<String,Object> paramsShop = new HashMap<>();
                paramsShop.put("content","You have a new order. Please go to the order list.");
                Gson gsonShop = new Gson();
                String contentShop  = gsonShop.toJson(paramsShop);
                ccairbagNotificationShop.setContent(contentShop);
                ccairbagNotificationShop.setTitle("new Order Confirmation");
                ccairbagNotificationShop.setNotificationType("2");
                ccairbagNotificationShop.setIsBroadcast(1);
                ccairbagNotificationShop.setDeleted(0);
                ccairbagNotificationMapper.insertCcairbagNotification(ccairbagNotificationShop);

                CcairbagNotificationUser ccairbagNotificationUserShop = new CcairbagNotificationUser();
                ccairbagNotificationUserShop.setNotificationId(ccairbagNotificationShop.getNotificationId());
                ccairbagNotificationUserShop.setUserId(orders1.getShopUserId());
                ccairbagNotificationUserShop.setReadStatus(0);
                ccairbagNotificationUserShop.setDeleted(0);
                ccairbagNotificationUserMapper.insertCcairbagNotificationUser(ccairbagNotificationUser);

                //发送模版邮件给商家： 显示 卖出的商品名称 数量  提醒发货
//                emailUtils.send("The merchant has shipped the order","The merchant has shipped the order. Tracking Number: "+dvyFlowNum+", Courier Service:"+logisticsName,users.getEmail());
                if (oConvertUtils.isNotEmpty(shopUsers.getEmail() )){
                    Map<String,Object> paramsEmail = new HashMap<>();
                    paramsEmail.put("productName",ccairbagOrderDetail.getProdName());
                    paramsEmail.put("orderNum",ccairbagOrderDetail.getOrderNumber());
                    paramsEmail.put("userName",users.getUserName());
                    paramsEmail.put("shopName",orders1.getShopName());
                    paramsEmail.put("productImg",ccairbagOrderDetail.getPic());
                    paramsEmail.put("money",ccairbagOrderDetail.getSubtotal());
                    emailUtils.sendTemplateMail(shopUsers.getEmail(), "Order Item ", "orderEmail.html", params);

                }

            }
        }


    }




    @Override
    public AppResult<CcairbagOrders> getOrderDetails(Long orderId) {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        Long userId = loginAppUser.getCcairbagUsers().getUserId();
        // 查询主订单
        CcairbagOrders order = ccairbagOrdersMapper.selectCcairbagOrdersByOrderId(orderId);
        if (order == null) {
            throw new RuntimeException("Order does not exist.");
        }
        if (!userId.equals(order.getUserId())) {
            throw new RuntimeException("订单不属于当前用户");
        }
        // 查询订单明细
        CcairbagOrderDetails detailsQuery = new CcairbagOrderDetails();
        detailsQuery.setOrderId(orderId);
        List<CcairbagOrderDetails> detailsList = ccairbagOrderDetailsMapper.selectCcairbagOrderDetailsList(detailsQuery);

        // 状态优先级映射（值越小优先级越高）
        Map<Integer, Integer> statusPriority = new HashMap<>();
        {
            statusPriority.put(0, 1);   // 待付款
            statusPriority.put(1, 2);   // 待发货
            statusPriority.put(2, 3);   // 待收货
            statusPriority.put(3, 4);   // 售后中
            statusPriority.put(4, 5);   // 已收货
            statusPriority.put(5, 6);   // 已完成
            statusPriority.put(6, 7);   // 已取消
            statusPriority.put(7, 8);   // 退款成功
            statusPriority.put(99, 0);  // 已关单（最高优先级）
        }

        if (!detailsList.isEmpty()) {
            int highestPriority = Integer.MAX_VALUE;
            Integer finalStatus = null;

            // 1. 遍历获取最高优先级状态
            for (CcairbagOrderDetails details : detailsList) {
                // 关联退款信息
                CcairbagOrderRefund refund = ccairbagOrderRefundMapper.selectRefundByOrderItemId(details.getDetailId());
                if (oConvertUtils.isNotEmpty(refund)) {
                    details.setCcairbagOrderRefund(refund);
                }

                // 获取当前状态优先级
                Integer currentPriority = statusPriority.get(details.getOrderStatus());
                if (currentPriority == null) continue;

                // 保留最高优先级状态
                if (currentPriority < highestPriority) {
                    highestPriority = currentPriority;
                    finalStatus = details.getOrderStatus();
                }
            }

            // 2. 更新主订单状态
            if (finalStatus != null && !finalStatus.equals(order.getOrderStatus())) {
                order.setOrderStatus(finalStatus);
                ccairbagOrdersMapper.updateCcairbagOrders(order);
            }
        }

        // 组合数据
        order.setOrderDetails(detailsList);
        CcairbagOrdersTotal total = ccairbagOrdersTotalMapper.queryById(order.getOrderTotalId());
        CcairbagOrderPayment payment = null;


        if (oConvertUtils.isNotEmpty(total)) {
            /**
             * 100（店铺订单总额）/150（支付订单总额）*9.5（手续费）=6.3333
             */
            BigDecimal payFee = order.getFinalAmount().divide(total.getTotalAmount(), 2, BigDecimal.ROUND_HALF_UP).multiply(total.getPaymentFee());
            payFee = payFee.setScale(2, BigDecimal.ROUND_HALF_UP);
            order.setPaymentFee(payFee);

            payment = ccairbagOrderPaymentMapper.selectCcairbagOrderPaymentByOrderId(total.getOrderTotalId());
            if (oConvertUtils.isNotEmpty(payment)){
                payment.setPayAmount(order.getFinalAmount());
                payment.setProductNums(order.getProductNums());
                order.setPayment(payment);
            }

        }

        List<LogisticsInfoDTO> logisticsInfoDTOList = new ArrayList<>();

        List<CcairbagUserAddr> addrList = new ArrayList<>();

        Map<String, LogisticsInfoDTO> logisticsMap = new HashMap<>();

        Map<Long, CcairbagUserAddr> addrMap = new HashMap<>();

        /***
         * 如果 物流单号不为空 就添加 物流对象LogisticsInfoDTO 到logisticsInfoDTOList
         * 如果物流对象LogisticsInfoDTO 相同就合并 不加进去logisticsInfoDTOList
         * details.getProdId() 详情中的物流id ，如果相同就合并 不然就加到addrList 中
         */
        for (CcairbagOrderDetails details : detailsList) {
            String logisticsNumber = details.getDvyFlowNum();

            if (oConvertUtils.isNotEmpty(logisticsNumber)) {
                if (!logisticsMap.containsKey(logisticsNumber)) {
                    LogisticsInfoDTO dto = new LogisticsInfoDTO();
                    dto.setLogisticsNumber(logisticsNumber);
                    dto.setLogisticsPlatformName(details.getLogisticsName());
                    dto.setLogisticsCode(details.getLogisticsCode());
                    dto.setCompletionTime(details.getFinallyTime());
                    if (oConvertUtils.isNotEmpty(payment)){
                        dto.setPaymentTime(payment.getCreateTime());
                    }
                    dto.setWaybillGenerationTime(details.getDvyTime());
                    logisticsMap.put(logisticsNumber, dto);
                    logisticsInfoDTOList.add(dto);
                }
            }
            if (logisticsInfoDTOList.isEmpty()){
                LogisticsInfoDTO dto1 = new LogisticsInfoDTO();
                if (oConvertUtils.isNotEmpty(payment)){
                    dto1.setPaymentTime(payment.getCreateTime());
                }
                logisticsInfoDTOList.add(dto1);
            }
            Long prodId = details.getProdId();

            if (!addrMap.containsKey(prodId)) {
                CcairbagOrders orders = ccairbagOrdersMapper.selectCcairbagOrdersByOrderId(details.getOrderId());
                CcairbagUserAddr addr = ccairbagUserAddrMapper.selectCcairbagUserAddrByAddrId(orders.getAddrId());
                addrMap.put(prodId, addr);
                addrList.add(addr);

            }
        }

        order.setAddrList(addrList);
        order.setLogisticsInfoDTOList(logisticsInfoDTOList);

        return new AppResult<>(order);
    }

    @Override
    public AppResult<CcairbagOrders> getSellerOrderDetails(Long orderId) {
        // 查询主订单
        CcairbagOrders order = ccairbagOrdersMapper.selectCcairbagOrdersByOrderId(orderId);
        if (order == null) {
            throw new RuntimeException("Order does not exist.");
        }
//        if (!userId.equals(order.getShopUserId())) {
//            throw new RuntimeException("订单不属于当前卖家");
//        }
        // 查询订单明细
        CcairbagOrderDetails detailsQuery = new CcairbagOrderDetails();
        detailsQuery.setOrderId(orderId);
        CcairbagUsers users = ccairbagUsersMapper.selectCcairbagUsersByUserId(order.getUserId());
        if (oConvertUtils.isNotEmpty(users)){
            order.setBuyerName(users.getUserName());
        }else {
            order.setBuyerName("visitor");
        }
        List<CcairbagOrderDetails> detailsList = ccairbagOrderDetailsMapper.selectCcairbagOrderDetailsList(detailsQuery);
        if (order.getOrderStatus() == 5) {
            Date updateTime = order.getUpdateTime();
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -7);
            Date sevenDaysAgo = calendar.getTime();
            if (new Date().after(sevenDaysAgo) && new Date().after(updateTime)) {
                // 满足当前时间大于 details.getUpdateTime() 7 天的条件
                order.setFundStatus(1);
            }else {
                order.setFundStatus(0);
            }
        }else {
            order.setFundStatus(0);
        }

        // 状态优先级映射（值越小优先级越高）
        Map<Integer, Integer> statusPriority = new HashMap<>();
        {
            statusPriority.put(0, 1);   // 待付款
            statusPriority.put(1, 2);   // 待发货
            statusPriority.put(2, 3);   // 待收货
            statusPriority.put(3, 4);   // 售后中
            statusPriority.put(4, 5);   // 已收货
            statusPriority.put(5, 6);   // 已完成
            statusPriority.put(6, 7);   // 已取消
            statusPriority.put(7, 8);   // 退款成功
            statusPriority.put(99, 0);  // 已关单（最高优先级）
        }

        if (!detailsList.isEmpty()) {
            int highestPriority = Integer.MAX_VALUE;
            Integer finalStatus = null;

            // 1. 遍历获取最高优先级状态
            for (CcairbagOrderDetails details : detailsList) {
                // 关联退款信息
                CcairbagOrderRefund refund = ccairbagOrderRefundMapper.selectRefundByOrderItemId(details.getDetailId());
                if (oConvertUtils.isNotEmpty(refund)) {
                    details.setCcairbagOrderRefund(refund);
                }

                // 获取当前状态优先级
                Integer currentPriority = statusPriority.get(details.getOrderStatus());
                if (currentPriority == null) continue;

                // 保留最高优先级状态
                if (currentPriority < highestPriority) {
                    highestPriority = currentPriority;
                    finalStatus = details.getOrderStatus();
                }
            }

            // 2. 更新主订单状态
            if (finalStatus != null && !finalStatus.equals(order.getOrderStatus())) {
                order.setOrderStatus(finalStatus);
                ccairbagOrdersMapper.updateCcairbagOrders(order);
            }
        }



        order.setOrderDetails(detailsList);
        CcairbagOrdersTotal total = ccairbagOrdersTotalMapper.queryById(order.getOrderTotalId());
        CcairbagOrderPayment payment = null;

        if (oConvertUtils.isNotEmpty(total)) {
            /**
             * 100（店铺订单总额）/150（支付订单总额）*9.5（手续费）=6.3333
             */
            BigDecimal payFee = order.getFinalAmount().divide(total.getTotalAmount(), 2, BigDecimal.ROUND_HALF_UP).multiply(total.getPaymentFee());
            payFee = payFee.setScale(2, BigDecimal.ROUND_HALF_UP);
            order.setPaymentFee(payFee);
            payment = ccairbagOrderPaymentMapper.selectCcairbagOrderPaymentByOrderId(total.getOrderTotalId());
            if (oConvertUtils.isNotEmpty(payment)){
                payment.setPayAmount(order.getFinalAmount());
                payment.setProductNums(order.getProductNums());
                order.setPayment(payment);
            }

        }

        List<LogisticsInfoDTO> logisticsInfoDTOList = new ArrayList<>();

        List<CcairbagUserAddr> addrList = new ArrayList<>();

        Map<String, LogisticsInfoDTO> logisticsMap = new HashMap<>();

        Map<Long, CcairbagUserAddr> addrMap = new HashMap<>();

        /***
         * 如果 物流单号不为空 就添加 物流对象LogisticsInfoDTO 到logisticsInfoDTOList
         * 如果物流对象LogisticsInfoDTO 相同就合并 不加进去logisticsInfoDTOList
         * details.getProdId() 详情中的物流id ，如果相同就合并 不然就加到addrList 中
         */
        for (CcairbagOrderDetails details : detailsList) {
            String logisticsNumber = details.getDvyFlowNum();

            if (oConvertUtils.isNotEmpty(logisticsNumber)) {
                if (!logisticsMap.containsKey(logisticsNumber)) {
                    LogisticsInfoDTO dto = new LogisticsInfoDTO();
                    dto.setLogisticsNumber(logisticsNumber);
                    dto.setLogisticsPlatformName(details.getLogisticsName());
                    dto.setLogisticsCode(details.getLogisticsCode());
                    dto.setCompletionTime(details.getFinallyTime());
                    if (oConvertUtils.isNotEmpty(payment)){
                        dto.setPaymentTime(payment.getCreateTime());
                    }
                    dto.setWaybillGenerationTime(details.getDvyTime());
                    logisticsMap.put(logisticsNumber, dto);
                    logisticsInfoDTOList.add(dto);
                }
            }
            if (logisticsInfoDTOList.isEmpty()){
                LogisticsInfoDTO dto1 = new LogisticsInfoDTO();
                if (oConvertUtils.isNotEmpty(payment)){
                    dto1.setPaymentTime(payment.getCreateTime());
                }
                logisticsInfoDTOList.add(dto1);
            }
            Long prodId = details.getProdId();
            if (!addrMap.containsKey(prodId)) {
//
                CcairbagOrders orders = ccairbagOrdersMapper.selectCcairbagOrdersByOrderId(details.getOrderId());
                CcairbagUserAddr addr = ccairbagUserAddrMapper.selectCcairbagUserAddrByAddrId(orders.getAddrId());
                addrMap.put(prodId, addr);
                addrList.add(addr);

            }
        }

        order.setAddrList(addrList);
        order.setLogisticsInfoDTOList(logisticsInfoDTOList);
        return new AppResult<>(order);
    }

    @Override
    public AppResult getOrderNum() {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        Long userId = loginAppUser.getCcairbagUsers().getUserId();
        CcairbagUsers users = ccairbagUsersMapper.selectCcairbagUsersByUserId(userId);
        if (oConvertUtils.isEmpty(users)){
            return AppResult.error("User does not exist.");
        }
        Map<String,String> map = ccairbagOrdersMapper.getOrderNum(userId);
        return new AppResult(map);
    }

    @Override
    public AppResult getShopOrderNum() {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        Long userId = loginAppUser.getCcairbagUsers().getUserId();
        CcairbagShops shops = ccairbagShopsMapper.getShopByUserId(userId);
        if (oConvertUtils.isEmpty(shops)){
            return AppResult.error("You do not have permission to view orders.");
        }
        CcairbagOrders orders = new CcairbagOrders();
        orders.setShopId(shops.getShopId());
        List<CcairbagOrders> ordersList = ccairbagOrdersMapper.selectCcairbagOrdersList(orders);
        List<Long> orderIds = new ArrayList<>();
        for (CcairbagOrders ccairbagOrders : ordersList){
            orderIds.add(ccairbagOrders.getOrderId());
        }
        if (orderIds.size() > 0){
            Map<String,String> map = ccairbagOrderDetailsMapper.getShopOrderNum(orderIds);
            BigDecimal availableMoney = ccairbagIncomeRecordsMapper.getSumMoneyFor7Days(shops.getShopId());
            map.put("availableMoney",availableMoney.toString());
            return new AppResult(map);
        }
        return AppResult.error("No orders available.");
    }


    @Override
    public AppResult closeOrder(String orderId) {
        //orderId 是否包含逗号,

        if (orderId.contains(",")){

            String[] orderIds = orderId.split(",");
            for (String id : orderIds) {

                CcairbagOrders order = ccairbagOrdersMapper.selectOrderBySeqForUpdate(Long.valueOf(id));

                if (order == null) {
                    return AppResult.error("订单已走回调逻辑，关单逻辑结束");
                }
                //关闭订单 改状态 + 释放库存
                CcairbagOrders ccairbagOrders = ccairbagOrdersMapper.selectCcairbagOrdersByOrderId(Long.valueOf(id));
                if (oConvertUtils.isEmpty(ccairbagOrders)){
                    throw new RuntimeException("Order does not exist.");
                }
                if (ccairbagOrders.getOrderStatus() != 0){
                    return AppResult.error("订单状态已修改,不能关单");
                }
                ccairbagOrders.setOrderStatus(99);
                ccairbagOrders.setCloseType(1);
                ccairbagOrders.setRefundSts("0");
                ccairbagOrdersMapper.updateCcairbagOrders(ccairbagOrders);
                CcairbagOrderDetails details = new CcairbagOrderDetails();
                details.setOrderId(ccairbagOrders.getOrderId());
                List<CcairbagOrderDetails> detailsList =ccairbagOrderDetailsMapper.selectCcairbagOrderDetailsList(details);
                for (CcairbagOrderDetails ccairbagOrderDetails : detailsList){
                    ccairbagOrderDetails.setOrderStatus(99);
                    // 没支付 自动关单 不会操作到数据库的库存  所以只改状态 并 释放库存
                    ccairbagOrderDetailsMapper.updateCcairbagOrderDetails(ccairbagOrderDetails);
                    redisStockService.releaseStock(ccairbagOrderDetails.getProdId(),ccairbagOrderDetails.getQuantity());
                }
            }
            return AppResult.success("关单成功");
        }else {
            CcairbagOrders orderx = ccairbagOrdersMapper.selectOrderBySeqForUpdate(Long.valueOf(orderId));

            if (orderx == null) {
                return AppResult.error("订单已走回调逻辑，关单逻辑结束");
            }
            //关闭订单 改状态 + 释放库存
            CcairbagOrders ccairbagOrders = ccairbagOrdersMapper.selectCcairbagOrdersByOrderId(Long.valueOf(orderId));
            if (oConvertUtils.isEmpty(ccairbagOrders)){
                throw new RuntimeException("Order does not exist.");
            }
            if (ccairbagOrders.getOrderStatus() > 0){
                return AppResult.error("订单状态已修改,不能关单");
            }
            ccairbagOrders.setOrderStatus(99);
            ccairbagOrders.setCloseType(1);
            ccairbagOrders.setRefundSts("0");
            ccairbagOrdersMapper.updateCcairbagOrders(ccairbagOrders);
            CcairbagOrderDetails details = new CcairbagOrderDetails();
            details.setOrderId(ccairbagOrders.getOrderId());
            List<CcairbagOrderDetails> detailsList =ccairbagOrderDetailsMapper.selectCcairbagOrderDetailsList(details);
            for (CcairbagOrderDetails ccairbagOrderDetails : detailsList){
                ccairbagOrderDetails.setOrderStatus(99);
                // 没支付 自动关单 不会操作到数据库的库存  所以只改状态 并 释放库存
                ccairbagOrderDetailsMapper.updateCcairbagOrderDetails(ccairbagOrderDetails);
                redisStockService.releaseStock(ccairbagOrderDetails.getProdId(),ccairbagOrderDetails.getQuantity());
            }
            return AppResult.success("关单成功");
        }

    }


    @Override
    public AppResult sendEmail(String orderId) {
        //发邮件 提醒发货
        CcairbagOrders orders = ccairbagOrdersMapper.selectCcairbagOrdersByOrderId(Long.valueOf(orderId));
        if (orders.getOrderStatus()!=1){
            return AppResult.success("订单状态已改,无需发货提醒邮件");
        }
        String time = DateUtils.parseDateToStr(orders.getPayTime(),"yyyy-MM-dd HH:mm:ss");
        CcairbagUsers users = ccairbagUsersMapper.selectCcairbagUsersByUserId(orders.getShopUserId());
        emailUtils.send("发货提醒","您有一笔订单客户于",time+"支付成功，请尽快发货",users.getEmail());

        return new AppResult("发货提醒邮件已发送");
    }



    /**
     * 校验订单信息：先扣款，后查询订单详情
     * @param id 前端提供的palpay的订单id
     * @return 订单存在并已扣款成功返回true
     */
    @Transactional
    @Override
    public AppResult onApproveOrder(String id) {
        PayPalApiConfig config = getConfig();
        try {
            IJPayHttpResponse orderResponse = PayPalApi.queryOrder(config, id);
            log.info(orderResponse.toString());
            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode event = objectMapper.readTree(orderResponse.getBody());
            JsonNode purchaseUnitsNode = event.path("purchase_units");
            JsonNode payerNode = event.path("payer");

            JSONObject jsonObject = JSON.parseObject(orderResponse.getBody());
            if ("APPROVED".equals(event.path("status").asText())){
                // 用户授权支付成功，进行扣款操作
                IJPayHttpResponse response = PayPalApi.captureOrder(config, id, "");
                log.info(response.toString());
                JSONObject captureJson = JSON.parseObject(response.getBody());
                log.info("订单已捕获，订单 ID: " + id + ", 状态: " + jsonObject.get("status"));
                if ("COMPLETED".equals(captureJson.get("status"))){
                    // 2.扣款操作成功，进行订单处理
                    if (!purchaseUnitsNode.isArray()) {
                        throw new RuntimeException("purchase_units is not an array");
                    }

                    for (JsonNode purchaseUnit : purchaseUnitsNode) {
                        // 处理每个 purchaseUnit
                        String localOrderId = purchaseUnit.path("reference_id").asText();

                        List<CcairbagOrders> ordersListx = ccairbagOrdersMapper.selectOrdersTotalList(Long.valueOf(localOrderId));
                        for (CcairbagOrders orders : ordersListx){
                            CcairbagOrders orderx = ccairbagOrdersMapper.selectOrderBySeqForUpdate(orders.getOrderId());
                            if (orderx == null) {
                                AppResult.error("订单已被锁，正在执行关单逻辑");
                            }
                        }
                        CcairbagOrdersTotal orders = ccairbagOrdersTotalMapper.queryById(Long.valueOf(localOrderId));

                        String value = purchaseUnit.path("amount").path("value")
                                .asText();
                        JsonNode payTimejson = event.path("create_time");

                        BigDecimal finalAmount = orders.getFinalAmount();
                        if (orders.getOrderStatus()!=0){
                            throw new RuntimeException("订单状态异常");
                        }

                        if (value.equals(finalAmount.toString())){
                            String payTimeString = payTimejson.asText();
                            Instant instant = Instant.parse(payTimeString);
                            Date date = Date.from(instant);
                            orders.setOrderStatus(1);
                            orders.setPayTime(date);
                            orders.setUpdateTime(new Date());
                            //更新订单表 添加订单支付表
                            ccairbagOrdersTotalMapper.update(orders);
                            List<CcairbagOrders> ordersList = ccairbagOrdersMapper.selectOrdersTotalList(Long.valueOf(localOrderId));
                            ccairbagOrdersMapper.updateOrderByTotalId(orders.getOrderTotalId());
                            for (CcairbagOrders orders1 : ordersList){
                                //更新订单明细表
                                ccairbagOrderDetailsMapper.updateOrderDetailsByOrderId(orders1.getOrderId());
                            }


                            CcairbagOrderPayment ccairbagOrderPayment = new CcairbagOrderPayment();
                            ccairbagOrderPayment.setBizPayNo(id);
                            ccairbagOrderPayment.setPayerAccount(payerNode.path("email_address").asText());
                            ccairbagOrderPayment.setPayerName(payerNode.path("name").path("given_name")
                                    .asText()+" "+ payerNode.path("name").path("surname").asText());
                            ccairbagOrderPayment.setTotalFreightAmount(new BigDecimal("0.00"));
                            ccairbagOrderPayment.setProductNums(orders.getProductNums());

                            ccairbagOrderPayment.setPayNo(orders.getOrderNumber());
                            ccairbagOrderPayment.setCreateTime(date);
                            ccairbagOrderPayment.setOrderId(Long.valueOf(localOrderId));
                            ccairbagOrderPayment.setPayStatus(0);
                            ccairbagOrderPayment.setPayType(0);
                            String currency = purchaseUnit.path("amount").path("currency_code")
                                    .asText();
                            ccairbagOrderPayment.setCreateTime(date);
                            ccairbagOrderPayment.setCurrency(currency);
                            ccairbagOrderPayment.setPayAmount(new BigDecimal(value));
                            ccairbagOrderPayment.setUserId(orders.getUserId());
                            ccairbagOrderPayment.setDeleted(0);
                            ccairbagOrderPaymentMapper.insertCcairbagOrderPayment(ccairbagOrderPayment);
                            // 减商品库存   添加 消息会话表 + 发一条消息记录 （确认订单位置）
                            updateRedisStock(Long.valueOf(localOrderId));
                            addMessage(orders);
                            //给商家钱包 添加对应金额
                            for (CcairbagOrders orderx : ordersList){
                               CcairbagShops shops = ccairbagShopsMapper.selectCcairbagShopsByShopId( orderx.getShopId());
                                shops.setWalletOrderIng(shops.getWalletOrderIng().add(orderx.getFinalAmount()));
                                ccairbagShopsMapper.updateCcairbagShops(shops);
                            }
                        }
                    }
                    return AppResult.success("订单捕获成功，待发货");
                }
            }else if("CREATED".equals(event.path("status").asText())){
                return AppResult.error("订单已创建，但未支付");
            }else if("COMPLETED".equals(event.path("status").asText())){
                return AppResult.error("订单已捕获成功，无需再次捕获");
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 2.扣款操作失败时也要查询订单，确认是否已扣款
            try {

                IJPayHttpResponse response = PayPalApi.queryOrder(config, id);
                log.info(response.toString());
                if (response.getStatus() == 200) {
                    log.info(response.toString());


                }

            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return AppResult.error("捕获成功");

    }

    @Override
    public AppResult getOrderList(Integer orderStauts,String search) {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        Long userId = loginAppUser.getCcairbagUsers().getUserId();
        CcairbagOrders ccairbagOrder = new CcairbagOrders();

        ccairbagOrder.setUserId(userId);
        ccairbagOrder.setOrderStatus(orderStauts);
        PageUtils.startPage();

        List<CcairbagOrders> ordersList = ccairbagOrdersMapper.selectCcairbagOrdersList(ccairbagOrder);


        for (CcairbagOrders ccairbagOrders : ordersList){

            int x = ccairbagOrdersMapper.isEvaluate(ccairbagOrders.getOrderId());
            if (x>0){
                ccairbagOrders.setIsEvaluate(1);
            }else {
                ccairbagOrders.setIsEvaluate(0);
            }
            CcairbagOrderDetails detailsx = new CcairbagOrderDetails();
            if (oConvertUtils.isNotEmpty(search)){
                detailsx.setProdName(search);
            }
            detailsx.setOrderId(ccairbagOrders.getOrderId());
            List<CcairbagOrderDetails> detailsList = ccairbagOrderDetailsMapper.selectCcairbagOrderDetailsList(detailsx);


            // 状态优先级映射（值越小优先级越高）
            Map<Integer, Integer> statusPriority = new HashMap<>();
            {
                statusPriority.put(0, 1);   // 待付款
                statusPriority.put(1, 2);   // 待发货
                statusPriority.put(2, 3);   // 待收货
                statusPriority.put(3, 4);   // 退款申请中
                statusPriority.put(4, 5);   // 待退货
                statusPriority.put(5, 6);   // 退货运输中
                statusPriority.put(6, 7);   // 平台介入中
                statusPriority.put(7, 8);   // 已收货
                statusPriority.put(8, 9);   // 已完成
                statusPriority.put(9, 10);   // 已取消
                statusPriority.put(10, 11);   // 退款成功
                statusPriority.put(99, 0);  // 已关单（最高优先级）
            }

            if (!detailsList.isEmpty()) {
                int highestPriority = Integer.MAX_VALUE;
                Integer finalStatus = null;

                // 1. 遍历获取最高优先级状态
                for (CcairbagOrderDetails details : detailsList) {
                    // 关联退款信息
                    CcairbagOrderRefund refund = ccairbagOrderRefundMapper.selectRefundByOrderItemId(details.getDetailId());
                    if (oConvertUtils.isNotEmpty(refund)) {
                        details.setCcairbagOrderRefund(refund);
                    }

                    // 获取当前状态优先级
                    Integer currentPriority = statusPriority.get(details.getOrderStatus());
                    if (currentPriority == null) continue;

                    // 保留最高优先级状态
                    if (currentPriority < highestPriority) {
                        highestPriority = currentPriority;
                        finalStatus = details.getOrderStatus();
                    }
                }

                // 2. 更新主订单状态
                if (finalStatus != null && !finalStatus.equals(ccairbagOrders.getOrderStatus())) {
                    ccairbagOrders.setOrderStatus(finalStatus);
                    ccairbagOrdersMapper.updateCcairbagOrders(ccairbagOrders);
                }


                ccairbagOrders.setOrderDetails(detailsList);

            }
        }
        ordersList.removeIf(order ->
                order.getOrderDetails() == null ||
                        order.getOrderDetails().isEmpty()
        );
        PageDataInfo<CcairbagOrders> pageDataInfo = new PageDataInfo<>(ordersList);
        PageUtils.clearPage();
        return new AppResult(pageDataInfo);
    }

    @Override
    public AppResult getShopOrderList(Integer orderStauts) {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        Long userId = loginAppUser.getCcairbagUsers().getUserId();
        CcairbagShops shops = ccairbagShopsMapper.getShopByUserId(userId);
        if (oConvertUtils.isEmpty(shops)){
            return AppResult.error("You do not have permission to view orders.");
        }
        CcairbagOrderDetails ccairbagOrderDetails = new CcairbagOrderDetails();
        ccairbagOrderDetails.setOrderStatus(orderStauts);
        ccairbagOrderDetails.setShopId(shops.getShopId());
        PageUtils.startPage();
        List<CcairbagOrderDetails> detailsList = ccairbagOrderDetailsMapper.selectCcairbagOrderDetailsListExt(ccairbagOrderDetails);
        for (CcairbagOrderDetails details : detailsList){
            CcairbagUsers users = ccairbagUsersMapper.selectCcairbagUsersByUserId(details.getUserId());
            log.info("users:"+users);
            if (!oConvertUtils.isEmpty(users)){
                details.setUserName(users.getUserName());
                details.setUserPhone(users.getPhone());
            }
            if (details.getUserId()==-99){
                CcairbagOrders orders = ccairbagOrdersMapper.selectCcairbagOrdersByOrderId(details.getOrderId());
                CcairbagUserAddr addr = ccairbagUserAddrMapper.selectCcairbagUserAddrByAddrId(orders.getAddrId());
                if (oConvertUtils.isNotEmpty(addr)){
                    details.setUserAddressExt(addr);
                    details.setPostCode(addr.getPostCode());
                }

            }else {
                CcairbagUserAddr addr = ccairbagUserAddrMapper.selectCcairbagUserAddrByUserIdExt(details.getUserId()).get(0);
                if (oConvertUtils.isNotEmpty(addr)){
                    details.setUserAddressExt(addr);
                    details.setPostCode(addr.getPostCode());
                }

            }

        }
        PageDataInfo<CcairbagOrderDetails> pageDataInfo = new PageDataInfo<>(detailsList);
        PageUtils.clearPage();
        return new AppResult(pageDataInfo);
    }

    @Override
    public AppResult delOrders(List<Long> orderIds, Integer userType) {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        Long userId = loginAppUser.getCcairbagUsers().getUserId();
        // 1:用户 2:商家
        if (userType == 1){
            for (Long orderId : orderIds){
                CcairbagOrders orders = ccairbagOrdersMapper.selectCcairbagOrdersByOrderId(orderId);
                if (!orders.getUserId().equals(userId)){
                   return new AppResult("该订单无关该用户");
                }
                if (oConvertUtils.isEmpty(orders)){
                    return new AppResult("订单不存在");
                }
                if (orders.getOrderStatus() == 99){
                    orders.setUserDelete(1);
                    ccairbagOrdersMapper.updateCcairbagOrders(orders);
                    ccairbagOrderDetailsMapper.updateDetailsDelByOrderId(orders.getOrderId());
                }
            }
        }else if (userType == 2){
            for (Long orderId : orderIds){

                CcairbagOrderDetails details = ccairbagOrderDetailsMapper.selectCcairbagOrderDetailsByDetailId(orderId);
                CcairbagOrders orders = ccairbagOrdersMapper.selectCcairbagOrdersByOrderId(details.getOrderId());

                if (!orders.getShopUserId().equals(userId)){
                    return new AppResult("该订单无关该用户");
                }
                if (oConvertUtils.isEmpty(details)){
                    return new AppResult("订单不存在");
                }
                if (details.getOrderStatus() == 99){
                    details.setShopDelete(1);
                    ccairbagOrderDetailsMapper.updateCcairbagOrderDetails(details);
                }
            }
        }else {
            return new AppResult("用户类型错误");
        }
        return new AppResult("success");
    }


    private void systemcloseOrderTask(String orderIds) throws AmqpException {
        MqMessage<String> m = new MqMessage<>();
        m.setQueueType(MQProperties.EXCHANGE_NAME);
        m.setBody(orderIds);
        //测试3分钟
        messageSender.sendMessageWithDelay(MQProperties.EXCHANGE_NAME,
                MQProperties.ROUTE_KEY,m , Long.valueOf(2*60*1000));

    }







}
