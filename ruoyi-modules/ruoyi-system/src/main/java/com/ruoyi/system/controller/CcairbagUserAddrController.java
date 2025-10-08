package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.api.domain.ccairbag.CcairbagUserAddr;
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
import com.ruoyi.system.service.ICcairbagUserAddrService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 用户地址管理Controller
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@RestController
@RequestMapping("ccairbag/addr")
public class CcairbagUserAddrController extends BaseController
{
    @Autowired
    private ICcairbagUserAddrService ccairbagUserAddrService;

    /**
     * 查询用户地址管理列表
     */
    @RequiresPermissions("ccairbag:addr:list")
    @GetMapping("/list")
    public TableDataInfo list(CcairbagUserAddr ccairbagUserAddr)
    {
        startPage();
        List<CcairbagUserAddr> list = ccairbagUserAddrService.selectCcairbagUserAddrList(ccairbagUserAddr);
        return getDataTable(list);
    }

    /**
     * 导出用户地址管理列表
     */
    @RequiresPermissions("ccairbag:addr:export")
    @Log(title = "用户地址管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CcairbagUserAddr ccairbagUserAddr)
    {
        List<CcairbagUserAddr> list = ccairbagUserAddrService.selectCcairbagUserAddrList(ccairbagUserAddr);
        ExcelUtil<CcairbagUserAddr> util = new ExcelUtil<CcairbagUserAddr>(CcairbagUserAddr.class);
        util.exportExcel(response, list, "用户地址管理数据");
    }

    /**
     * 获取用户地址管理详细信息
     */
    @RequiresPermissions("ccairbag:addr:query")
    @GetMapping(value = "/{addrId}")
    public AjaxResult getInfo(@PathVariable("addrId") Long addrId)
    {
        return success(ccairbagUserAddrService.selectCcairbagUserAddrByAddrId(addrId));
    }

    /**
     * 新增用户地址管理
     */
    @RequiresPermissions("ccairbag:addr:add")
    @Log(title = "用户地址管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CcairbagUserAddr ccairbagUserAddr)
    {
        return toAjax(ccairbagUserAddrService.insertCcairbagUserAddr(ccairbagUserAddr));
    }

    /**
     * 修改用户地址管理
     */
    @RequiresPermissions("ccairbag:addr:edit")
    @Log(title = "用户地址管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CcairbagUserAddr ccairbagUserAddr)
    {
        return toAjax(ccairbagUserAddrService.updateCcairbagUserAddr(ccairbagUserAddr));
    }

    /**
     * 删除用户地址管理
     */
    @RequiresPermissions("ccairbag:addr:remove")
    @Log(title = "用户地址管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{addrIds}")
    public AjaxResult remove(@PathVariable Long[] addrIds)
    {
        return toAjax(ccairbagUserAddrService.deleteCcairbagUserAddrByAddrIds(addrIds));
    }
}
