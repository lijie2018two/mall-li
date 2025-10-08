package com.ruoyi.system.controller;

import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.system.api.domain.ccairbag.CcairbagProductPromotionRelations;
import com.ruoyi.system.service.ICcairbagProductPromotionRelationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 商品 - 活动关联Controller
 * 
 * @author lidabai
 * @date 2025-07-15
 */
@RestController
@RequestMapping("/relations")
public class CcairbagProductPromotionRelationsController extends BaseController
{
    @Autowired
    private ICcairbagProductPromotionRelationsService ccairbagProductPromotionRelationsService;

    /**
     * 查询商品 - 活动关联列表
     */
    @RequiresPermissions("system:relations:list")
    @GetMapping("/list")
    public TableDataInfo list(CcairbagProductPromotionRelations ccairbagProductPromotionRelations)
    {
        startPage();
        List<CcairbagProductPromotionRelations> list = ccairbagProductPromotionRelationsService.selectCcairbagProductPromotionRelationsList(ccairbagProductPromotionRelations);
        return getDataTable(list);
    }

    /**
     * 导出商品 - 活动关联列表
     */
    @RequiresPermissions("system:relations:export")
    @Log(title = "商品 - 活动关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CcairbagProductPromotionRelations ccairbagProductPromotionRelations)
    {
        List<CcairbagProductPromotionRelations> list = ccairbagProductPromotionRelationsService.selectCcairbagProductPromotionRelationsList(ccairbagProductPromotionRelations);
        ExcelUtil<CcairbagProductPromotionRelations> util = new ExcelUtil<CcairbagProductPromotionRelations>(CcairbagProductPromotionRelations.class);
        util.exportExcel(response, list, "商品 - 活动关联数据");
    }

    /**
     * 获取商品 - 活动关联详细信息
     */
    @RequiresPermissions("system:relations:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(ccairbagProductPromotionRelationsService.selectCcairbagProductPromotionRelationsById(id));
    }

    /**
     * 新增商品 - 活动关联
     */
    @RequiresPermissions("system:relations:add")
    @Log(title = "商品 - 活动关联", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CcairbagProductPromotionRelations ccairbagProductPromotionRelations)
    {
        return toAjax(ccairbagProductPromotionRelationsService.insertCcairbagProductPromotionRelations(ccairbagProductPromotionRelations));
    }

    /**
     * 修改商品 - 活动关联
     */
    @RequiresPermissions("system:relations:edit")
    @Log(title = "商品 - 活动关联", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CcairbagProductPromotionRelations ccairbagProductPromotionRelations)
    {
        return toAjax(ccairbagProductPromotionRelationsService.updateCcairbagProductPromotionRelations(ccairbagProductPromotionRelations));
    }

    /**
     * 删除商品 - 活动关联
     */
    @RequiresPermissions("system:relations:remove")
    @Log(title = "商品 - 活动关联", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(ccairbagProductPromotionRelationsService.deleteCcairbagProductPromotionRelationsByIds(ids));
    }
}
