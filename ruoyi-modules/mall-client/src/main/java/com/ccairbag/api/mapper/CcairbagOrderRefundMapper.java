package com.ccairbag.api.mapper;

import com.ruoyi.system.api.domain.ccairbag.CcairbagOrderRefund;

import java.util.List;

/**
 * 订单退款Mapper接口
 * 
 * @author lidabai
 * @date 2025-02-20
 */
public interface CcairbagOrderRefundMapper 
{
    /**
     * 查询订单退款
     * 
     * @param refundId 订单退款主键
     * @return 订单退款
     */
    public CcairbagOrderRefund selectCcairbagOrderRefundByRefundId(Long refundId);

    public CcairbagOrderRefund selectRefundByOrderItemId(Long orderItemId);

    public CcairbagOrderRefund selectRefundByOrderItemIdExt(Long orderItemId);


    /**
     * 查询订单退款列表
     * 
     * @param ccairbagOrderRefund 订单退款
     * @return 订单退款集合
     */
    public List<CcairbagOrderRefund> selectCcairbagOrderRefundList(CcairbagOrderRefund ccairbagOrderRefund);

    /**
     * 新增订单退款
     * 
     * @param ccairbagOrderRefund 订单退款
     * @return 结果
     */
    public int insertCcairbagOrderRefund(CcairbagOrderRefund ccairbagOrderRefund);

    /**
     * 修改订单退款
     * 
     * @param ccairbagOrderRefund 订单退款
     * @return 结果
     */
    public int updateCcairbagOrderRefund(CcairbagOrderRefund ccairbagOrderRefund);

    /**
     * 删除订单退款
     * 
     * @param refundId 订单退款主键
     * @return 结果
     */
    public int deleteCcairbagOrderRefundByRefundId(Long refundId);

    /**
     * 批量删除订单退款
     * 
     * @param refundIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCcairbagOrderRefundByRefundIds(Long[] refundIds);
}
