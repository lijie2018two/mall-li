package com.ruoyi.system.service;

import com.ruoyi.system.api.domain.ccairbag.CcairbagProductPromotionRelations;

import java.util.List;

/**
 * 商品 - 活动关联Service接口
 * 
 * @author lidabai
 * @date 2025-07-15
 */
public interface ICcairbagProductPromotionRelationsService 
{
    /**
     * 查询商品 - 活动关联
     * 
     * @param id 商品 - 活动关联主键
     * @return 商品 - 活动关联
     */
    public CcairbagProductPromotionRelations selectCcairbagProductPromotionRelationsById(Long id);

    /**
     * 查询商品 - 活动关联列表
     * 
     * @param ccairbagProductPromotionRelations 商品 - 活动关联
     * @return 商品 - 活动关联集合
     */
    public List<CcairbagProductPromotionRelations> selectCcairbagProductPromotionRelationsList(CcairbagProductPromotionRelations ccairbagProductPromotionRelations);

    /**
     * 新增商品 - 活动关联
     * 
     * @param ccairbagProductPromotionRelations 商品 - 活动关联
     * @return 结果
     */
    public int insertCcairbagProductPromotionRelations(CcairbagProductPromotionRelations ccairbagProductPromotionRelations);

    /**
     * 修改商品 - 活动关联
     * 
     * @param ccairbagProductPromotionRelations 商品 - 活动关联
     * @return 结果
     */
    public int updateCcairbagProductPromotionRelations(CcairbagProductPromotionRelations ccairbagProductPromotionRelations);

    /**
     * 批量删除商品 - 活动关联
     * 
     * @param ids 需要删除的商品 - 活动关联主键集合
     * @return 结果
     */
    public int deleteCcairbagProductPromotionRelationsByIds(Long[] ids);

    /**
     * 删除商品 - 活动关联信息
     * 
     * @param id 商品 - 活动关联主键
     * @return 结果
     */
    public int deleteCcairbagProductPromotionRelationsById(Long id);
}
