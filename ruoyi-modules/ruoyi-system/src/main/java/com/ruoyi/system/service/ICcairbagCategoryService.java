package com.ruoyi.system.service;

import com.ruoyi.system.api.domain.ccairbag.CcairbagCategory;

import java.util.List;

/**
 * 商品类目Service接口
 * 
 * @author lidabai
 * @date 2025-02-24
 */
public interface ICcairbagCategoryService 
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



    public int deleteCcairbagCategoryByCategoryIds(Long[] categoryIds);




    List<CcairbagCategory> getCategoryTree();

}
