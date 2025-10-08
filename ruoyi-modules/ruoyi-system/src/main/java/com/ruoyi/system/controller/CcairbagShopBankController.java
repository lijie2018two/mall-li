package com.ruoyi.system.controller;

import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.system.api.domain.ccairbag.CcairbagShopBank;
import com.ruoyi.system.service.ICcairbagShopBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 绑定的银行卡Controller
 * 
 * @author lidabai
 * @date 2025-04-27
 */
@RestController
@RequestMapping("/bank")
public class CcairbagShopBankController extends BaseController
{
    @Autowired
    private ICcairbagShopBankService ccairbagShopBankService;

    /**
     * 查询绑定的银行卡列表
     */
    @RequiresPermissions("ccairbag/system:bank:list")
    @GetMapping("/list")
    public TableDataInfo list(CcairbagShopBank ccairbagShopBank)
    {
        startPage();
        List<CcairbagShopBank> list = ccairbagShopBankService.selectCcairbagShopBankList(ccairbagShopBank);
        return getDataTable(list);
    }

    /**
     * 导出绑定的银行卡列表
     */
    @RequiresPermissions("ccairbag/system:bank:export")
    @Log(title = "绑定的银行卡", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CcairbagShopBank ccairbagShopBank)
    {
        List<CcairbagShopBank> list = ccairbagShopBankService.selectCcairbagShopBankList(ccairbagShopBank);
        ExcelUtil<CcairbagShopBank> util = new ExcelUtil<CcairbagShopBank>(CcairbagShopBank.class);
        util.exportExcel(response, list, "绑定的银行卡数据");
    }

    /**
     * 获取绑定的银行卡详细信息
     */
    @RequiresPermissions("ccairbag/system:bank:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(ccairbagShopBankService.selectCcairbagShopBankById(id));
    }

    /**
     * 新增绑定的银行卡
     */
    @RequiresPermissions("ccairbag/system:bank:add")
    @Log(title = "绑定的银行卡", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CcairbagShopBank ccairbagShopBank)
    {
        return toAjax(ccairbagShopBankService.insertCcairbagShopBank(ccairbagShopBank));
    }

    /**
     * 修改绑定的银行卡
     */
    @RequiresPermissions("ccairbag/system:bank:edit")
    @Log(title = "绑定的银行卡", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CcairbagShopBank ccairbagShopBank)
    {
        return toAjax(ccairbagShopBankService.updateCcairbagShopBank(ccairbagShopBank));
    }

    /**
     * 删除绑定的银行卡
     */
    @RequiresPermissions("ccairbag/system:bank:remove")
    @Log(title = "绑定的银行卡", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(ccairbagShopBankService.deleteCcairbagShopBankByIds(ids));
    }
}
