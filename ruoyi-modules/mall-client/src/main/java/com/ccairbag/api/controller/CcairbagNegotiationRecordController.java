package com.ccairbag.api.controller;

import com.ccairbag.api.service.ICcairbagNegotiationRecordService;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.system.api.domain.ccairbag.CcairbagNegotiationRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 议价记录Controller
 * 
 * @author lidabai
 * @date 2025-05-12
 */
@RestController
@RequestMapping("/record")
public class CcairbagNegotiationRecordController extends BaseController
{
    @Autowired
    private ICcairbagNegotiationRecordService ccairbagNegotiationRecordService;

    /**
     * 查询议价记录列表
     */
    @RequiresPermissions("ccairbag/system:record:list")
    @GetMapping("/list")
    public TableDataInfo list(CcairbagNegotiationRecord ccairbagNegotiationRecord)
    {
        startPage();
        List<CcairbagNegotiationRecord> list = ccairbagNegotiationRecordService.selectCcairbagNegotiationRecordList(ccairbagNegotiationRecord);
        return getDataTable(list);
    }

    /**
     * 导出议价记录列表
     */
    @RequiresPermissions("ccairbag/system:record:export")
    @Log(title = "议价记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CcairbagNegotiationRecord ccairbagNegotiationRecord)
    {
        List<CcairbagNegotiationRecord> list = ccairbagNegotiationRecordService.selectCcairbagNegotiationRecordList(ccairbagNegotiationRecord);
        ExcelUtil<CcairbagNegotiationRecord> util = new ExcelUtil<CcairbagNegotiationRecord>(CcairbagNegotiationRecord.class);
        util.exportExcel(response, list, "议价记录数据");
    }

    /**
     * 获取议价记录详细信息
     */
    @RequiresPermissions("ccairbag/system:record:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(ccairbagNegotiationRecordService.selectCcairbagNegotiationRecordById(id));
    }

    /**
     * 新增议价记录
     */
    @RequiresPermissions("ccairbag/system:record:add")
    @Log(title = "议价记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CcairbagNegotiationRecord ccairbagNegotiationRecord)
    {
        return toAjax(ccairbagNegotiationRecordService.insertCcairbagNegotiationRecord(ccairbagNegotiationRecord));
    }

    /**
     * 修改议价记录
     */
    @RequiresPermissions("ccairbag/system:record:edit")
    @Log(title = "议价记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CcairbagNegotiationRecord ccairbagNegotiationRecord)
    {
        return toAjax(ccairbagNegotiationRecordService.updateCcairbagNegotiationRecord(ccairbagNegotiationRecord));
    }

    /**
     * 删除议价记录
     */
    @RequiresPermissions("ccairbag/system:record:remove")
    @Log(title = "议价记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(ccairbagNegotiationRecordService.deleteCcairbagNegotiationRecordByIds(ids));
    }

    @GetMapping("/cancelNegotiable")
    public AppResult cancelNegotiable(Long id)  {

        return ccairbagNegotiationRecordService.cancelNegotiable(id);
    }
}
