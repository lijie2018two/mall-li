package com.ccairbag.api.mapper;

import com.ruoyi.system.api.domain.ccairbag.CcairbagOrders;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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

    public List<CcairbagOrders> selectOrdersTotalList(Long orderTotalId);


    /**
     * 使用行锁查询订单（用于并发控制）
     * @return 订单信息
     */
    CcairbagOrders selectOrderBySeqForUpdate(Long orderId);

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


    int updateOrderByTotalId(Long orderTotalId);

    Map<String,String> getOrderNum(Long userId);

    //商家总销售额
    BigDecimal getShopTotalSales(Long shopId);

    //总订单数
    int getShopTotalOrder(Long shopId);
    //商品数量
    int getShopTotalGoods(Long shopId);

    int isEvaluate(Long orderId);
}
