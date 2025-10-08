package com.ccairbag.api.service;

import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.system.api.domain.ccairbag.dto.RefundOrderDTO;
import io.swagger.annotations.ApiParam;

/**
 * 订单详情Service接口
 * 
 * @author lidabai
 * @date 2025-02-20
 */
public interface ICcairbagOrderDetailsService 
{



    AppResult setDvyFlowNum(Long detailId,String dvyFlowNum,String courierCode,String  logisticsName) throws Exception;

    AppResult refundOrderIng(Long detailId);




    AppResult shopRefundOrderIng(Long detailId);

    AppResult refundOrder(RefundOrderDTO refundOrderDTO);

    AppResult refundOrderExt(RefundOrderDTO refundOrderDTO);


    AppResult refundShopOrder(RefundOrderDTO refundOrderDTO);


    AppResult getRefundOrderType();


    AppResult refundShopOrderSign(RefundOrderDTO refundOrderDTO);

    AppResult refundShopOrderSignExt(RefundOrderDTO refundOrderDTO);

    AppResult manualRefund(RefundOrderDTO refundOrderDTO);

    AppResult platformIntervention(RefundOrderDTO refundOrderDTO);
    AppResult signOrder(Long detailId);
    AppResult getDvyFlowInfo(@ApiParam("detailId") Long detailId);

    AppResult getDvyCompanyList();

    AppResult signOrder7Day(String detailId);

    AppResult cancelorder5Day(String detailId);

    AppResult createTrack( String dvyFlowNum, String  courierCode, String  ordernum);




}
