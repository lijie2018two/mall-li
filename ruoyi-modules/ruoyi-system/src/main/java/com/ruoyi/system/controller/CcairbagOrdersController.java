package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.api.domain.ccairbag.CcairbagOrders;
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
import com.ruoyi.system.service.ICcairbagOrdersService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 订单Controller
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@RestController
@RequestMapping("ccairbag/orders")
public class CcairbagOrdersController extends BaseController
{
    @Autowired
    private ICcairbagOrdersService ccairbagOrdersService;

    /**
     * 查询订单列表
     */
    @RequiresPermissions("ccairbag:orders:list")
    @GetMapping("/list")
    public TableDataInfo list(CcairbagOrders ccairbagOrders)
    {
        startPage();
        List<CcairbagOrders> list = ccairbagOrdersService.selectCcairbagOrdersList(ccairbagOrders);
        return getDataTable(list);
    }

    /**
     * 导出订单列表
     */
    @RequiresPermissions("ccairbag:orders:export")
    @Log(title = "订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CcairbagOrders ccairbagOrders)
    {
        List<CcairbagOrders> list = ccairbagOrdersService.selectCcairbagOrdersList(ccairbagOrders);
        ExcelUtil<CcairbagOrders> util = new ExcelUtil<CcairbagOrders>(CcairbagOrders.class);
        util.exportExcel(response, list, "订单数据");
    }

    /**
     * 获取订单详细信息
     */
    @RequiresPermissions("ccairbag:orders:query")
    @GetMapping(value = "/{orderId}")
    public AjaxResult getInfo(@PathVariable("orderId") Long orderId)
    {
        return success(ccairbagOrdersService.selectCcairbagOrdersByOrderId(orderId));
    }

    /**
     * 新增订单
     */
    @RequiresPermissions("ccairbag:orders:add")
    @Log(title = "订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CcairbagOrders ccairbagOrders)
    {
        return toAjax(ccairbagOrdersService.insertCcairbagOrders(ccairbagOrders));
    }

    /**
     * 修改订单
     */
    @RequiresPermissions("ccairbag:orders:edit")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CcairbagOrders ccairbagOrders)
    {
        return toAjax(ccairbagOrdersService.updateCcairbagOrders(ccairbagOrders));
    }

    /**
     * 删除订单
     */
    @RequiresPermissions("ccairbag:orders:remove")
    @Log(title = "订单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{orderIds}")
    public AjaxResult remove(@PathVariable Long[] orderIds)
    {
        return toAjax(ccairbagOrdersService.deleteCcairbagOrdersByOrderIds(orderIds));
    }
}
