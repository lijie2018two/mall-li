package com.ccairbag.api.controller;

import com.ccairbag.api.service.ICcairbagSkusService;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.system.api.domain.ccairbag.CcairbagSkus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * skuController
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@RestController
@RequestMapping("ccairbag/skus")
public class CcairbagSkusController extends BaseController
{
    @Autowired
    private ICcairbagSkusService ccairbagSkusService;

    /**
     * 查询sku列表
     */
    @RequiresPermissions("ccairbag:skus:list")
    @GetMapping("/list")
    public TableDataInfo list(CcairbagSkus ccairbagSkus)
    {
        startPage();
        List<CcairbagSkus> list = ccairbagSkusService.selectCcairbagSkusList(ccairbagSkus);
        return getDataTable(list);
    }

    /**
     * 导出sku列表
     */
    @RequiresPermissions("ccairbag:skus:export")
    @Log(title = "sku", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CcairbagSkus ccairbagSkus)
    {
        List<CcairbagSkus> list = ccairbagSkusService.selectCcairbagSkusList(ccairbagSkus);
        ExcelUtil<CcairbagSkus> util = new ExcelUtil<CcairbagSkus>(CcairbagSkus.class);
        util.exportExcel(response, list, "sku数据");
    }

    /**
     * 获取sku详细信息
     */
    @RequiresPermissions("ccairbag:skus:query")
    @GetMapping(value = "/{skuId}")
    public AjaxResult getInfo(@PathVariable("skuId") Long skuId)
    {
        return success(ccairbagSkusService.selectCcairbagSkusBySkuId(skuId));
    }

    /**
     * 新增sku
     */
    @RequiresPermissions("ccairbag:skus:add")
    @Log(title = "sku", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CcairbagSkus ccairbagSkus)
    {
        return toAjax(ccairbagSkusService.insertCcairbagSkus(ccairbagSkus));
    }

    /**
     * 修改sku
     */
    @RequiresPermissions("ccairbag:skus:edit")
    @Log(title = "sku", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CcairbagSkus ccairbagSkus)
    {
        return toAjax(ccairbagSkusService.updateCcairbagSkus(ccairbagSkus));
    }

    /**
     * 删除sku
     */
    @RequiresPermissions("ccairbag:skus:remove")
    @Log(title = "sku", businessType = BusinessType.DELETE)
	@DeleteMapping("/{skuIds}")
    public AjaxResult remove(@PathVariable Long[] skuIds)
    {
        return toAjax(ccairbagSkusService.deleteCcairbagSkusBySkuIds(skuIds));
    }
}
