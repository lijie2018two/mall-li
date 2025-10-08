package com.ccairbag.api.mapper;


import com.ruoyi.system.api.domain.ccairbag.CcairbagProductPromotionRelations;
import com.ruoyi.system.api.domain.ccairbag.CcairbagProducts;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品 - 活动关联Mapper接口
 * 
 * @author lidabai
 * @date 2025-05-08
 */
public interface CcairbagProductPromotionRelationsMapper 
{
    /**
     * 查询商品 - 活动关联
     * 
     * @param id 商品 - 活动关联主键
     * @return 商品 - 活动关联
     */
    public CcairbagProductPromotionRelations selectCcairbagProductPromotionRelationsById(Long id);

    CcairbagProductPromotionRelations getPromotionRelationsInfo(@Param("productId") Long productId,@Param("activityId") Long activityId);
    List<CcairbagProducts> selectProductList(Long activityId);


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
     * 删除商品 - 活动关联
     * 
     * @param id 商品 - 活动关联主键
     * @return 结果
     */
    public int deleteCcairbagProductPromotionRelationsById(Long id);

    /**
     * 批量删除商品 - 活动关联
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCcairbagProductPromotionRelationsByIds(Long[] ids);
}
