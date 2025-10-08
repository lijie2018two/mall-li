package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.api.domain.ccairbag.CcairbagConversations;
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
import com.ruoyi.system.service.ICcairbagConversationsService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 消息会话Controller
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@RestController
@RequestMapping("ccairbag/conversations")
public class CcairbagConversationsController extends BaseController
{
    @Autowired
    private ICcairbagConversationsService ccairbagConversationsService;

    /**
     * 查询消息会话列表
     */
    @RequiresPermissions("ccairbag:conversations:list")
    @GetMapping("/list")
    public TableDataInfo list(CcairbagConversations ccairbagConversations)
    {
        startPage();
        List<CcairbagConversations> list = ccairbagConversationsService.selectCcairbagConversationsList(ccairbagConversations);
        return getDataTable(list);
    }

    /**
     * 导出消息会话列表
     */
    @RequiresPermissions("ccairbag:conversations:export")
    @Log(title = "消息会话", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CcairbagConversations ccairbagConversations)
    {
        List<CcairbagConversations> list = ccairbagConversationsService.selectCcairbagConversationsList(ccairbagConversations);
        ExcelUtil<CcairbagConversations> util = new ExcelUtil<CcairbagConversations>(CcairbagConversations.class);
        util.exportExcel(response, list, "消息会话数据");
    }

    /**
     * 获取消息会话详细信息
     */
    @RequiresPermissions("ccairbag:conversations:query")
    @GetMapping(value = "/{conversationId}")
    public AjaxResult getInfo(@PathVariable("conversationId") Long conversationId)
    {
        return success(ccairbagConversationsService.selectCcairbagConversationsByConversationId(conversationId));
    }

    /**
     * 新增消息会话
     */
    @RequiresPermissions("ccairbag:conversations:add")
    @Log(title = "消息会话", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CcairbagConversations ccairbagConversations)
    {
        return toAjax(ccairbagConversationsService.insertCcairbagConversations(ccairbagConversations));
    }

    /**
     * 修改消息会话
     */
    @RequiresPermissions("ccairbag:conversations:edit")
    @Log(title = "消息会话", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CcairbagConversations ccairbagConversations)
    {
        return toAjax(ccairbagConversationsService.updateCcairbagConversations(ccairbagConversations));
    }

    /**
     * 删除消息会话
     */
    @RequiresPermissions("ccairbag:conversations:remove")
    @Log(title = "消息会话", businessType = BusinessType.DELETE)
	@DeleteMapping("/{conversationIds}")
    public AjaxResult remove(@PathVariable Long[] conversationIds)
    {
        return toAjax(ccairbagConversationsService.deleteCcairbagConversationsByConversationIds(conversationIds));
    }
}
