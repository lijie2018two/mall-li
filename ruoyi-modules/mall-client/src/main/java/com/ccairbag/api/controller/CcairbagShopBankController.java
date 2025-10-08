package com.ccairbag.api.controller;

import com.ccairbag.api.service.ICcairbagShopBankService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.system.api.domain.ccairbag.CcairbagShopBank;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 绑定的银行卡Controller
 * 
 * @author lidabai
 * @date 2025-04-27
 */
@RestController
@RequestMapping("ccairbag/bank")
public class CcairbagShopBankController extends BaseController
{
    @Autowired
    private ICcairbagShopBankService ccairbagShopBankService;

    @ApiOperation(value = "查询改商户 绑定的卡列表")
    @GetMapping("/list")
    public AppResult list()
    {
        return ccairbagShopBankService.list();
    }



    @ApiOperation(value = "查询绑定的卡详情")
    @GetMapping(value = "/getInfo")
    public AppResult getInfo( Long id)
    {
        return ccairbagShopBankService.getInfo(id);
    }


    @ApiOperation(value = "新增绑定的银行卡")
    @PostMapping("/addBankCars")
    public AppResult addBankCars(@RequestBody CcairbagShopBank ccairbagShopBank)
    {
        return ccairbagShopBankService.addBankCars(ccairbagShopBank);
    }


    @ApiOperation(value = "修改绑定的银行卡")
    @PostMapping("/editBankCars")
    public AppResult editBankCars(@RequestBody CcairbagShopBank ccairbagShopBank)
    {
        return ccairbagShopBankService.editBankCars(ccairbagShopBank);
    }

    @ApiOperation(value = "删除绑定的银行卡")
    @PostMapping("/delBankCars")
    public AppResult delBankCars(Long id)
    {
        return ccairbagShopBankService.delBankCars(id);
    }

}
