package com.ruoyi.system.mapper;

import com.ruoyi.system.api.domain.ccairbag.CcairbagOrders;

import java.util.List;

/**
 * 订单Mapper接口
 * 
 * @author lidabai
 * @date 2025-02-20
 */
public interface CcairbagOrdersMapper 
{
    /**
     * 查询订单
     * 
     * @param orderId 订单主键
     * @return 订单
     */
    public CcairbagOrders selectCcairbagOrdersByOrderId(Long orderId);

    /**
     * 查询订单列表
     * 
     * @param ccairbagOrders 订单
     * @return 订单集合
     */
    public List<CcairbagOrders> selectCcairbagOrdersList(CcairbagOrders ccairbagOrders);

    /**
     * 新增订单
     * 
     * @param ccairbagOrders 订单
     * @return 结果
     */
    public int insertCcairbagOrders(CcairbagOrders ccairbagOrders);

    /**
     * 修改订单
     * 
     * @param ccairbagOrders 订单
     * @return 结果
     */
    public int updateCcairbagOrders(CcairbagOrders ccairbagOrders);

    /**
     * 删除订单
     * 
     * @param orderId 订单主键
     * @return 结果
     */
    public int deleteCcairbagOrdersByOrderId(Long orderId);

    /**
     * 批量删除订单
     * 
     * @param orderIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCcairbagOrdersByOrderIds(Long[] orderIds);
}
