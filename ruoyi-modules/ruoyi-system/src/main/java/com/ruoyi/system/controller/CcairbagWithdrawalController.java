package com.ruoyi.system.controller;

import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.system.api.domain.ccairbag.CcairbagWithdrawal;
import com.ruoyi.system.service.ICcairbagWithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 提现记录Controller
 * 
 * @author lidabai
 * @date 2025-04-27
 */
@RestController
@RequestMapping("/withdrawal")
public class CcairbagWithdrawalController extends BaseController
{
    @Autowired
    private ICcairbagWithdrawalService ccairbagWithdrawalService;

    /**
     * 查询提现记录列表
     */
    @RequiresPermissions("ccairbag/system:withdrawal:list")
    @GetMapping("/list")
    public TableDataInfo list(CcairbagWithdrawal ccairbagWithdrawal)
    {
        startPage();
        List<CcairbagWithdrawal> list = ccairbagWithdrawalService.selectCcairbagWithdrawalList(ccairbagWithdrawal);
        return getDataTable(list);
    }

    /**
     * 导出提现记录列表
     */
    @RequiresPermissions("ccairbag/system:withdrawal:export")
    @Log(title = "提现记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CcairbagWithdrawal ccairbagWithdrawal)
    {
        List<CcairbagWithdrawal> list = ccairbagWithdrawalService.selectCcairbagWithdrawalList(ccairbagWithdrawal);
        ExcelUtil<CcairbagWithdrawal> util = new ExcelUtil<CcairbagWithdrawal>(CcairbagWithdrawal.class);
        util.exportExcel(response, list, "提现记录数据");
    }

    /**
     * 获取提现记录详细信息
     */
    @RequiresPermissions("ccairbag/system:withdrawal:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(ccairbagWithdrawalService.selectCcairbagWithdrawalById(id));
    }

    /**
     * 新增提现记录
     */
    @RequiresPermissions("ccairbag/system:withdrawal:add")
    @Log(title = "提现记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CcairbagWithdrawal ccairbagWithdrawal)
    {
        return toAjax(ccairbagWithdrawalService.insertCcairbagWithdrawal(ccairbagWithdrawal));
    }

    /**
     * 修改提现记录
     */
    @RequiresPermissions("ccairbag/system:withdrawal:edit")
    @Log(title = "提现记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CcairbagWithdrawal ccairbagWithdrawal)
    {
        return ccairbagWithdrawalService.updateCcairbagWithdrawal(ccairbagWithdrawal);
    }

    /**
     * 删除提现记录
     */
    @RequiresPermissions("ccairbag/system:withdrawal:remove")
    @Log(title = "提现记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(ccairbagWithdrawalService.deleteCcairbagWithdrawalByIds(ids));
    }
}
