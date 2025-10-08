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
import com.ruoyi.system.domain.CcairbagIncomeRecords;
import com.ruoyi.system.service.ICcairbagIncomeRecordsService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 收入支出记录Controller
 * 
 * @author lidabai
 * @date 2025-03-20
 */
@RestController
@RequestMapping("/records")
public class CcairbagIncomeRecordsController extends BaseController
{
    @Autowired
    private ICcairbagIncomeRecordsService ccairbagIncomeRecordsService;

    /**
     * 查询收入支出记录列表
     */
    @RequiresPermissions("ccaigbag/system:records:list")
    @GetMapping("/list")
    public TableDataInfo list(CcairbagIncomeRecords ccairbagIncomeRecords)
    {
        startPage();
        List<CcairbagIncomeRecords> list = ccairbagIncomeRecordsService.selectCcairbagIncomeRecordsList(ccairbagIncomeRecords);
        return getDataTable(list);
    }

    /**
     * 导出收入支出记录列表
     */
    @RequiresPermissions("ccaigbag/system:records:export")
    @Log(title = "收入支出记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CcairbagIncomeRecords ccairbagIncomeRecords)
    {
        List<CcairbagIncomeRecords> list = ccairbagIncomeRecordsService.selectCcairbagIncomeRecordsList(ccairbagIncomeRecords);
        ExcelUtil<CcairbagIncomeRecords> util = new ExcelUtil<CcairbagIncomeRecords>(CcairbagIncomeRecords.class);
        util.exportExcel(response, list, "收入支出记录数据");
    }

    /**
     * 获取收入支出记录详细信息
     */
    @RequiresPermissions("ccaigbag/system:records:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(ccairbagIncomeRecordsService.selectCcairbagIncomeRecordsById(id));
    }

    /**
     * 新增收入支出记录
     */
    @RequiresPermissions("ccaigbag/system:records:add")
    @Log(title = "收入支出记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CcairbagIncomeRecords ccairbagIncomeRecords)
    {
        return toAjax(ccairbagIncomeRecordsService.insertCcairbagIncomeRecords(ccairbagIncomeRecords));
    }

    /**
     * 修改收入支出记录
     */
    @RequiresPermissions("ccaigbag/system:records:edit")
    @Log(title = "收入支出记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CcairbagIncomeRecords ccairbagIncomeRecords)
    {
        return toAjax(ccairbagIncomeRecordsService.updateCcairbagIncomeRecords(ccairbagIncomeRecords));
    }

    /**
     * 删除收入支出记录
     */
    @RequiresPermissions("ccaigbag/system:records:remove")
    @Log(title = "收入支出记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(ccairbagIncomeRecordsService.deleteCcairbagIncomeRecordsByIds(ids));
    }
}
