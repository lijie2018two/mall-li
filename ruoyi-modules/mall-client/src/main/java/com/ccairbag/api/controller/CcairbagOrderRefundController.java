package com.ccairbag.api.controller;

import com.ccairbag.api.service.ICcairbagOrderRefundService;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.system.api.domain.ccairbag.CcairbagOrderRefund;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 订单退款Controller
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@RestController
@RequestMapping("ccairbag/refund")
public class CcairbagOrderRefundController extends BaseController
{
    @Autowired
    private ICcairbagOrderRefundService ccairbagOrderRefundService;

    /**
     * 查询订单退款列表
     */
    @RequiresPermissions("ccairbag:refund:list")
    @GetMapping("/list")
    public TableDataInfo list(CcairbagOrderRefund ccairbagOrderRefund)
    {
        startPage();
        List<CcairbagOrderRefund> list = ccairbagOrderRefundService.selectCcairbagOrderRefundList(ccairbagOrderRefund);
        return getDataTable(list);
    }

    /**
     * 导出订单退款列表
     */
    @RequiresPermissions("ccairbag:refund:export")
    @Log(title = "订单退款", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CcairbagOrderRefund ccairbagOrderRefund)
    {
        List<CcairbagOrderRefund> list = ccairbagOrderRefundService.selectCcairbagOrderRefundList(ccairbagOrderRefund);
        ExcelUtil<CcairbagOrderRefund> util = new ExcelUtil<CcairbagOrderRefund>(CcairbagOrderRefund.class);
        util.exportExcel(response, list, "订单退款数据");
    }

    /**
     * 获取订单退款详细信息
     */
    @RequiresPermissions("ccairbag:refund:query")
    @GetMapping(value = "/{refundId}")
    public AjaxResult getInfo(@PathVariable("refundId") Long refundId)
    {
        return success(ccairbagOrderRefundService.selectCcairbagOrderRefundByRefundId(refundId));
    }

    /**
     * 新增订单退款
     */
    @RequiresPermissions("ccairbag:refund:add")
    @Log(title = "订单退款", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CcairbagOrderRefund ccairbagOrderRefund)
    {
        return toAjax(ccairbagOrderRefundService.insertCcairbagOrderRefund(ccairbagOrderRefund));
    }

    /**
     * 修改订单退款
     */
    @RequiresPermissions("ccairbag:refund:edit")
    @Log(title = "订单退款", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CcairbagOrderRefund ccairbagOrderRefund)
    {
        return toAjax(ccairbagOrderRefundService.updateCcairbagOrderRefund(ccairbagOrderRefund));
    }

    /**
     * 删除订单退款
     */
    @RequiresPermissions("ccairbag:refund:remove")
    @Log(title = "订单退款", businessType = BusinessType.DELETE)
	@DeleteMapping("/{refundIds}")
    public AjaxResult remove(@PathVariable Long[] refundIds)
    {
        return toAjax(ccairbagOrderRefundService.deleteCcairbagOrderRefundByRefundIds(refundIds));
    }
}
