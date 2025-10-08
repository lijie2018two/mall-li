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
import com.ruoyi.system.domain.CcairbagProductLogistics;
import com.ruoyi.system.service.ICcairbagProductLogisticsService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 物流商品关联Controller
 * 
 * @author lidabai
 * @date 2025-03-06
 */
@RestController
@RequestMapping("/ccairbag/logistics")
public class CcairbagProductLogisticsController extends BaseController
{
    @Autowired
    private ICcairbagProductLogisticsService ccairbagProductLogisticsService;

    /**
     * 查询物流商品关联列表
     */
    @RequiresPermissions("system:ccairbag/logistics:list")
    @GetMapping("/list")
    public TableDataInfo list(CcairbagProductLogistics ccairbagProductLogistics)
    {
        startPage();
        List<CcairbagProductLogistics> list = ccairbagProductLogisticsService.selectCcairbagProductLogisticsList(ccairbagProductLogistics);
        return getDataTable(list);
    }

    /**
     * 导出物流商品关联列表
     */
    @RequiresPermissions("system:ccairbag/logistics:export")
    @Log(title = "物流商品关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CcairbagProductLogistics ccairbagProductLogistics)
    {
        List<CcairbagProductLogistics> list = ccairbagProductLogisticsService.selectCcairbagProductLogisticsList(ccairbagProductLogistics);
        ExcelUtil<CcairbagProductLogistics> util = new ExcelUtil<CcairbagProductLogistics>(CcairbagProductLogistics.class);
        util.exportExcel(response, list, "物流商品关联数据");
    }

    /**
     * 获取物流商品关联详细信息
     */
    @RequiresPermissions("system:ccairbag/logistics:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(ccairbagProductLogisticsService.selectCcairbagProductLogisticsById(id));
    }

    /**
     * 新增物流商品关联
     */
    @RequiresPermissions("system:ccairbag/logistics:add")
    @Log(title = "物流商品关联", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CcairbagProductLogistics ccairbagProductLogistics)
    {
        return toAjax(ccairbagProductLogisticsService.insertCcairbagProductLogistics(ccairbagProductLogistics));
    }

    /**
     * 修改物流商品关联
     */
    @RequiresPermissions("system:ccairbag/logistics:edit")
    @Log(title = "物流商品关联", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CcairbagProductLogistics ccairbagProductLogistics)
    {
        return toAjax(ccairbagProductLogisticsService.updateCcairbagProductLogistics(ccairbagProductLogistics));
    }

    /**
     * 删除物流商品关联
     */
    @RequiresPermissions("system:ccairbag/logistics:remove")
    @Log(title = "物流商品关联", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(ccairbagProductLogisticsService.deleteCcairbagProductLogisticsByIds(ids));
    }
}
