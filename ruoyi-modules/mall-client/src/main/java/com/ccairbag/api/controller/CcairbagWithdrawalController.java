package com.ccairbag.api.controller;

import com.ccairbag.api.service.ICcairbagWithdrawalService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.system.api.domain.ccairbag.CcairbagWithdrawal;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 提现记录Controller
 * 
 * @author lidabai
 * @date 2025-04-27
 */
@RestController
@RequestMapping("ccairbag/withdrawal")
public class CcairbagWithdrawalController extends BaseController
{
    @Autowired
    private ICcairbagWithdrawalService ccairbagWithdrawalService;


    @ApiOperation(value = "查询商家自己的提现记录列表")
    @GetMapping("/list")
    public AppResult list()
    {
        return ccairbagWithdrawalService.list();
    }



    @ApiOperation(value = "查询提现记录详情")
    @GetMapping(value = "/getInfo")
    public AppResult getInfo(Long id)
    {
        return ccairbagWithdrawalService.getInfo(id);
    }


    @ApiOperation(value = "商家提交提现表单")
    @PostMapping("/addWithdrawal")
    public AppResult addWithdrawal(@RequestBody CcairbagWithdrawal ccairbagWithdrawal)
    {
        return ccairbagWithdrawalService.addWithdrawal(ccairbagWithdrawal);
    }




}
