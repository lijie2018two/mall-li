package com.ccairbag.api.mapper;

import com.ruoyi.system.api.domain.ccairbag.CcairbagCategory;

import java.util.List;

/**
 * 商品类目Mapper接口
 * 
 * @author lidabai
 * @date 2025-02-24
 */
public interface CcairbagCategoryMapper 
{
    /**
     * 查询商品类目
     * 
     * @param categoryId 商品类目主键
     * @return 商品类目
     */
    public CcairbagCategory selectCcairbagCategoryByCategoryId(Long categoryId);



    /**
     * 查询商品类目列表
     * 
     * @param ccairbagCategory 商品类目
     * @return 商品类目集合
     */
    public List<CcairbagCategory> selectCcairbagCategoryList(CcairbagCategory ccairbagCategory);

    /**
     * 新增商品类目
     * 
     * @param ccairbagCategory 商品类目
     * @return 结果
     */
    public int insertCcairbagCategory(CcairbagCategory ccairbagCategory);

    /**
     * 修改商品类目
     * 
     * @param ccairbagCategory 商品类目
     * @return 结果
     */
    public int updateCcairbagCategory(CcairbagCategory ccairbagCategory);

    /**
     * 删除商品类目
     * 
     * @param categoryId 商品类目主键
     * @return 结果
     */
    public int deleteCcairbagCategoryByCategoryId(Long categoryId);

    /**
     * 批量删除商品类目
     * 
     * @param categoryIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCcairbagCategoryByCategoryIds(Long[] categoryIds);

    List<CcairbagCategory> selectCcairbagCategoriesByParentId(Long parentId);

    //获取商品热门分类
    List<CcairbagCategory> getShopPopularCategoriesBySales(Long shopId);

}
