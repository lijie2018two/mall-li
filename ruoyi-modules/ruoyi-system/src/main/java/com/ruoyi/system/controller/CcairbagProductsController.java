package com.ruoyi.system.controller;

import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import java.io.InputStream;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.system.api.domain.ccairbag.CcairbagProducts;
import com.ruoyi.system.service.ICcairbagProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

/**
 * 商品Controller
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@RestController
@RequestMapping("ccairbag/products")
public class CcairbagProductsController extends BaseController
{
    @Autowired
    private ICcairbagProductsService ccairbagProductsService;

    /**
     * 查询商品列表
     */
    @RequiresPermissions("ccairbag:products:list")
    @GetMapping("/list")
    public TableDataInfo list(CcairbagProducts ccairbagProducts)
    {
        startPage();
        List<CcairbagProducts> list = ccairbagProductsService.selectCcairbagProductsList(ccairbagProducts);
        return getDataTable(list);
    }

    /**
     * 导出商品列表
     */
    @RequiresPermissions("ccairbag:products:export")
    @Log(title = "商品", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CcairbagProducts ccairbagProducts)
    {
        List<CcairbagProducts> list = ccairbagProductsService.selectCcairbagProductsList(ccairbagProducts);
        ExcelUtil<CcairbagProducts> util = new ExcelUtil<CcairbagProducts>(CcairbagProducts.class);
        util.exportExcel(response, list, "商品数据");
    }

    /**
     * 获取商品详细信息
     */
    @RequiresPermissions("ccairbag:products:query")
    @GetMapping(value = "/{productId}")
    public AjaxResult getInfo(@PathVariable("productId") Long productId)
    {
        return success(ccairbagProductsService.selectCcairbagProductsByProductId(productId));
    }

    /**
     * 新增商品
     */
    @RequiresPermissions("ccairbag:products:add")
    @Log(title = "商品", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CcairbagProducts ccairbagProducts)
    {
        return toAjax(ccairbagProductsService.insertCcairbagProducts(ccairbagProducts));
    }

    /**
     * 修改商品
     */
    @RequiresPermissions("ccairbag:products:edit")
    @Log(title = "商品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CcairbagProducts ccairbagProducts)
    {
        return toAjax(ccairbagProductsService.updateCcairbagProducts(ccairbagProducts));
    }

    /**
     * 删除商品
     */
    @RequiresPermissions("ccairbag:products:remove")
    @Log(title = "商品", businessType = BusinessType.DELETE)
	@DeleteMapping("/{productIds}")
    public AjaxResult remove(@PathVariable Long[] productIds)
    {
        return toAjax(ccairbagProductsService.deleteCcairbagProductsByProductIds(productIds));
    }


    /**
     * 导出模板
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<CcairbagProducts> util = new ExcelUtil<CcairbagProducts>(CcairbagProducts.class);
        util.importTemplateExcel(response, "商品数据");
    }




    /**
     * 导入数据
     */
    @Log(title = "商品", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception
    {
        ExcelUtil<CcairbagProducts> util = new ExcelUtil<CcairbagProducts>(CcairbagProducts.class);
        InputStream inputStream = file.getInputStream();
        List<CcairbagProducts> list = util.importExcel(inputStream );
        inputStream.close();
        int count = ccairbagProductsService.batchInsertCcairbagProducts(list);
        return AjaxResult.success("导入成功" + count + "条信息！");
    }
}
