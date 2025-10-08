package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.api.domain.ccairbag.CcairbagProductPropValue;
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
import com.ruoyi.system.service.ICcairbagProductPropValueService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 商品属性值Controller
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@RestController
@RequestMapping("ccairbag/value")
public class CcairbagProductPropValueController extends BaseController
{
    @Autowired
    private ICcairbagProductPropValueService ccairbagProductPropValueService;

    /**
     * 查询商品属性值列表
     */
    @RequiresPermissions("ccairbag:value:list")
    @GetMapping("/list")
    public TableDataInfo list(CcairbagProductPropValue ccairbagProductPropValue)
    {
        startPage();
        List<CcairbagProductPropValue> list = ccairbagProductPropValueService.selectCcairbagProductPropValueList(ccairbagProductPropValue);
        return getDataTable(list);
    }

    /**
     * 导出商品属性值列表
     */
    @RequiresPermissions("ccairbag:value:export")
    @Log(title = "商品属性值", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CcairbagProductPropValue ccairbagProductPropValue)
    {
        List<CcairbagProductPropValue> list = ccairbagProductPropValueService.selectCcairbagProductPropValueList(ccairbagProductPropValue);
        ExcelUtil<CcairbagProductPropValue> util = new ExcelUtil<CcairbagProductPropValue>(CcairbagProductPropValue.class);
        util.exportExcel(response, list, "商品属性值数据");
    }

    /**
     * 获取商品属性值详细信息
     */
    @RequiresPermissions("ccairbag:value:query")
    @GetMapping(value = "/{valueId}")
    public AjaxResult getInfo(@PathVariable("valueId") Long valueId)
    {
        return success(ccairbagProductPropValueService.selectCcairbagProductPropValueByValueId(valueId));
    }

    /**
     * 新增商品属性值
     */
    @RequiresPermissions("ccairbag:value:add")
    @Log(title = "商品属性值", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CcairbagProductPropValue ccairbagProductPropValue)
    {
        return toAjax(ccairbagProductPropValueService.insertCcairbagProductPropValue(ccairbagProductPropValue));
    }

    /**
     * 修改商品属性值
     */
    @RequiresPermissions("ccairbag:value:edit")
    @Log(title = "商品属性值", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CcairbagProductPropValue ccairbagProductPropValue)
    {
        return toAjax(ccairbagProductPropValueService.updateCcairbagProductPropValue(ccairbagProductPropValue));
    }

    /**
     * 删除商品属性值
     */
    @RequiresPermissions("ccairbag:value:remove")
    @Log(title = "商品属性值", businessType = BusinessType.DELETE)
	@DeleteMapping("/{valueIds}")
    public AjaxResult remove(@PathVariable Long[] valueIds)
    {
        return toAjax(ccairbagProductPropValueService.deleteCcairbagProductPropValueByValueIds(valueIds));
    }
}
