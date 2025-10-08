package com.ccairbag.api.service.impl;

import com.ccairbag.api.mapper.CcairbagProductPropMapper;
import com.ccairbag.api.service.ICcairbagProductPropService;
import com.ruoyi.system.api.domain.ccairbag.CcairbagProductProp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品属性Service业务层处理
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Service
public class CcairbagProductPropServiceImpl implements ICcairbagProductPropService
{
    @Autowired
    private CcairbagProductPropMapper ccairbagProductPropMapper;

    /**
     * 查询商品属性
     * 
     * @param propId 商品属性主键
     * @return 商品属性
     */
    @Override
    public CcairbagProductProp selectCcairbagProductPropByPropId(Long propId)
    {
        return ccairbagProductPropMapper.selectCcairbagProductPropByPropId(propId);
    }

    /**
     * 查询商品属性列表
     * 
     * @param ccairbagProductProp 商品属性
     * @return 商品属性
     */
    @Override
    public List<CcairbagProductProp> selectCcairbagProductPropList(CcairbagProductProp ccairbagProductProp)
    {
        return ccairbagProductPropMapper.selectCcairbagProductPropList(ccairbagProductProp);
    }

    /**
     * 新增商品属性
     * 
     * @param ccairbagProductProp 商品属性
     * @return 结果
     */
    @Override
    public int insertCcairbagProductProp(CcairbagProductProp ccairbagProductProp)
    {
        return ccairbagProductPropMapper.insertCcairbagProductProp(ccairbagProductProp);
    }

    /**
     * 修改商品属性
     * 
     * @param ccairbagProductProp 商品属性
     * @return 结果
     */
    @Override
    public int updateCcairbagProductProp(CcairbagProductProp ccairbagProductProp)
    {
        return ccairbagProductPropMapper.updateCcairbagProductProp(ccairbagProductProp);
    }

    /**
     * 批量删除商品属性
     * 
     * @param propIds 需要删除的商品属性主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagProductPropByPropIds(Long[] propIds)
    {
        return ccairbagProductPropMapper.deleteCcairbagProductPropByPropIds(propIds);
    }

    /**
     * 删除商品属性信息
     * 
     * @param propId 商品属性主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagProductPropByPropId(Long propId)
    {
        return ccairbagProductPropMapper.deleteCcairbagProductPropByPropId(propId);
    }
}
