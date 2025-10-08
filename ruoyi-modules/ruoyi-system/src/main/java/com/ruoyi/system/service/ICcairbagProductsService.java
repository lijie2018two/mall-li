package com.ruoyi.system.service;

import com.ruoyi.system.api.domain.ccairbag.CcairbagProducts;

import java.util.List;

/**
 * 商品Service接口
 * 
 * @author lidabai
 * @date 2025-02-20
 */
public interface ICcairbagProductsService 
{
    /**
     * 查询商品
     * 
     * @param productId 商品主键
     * @return 商品
     */
    public CcairbagProducts selectCcairbagProductsByProductId(Long productId);

    /**
     * 查询商品列表
     * 
     * @param ccairbagProducts 商品
     * @return 商品集合
     */
    public List<CcairbagProducts> selectCcairbagProductsList(CcairbagProducts ccairbagProducts);

    /**
     * 新增商品
     * 
     * @param ccairbagProducts 商品
     * @return 结果
     */
    public int insertCcairbagProducts(CcairbagProducts ccairbagProducts);

    /**
     * 修改商品
     * 
     * @param ccairbagProducts 商品
     * @return 结果
     */
    public int updateCcairbagProducts(CcairbagProducts ccairbagProducts);

    /**
     * 批量删除商品
     * 
     * @param productIds 需要删除的商品主键集合
     * @return 结果
     */
    public int deleteCcairbagProductsByProductIds(Long[] productIds);

    /**
     * 删除商品信息
     * 
     * @param productId 商品主键
     * @return 结果
     */
    public int deleteCcairbagProductsByProductId(Long productId);

    /**
     * 批量新增商品
     *
     * @param ccairbagProductss 商品List
     * @return 结果
     */
    public int batchInsertCcairbagProducts(List<CcairbagProducts> ccairbagProductss);
}
