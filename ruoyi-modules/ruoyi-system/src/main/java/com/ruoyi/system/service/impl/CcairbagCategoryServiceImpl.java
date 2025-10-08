package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.domain.ccairbag.CcairbagCategory;
import com.ruoyi.system.mapper.CcairbagCategoryMapper;
import com.ruoyi.system.service.ICcairbagCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        CcairbagCategory category = ccairbagCategoryMapper.selectCcairbagCategoryByCategoryId(ccairbagCategory.getParentId());

        ccairbagCategory.setDeleted(0);
        if (ccairbagCategory.getParentId()!=0){
            ccairbagCategory.setGrade(category.getGrade()+1);
            ccairbagCategory.setFullCategoryName(category.getFullCategoryName()+"/"+ccairbagCategory.getCategoryName());
            ccairbagCategory.setFullCategoryNameEn(category.getFullCategoryNameEn()+"/"+ccairbagCategory.getCategoryNameEn());
        }else {
            ccairbagCategory.setGrade(0);
            ccairbagCategory.setFullCategoryName(ccairbagCategory.getCategoryName());
        }
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

    public int deleteCcairbagCategoryByCategoryId(Long categoryId)
    {
        return ccairbagCategoryMapper.deleteCcairbagCategoryByCategoryId(categoryId);
    }

    @Override
    public int deleteCcairbagCategoryByCategoryIds(Long[] categoryIds)
    {
        return ccairbagCategoryMapper.deleteCcairbagCategoryByCategoryIds(categoryIds);
    }
}
