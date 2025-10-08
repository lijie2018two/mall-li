package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.api.domain.ccairbag.CcairbagNotificationUser;
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
import com.ruoyi.system.service.ICcairbagNotificationUserService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 用户通知关联Controller
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@RestController
@RequestMapping("ccairbag/Notificationuser")
public class CcairbagNotificationUserController extends BaseController
{
    @Autowired
    private ICcairbagNotificationUserService ccairbagNotificationUserService;

    /**
     * 查询用户通知关联列表
     */
    @RequiresPermissions("ccairbag:Notificationuser:list")
    @GetMapping("/list")
    public TableDataInfo list(CcairbagNotificationUser ccairbagNotificationUser)
    {
        startPage();
        List<CcairbagNotificationUser> list = ccairbagNotificationUserService.selectCcairbagNotificationUserList(ccairbagNotificationUser);
        return getDataTable(list);
    }

    /**
     * 导出用户通知关联列表
     */
    @RequiresPermissions("ccairbag:Notificationuser:export")
    @Log(title = "用户通知关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CcairbagNotificationUser ccairbagNotificationUser)
    {
        List<CcairbagNotificationUser> list = ccairbagNotificationUserService.selectCcairbagNotificationUserList(ccairbagNotificationUser);
        ExcelUtil<CcairbagNotificationUser> util = new ExcelUtil<CcairbagNotificationUser>(CcairbagNotificationUser.class);
        util.exportExcel(response, list, "用户通知关联数据");
    }

    /**
     * 获取用户通知关联详细信息
     */
    @RequiresPermissions("ccairbag:Notificationuser:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(ccairbagNotificationUserService.selectCcairbagNotificationUserById(id));
    }

    /**
     * 新增用户通知关联
     */
    @RequiresPermissions("ccairbag:Notificationuser:add")
    @Log(title = "用户通知关联", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CcairbagNotificationUser ccairbagNotificationUser)
    {
        return toAjax(ccairbagNotificationUserService.insertCcairbagNotificationUser(ccairbagNotificationUser));
    }

    /**
     * 修改用户通知关联
     */
    @RequiresPermissions("ccairbag:Notificationuser:edit")
    @Log(title = "用户通知关联", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CcairbagNotificationUser ccairbagNotificationUser)
    {
        return toAjax(ccairbagNotificationUserService.updateCcairbagNotificationUser(ccairbagNotificationUser));
    }

    /**
     * 删除用户通知关联
     */
    @RequiresPermissions("ccairbag:Notificationuser:remove")
    @Log(title = "用户通知关联", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(ccairbagNotificationUserService.deleteCcairbagNotificationUserByIds(ids));
    }
}
