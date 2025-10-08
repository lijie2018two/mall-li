package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.system.api.domain.ccairbag.CcairbagOrders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.CcairbagOrdersMapper;
import com.ruoyi.system.service.ICcairbagOrdersService;

/**
 * 订单Service业务层处理
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Service
public class CcairbagOrdersServiceImpl implements ICcairbagOrdersService 
{
    @Autowired
    private CcairbagOrdersMapper ccairbagOrdersMapper;

    /**
     * 查询订单
     * 
     * @param orderId 订单主键
     * @return 订单
     */
    @Override
    public CcairbagOrders selectCcairbagOrdersByOrderId(Long orderId)
    {
        return ccairbagOrdersMapper.selectCcairbagOrdersByOrderId(orderId);
    }

    /**
     * 查询订单列表
     * 
     * @param ccairbagOrders 订单
     * @return 订单
     */
    @Override
    public List<CcairbagOrders> selectCcairbagOrdersList(CcairbagOrders ccairbagOrders)
    {
        return ccairbagOrdersMapper.selectCcairbagOrdersList(ccairbagOrders);
    }

    /**
     * 新增订单
     * 
     * @param ccairbagOrders 订单
     * @return 结果
     */
    @Override
    public int insertCcairbagOrders(CcairbagOrders ccairbagOrders)
    {
        ccairbagOrders.setCreateTime(DateUtils.getNowDate());
        return ccairbagOrdersMapper.insertCcairbagOrders(ccairbagOrders);
    }

    /**
     * 修改订单
     * 
     * @param ccairbagOrders 订单
     * @return 结果
     */
    @Override
    public int updateCcairbagOrders(CcairbagOrders ccairbagOrders)
    {
        ccairbagOrders.setUpdateTime(DateUtils.getNowDate());
        return ccairbagOrdersMapper.updateCcairbagOrders(ccairbagOrders);
    }

    /**
     * 批量删除订单
     * 
     * @param orderIds 需要删除的订单主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagOrdersByOrderIds(Long[] orderIds)
    {
        return ccairbagOrdersMapper.deleteCcairbagOrdersByOrderIds(orderIds);
    }

    /**
     * 删除订单信息
     * 
     * @param orderId 订单主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagOrdersByOrderId(Long orderId)
    {
        return ccairbagOrdersMapper.deleteCcairbagOrdersByOrderId(orderId);
    }
}
