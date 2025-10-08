package com.ccairbag.api.service.impl;

import com.ccairbag.api.mapper.CcairbagOrderPaymentMapper;
import com.ccairbag.api.service.ICcairbagOrderPaymentService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.system.api.domain.ccairbag.CcairbagOrderPayment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订单支付Service业务层处理
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Service
public class CcairbagOrderPaymentServiceImpl implements ICcairbagOrderPaymentService
{
    @Resource
    private CcairbagOrderPaymentMapper ccairbagOrderPaymentMapper;

    /**
     * 查询订单支付
     * 
     * @param paymentId 订单支付主键
     * @return 订单支付
     */
    @Override
    public CcairbagOrderPayment selectCcairbagOrderPaymentByPaymentId(Long paymentId)
    {
        return ccairbagOrderPaymentMapper.selectCcairbagOrderPaymentByPaymentId(paymentId);
    }

    /**
     * 查询订单支付列表
     * 
     * @param ccairbagOrderPayment 订单支付
     * @return 订单支付
     */
    @Override
    public List<CcairbagOrderPayment> selectCcairbagOrderPaymentList(CcairbagOrderPayment ccairbagOrderPayment)
    {
        return ccairbagOrderPaymentMapper.selectCcairbagOrderPaymentList(ccairbagOrderPayment);
    }

    /**
     * 新增订单支付
     * 
     * @param ccairbagOrderPayment 订单支付
     * @return 结果
     */
    @Override
    public int insertCcairbagOrderPayment(CcairbagOrderPayment ccairbagOrderPayment)
    {
        ccairbagOrderPayment.setCreateTime(DateUtils.getNowDate());
        return ccairbagOrderPaymentMapper.insertCcairbagOrderPayment(ccairbagOrderPayment);
    }

    /**
     * 修改订单支付
     * 
     * @param ccairbagOrderPayment 订单支付
     * @return 结果
     */
    @Override
    public int updateCcairbagOrderPayment(CcairbagOrderPayment ccairbagOrderPayment)
    {
        return ccairbagOrderPaymentMapper.updateCcairbagOrderPayment(ccairbagOrderPayment);
    }

    /**
     * 批量删除订单支付
     * 
     * @param paymentIds 需要删除的订单支付主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagOrderPaymentByPaymentIds(Long[] paymentIds)
    {
        return ccairbagOrderPaymentMapper.deleteCcairbagOrderPaymentByPaymentIds(paymentIds);
    }

    /**
     * 删除订单支付信息
     * 
     * @param paymentId 订单支付主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagOrderPaymentByPaymentId(Long paymentId)
    {
        return ccairbagOrderPaymentMapper.deleteCcairbagOrderPaymentByPaymentId(paymentId);
    }
}
