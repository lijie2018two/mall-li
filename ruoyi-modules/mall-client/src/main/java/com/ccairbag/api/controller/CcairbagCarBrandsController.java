package com.ccairbag.api.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.ccairbag.api.service.ICcairbagCarBrandsService;
import io.swagger.annotations.ApiOperation;
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
import com.ruoyi.system.domain.CcairbagCarBrands;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 汽车品牌型号Controller
 * 
 * @author lidabai
 * @date 2025-03-12
 */
@RestController
@RequestMapping("/ccairbag/brands")
public class CcairbagCarBrandsController extends BaseController
{
    @Autowired
    private ICcairbagCarBrandsService ccairbagCarBrandsService;

    @ApiOperation(value = "查询汽车品牌列表")
    @GetMapping("/getMakeList")
    public List<CcairbagCarBrands> getMakeList(CcairbagCarBrands ccairbagCarBrands)
    {
        List<CcairbagCarBrands> list = ccairbagCarBrandsService.getMakeList();
        return list;
    }

    @ApiOperation(value = "根据汽车品牌查询汽车型号列表")
    @GetMapping("/getModelListByMake")
    public List<CcairbagCarBrands> getModelListByMake(String make)
    {
        List<CcairbagCarBrands> list = ccairbagCarBrandsService.getModelListByMake(make);
        return list;
    }


    @ApiOperation(value = "根据汽车型号查询汽车年份列表")
    @GetMapping("/getYearListByModel")
    public List<CcairbagCarBrands> getYearListByModel(String model)
    {
        List<CcairbagCarBrands> list = ccairbagCarBrandsService.getYearListByModel(model);
        return list;
    }


}
