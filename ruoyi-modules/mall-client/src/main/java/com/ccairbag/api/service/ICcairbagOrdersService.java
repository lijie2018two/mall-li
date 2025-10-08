package com.ccairbag.api.service;

import com.alipay.api.AlipayApiException;
import com.ccairbag.api.domain.dto.CreateOrderDto;
import com.ccairbag.api.domain.dto.ShopCartDtoExt;
import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.system.api.domain.ccairbag.CcairbagOrders;
import com.ruoyi.system.api.domain.ccairbag.CcairbagUserAddr;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 订单Service接口
 * 
 * @author lidabai
 * @date 2025-02-20
 */
public interface ICcairbagOrdersService 
{


    AppResult createOrder( CreateOrderDto createOrderDto);

    AppResult createOrderExt( CreateOrderDto createOrderDto);

    AppResult updateOrderExt( CcairbagUserAddr ccairbagUserAddr);
    AppResult getOrderInfoExt(String orderId,String randomNum);
    AppResult<CcairbagOrders> createOrderByNegotiationId(Long id);
    AppResult createOrderByCart(ShopCartDtoExt shopCartDtoExt);

    AppResult generatePaymentLink(List<Long> orderIds);

    AppResult generateAliPayLink(List<Long> orderIds) throws AlipayApiException;

    AppResult generateStripePayLink(List<Long> orderIds);

    AppResult generateStripePayoutExt();
    AppResult generateVisaPay(List<Long> orderIds);


    AppResult getOrderAmount(List<Long> orderIds,int payType);
    String alinotify(HttpServletRequest request);

    void stripeNotify(HttpServletRequest request, HttpServletResponse response);

    AppResult getOrderInfo(String id);

    AppResult onApproveOrder( String id);

    AppResult getOrderList(Integer orderStauts,String search);

    AppResult getShopOrderList(Integer orderStauts);

    AppResult delOrders(List<Long> orderIds, Integer userType);
    AppResult<CcairbagOrders> getOrderDetails(Long orderId);

    AppResult<CcairbagOrders> getSellerOrderDetails(Long orderId);


    AppResult getOrderNum();

    AppResult getShopOrderNum();

    AppResult closeOrder(String orderId);

    AppResult sendEmail(String orderId);






}
