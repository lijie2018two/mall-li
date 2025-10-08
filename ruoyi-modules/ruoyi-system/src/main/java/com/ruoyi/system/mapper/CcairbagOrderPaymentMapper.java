package com.ruoyi.system.mapper;

import com.ruoyi.system.api.domain.ccairbag.CcairbagOrderPayment;

import java.util.List;

/**
 * 订单支付Mapper接口
 * 
 * @author lidabai
 * @date 2025-02-20
 */
public interface CcairbagOrderPaymentMapper 
{
    /**
     * 查询订单支付
     * 
     * @param paymentId 订单支付主键
     * @return 订单支付
     */
    public CcairbagOrderPayment selectCcairbagOrderPaymentByPaymentId(Long paymentId);

    public CcairbagOrderPayment selectCcairbagOrderPaymentByOrderId(Long orderId);

    /**
     * 查询订单支付列表
     * 
     * @param ccairbagOrderPayment 订单支付
     * @return 订单支付集合
     */
    public List<CcairbagOrderPayment> selectCcairbagOrderPaymentList(CcairbagOrderPayment ccairbagOrderPayment);

    /**
     * 新增订单支付
     * 
     * @param ccairbagOrderPayment 订单支付
     * @return 结果
     */
    public int insertCcairbagOrderPayment(CcairbagOrderPayment ccairbagOrderPayment);

    /**
     * 修改订单支付
     * 
     * @param ccairbagOrderPayment 订单支付
     * @return 结果
     */
    public int updateCcairbagOrderPayment(CcairbagOrderPayment ccairbagOrderPayment);

    /**
     * 删除订单支付
     * 
     * @param paymentId 订单支付主键
     * @return 结果
     */
    public int deleteCcairbagOrderPaymentByPaymentId(Long paymentId);

    /**
     * 批量删除订单支付
     * 
     * @param paymentIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCcairbagOrderPaymentByPaymentIds(Long[] paymentIds);
}
