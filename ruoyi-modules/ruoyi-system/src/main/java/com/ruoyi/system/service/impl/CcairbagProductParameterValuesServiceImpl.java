package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.system.api.domain.ccairbag.CcairbagProductParameterValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.CcairbagProductParameterValuesMapper;
import com.ruoyi.system.service.ICcairbagProductParameterValuesService;

/**
 * 商品参数值表Service业务层处理
 * 
 * @author lidabai
 * @date 2025-02-24
 */
@Service
public class CcairbagProductParameterValuesServiceImpl implements ICcairbagProductParameterValuesService 
{
    @Autowired
    private CcairbagProductParameterValuesMapper ccairbagProductParameterValuesMapper;

    /**
     * 查询商品参数值表
     * 
     * @param valueId 商品参数值表主键
     * @return 商品参数值表
     */
    @Override
    public CcairbagProductParameterValues selectCcairbagProductParameterValuesByValueId(Long valueId)
    {
        return ccairbagProductParameterValuesMapper.selectCcairbagProductParameterValuesByValueId(valueId);
    }

    /**
     * 查询商品参数值表列表
     * 
     * @param ccairbagProductParameterValues 商品参数值表
     * @return 商品参数值表
     */
    @Override
    public List<CcairbagProductParameterValues> selectCcairbagProductParameterValuesList(CcairbagProductParameterValues ccairbagProductParameterValues)
    {
        return ccairbagProductParameterValuesMapper.selectCcairbagProductParameterValuesList(ccairbagProductParameterValues);
    }

    /**
     * 新增商品参数值表
     * 
     * @param ccairbagProductParameterValues 商品参数值表
     * @return 结果
     */
    @Override
    public int insertCcairbagProductParameterValues(CcairbagProductParameterValues ccairbagProductParameterValues)
    {
        ccairbagProductParameterValues.setCreateTime(DateUtils.getNowDate());
        return ccairbagProductParameterValuesMapper.insertCcairbagProductParameterValues(ccairbagProductParameterValues);
    }

    /**
     * 修改商品参数值表
     * 
     * @param ccairbagProductParameterValues 商品参数值表
     * @return 结果
     */
    @Override
    public int updateCcairbagProductParameterValues(CcairbagProductParameterValues ccairbagProductParameterValues)
    {
        ccairbagProductParameterValues.setUpdateTime(DateUtils.getNowDate());
        return ccairbagProductParameterValuesMapper.updateCcairbagProductParameterValues(ccairbagProductParameterValues);
    }

    /**
     * 批量删除商品参数值表
     * 
     * @param valueIds 需要删除的商品参数值表主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagProductParameterValuesByValueIds(Long[] valueIds)
    {
        return ccairbagProductParameterValuesMapper.deleteCcairbagProductParameterValuesByValueIds(valueIds);
    }

    /**
     * 删除商品参数值表信息
     * 
     * @param valueId 商品参数值表主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagProductParameterValuesByValueId(Long valueId)
    {
        return ccairbagProductParameterValuesMapper.deleteCcairbagProductParameterValuesByValueId(valueId);
    }
}
