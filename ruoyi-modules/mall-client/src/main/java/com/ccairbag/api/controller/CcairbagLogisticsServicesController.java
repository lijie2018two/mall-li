package com.ccairbag.api.controller;

import com.ccairbag.api.service.ICcairbagLogisticsServicesService;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.system.domain.CcairbagLogisticsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 物流服务Controller
 * 
 * @author lidabai
 * @date 2025-03-06
 */
@RestController
@RequestMapping("/ccairbag/services")
public class CcairbagLogisticsServicesController extends BaseController
{
    @Autowired
    private ICcairbagLogisticsServicesService ccairbagLogisticsServicesService;

    /**
     * 查询物流服务列表
     */
    @RequiresPermissions("system:ccairbag/services:list")
    @GetMapping("/list")
    public TableDataInfo list(CcairbagLogisticsServices ccairbagLogisticsServices)
    {
        startPage();
        List<CcairbagLogisticsServices> list = ccairbagLogisticsServicesService.selectCcairbagLogisticsServicesList(ccairbagLogisticsServices);
        return getDataTable(list);
    }

    /**
     * 导出物流服务列表
     */
    @RequiresPermissions("system:ccairbag/services:export")
    @Log(title = "物流服务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CcairbagLogisticsServices ccairbagLogisticsServices)
    {
        List<CcairbagLogisticsServices> list = ccairbagLogisticsServicesService.selectCcairbagLogisticsServicesList(ccairbagLogisticsServices);
        ExcelUtil<CcairbagLogisticsServices> util = new ExcelUtil<CcairbagLogisticsServices>(CcairbagLogisticsServices.class);
        util.exportExcel(response, list, "物流服务数据");
    }

    /**
     * 获取物流服务详细信息
     */
    @RequiresPermissions("system:ccairbag/services:query")
    @GetMapping(value = "/{serviceId}")
    public AjaxResult getInfo(@PathVariable("serviceId") Long serviceId)
    {
        return success(ccairbagLogisticsServicesService.selectCcairbagLogisticsServicesByServiceId(serviceId));
    }

    /**
     * 新增物流服务
     */
    @RequiresPermissions("system:ccairbag/services:add")
    @Log(title = "物流服务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CcairbagLogisticsServices ccairbagLogisticsServices)
    {
        return toAjax(ccairbagLogisticsServicesService.insertCcairbagLogisticsServices(ccairbagLogisticsServices));
    }

    /**
     * 修改物流服务
     */
    @RequiresPermissions("system:ccairbag/services:edit")
    @Log(title = "物流服务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CcairbagLogisticsServices ccairbagLogisticsServices)
    {
        return toAjax(ccairbagLogisticsServicesService.updateCcairbagLogisticsServices(ccairbagLogisticsServices));
    }

    /**
     * 删除物流服务
     */
    @RequiresPermissions("system:ccairbag/services:remove")
    @Log(title = "物流服务", businessType = BusinessType.DELETE)
	@DeleteMapping("/{serviceIds}")
    public AjaxResult remove(@PathVariable Long[] serviceIds)
    {
        return toAjax(ccairbagLogisticsServicesService.deleteCcairbagLogisticsServicesByServiceIds(serviceIds));
    }
}
