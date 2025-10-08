package com.ruoyi.system.mapper;

import com.ruoyi.system.api.domain.ccairbag.CcairbagOrderDetails;

import java.util.List;

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
