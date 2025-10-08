package com.ccairbag.api.service.impl;

import com.ccairbag.api.mapper.CcairbagOrderRefundMapper;
import com.ccairbag.api.service.ICcairbagOrderRefundService;
import com.ruoyi.system.api.domain.ccairbag.CcairbagOrderRefund;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单退款Service业务层处理
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Service
public class CcairbagOrderRefundServiceImpl implements ICcairbagOrderRefundService
{
    @Autowired
    private CcairbagOrderRefundMapper ccairbagOrderRefundMapper;

    /**
     * 查询订单退款
     * 
     * @param refundId 订单退款主键
     * @return 订单退款
     */
    @Override
    public CcairbagOrderRefund selectCcairbagOrderRefundByRefundId(Long refundId)
    {
        return ccairbagOrderRefundMapper.selectCcairbagOrderRefundByRefundId(refundId);
    }

    /**
     * 查询订单退款列表
     * 
     * @param ccairbagOrderRefund 订单退款
     * @return 订单退款
     */
    @Override
    public List<CcairbagOrderRefund> selectCcairbagOrderRefundList(CcairbagOrderRefund ccairbagOrderRefund)
    {
        return ccairbagOrderRefundMapper.selectCcairbagOrderRefundList(ccairbagOrderRefund);
    }

    /**
     * 新增订单退款
     * 
     * @param ccairbagOrderRefund 订单退款
     * @return 结果
     */
    @Override
    public int insertCcairbagOrderRefund(CcairbagOrderRefund ccairbagOrderRefund)
    {
        return ccairbagOrderRefundMapper.insertCcairbagOrderRefund(ccairbagOrderRefund);
    }

    /**
     * 修改订单退款
     * 
     * @param ccairbagOrderRefund 订单退款
     * @return 结果
     */
    @Override
    public int updateCcairbagOrderRefund(CcairbagOrderRefund ccairbagOrderRefund)
    {
        return ccairbagOrderRefundMapper.updateCcairbagOrderRefund(ccairbagOrderRefund);
    }

    /**
     * 批量删除订单退款
     * 
     * @param refundIds 需要删除的订单退款主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagOrderRefundByRefundIds(Long[] refundIds)
    {
        return ccairbagOrderRefundMapper.deleteCcairbagOrderRefundByRefundIds(refundIds);
    }

    /**
     * 删除订单退款信息
     * 
     * @param refundId 订单退款主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagOrderRefundByRefundId(Long refundId)
    {
        return ccairbagOrderRefundMapper.deleteCcairbagOrderRefundByRefundId(refundId);
    }
}
