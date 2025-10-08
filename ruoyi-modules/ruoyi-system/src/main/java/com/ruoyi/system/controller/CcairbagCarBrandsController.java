package com.ruoyi.system.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.system.domain.CcairbagCarBrands;
import com.ruoyi.system.service.ICcairbagCarBrandsService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 汽车品牌型号Controller
 * 
 * @author lidabai
 * @date 2025-03-12
 */
@RestController
@RequestMapping("/brands")
public class CcairbagCarBrandsController extends BaseController
{
    @Autowired
    private ICcairbagCarBrandsService ccairbagCarBrandsService;

    /**
     * 查询汽车品牌型号列表
     */
    @RequiresPermissions("ccairbag/system:brands:list")
    @GetMapping("/list")
    public TableDataInfo list(CcairbagCarBrands ccairbagCarBrands)
    {
        startPage();
        List<CcairbagCarBrands> list = ccairbagCarBrandsService.selectCcairbagCarBrandsList(ccairbagCarBrands);
        return getDataTable(list);
    }

    /**
     * 导出汽车品牌型号列表
     */
    @RequiresPermissions("ccairbag/system:brands:export")
    @Log(title = "汽车品牌型号", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CcairbagCarBrands ccairbagCarBrands)
    {
        List<CcairbagCarBrands> list = ccairbagCarBrandsService.selectCcairbagCarBrandsList(ccairbagCarBrands);
        ExcelUtil<CcairbagCarBrands> util = new ExcelUtil<CcairbagCarBrands>(CcairbagCarBrands.class);
        util.exportExcel(response, list, "汽车品牌型号数据");
    }

    /**
     * 获取汽车品牌型号详细信息
     */
    @RequiresPermissions("ccairbag/system:brands:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(ccairbagCarBrandsService.selectCcairbagCarBrandsById(id));
    }

    /**
     * 新增汽车品牌型号
     */
    @RequiresPermissions("ccairbag/system:brands:add")
    @Log(title = "汽车品牌型号", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CcairbagCarBrands ccairbagCarBrands)
    {
        return toAjax(ccairbagCarBrandsService.insertCcairbagCarBrands(ccairbagCarBrands));
    }

    /**
     * 修改汽车品牌型号
     */
    @RequiresPermissions("ccairbag/system:brands:edit")
    @Log(title = "汽车品牌型号", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CcairbagCarBrands ccairbagCarBrands)
    {
        return toAjax(ccairbagCarBrandsService.updateCcairbagCarBrands(ccairbagCarBrands));
    }

    /**
     * 删除汽车品牌型号
     */
    @RequiresPermissions("ccairbag/system:brands:remove")
    @Log(title = "汽车品牌型号", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(ccairbagCarBrandsService.deleteCcairbagCarBrandsByIds(ids));
    }
}
