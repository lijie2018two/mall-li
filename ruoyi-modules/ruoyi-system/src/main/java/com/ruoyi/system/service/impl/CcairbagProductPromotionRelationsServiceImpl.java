package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.system.api.domain.ccairbag.CcairbagProductPromotionRelations;
import com.ruoyi.system.mapper.CcairbagProductPromotionRelationsMapper;
import com.ruoyi.system.service.ICcairbagProductPromotionRelationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品 - 活动关联Service业务层处理
 * 
 * @author lidabai
 * @date 2025-07-15
 */
@Service
public class CcairbagProductPromotionRelationsServiceImpl implements ICcairbagProductPromotionRelationsService 
{
    @Autowired
    private CcairbagProductPromotionRelationsMapper ccairbagProductPromotionRelationsMapper;

    /**
     * 查询商品 - 活动关联
     * 
     * @param id 商品 - 活动关联主键
     * @return 商品 - 活动关联
     */
    @Override
    public CcairbagProductPromotionRelations selectCcairbagProductPromotionRelationsById(Long id)
    {
        return ccairbagProductPromotionRelationsMapper.selectCcairbagProductPromotionRelationsById(id);
    }

    /**
     * 查询商品 - 活动关联列表
     * 
     * @param ccairbagProductPromotionRelations 商品 - 活动关联
     * @return 商品 - 活动关联
     */
    @Override
    public List<CcairbagProductPromotionRelations> selectCcairbagProductPromotionRelationsList(CcairbagProductPromotionRelations ccairbagProductPromotionRelations)
    {
        return ccairbagProductPromotionRelationsMapper.selectCcairbagProductPromotionRelationsList(ccairbagProductPromotionRelations);
    }

    /**
     * 新增商品 - 活动关联
     * 
     * @param ccairbagProductPromotionRelations 商品 - 活动关联
     * @return 结果
     */
    @Override
    public int insertCcairbagProductPromotionRelations(CcairbagProductPromotionRelations ccairbagProductPromotionRelations)
    {
        ccairbagProductPromotionRelations.setCreateTime(DateUtils.getNowDate());
        return ccairbagProductPromotionRelationsMapper.insertCcairbagProductPromotionRelations(ccairbagProductPromotionRelations);
    }

    /**
     * 修改商品 - 活动关联
     * 
     * @param ccairbagProductPromotionRelations 商品 - 活动关联
     * @return 结果
     */
    @Override
    public int updateCcairbagProductPromotionRelations(CcairbagProductPromotionRelations ccairbagProductPromotionRelations)
    {
        ccairbagProductPromotionRelations.setUpdateTime(DateUtils.getNowDate());
        return ccairbagProductPromotionRelationsMapper.updateCcairbagProductPromotionRelations(ccairbagProductPromotionRelations);
    }

    /**
     * 批量删除商品 - 活动关联
     * 
     * @param ids 需要删除的商品 - 活动关联主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagProductPromotionRelationsByIds(Long[] ids)
    {
        return ccairbagProductPromotionRelationsMapper.deleteCcairbagProductPromotionRelationsByIds(ids);
    }

    /**
     * 删除商品 - 活动关联信息
     * 
     * @param id 商品 - 活动关联主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagProductPromotionRelationsById(Long id)
    {
        return ccairbagProductPromotionRelationsMapper.deleteCcairbagProductPromotionRelationsById(id);
    }
}
