package com.ccairbag.api.service.impl;

import com.ccairbag.api.mapper.CcairbagProductPropValueMapper;
import com.ccairbag.api.service.ICcairbagProductPropValueService;
import com.ruoyi.system.api.domain.ccairbag.CcairbagProductPropValue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品属性值Service业务层处理
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Service
public class CcairbagProductPropValueServiceImpl implements ICcairbagProductPropValueService
{
    @Resource
    private CcairbagProductPropValueMapper ccairbagProductPropValueMapper;

    /**
     * 查询商品属性值
     * 
     * @param valueId 商品属性值主键
     * @return 商品属性值
     */
    @Override
    public CcairbagProductPropValue selectCcairbagProductPropValueByValueId(Long valueId)
    {
        return ccairbagProductPropValueMapper.selectCcairbagProductPropValueByValueId(valueId);
    }

    /**
     * 查询商品属性值列表
     * 
     * @param ccairbagProductPropValue 商品属性值
     * @return 商品属性值
     */
    @Override
    public List<CcairbagProductPropValue> selectCcairbagProductPropValueList(CcairbagProductPropValue ccairbagProductPropValue)
    {
        return ccairbagProductPropValueMapper.selectCcairbagProductPropValueList(ccairbagProductPropValue);
    }

    /**
     * 新增商品属性值
     * 
     * @param ccairbagProductPropValue 商品属性值
     * @return 结果
     */
    @Override
    public int insertCcairbagProductPropValue(CcairbagProductPropValue ccairbagProductPropValue)
    {
        return ccairbagProductPropValueMapper.insertCcairbagProductPropValue(ccairbagProductPropValue);
    }

    /**
     * 修改商品属性值
     * 
     * @param ccairbagProductPropValue 商品属性值
     * @return 结果
     */
    @Override
    public int updateCcairbagProductPropValue(CcairbagProductPropValue ccairbagProductPropValue)
    {
        return ccairbagProductPropValueMapper.updateCcairbagProductPropValue(ccairbagProductPropValue);
    }

    /**
     * 批量删除商品属性值
     * 
     * @param valueIds 需要删除的商品属性值主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagProductPropValueByValueIds(Long[] valueIds)
    {
        return ccairbagProductPropValueMapper.deleteCcairbagProductPropValueByValueIds(valueIds);
    }

    /**
     * 删除商品属性值信息
     * 
     * @param valueId 商品属性值主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagProductPropValueByValueId(Long valueId)
    {
        return ccairbagProductPropValueMapper.deleteCcairbagProductPropValueByValueId(valueId);
    }
}
