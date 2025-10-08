package com.ruoyi.system.controller;

import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.system.api.domain.ccairbag.CcairbagUserRegistration;
import com.ruoyi.system.service.ICcairbagUserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 用户信息登记Controller
 * 
 * @author lidabai
 * @date 2025-03-20
 */
@RestController
@RequestMapping("/registration")
public class CcairbagUserRegistrationController extends BaseController
{
    @Autowired
    private ICcairbagUserRegistrationService ccairbagUserRegistrationService;

    /**
     * 查询用户信息登记列表
     */
    @RequiresPermissions("ccairbag/system:registration:list")
    @GetMapping("/list")
    public TableDataInfo list(CcairbagUserRegistration ccairbagUserRegistration)
    {
        startPage();
        List<CcairbagUserRegistration> list = ccairbagUserRegistrationService.selectCcairbagUserRegistrationList(ccairbagUserRegistration);
        return getDataTable(list);
    }

    /**
     * 导出用户信息登记列表
     */
    @RequiresPermissions("ccairbag/system:registration:export")
    @Log(title = "用户信息登记", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CcairbagUserRegistration ccairbagUserRegistration)
    {
        List<CcairbagUserRegistration> list = ccairbagUserRegistrationService.selectCcairbagUserRegistrationList(ccairbagUserRegistration);
        ExcelUtil<CcairbagUserRegistration> util = new ExcelUtil<CcairbagUserRegistration>(CcairbagUserRegistration.class);
        util.exportExcel(response, list, "用户信息登记数据");
    }

    /**
     * 获取用户信息登记详细信息
     */
    @RequiresPermissions("ccairbag/system:registration:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(ccairbagUserRegistrationService.selectCcairbagUserRegistrationById(id));
    }

    /**
     * 新增用户信息登记
     */
    @RequiresPermissions("ccairbag/system:registration:add")
    @Log(title = "用户信息登记", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CcairbagUserRegistration ccairbagUserRegistration)
    {
        return toAjax(ccairbagUserRegistrationService.insertCcairbagUserRegistration(ccairbagUserRegistration));
    }

    /**
     * 修改用户信息登记
     */
    @RequiresPermissions("ccairbag/system:registration:edit")
    @Log(title = "用户信息登记", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CcairbagUserRegistration ccairbagUserRegistration)
    {
        return toAjax(ccairbagUserRegistrationService.updateCcairbagUserRegistration(ccairbagUserRegistration));
    }

    /**
     * 删除用户信息登记
     */
    @RequiresPermissions("ccairbag/system:registration:remove")
    @Log(title = "用户信息登记", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(ccairbagUserRegistrationService.deleteCcairbagUserRegistrationByIds(ids));
    }
}
