package com.ccairbag.api.controller;

import com.ccairbag.api.service.ICcairbagMessagesService;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.system.api.domain.ccairbag.CcairbagMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 消息Controller
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@RestController
@RequestMapping("ccairbag/messages")
public class CcairbagMessagesController extends BaseController
{
    @Autowired
    private ICcairbagMessagesService ccairbagMessagesService;

    /**
     * 查询消息列表
     */
    @RequiresPermissions("ccairbag:messages:list")
    @GetMapping("/list")
    public TableDataInfo list(CcairbagMessages ccairbagMessages)
    {
        startPage();
        List<CcairbagMessages> list = ccairbagMessagesService.selectCcairbagMessagesList(ccairbagMessages);
        return getDataTable(list);
    }

    /**
     * 导出消息列表
     */
    @RequiresPermissions("ccairbag:messages:export")
    @Log(title = "消息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CcairbagMessages ccairbagMessages)
    {
        List<CcairbagMessages> list = ccairbagMessagesService.selectCcairbagMessagesList(ccairbagMessages);
        ExcelUtil<CcairbagMessages> util = new ExcelUtil<CcairbagMessages>(CcairbagMessages.class);
        util.exportExcel(response, list, "消息数据");
    }

    /**
     * 获取消息详细信息
     */
    @RequiresPermissions("ccairbag:messages:query")
    @GetMapping(value = "/{messageId}")
    public AjaxResult getInfo(@PathVariable("messageId") Long messageId)
    {
        return success(ccairbagMessagesService.selectCcairbagMessagesByMessageId(messageId));
    }

    /**
     * 新增消息
     */
    @RequiresPermissions("ccairbag:messages:add")
    @Log(title = "消息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CcairbagMessages ccairbagMessages)
    {
        return toAjax(ccairbagMessagesService.insertCcairbagMessages(ccairbagMessages));
    }

    /**
     * 修改消息
     */
    @RequiresPermissions("ccairbag:messages:edit")
    @Log(title = "消息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CcairbagMessages ccairbagMessages)
    {
        return toAjax(ccairbagMessagesService.updateCcairbagMessages(ccairbagMessages));
    }




}
