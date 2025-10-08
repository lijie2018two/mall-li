package com.ruoyi.system.controller;

import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.system.api.domain.ccairbag.CcairbagOrderRefund;
import com.ruoyi.system.service.ICcairbagOrderRefundService;
import io.swagger.annotations.ApiOperation;
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





    @ApiOperation(value = "卖家问题 直接退款")
    @GetMapping("/handleSellerProblem")
    public AjaxResult handleSellerProblem(Long refundId)
    {
        return ccairbagOrderRefundService.handleSellerProblem(refundId);
    }

    @ApiOperation(value = "买家问题 直接退回已收货状态")
    @GetMapping("/handleBuyerProblem")
    public AjaxResult handleBuyerProblem(Long refundId)
    {
        return ccairbagOrderRefundService.handleBuyerProblem(refundId);
    }


}
