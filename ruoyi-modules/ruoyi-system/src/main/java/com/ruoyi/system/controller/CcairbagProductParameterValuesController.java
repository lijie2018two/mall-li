package com.ruoyi.system.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.api.domain.ccairbag.CcairbagProductParameterValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.system.service.ICcairbagProductParameterValuesService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 商品参数值表Controller
 * 
 * @author lidabai
 * @date 2025-02-24
 */
@RestController
@RequestMapping("ccairbag/values")
public class CcairbagProductParameterValuesController extends BaseController
{
    @Autowired
    private ICcairbagProductParameterValuesService ccairbagProductParameterValuesService;

    /**
     * 查询商品参数值表列表
     */
    @RequiresPermissions("system/ccairbag:values:list")
    @GetMapping("/list")
    public TableDataInfo list(CcairbagProductParameterValues ccairbagProductParameterValues)
    {
        startPage();
        List<CcairbagProductParameterValues> list = ccairbagProductParameterValuesService.selectCcairbagProductParameterValuesList(ccairbagProductParameterValues);
        return getDataTable(list);
    }

    /**
     * 导出商品参数值表列表
     */
    @RequiresPermissions("system/ccairbag:values:export")
    @Log(title = "商品参数值表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CcairbagProductParameterValues ccairbagProductParameterValues)
    {
        List<CcairbagProductParameterValues> list = ccairbagProductParameterValuesService.selectCcairbagProductParameterValuesList(ccairbagProductParameterValues);
        ExcelUtil<CcairbagProductParameterValues> util = new ExcelUtil<CcairbagProductParameterValues>(CcairbagProductParameterValues.class);
        util.exportExcel(response, list, "商品参数值表数据");
    }

    /**
     * 获取商品参数值表详细信息
     */
    @RequiresPermissions("system/ccairbag:values:query")
    @GetMapping(value = "/{valueId}")
    public AjaxResult getInfo(@PathVariable("valueId") Long valueId)
    {
        return success(ccairbagProductParameterValuesService.selectCcairbagProductParameterValuesByValueId(valueId));
    }

    /**
     * 新增商品参数值表
     */
    @RequiresPermissions("system/ccairbag:values:add")
    @Log(title = "商品参数值表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CcairbagProductParameterValues ccairbagProductParameterValues)
    {
        return toAjax(ccairbagProductParameterValuesService.insertCcairbagProductParameterValues(ccairbagProductParameterValues));
    }

    /**
     * 修改商品参数值表
     */
    @RequiresPermissions("system/ccairbag:values:edit")
    @Log(title = "商品参数值表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CcairbagProductParameterValues ccairbagProductParameterValues)
    {
        return toAjax(ccairbagProductParameterValuesService.updateCcairbagProductParameterValues(ccairbagProductParameterValues));
    }

    /**
     * 删除商品参数值表
     */
    @RequiresPermissions("system/ccairbag:values:remove")
    @Log(title = "商品参数值表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{valueIds}")
    public AjaxResult remove(@PathVariable Long[] valueIds)
    {
        return toAjax(ccairbagProductParameterValuesService.deleteCcairbagProductParameterValuesByValueIds(valueIds));
    }
}
