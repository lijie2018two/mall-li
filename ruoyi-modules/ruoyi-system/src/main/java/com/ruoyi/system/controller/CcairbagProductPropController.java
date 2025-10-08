package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.api.domain.ccairbag.CcairbagProductProp;
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
import com.ruoyi.system.service.ICcairbagProductPropService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 商品属性Controller
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@RestController
@RequestMapping("ccairbag/prop")
public class CcairbagProductPropController extends BaseController
{
    @Autowired
    private ICcairbagProductPropService ccairbagProductPropService;

    /**
     * 查询商品属性列表
     */
    @RequiresPermissions("ccairbag:prop:list")
    @GetMapping("/list")
    public TableDataInfo list(CcairbagProductProp ccairbagProductProp)
    {
        startPage();
        List<CcairbagProductProp> list = ccairbagProductPropService.selectCcairbagProductPropList(ccairbagProductProp);
        return getDataTable(list);
    }

    /**
     * 导出商品属性列表
     */
    @RequiresPermissions("ccairbag:prop:export")
    @Log(title = "商品属性", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CcairbagProductProp ccairbagProductProp)
    {
        List<CcairbagProductProp> list = ccairbagProductPropService.selectCcairbagProductPropList(ccairbagProductProp);
        ExcelUtil<CcairbagProductProp> util = new ExcelUtil<CcairbagProductProp>(CcairbagProductProp.class);
        util.exportExcel(response, list, "商品属性数据");
    }

    /**
     * 获取商品属性详细信息
     */
    @RequiresPermissions("ccairbag:prop:query")
    @GetMapping(value = "/{propId}")
    public AjaxResult getInfo(@PathVariable("propId") Long propId)
    {
        return success(ccairbagProductPropService.selectCcairbagProductPropByPropId(propId));
    }

    /**
     * 新增商品属性
     */
    @RequiresPermissions("ccairbag:prop:add")
    @Log(title = "商品属性", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CcairbagProductProp ccairbagProductProp)
    {
        return toAjax(ccairbagProductPropService.insertCcairbagProductProp(ccairbagProductProp));
    }

    /**
     * 修改商品属性
     */
    @RequiresPermissions("ccairbag:prop:edit")
    @Log(title = "商品属性", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CcairbagProductProp ccairbagProductProp)
    {
        return toAjax(ccairbagProductPropService.updateCcairbagProductProp(ccairbagProductProp));
    }

    /**
     * 删除商品属性
     */
    @RequiresPermissions("ccairbag:prop:remove")
    @Log(title = "商品属性", businessType = BusinessType.DELETE)
	@DeleteMapping("/{propIds}")
    public AjaxResult remove(@PathVariable Long[] propIds)
    {
        return toAjax(ccairbagProductPropService.deleteCcairbagProductPropByPropIds(propIds));
    }
}
