package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.api.domain.ccairbag.CcairbagShoppingCarts;
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
import com.ruoyi.system.service.ICcairbagShoppingCartsService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 购物车Controller
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@RestController
@RequestMapping("ccairbag/carts")
public class CcairbagShoppingCartsController extends BaseController
{
    @Autowired
    private ICcairbagShoppingCartsService ccairbagShoppingCartsService;

    /**
     * 查询购物车列表
     */
    @RequiresPermissions("ccairbag:carts:list")
    @GetMapping("/list")
    public TableDataInfo list(CcairbagShoppingCarts ccairbagShoppingCarts)
    {
        startPage();
        List<CcairbagShoppingCarts> list = ccairbagShoppingCartsService.selectCcairbagShoppingCartsList(ccairbagShoppingCarts);
        return getDataTable(list);
    }

    /**
     * 导出购物车列表
     */
    @RequiresPermissions("ccairbag:carts:export")
    @Log(title = "购物车", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CcairbagShoppingCarts ccairbagShoppingCarts)
    {
        List<CcairbagShoppingCarts> list = ccairbagShoppingCartsService.selectCcairbagShoppingCartsList(ccairbagShoppingCarts);
        ExcelUtil<CcairbagShoppingCarts> util = new ExcelUtil<CcairbagShoppingCarts>(CcairbagShoppingCarts.class);
        util.exportExcel(response, list, "购物车数据");
    }

    /**
     * 获取购物车详细信息
     */
    @RequiresPermissions("ccairbag:carts:query")
    @GetMapping(value = "/{cartId}")
    public AjaxResult getInfo(@PathVariable("cartId") Long cartId)
    {
        return success(ccairbagShoppingCartsService.selectCcairbagShoppingCartsByCartId(cartId));
    }

    /**
     * 新增购物车
     */
    @RequiresPermissions("ccairbag:carts:add")
    @Log(title = "购物车", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CcairbagShoppingCarts ccairbagShoppingCarts)
    {
        return toAjax(ccairbagShoppingCartsService.insertCcairbagShoppingCarts(ccairbagShoppingCarts));
    }

    /**
     * 修改购物车
     */
    @RequiresPermissions("ccairbag:carts:edit")
    @Log(title = "购物车", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CcairbagShoppingCarts ccairbagShoppingCarts)
    {
        return toAjax(ccairbagShoppingCartsService.updateCcairbagShoppingCarts(ccairbagShoppingCarts));
    }

    /**
     * 删除购物车
     */
    @RequiresPermissions("ccairbag:carts:remove")
    @Log(title = "购物车", businessType = BusinessType.DELETE)
	@DeleteMapping("/{cartIds}")
    public AjaxResult remove(@PathVariable Long[] cartIds)
    {
        return toAjax(ccairbagShoppingCartsService.deleteCcairbagShoppingCartsByCartIds(cartIds));
    }
}
