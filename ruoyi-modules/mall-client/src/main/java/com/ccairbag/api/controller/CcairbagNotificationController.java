package com.ccairbag.api.controller;

import com.ccairbag.api.service.ICcairbagNotificationService;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.system.api.domain.ccairbag.CcairbagNotification;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 通知Controller
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@RestController
@RequestMapping("ccairbag/notification")
public class CcairbagNotificationController extends BaseController
{
    @Autowired
    private ICcairbagNotificationService ccairbagNotificationService;

    /**
     * 查询通知列表
     */
    @RequiresPermissions("ccairbag:notification:list")
    @GetMapping("/list")
    public TableDataInfo list(CcairbagNotification ccairbagNotification)
    {
        startPage();
        List<CcairbagNotification> list = ccairbagNotificationService.selectCcairbagNotificationList(ccairbagNotification);
        return getDataTable(list);
    }

    /**
     * 导出通知列表
     */
    @RequiresPermissions("ccairbag:notification:export")
    @Log(title = "通知", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CcairbagNotification ccairbagNotification)
    {
        List<CcairbagNotification> list = ccairbagNotificationService.selectCcairbagNotificationList(ccairbagNotification);
        ExcelUtil<CcairbagNotification> util = new ExcelUtil<CcairbagNotification>(CcairbagNotification.class);
        util.exportExcel(response, list, "通知数据");
    }

    /**
     * 获取通知详细信息
     */
    @RequiresPermissions("ccairbag:notification:query")
    @GetMapping(value = "/{notificationId}")
    public AjaxResult getInfo(@PathVariable("notificationId") Long notificationId)
    {
        return success(ccairbagNotificationService.selectCcairbagNotificationByNotificationId(notificationId));
    }

    /**
     * 新增通知
     */
    @RequiresPermissions("ccairbag:notification:add")
    @Log(title = "通知", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CcairbagNotification ccairbagNotification)
    {
        return toAjax(ccairbagNotificationService.insertCcairbagNotification(ccairbagNotification));
    }

    /**
     * 修改通知
     */
    @RequiresPermissions("ccairbag:notification:edit")
    @Log(title = "通知", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CcairbagNotification ccairbagNotification)
    {
        return toAjax(ccairbagNotificationService.updateCcairbagNotification(ccairbagNotification));
    }

    /**
     * 删除通知
     */
    @RequiresPermissions("ccairbag:notification:remove")
    @Log(title = "通知", businessType = BusinessType.DELETE)
	@DeleteMapping("/{notificationIds}")
    public AjaxResult remove(@PathVariable Long[] notificationIds)
    {
        return toAjax(ccairbagNotificationService.deleteCcairbagNotificationByNotificationIds(notificationIds));
    }

    @ApiOperation(value = "查看通知，设置已读")
    @PostMapping("/markNotificationsAsRead")
    public AppResult markNotificationsAsRead(@RequestParam Long notificationId )
    {

        return ccairbagNotificationService.markNotificationsAsRead(notificationId);
    }

    @ApiOperation("消息通知详情")
    @GetMapping("/getNotificationItem")
    public AppResult getNotificationItem(@RequestParam Long notificationId)
    {
        return  ccairbagNotificationService.getNotificationItem(notificationId);
    }
}
