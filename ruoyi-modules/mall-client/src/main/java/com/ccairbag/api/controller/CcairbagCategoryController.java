package com.ccairbag.api.controller;

import com.ccairbag.api.service.ICcairbagCategoryService;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.system.api.domain.ccairbag.CcairbagCategory;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 商品类目Controller
 * 
 * @author lidabai
 * @date 2025-02-24
 */
@RestController
@RequestMapping("ccairbag/category")
public class CcairbagCategoryController extends BaseController
{
    @Autowired
    private ICcairbagCategoryService ccairbagCategoryService;

    /**
     * 查询商品类目列表
     */
    @RequiresPermissions("system/ccairbag:category:list")
    @GetMapping("/list")
    public AjaxResult list(CcairbagCategory ccairbagCategory)
    {
        List<CcairbagCategory> list = ccairbagCategoryService.selectCcairbagCategoryList(ccairbagCategory);
        return success(list);
    }

    /**
     * 导出商品类目列表
     */
    @RequiresPermissions("system/ccairbag:category:export")
    @Log(title = "商品类目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CcairbagCategory ccairbagCategory)
    {
        List<CcairbagCategory> list = ccairbagCategoryService.selectCcairbagCategoryList(ccairbagCategory);
        ExcelUtil<CcairbagCategory> util = new ExcelUtil<CcairbagCategory>(CcairbagCategory.class);
        util.exportExcel(response, list, "商品类目数据");
    }

    /**
     * 获取商品类目详细信息
     */
    @RequiresPermissions("system/ccairbag:category:query")
    @GetMapping(value = "/{categoryId}")
    public AjaxResult getInfo(@PathVariable("categoryId") Long categoryId)
    {
        return success(ccairbagCategoryService.selectCcairbagCategoryByCategoryId(categoryId));
    }

    /**
     * 新增商品类目
     */
    @RequiresPermissions("system/ccairbag:category:add")
    @Log(title = "商品类目", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CcairbagCategory ccairbagCategory)
    {
        return toAjax(ccairbagCategoryService.insertCcairbagCategory(ccairbagCategory));
    }

    /**
     * 修改商品类目
     */
    @RequiresPermissions("system/ccairbag:category:edit")
    @Log(title = "商品类目", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CcairbagCategory ccairbagCategory)
    {
        return toAjax(ccairbagCategoryService.updateCcairbagCategory(ccairbagCategory));
    }

    /**
     * 删除商品类目
     */
    @RequiresPermissions("system/ccairbag:category:remove")
    @Log(title = "商品类目", businessType = BusinessType.DELETE)
	@DeleteMapping("/{categoryIds}")
    public AjaxResult remove(@PathVariable Long[] categoryIds)
    {
        return toAjax(ccairbagCategoryService.deleteCcairbagCategoryByCategoryIds(categoryIds));
    }

    @ApiOperation(value = "获取平台设置的商品品类")
    @GetMapping("/getCategoryTree")
    public List<CcairbagCategory> getCategoryTree(){
        return ccairbagCategoryService.getCategoryTree();
    }
}
