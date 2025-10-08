package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.system.api.domain.ccairbag.CcairbagOrderDetails;
import com.ruoyi.system.mapper.CcairbagOrderDetailsMapper;
import com.ruoyi.system.service.ICcairbagOrderDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订单详情Service业务层处理
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Service
public class CcairbagOrderDetailsServiceImpl implements ICcairbagOrderDetailsService 
{
    @Resource
    private CcairbagOrderDetailsMapper ccairbagOrderDetailsMapper;

    /**
     * 查询订单详情
     * 
     * @param detailId 订单详情主键
     * @return 订单详情
     */
    @Override
    public CcairbagOrderDetails selectCcairbagOrderDetailsByDetailId(Long detailId)
    {
        return ccairbagOrderDetailsMapper.selectCcairbagOrderDetailsByDetailId(detailId);
    }

    /**
     * 查询订单详情列表
     * 
     * @param ccairbagOrderDetails 订单详情
     * @return 订单详情
     */
    @Override
    public List<CcairbagOrderDetails> selectCcairbagOrderDetailsList(CcairbagOrderDetails ccairbagOrderDetails)
    {
        List<CcairbagOrderDetails> list = ccairbagOrderDetailsMapper.selectCcairbagOrderDetailsList(ccairbagOrderDetails);
        return list;
    }

    /**
     * 新增订单详情
     * 
     * @param ccairbagOrderDetails 订单详情
     * @return 结果
     */
    @Override
    public int insertCcairbagOrderDetails(CcairbagOrderDetails ccairbagOrderDetails)
    {
        ccairbagOrderDetails.setCreateTime(DateUtils.getNowDate());
        return ccairbagOrderDetailsMapper.insertCcairbagOrderDetails(ccairbagOrderDetails);
    }

    /**
     * 修改订单详情
     * 
     * @param ccairbagOrderDetails 订单详情
     * @return 结果
     */
    @Override
    public int updateCcairbagOrderDetails(CcairbagOrderDetails ccairbagOrderDetails)
    {
        ccairbagOrderDetails.setUpdateTime(DateUtils.getNowDate());
        return ccairbagOrderDetailsMapper.updateCcairbagOrderDetails(ccairbagOrderDetails);
    }

    /**
     * 批量删除订单详情
     * 
     * @param detailIds 需要删除的订单详情主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagOrderDetailsByDetailIds(Long[] detailIds)
    {
        return ccairbagOrderDetailsMapper.deleteCcairbagOrderDetailsByDetailIds(detailIds);
    }

    /**
     * 删除订单详情信息
     * 
     * @param detailId 订单详情主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagOrderDetailsByDetailId(Long detailId)
    {
        return ccairbagOrderDetailsMapper.deleteCcairbagOrderDetailsByDetailId(detailId);
    }
}
