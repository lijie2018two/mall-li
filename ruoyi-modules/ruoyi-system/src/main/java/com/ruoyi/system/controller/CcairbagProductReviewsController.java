package com.ruoyi.system.controller;

import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.system.api.domain.ccairbag.CcairbagProductReviews;
import com.ruoyi.system.service.ICcairbagProductReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

/**
 * 商品评论Controller
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@RestController
@RequestMapping("ccairbag/reviews")
public class CcairbagProductReviewsController extends BaseController
{
    @Autowired
    private ICcairbagProductReviewsService ccairbagProductReviewsService;

    /**
     * 查询商品评论列表
     */
    @RequiresPermissions("ccairbag:reviews:list")
    @GetMapping("/list")
    public TableDataInfo list(CcairbagProductReviews ccairbagProductReviews)
    {
        startPage();
        List<CcairbagProductReviews> list = ccairbagProductReviewsService.selectCcairbagProductReviewsList(ccairbagProductReviews);
        return getDataTable(list);
    }

    /**
     * 导出商品评论列表
     */
    @RequiresPermissions("ccairbag:reviews:export")
    @Log(title = "商品评论", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CcairbagProductReviews ccairbagProductReviews)
    {
        List<CcairbagProductReviews> list = ccairbagProductReviewsService.selectCcairbagProductReviewsList(ccairbagProductReviews);
        ExcelUtil<CcairbagProductReviews> util = new ExcelUtil<CcairbagProductReviews>(CcairbagProductReviews.class);
        util.exportExcel(response, list, "商品评论数据");
    }

    /**
     * 获取商品评论详细信息
     */
    @RequiresPermissions("ccairbag:reviews:query")
    @GetMapping(value = "/{reviewId}")
    public AjaxResult getInfo(@PathVariable("reviewId") Long reviewId)
    {
        return success(ccairbagProductReviewsService.selectCcairbagProductReviewsByReviewId(reviewId));
    }

    /**
     * 新增商品评论
     */
    @RequiresPermissions("ccairbag:reviews:add")
    @Log(title = "商品评论", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CcairbagProductReviews ccairbagProductReviews)
    {
        return toAjax(ccairbagProductReviewsService.insertCcairbagProductReviews(ccairbagProductReviews));
    }

    /**
     * 修改商品评论
     */
    @RequiresPermissions("ccairbag:reviews:edit")
    @Log(title = "商品评论", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CcairbagProductReviews ccairbagProductReviews)
    {
        return toAjax(ccairbagProductReviewsService.updateCcairbagProductReviews(ccairbagProductReviews));
    }

    /**
     * 删除商品评论
     */
    @RequiresPermissions("ccairbag:reviews:remove")
    @Log(title = "商品评论", businessType = BusinessType.DELETE)
	@DeleteMapping("/{reviewIds}")
    public AjaxResult remove(@PathVariable Long[] reviewIds)
    {
        return toAjax(ccairbagProductReviewsService.deleteCcairbagProductReviewsByReviewIds(reviewIds));
    }




    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<CcairbagProductReviews> util = new ExcelUtil<CcairbagProductReviews>(CcairbagProductReviews.class);
        util.importTemplateExcel(response, "商品数据");
    }


    /**
     * 导入数据
     */
    @Log(title = "商品评论", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception
    {
        ExcelUtil<CcairbagProductReviews> util = new ExcelUtil<CcairbagProductReviews>(CcairbagProductReviews.class);
        InputStream inputStream = file.getInputStream();
        List<CcairbagProductReviews> list = util.importExcel(inputStream );
        inputStream.close();
        int count = ccairbagProductReviewsService.batchInsertCcairbagProductReviews(list);
        return AjaxResult.success("导入成功" + count + "条信息！");
    }

}
