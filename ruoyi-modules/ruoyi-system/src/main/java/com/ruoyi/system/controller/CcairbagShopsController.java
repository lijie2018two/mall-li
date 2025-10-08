package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.api.domain.ccairbag.CcairbagShops;
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
import com.ruoyi.system.service.ICcairbagShopsService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 店铺Controller
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@RestController
@RequestMapping("ccairbag/shops")
public class CcairbagShopsController extends BaseController
{
    @Autowired
    private ICcairbagShopsService ccairbagShopsService;

    /**
     * 查询店铺列表
     */
    @RequiresPermissions("ccairbag:shops:list")
    @GetMapping("/list")
    public TableDataInfo list(CcairbagShops ccairbagShops)
    {
        startPage();
        List<CcairbagShops> list = ccairbagShopsService.selectCcairbagShopsList(ccairbagShops);
        return getDataTable(list);
    }

    /**
     * 导出店铺列表
     */
    @RequiresPermissions("ccairbag:shops:export")
    @Log(title = "店铺", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CcairbagShops ccairbagShops)
    {
        List<CcairbagShops> list = ccairbagShopsService.selectCcairbagShopsList(ccairbagShops);
        ExcelUtil<CcairbagShops> util = new ExcelUtil<CcairbagShops>(CcairbagShops.class);
        util.exportExcel(response, list, "店铺数据");
    }

    /**
     * 获取店铺详细信息
     */
    @RequiresPermissions("ccairbag:shops:query")
    @GetMapping(value = "/{shopId}")
    public AjaxResult getInfo(@PathVariable("shopId") Long shopId)
    {
        return success(ccairbagShopsService.selectCcairbagShopsByShopId(shopId));
    }

    /**
     * 新增店铺
     */
    @RequiresPermissions("ccairbag:shops:add")
    @Log(title = "店铺", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CcairbagShops ccairbagShops)
    {
        return toAjax(ccairbagShopsService.insertCcairbagShops(ccairbagShops));
    }

    /**
     * 修改店铺
     */
    @RequiresPermissions("ccairbag:shops:edit")
    @Log(title = "店铺", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CcairbagShops ccairbagShops)
    {
        return toAjax(ccairbagShopsService.updateCcairbagShops(ccairbagShops));
    }

    /**
     * 删除店铺
     */
    @RequiresPermissions("ccairbag:shops:remove")
    @Log(title = "店铺", businessType = BusinessType.DELETE)
	@DeleteMapping("/{shopIds}")
    public AjaxResult remove(@PathVariable Long[] shopIds)
    {
        return toAjax(ccairbagShopsService.deleteCcairbagShopsByShopIds(shopIds));
    }
}
