package com.ccairbag.api.service;

import com.ruoyi.system.api.domain.ccairbag.CcairbagProductProp;

import java.util.List;

/**
 * 商品属性Service接口
 * 
 * @author lidabai
 * @date 2025-02-20
 */
public interface ICcairbagProductPropService 
{
    /**
     * 查询商品属性
     * 
     * @param propId 商品属性主键
     * @return 商品属性
     */
    public CcairbagProductProp selectCcairbagProductPropByPropId(Long propId);

    /**
     * 查询商品属性列表
     * 
     * @param ccairbagProductProp 商品属性
     * @return 商品属性集合
     */
    public List<CcairbagProductProp> selectCcairbagProductPropList(CcairbagProductProp ccairbagProductProp);

    /**
     * 新增商品属性
     * 
     * @param ccairbagProductProp 商品属性
     * @return 结果
     */
    public int insertCcairbagProductProp(CcairbagProductProp ccairbagProductProp);

    /**
     * 修改商品属性
     * 
     * @param ccairbagProductProp 商品属性
     * @return 结果
     */
    public int updateCcairbagProductProp(CcairbagProductProp ccairbagProductProp);

    /**
     * 批量删除商品属性
     * 
     * @param propIds 需要删除的商品属性主键集合
     * @return 结果
     */
    public int deleteCcairbagProductPropByPropIds(Long[] propIds);

    /**
     * 删除商品属性信息
     * 
     * @param propId 商品属性主键
     * @return 结果
     */
    public int deleteCcairbagProductPropByPropId(Long propId);
}
