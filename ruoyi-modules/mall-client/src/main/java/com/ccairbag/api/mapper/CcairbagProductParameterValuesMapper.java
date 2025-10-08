package com.ccairbag.api.mapper;

import com.ruoyi.system.api.domain.ccairbag.CcairbagProductParameterValues;

import java.util.List;

/**
 * 商品参数值表Mapper接口
 * 
 * @author lidabai
 * @date 2025-02-24
 */
public interface CcairbagProductParameterValuesMapper 
{
    /**
     * 查询商品参数值表
     * 
     * @param valueId 商品参数值表主键
     * @return 商品参数值表
     */
    public CcairbagProductParameterValues selectCcairbagProductParameterValuesByValueId(Long valueId);

    /**
     * 查询商品参数值表列表
     * 
     * @param ccairbagProductParameterValues 商品参数值表
     * @return 商品参数值表集合
     */
    public List<CcairbagProductParameterValues> selectCcairbagProductParameterValuesList(CcairbagProductParameterValues ccairbagProductParameterValues);

    /**
     * 新增商品参数值表
     * 
     * @param ccairbagProductParameterValues 商品参数值表
     * @return 结果
     */
    public int insertCcairbagProductParameterValues(CcairbagProductParameterValues ccairbagProductParameterValues);

    /**
     * 修改商品参数值表
     * 
     * @param ccairbagProductParameterValues 商品参数值表
     * @return 结果
     */
    public int updateCcairbagProductParameterValues(CcairbagProductParameterValues ccairbagProductParameterValues);

    /**
     * 删除商品参数值表
     * 
     * @param valueId 商品参数值表主键
     * @return 结果
     */
    public int deleteCcairbagProductParameterValuesByValueId(Long valueId);

    /**
     * 批量删除商品参数值表
     * 
     * @param valueIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCcairbagProductParameterValuesByValueIds(Long[] valueIds);

    List<CcairbagProductParameterValues> getParameterValuesByProductId(Long productId);
}
