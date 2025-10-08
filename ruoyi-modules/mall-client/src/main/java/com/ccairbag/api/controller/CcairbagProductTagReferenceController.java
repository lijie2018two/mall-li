package com.ccairbag.api.controller;

import com.ccairbag.api.service.ICcairbagProductTagReferenceService;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.system.api.domain.ccairbag.CcairbagProductTagReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 商品标签关系表Controller
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@RestController
@RequestMapping("ccairbag/reference")
public class CcairbagProductTagReferenceController extends BaseController
{
    @Autowired
    private ICcairbagProductTagReferenceService ccairbagProductTagReferenceService;

    /**
     * 查询商品标签关系表列表
     */
    @RequiresPermissions("ccairbag:reference:list")
    @GetMapping("/list")
    public TableDataInfo list(CcairbagProductTagReference ccairbagProductTagReference)
    {
        startPage();
        List<CcairbagProductTagReference> list = ccairbagProductTagReferenceService.selectCcairbagProductTagReferenceList(ccairbagProductTagReference);
        return getDataTable(list);
    }

    /**
     * 导出商品标签关系表列表
     */
    @RequiresPermissions("ccairbag:reference:export")
    @Log(title = "商品标签关系表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CcairbagProductTagReference ccairbagProductTagReference)
    {
        List<CcairbagProductTagReference> list = ccairbagProductTagReferenceService.selectCcairbagProductTagReferenceList(ccairbagProductTagReference);
        ExcelUtil<CcairbagProductTagReference> util = new ExcelUtil<CcairbagProductTagReference>(CcairbagProductTagReference.class);
        util.exportExcel(response, list, "商品标签关系表数据");
    }

    /**
     * 获取商品标签关系表详细信息
     */
    @RequiresPermissions("ccairbag:reference:query")
    @GetMapping(value = "/{referenceId}")
    public AjaxResult getInfo(@PathVariable("referenceId") String referenceId)
    {
        return success(ccairbagProductTagReferenceService.selectCcairbagProductTagReferenceByReferenceId(referenceId));
    }

    /**
     * 新增商品标签关系表
     */
    @RequiresPermissions("ccairbag:reference:add")
    @Log(title = "商品标签关系表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CcairbagProductTagReference ccairbagProductTagReference)
    {
        return toAjax(ccairbagProductTagReferenceService.insertCcairbagProductTagReference(ccairbagProductTagReference));
    }

    /**
     * 修改商品标签关系表
     */
    @RequiresPermissions("ccairbag:reference:edit")
    @Log(title = "商品标签关系表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CcairbagProductTagReference ccairbagProductTagReference)
    {
        return toAjax(ccairbagProductTagReferenceService.updateCcairbagProductTagReference(ccairbagProductTagReference));
    }

    /**
     * 删除商品标签关系表
     */
    @RequiresPermissions("ccairbag:reference:remove")
    @Log(title = "商品标签关系表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{referenceIds}")
    public AjaxResult remove(@PathVariable String[] referenceIds)
    {
        return toAjax(ccairbagProductTagReferenceService.deleteCcairbagProductTagReferenceByReferenceIds(referenceIds));
    }
}
