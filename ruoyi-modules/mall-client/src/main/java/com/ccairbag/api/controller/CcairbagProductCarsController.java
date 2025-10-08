package com.ccairbag.api.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.ccairbag.api.service.ICcairbagProductCarsService;
import com.ruoyi.system.api.domain.ccairbag.CcairbagProductCars;
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

import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 商品关联的适用车辆Controller
 * 
 * @author lidabai
 * @date 2025-03-12
 */
@RestController
@RequestMapping("/cars")
public class CcairbagProductCarsController extends BaseController
{
    @Autowired
    private ICcairbagProductCarsService ccairbagProductCarsService;

    /**
     * 查询商品关联的适用车辆列表
     */
    @RequiresPermissions("ccairbag/system:cars:list")
    @GetMapping("/list")
    public TableDataInfo list(CcairbagProductCars ccairbagProductCars)
    {
        startPage();
        List<CcairbagProductCars> list = ccairbagProductCarsService.selectCcairbagProductCarsList(ccairbagProductCars);
        return getDataTable(list);
    }

    /**
     * 导出商品关联的适用车辆列表
     */
    @RequiresPermissions("ccairbag/system:cars:export")
    @Log(title = "商品关联的适用车辆", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CcairbagProductCars ccairbagProductCars)
    {
        List<CcairbagProductCars> list = ccairbagProductCarsService.selectCcairbagProductCarsList(ccairbagProductCars);
        ExcelUtil<CcairbagProductCars> util = new ExcelUtil<CcairbagProductCars>(CcairbagProductCars.class);
        util.exportExcel(response, list, "商品关联的适用车辆数据");
    }

    /**
     * 获取商品关联的适用车辆详细信息
     */
    @RequiresPermissions("ccairbag/system:cars:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(ccairbagProductCarsService.selectCcairbagProductCarsById(id));
    }

    /**
     * 新增商品关联的适用车辆
     */
    @RequiresPermissions("ccairbag/system:cars:add")
    @Log(title = "商品关联的适用车辆", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CcairbagProductCars ccairbagProductCars)
    {
        return toAjax(ccairbagProductCarsService.insertCcairbagProductCars(ccairbagProductCars));
    }

    /**
     * 修改商品关联的适用车辆
     */
    @RequiresPermissions("ccairbag/system:cars:edit")
    @Log(title = "商品关联的适用车辆", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CcairbagProductCars ccairbagProductCars)
    {
        return toAjax(ccairbagProductCarsService.updateCcairbagProductCars(ccairbagProductCars));
    }

    /**
     * 删除商品关联的适用车辆
     */
    @RequiresPermissions("ccairbag/system:cars:remove")
    @Log(title = "商品关联的适用车辆", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(ccairbagProductCarsService.deleteCcairbagProductCarsByIds(ids));
    }
}
