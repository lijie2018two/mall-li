package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.api.domain.ccairbag.CcairbagOrderDetails;
import com.ruoyi.system.service.ICcairbagOrderRefundService;
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
import com.ruoyi.system.service.ICcairbagOrderDetailsService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 订单详情Controller
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@RestController
@RequestMapping("ccairbag/details")
public class CcairbagOrderDetailsController extends BaseController
{
    @Autowired
    private ICcairbagOrderDetailsService ccairbagOrderDetailsService;

    /**
     * 查询订单详情列表
     */
    @RequiresPermissions("ccairbag:details:list")
    @GetMapping("/list")
    public TableDataInfo list(CcairbagOrderDetails ccairbagOrderDetails)
    {
        startPage();
        List<CcairbagOrderDetails> list = ccairbagOrderDetailsService.selectCcairbagOrderDetailsList(ccairbagOrderDetails);
        return getDataTable(list);
    }

    /**
     * 导出订单详情列表
     */
    @RequiresPermissions("ccairbag:details:export")
    @Log(title = "订单详情", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CcairbagOrderDetails ccairbagOrderDetails)
    {
        List<CcairbagOrderDetails> list = ccairbagOrderDetailsService.selectCcairbagOrderDetailsList(ccairbagOrderDetails);
        ExcelUtil<CcairbagOrderDetails> util = new ExcelUtil<CcairbagOrderDetails>(CcairbagOrderDetails.class);
        util.exportExcel(response, list, "订单详情数据");
    }

    /**
     * 获取订单详情详细信息
     */
    @RequiresPermissions("ccairbag:details:query")
    @GetMapping(value = "/{detailId}")
    public AjaxResult getInfo(@PathVariable("detailId") Long detailId)
    {
        return success(ccairbagOrderDetailsService.selectCcairbagOrderDetailsByDetailId(detailId));
    }

    /**
     * 新增订单详情
     */
    @RequiresPermissions("ccairbag:details:add")
    @Log(title = "订单详情", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CcairbagOrderDetails ccairbagOrderDetails)
    {
        return toAjax(ccairbagOrderDetailsService.insertCcairbagOrderDetails(ccairbagOrderDetails));
    }

    /**
     * 修改订单详情
     */
    @RequiresPermissions("ccairbag:details:edit")
    @Log(title = "订单详情", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CcairbagOrderDetails ccairbagOrderDetails)
    {
        return toAjax(ccairbagOrderDetailsService.updateCcairbagOrderDetails(ccairbagOrderDetails));
    }

    /**
     * 删除订单详情
     */
    @RequiresPermissions("ccairbag:details:remove")
    @Log(title = "订单详情", businessType = BusinessType.DELETE)
	@DeleteMapping("/{detailIds}")
    public AjaxResult remove(@PathVariable Long[] detailIds)
    {
        return toAjax(ccairbagOrderDetailsService.deleteCcairbagOrderDetailsByDetailIds(detailIds));
    }
}
