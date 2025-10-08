package com.ruoyi.system.service;

import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.system.api.domain.ccairbag.CcairbagOrderRefund;

import java.util.List;

/**
 * 订单退款Service接口
 * 
 * @author lidabai
 * @date 2025-02-20
 */
public interface ICcairbagOrderRefundService 
{


    /**
     * 查询订单退款列表
     * 
     * @param ccairbagOrderRefund 订单退款
     * @return 订单退款集合
     */
    public List<CcairbagOrderRefund> selectCcairbagOrderRefundList(CcairbagOrderRefund ccairbagOrderRefund);




    AjaxResult handleSellerProblem(Long refundId);

    AjaxResult handleBuyerProblem(Long refundId);


}
