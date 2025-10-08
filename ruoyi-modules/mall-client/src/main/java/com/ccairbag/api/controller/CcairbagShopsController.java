package com.ccairbag.api.controller;

import com.ccairbag.api.service.ICcairbagShopsService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.common.core.web.page.PageDataInfo;
import com.ruoyi.system.api.domain.ccairbag.CcairbagShops;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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



    @ApiOperation(value = "添加店铺信息")
    @PostMapping("/shopAddExt")
    public CcairbagShops shopAddExt(@RequestBody CcairbagShops ccairbagShops)
    {
        return ccairbagShopsService.addExt(ccairbagShops);
    }

    @ApiOperation(value = "修改店铺信息")
    @PostMapping("/editShop")
    public AppResult editShop(@RequestBody CcairbagShops ccairbagShops)
    {
        return ccairbagShopsService.editShop(ccairbagShops);
    }

    @ApiOperation(value = "添加销售资质信息")
    @PostMapping("/addShopBusinessQualification")
    public AppResult addShopBusinessQualification(@RequestBody CcairbagShops ccairbagShops)
    {
        return ccairbagShopsService.addShopBusinessQualification(ccairbagShops);
    }


    @ApiOperation(value = "通过用户id查询店铺信息")
    @GetMapping("/getShopByUserId/{userId}")
    public CcairbagShops getShopByUserId(@PathVariable("userId") Long userId)
    {
        return ccairbagShopsService.getShopByUserId(userId);
    }

    @ApiOperation(value = "根据商家id 获取商家详情")
    @GetMapping("/getShopById")
    public AppResult<CcairbagShops> getShopById(@ApiParam("商家id") Long shopId)
    {
        return ccairbagShopsService.getShopById(shopId);
    }

    @ApiOperation(value = "查询所有店铺信息")
    @GetMapping("/getAllShopInfo")
    public AppResult<PageDataInfo<CcairbagShops>> getAllShopInfo()
    {
        return ccairbagShopsService.getAllShopInfo();
    }

    @ApiOperation(value = "销售仪表盘")
    @GetMapping("/salesDashboard")
    public AppResult salesDashboard()
    {
        return ccairbagShopsService.salesDashboard();
    }


    @ApiOperation(value = "获取商户钱包金额")
    @GetMapping("/getShopMoney")
    public AppResult getShopMoney()
    {
        return ccairbagShopsService.getShopMoney();
    }



}
