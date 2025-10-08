package com.ruoyi.system.controller;

import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.system.api.domain.ccairbag.CcairbagUsers;
import com.ruoyi.system.service.ICcairbagUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 用户管理Controller
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@RestController
@RequestMapping("ccairbag/users")
public class CcairbagUsersController extends BaseController
{
    @Autowired
    private ICcairbagUsersService ccairbagUsersService;

    /**
     * 查询用户管理列表
     */
    @RequiresPermissions("ccairbag:users:list")
    @GetMapping("/list")
    public TableDataInfo list(CcairbagUsers ccairbagUsers)
    {
        startPage();
        List<CcairbagUsers> list = ccairbagUsersService.selectCcairbagUsersList(ccairbagUsers);
        return getDataTable(list);
    }

    /**
     * 导出用户管理列表
     */
    @RequiresPermissions("ccairbag:users:export")
    @Log(title = "用户管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CcairbagUsers ccairbagUsers)
    {
        List<CcairbagUsers> list = ccairbagUsersService.selectCcairbagUsersList(ccairbagUsers);
        ExcelUtil<CcairbagUsers> util = new ExcelUtil<CcairbagUsers>(CcairbagUsers.class);
        util.exportExcel(response, list, "用户管理数据");
    }

    /**
     * 获取用户管理详细信息
     */
    @RequiresPermissions("ccairbag:users:query")
    @GetMapping(value = "/{userId}")
    public AjaxResult getInfo(@PathVariable("userId") Long userId)
    {
        return success(ccairbagUsersService.selectCcairbagUsersByUserId(userId));
    }

    @RequiresPermissions("ccairbag:users:query")
    @GetMapping(value = "/getUsersExt/{userId}")
    public AjaxResult getUsersExt(@PathVariable("userId") Long userId)
    {
        return success(ccairbagUsersService.getUsersExt(userId));
    }

    /**
     * 新增用户管理
     */
    @RequiresPermissions("ccairbag:users:add")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CcairbagUsers ccairbagUsers)
    {
        return toAjax(ccairbagUsersService.insertCcairbagUsers(ccairbagUsers));
    }

    /**
     * 修改用户管理
     */
    @RequiresPermissions("ccairbag:users:edit")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CcairbagUsers ccairbagUsers)
    {
        return toAjax(ccairbagUsersService.updateCcairbagUsers(ccairbagUsers));
    }



    /**
     * 删除用户管理
     */
    @RequiresPermissions("ccairbag:users:remove")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds)
    {
        return toAjax(ccairbagUsersService.deleteCcairbagUsersByUserIds(userIds));
    }
}
