package com.ccairbag.api.controller;

import com.ccairbag.api.service.ICcairbagCarouselService;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.system.api.domain.ccairbag.CcairbagCarousel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 轮播图Controller
 * 
 * @author lidabai
 * @date 2025-04-30
 */
@RestController
@RequestMapping("ccairbag/carousel")
public class CcairbagCarouselController extends BaseController
{
    @Autowired
    private ICcairbagCarouselService ccairbagCarouselService;

    /**
     * 查询轮播图列表
     */
    @RequiresPermissions("system:carousel:list")
    @GetMapping("/list")
    public TableDataInfo list(CcairbagCarousel ccairbagCarousel)
    {
        startPage();
        List<CcairbagCarousel> list = ccairbagCarouselService.selectCcairbagCarouselList(ccairbagCarousel);
        return getDataTable(list);
    }

    /**
     * 导出轮播图列表
     */
    @RequiresPermissions("system:carousel:export")
    @Log(title = "轮播图", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CcairbagCarousel ccairbagCarousel)
    {
        List<CcairbagCarousel> list = ccairbagCarouselService.selectCcairbagCarouselList(ccairbagCarousel);
        ExcelUtil<CcairbagCarousel> util = new ExcelUtil<CcairbagCarousel>(CcairbagCarousel.class);
        util.exportExcel(response, list, "轮播图数据");
    }

    /**
     * 获取轮播图详细信息
     */
    @RequiresPermissions("system:carousel:query")
    @GetMapping(value = "/{carouselId}")
    public AjaxResult getInfo(@PathVariable("carouselId") Long carouselId)
    {
        return success(ccairbagCarouselService.selectCcairbagCarouselByCarouselId(carouselId));
    }

    /**
     * 新增轮播图
     */
    @RequiresPermissions("system:carousel:add")
    @Log(title = "轮播图", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CcairbagCarousel ccairbagCarousel)
    {
        return toAjax(ccairbagCarouselService.insertCcairbagCarousel(ccairbagCarousel));
    }

    /**
     * 修改轮播图
     */
    @RequiresPermissions("system:carousel:edit")
    @Log(title = "轮播图", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CcairbagCarousel ccairbagCarousel)
    {
        return toAjax(ccairbagCarouselService.updateCcairbagCarousel(ccairbagCarousel));
    }

    /**
     * 删除轮播图
     */
    @RequiresPermissions("system:carousel:remove")
    @Log(title = "轮播图", businessType = BusinessType.DELETE)
	@DeleteMapping("/{carouselIds}")
    public AjaxResult remove(@PathVariable Long[] carouselIds)
    {
        return toAjax(ccairbagCarouselService.deleteCcairbagCarouselByCarouselIds(carouselIds));
    }
}
