package com.ccairbag.api.service.impl;

import com.ccairbag.api.mapper.CcairbagCategoryMapper;
import com.ccairbag.api.service.ICcairbagCategoryService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.domain.ccairbag.CcairbagCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 商品类目Service业务层处理
 * 
 * @author lidabai
 * @date 2025-02-24
 */
@Service
public class CcairbagCategoryServiceImpl implements ICcairbagCategoryService
{
    @Resource
    private CcairbagCategoryMapper ccairbagCategoryMapper;

    /**
     * 查询商品类目
     * 
     * @param categoryId 商品类目主键
     * @return 商品类目
     */
    @Override
    public CcairbagCategory selectCcairbagCategoryByCategoryId(Long categoryId)
    {
        return ccairbagCategoryMapper.selectCcairbagCategoryByCategoryId(categoryId);
    }

    /**
     * 查询商品类目列表
     * 
     * @param ccairbagCategory 商品类目
     * @return 商品类目
     */
    @Override
    public List<CcairbagCategory> selectCcairbagCategoryList(CcairbagCategory ccairbagCategory)
    {
        return ccairbagCategoryMapper.selectCcairbagCategoryList(ccairbagCategory);
    }

    /**
     * 新增商品类目
     * 
     * @param ccairbagCategory 商品类目
     * @return 结果
     */
    @Override
    public int insertCcairbagCategory(CcairbagCategory ccairbagCategory)
    {

        int grade = ccairbagCategoryMapper.selectCcairbagCategoryByCategoryId(ccairbagCategory.getParentId()).getGrade();
        ccairbagCategory.setGrade(grade+1);
        ccairbagCategory.setDeleted(0);
        ccairbagCategory.setRecTime(DateUtils.getNowDate());
        ccairbagCategory.setCreateBy(SecurityUtils.getUsername());
        ccairbagCategory.setCreateTime(DateUtils.getNowDate());
        return ccairbagCategoryMapper.insertCcairbagCategory(ccairbagCategory);
    }

    /**
     * 修改商品类目
     * 
     * @param ccairbagCategory 商品类目
     * @return 结果
     */
    @Override
    public int updateCcairbagCategory(CcairbagCategory ccairbagCategory)
    {
        ccairbagCategory.setUpdateTime(DateUtils.getNowDate());
        ccairbagCategory.setUpdateBy(SecurityUtils.getUsername());
        return ccairbagCategoryMapper.updateCcairbagCategory(ccairbagCategory);
    }

    /**
     * 批量删除商品类目
     * 
     * @param categoryIds 需要删除的商品类目主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagCategoryByCategoryIds(Long[] categoryIds)
    {
        return ccairbagCategoryMapper.deleteCcairbagCategoryByCategoryIds(categoryIds);
    }

    @Override
    public List<CcairbagCategory> getCategoryTree() {
        List<CcairbagCategory> categoryList = ccairbagCategoryMapper.selectCcairbagCategoryList(new CcairbagCategory());
        Map<Long, CcairbagCategory> categoryMap = new HashMap<>();
        List<CcairbagCategory> rootCategories = new ArrayList<>();
        // 第一步：将所有品类对象存储到 categoryMap 中
        for (CcairbagCategory category : categoryList) {
            categoryMap.put(category.getCategoryId(), category);
        }

        // 第二步：构建树形结构
        for (CcairbagCategory category : categoryList) {
            Long parentId = category.getParentId();
            if (parentId == 0) {
                // 如果父品类 ID 为空，说明是根品类，添加到根品类列表中
                rootCategories.add(category);
            } else {
                // 找到父品类对象
                CcairbagCategory parent = categoryMap.get(parentId);
                if (parent != null) {
                    // 将当前品类添加到父品类的子品类列表中
                    parent.getChildren().add(category);
                }
            }
        }
        return rootCategories;
    }


}
