package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.api.domain.ccairbag.CcairbagProductFavorite;
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
import com.ruoyi.system.service.ICcairbagProductFavoriteService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 商品收藏Controller
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@RestController
@RequestMapping("ccairbag/favorite")
public class CcairbagProductFavoriteController extends BaseController
{
    @Autowired
    private ICcairbagProductFavoriteService ccairbagProductFavoriteService;

    /**
     * 查询商品收藏列表
     */
    @RequiresPermissions("ccairbag:favorite:list")
    @GetMapping("/list")
    public TableDataInfo list(CcairbagProductFavorite ccairbagProductFavorite)
    {
        startPage();
        List<CcairbagProductFavorite> list = ccairbagProductFavoriteService.selectCcairbagProductFavoriteList(ccairbagProductFavorite);
        return getDataTable(list);
    }

    /**
     * 导出商品收藏列表
     */
    @RequiresPermissions("ccairbag:favorite:export")
    @Log(title = "商品收藏", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CcairbagProductFavorite ccairbagProductFavorite)
    {
        List<CcairbagProductFavorite> list = ccairbagProductFavoriteService.selectCcairbagProductFavoriteList(ccairbagProductFavorite);
        ExcelUtil<CcairbagProductFavorite> util = new ExcelUtil<CcairbagProductFavorite>(CcairbagProductFavorite.class);
        util.exportExcel(response, list, "商品收藏数据");
    }

    /**
     * 获取商品收藏详细信息
     */
    @RequiresPermissions("ccairbag:favorite:query")
    @GetMapping(value = "/{favoriteId}")
    public AjaxResult getInfo(@PathVariable("favoriteId") Long favoriteId)
    {
        return success(ccairbagProductFavoriteService.selectCcairbagProductFavoriteByFavoriteId(favoriteId));
    }

    /**
     * 新增商品收藏
     */
    @RequiresPermissions("ccairbag:favorite:add")
    @Log(title = "商品收藏", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CcairbagProductFavorite ccairbagProductFavorite)
    {
        return toAjax(ccairbagProductFavoriteService.insertCcairbagProductFavorite(ccairbagProductFavorite));
    }

    /**
     * 修改商品收藏
     */
    @RequiresPermissions("ccairbag:favorite:edit")
    @Log(title = "商品收藏", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CcairbagProductFavorite ccairbagProductFavorite)
    {
        return toAjax(ccairbagProductFavoriteService.updateCcairbagProductFavorite(ccairbagProductFavorite));
    }

    /**
     * 删除商品收藏
     */
    @RequiresPermissions("ccairbag:favorite:remove")
    @Log(title = "商品收藏", businessType = BusinessType.DELETE)
	@DeleteMapping("/{favoriteIds}")
    public AjaxResult remove(@PathVariable Long[] favoriteIds)
    {
        return toAjax(ccairbagProductFavoriteService.deleteCcairbagProductFavoriteByFavoriteIds(favoriteIds));
    }
}
