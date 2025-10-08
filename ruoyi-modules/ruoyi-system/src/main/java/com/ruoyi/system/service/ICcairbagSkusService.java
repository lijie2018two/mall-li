package com.ruoyi.system.service;

import com.ruoyi.system.api.domain.ccairbag.CcairbagSkus;

import java.util.List;

/**
 * skuService接口
 * 
 * @author lidabai
 * @date 2025-02-20
 */
public interface ICcairbagSkusService 
{
    /**
     * 查询sku
     * 
     * @param skuId sku主键
     * @return sku
     */
    public CcairbagSkus selectCcairbagSkusBySkuId(Long skuId);

    /**
     * 查询sku列表
     * 
     * @param ccairbagSkus sku
     * @return sku集合
     */
    public List<CcairbagSkus> selectCcairbagSkusList(CcairbagSkus ccairbagSkus);

    /**
     * 新增sku
     * 
     * @param ccairbagSkus sku
     * @return 结果
     */
    public int insertCcairbagSkus(CcairbagSkus ccairbagSkus);

    /**
     * 修改sku
     * 
     * @param ccairbagSkus sku
     * @return 结果
     */
    public int updateCcairbagSkus(CcairbagSkus ccairbagSkus);

    /**
     * 批量删除sku
     * 
     * @param skuIds 需要删除的sku主键集合
     * @return 结果
     */
    public int deleteCcairbagSkusBySkuIds(Long[] skuIds);

    /**
     * 删除sku信息
     * 
     * @param skuId sku主键
     * @return 结果
     */
    public int deleteCcairbagSkusBySkuId(Long skuId);
}
