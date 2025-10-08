package com.ruoyi.system.service;

import com.ruoyi.system.api.domain.ccairbag.CcairbagShops;

import java.util.List;

/**
 * 店铺Service接口
 * 
 * @author lidabai
 * @date 2025-02-20
 */
public interface ICcairbagShopsService 
{
    /**
     * 查询店铺
     * 
     * @param shopId 店铺主键
     * @return 店铺
     */
    public CcairbagShops selectCcairbagShopsByShopId(Long shopId);

    /**
     * 查询店铺列表
     * 
     * @param ccairbagShops 店铺
     * @return 店铺集合
     */
    public List<CcairbagShops> selectCcairbagShopsList(CcairbagShops ccairbagShops);

    /**
     * 新增店铺
     * 
     * @param ccairbagShops 店铺
     * @return 结果
     */
    public int insertCcairbagShops(CcairbagShops ccairbagShops);

    /**
     * 修改店铺
     * 
     * @param ccairbagShops 店铺
     * @return 结果
     */
    public int updateCcairbagShops(CcairbagShops ccairbagShops);

    /**
     * 批量删除店铺
     * 
     * @param shopIds 需要删除的店铺主键集合
     * @return 结果
     */
    public int deleteCcairbagShopsByShopIds(Long[] shopIds);

    /**
     * 删除店铺信息
     * 
     * @param shopId 店铺主键
     * @return 结果
     */
    public int deleteCcairbagShopsByShopId(Long shopId);
}
