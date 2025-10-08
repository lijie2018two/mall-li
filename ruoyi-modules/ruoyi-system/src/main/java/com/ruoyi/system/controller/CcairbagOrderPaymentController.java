package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.api.domain.ccairbag.CcairbagOrderPayment;
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
import com.ruoyi.system.service.ICcairbagOrderPaymentService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 订单支付Controller
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@RestController
@RequestMapping("ccairbag/payment")
public class CcairbagOrderPaymentController extends BaseController
{
    @Autowired
    private ICcairbagOrderPaymentService ccairbagOrderPaymentService;

    /**
     * 查询订单支付列表
     */
    @RequiresPermissions("ccairbag:payment:list")
    @GetMapping("/list")
    public TableDataInfo list(CcairbagOrderPayment ccairbagOrderPayment)
    {
        startPage();
        List<CcairbagOrderPayment> list = ccairbagOrderPaymentService.selectCcairbagOrderPaymentList(ccairbagOrderPayment);
        return getDataTable(list);
    }

    /**
     * 导出订单支付列表
     */
    @RequiresPermissions("ccairbag:payment:export")
    @Log(title = "订单支付", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CcairbagOrderPayment ccairbagOrderPayment)
    {
        List<CcairbagOrderPayment> list = ccairbagOrderPaymentService.selectCcairbagOrderPaymentList(ccairbagOrderPayment);
        ExcelUtil<CcairbagOrderPayment> util = new ExcelUtil<CcairbagOrderPayment>(CcairbagOrderPayment.class);
        util.exportExcel(response, list, "订单支付数据");
    }

    /**
     * 获取订单支付详细信息
     */
    @RequiresPermissions("ccairbag:payment:query")
    @GetMapping(value = "/{paymentId}")
    public AjaxResult getInfo(@PathVariable("paymentId") Long paymentId)
    {
        return success(ccairbagOrderPaymentService.selectCcairbagOrderPaymentByPaymentId(paymentId));
    }

    /**
     * 新增订单支付
     */
    @RequiresPermissions("ccairbag:payment:add")
    @Log(title = "订单支付", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CcairbagOrderPayment ccairbagOrderPayment)
    {
        return toAjax(ccairbagOrderPaymentService.insertCcairbagOrderPayment(ccairbagOrderPayment));
    }

    /**
     * 修改订单支付
     */
    @RequiresPermissions("ccairbag:payment:edit")
    @Log(title = "订单支付", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CcairbagOrderPayment ccairbagOrderPayment)
    {
        return toAjax(ccairbagOrderPaymentService.updateCcairbagOrderPayment(ccairbagOrderPayment));
    }

    /**
     * 删除订单支付
     */
    @RequiresPermissions("ccairbag:payment:remove")
    @Log(title = "订单支付", businessType = BusinessType.DELETE)
	@DeleteMapping("/{paymentIds}")
    public AjaxResult remove(@PathVariable Long[] paymentIds)
    {
        return toAjax(ccairbagOrderPaymentService.deleteCcairbagOrderPaymentByPaymentIds(paymentIds));
    }
}
