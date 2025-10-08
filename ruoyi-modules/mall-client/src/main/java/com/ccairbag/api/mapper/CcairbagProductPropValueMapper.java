package com.ccairbag.api.mapper;

import com.ruoyi.system.api.domain.ccairbag.CcairbagProductPropValue;

import java.util.List;

/**
 * 商品属性值Mapper接口
 * 
 * @author lidabai
 * @date 2025-02-20
 */
public interface CcairbagProductPropValueMapper 
{
    /**
     * 查询商品属性值
     * 
     * @param valueId 商品属性值主键
     * @return 商品属性值
     */
    public CcairbagProductPropValue selectCcairbagProductPropValueByValueId(Long valueId);

    /**
     * 查询商品属性值列表
     * 
     * @param ccairbagProductPropValue 商品属性值
     * @return 商品属性值集合
     */
    public List<CcairbagProductPropValue> selectCcairbagProductPropValueList(CcairbagProductPropValue ccairbagProductPropValue);

    /**
     * 新增商品属性值
     * 
     * @param ccairbagProductPropValue 商品属性值
     * @return 结果
     */
    public int insertCcairbagProductPropValue(CcairbagProductPropValue ccairbagProductPropValue);

    public int updatePropValueBy(CcairbagProductPropValue ccairbagProductPropValue);

    /**
     * 修改商品属性值
     * 
     * @param ccairbagProductPropValue 商品属性值
     * @return 结果
     */
    public int updateCcairbagProductPropValue(CcairbagProductPropValue ccairbagProductPropValue);

    /**
     * 删除商品属性值
     * 
     * @param valueId 商品属性值主键
     * @return 结果
     */
    public int deleteCcairbagProductPropValueByValueId(Long valueId);

    /**
     * 批量删除商品属性值
     * 
     * @param valueIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCcairbagProductPropValueByValueIds(Long[] valueIds);
}
