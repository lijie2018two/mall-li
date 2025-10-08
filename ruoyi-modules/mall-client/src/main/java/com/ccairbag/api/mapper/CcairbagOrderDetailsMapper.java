package com.ccairbag.api.mapper;

import com.ruoyi.system.api.domain.ccairbag.CcairbagOrderDetails;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 订单详情Mapper接口
 * 
 * @author lidabai
 * @date 2025-02-20
 */
public interface CcairbagOrderDetailsMapper 
{
    /**
     * 查询订单详情
     * 
     * @param detailId 订单详情主键
     * @return 订单详情
     */
    public CcairbagOrderDetails selectCcairbagOrderDetailsByDetailId(Long detailId);

    /**
     * 查询订单详情列表
     * 
     * @param ccairbagOrderDetails 订单详情
     * @return 订单详情集合
     */
    public List<CcairbagOrderDetails> selectCcairbagOrderDetailsList(CcairbagOrderDetails ccairbagOrderDetails);

    public List<CcairbagOrderDetails> selectCcairbagOrderDetailsListExt(CcairbagOrderDetails ccairbagOrderDetails);

    public int getNumByUserId(Long userId);


    List<CcairbagOrderDetails> selectOrderItemsByOrderIds(@Param("orderIds") List<Long> orderIds);


    Map<String,String> getShopOrderNum(@Param("orderIds") List<Long> orderIds);
    /**
     * 新增订单详情
     * 
     * @param ccairbagOrderDetails 订单详情
     * @return 结果
     */
    public int insertCcairbagOrderDetails(CcairbagOrderDetails ccairbagOrderDetails);

    /**
     * 修改订单详情
     * 
     * @param ccairbagOrderDetails 订单详情
     * @return 结果
     */
    public int updateCcairbagOrderDetails(CcairbagOrderDetails ccairbagOrderDetails);


    public int updateOrderDetailsByOrderId(Long orderId);

    public int updateDetailsDelByOrderId(Long orderId);



    //查询 这个订单id 下的子订单状态是  是否还有待收货 orderstatus=2
    int getOrderStatusByOrderId(Long orderId);
    /**
     * 删除订单详情
     * 
     * @param detailId 订单详情主键
     * @return 结果
     */
    public int deleteCcairbagOrderDetailsByDetailId(Long detailId);

    /**
     * 批量删除订单详情
     * 
     * @param detailIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCcairbagOrderDetailsByDetailIds(Long[] detailIds);
}
