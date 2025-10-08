package com.ccairbag.api.controller;

import com.ccairbag.api.domain.dto.UsersInfo;
import com.ccairbag.api.service.ICcairbagUsersService;
import com.ruoyi.common.core.utils.oConvertUtils;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.system.api.domain.ccairbag.CcairbagShops;
import com.ruoyi.system.api.domain.ccairbag.CcairbagUserRegistration;
import com.ruoyi.system.api.domain.ccairbag.CcairbagUsers;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



/**
 * 用户管理Controller
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@RestController
@RequestMapping("ccairbag/users")
public class CcairbagUsersController extends BaseController
{
    @Autowired
    private ICcairbagUsersService ccairbagUsersService;



    @ApiOperation(value = "通过邮箱查询用户信息")
    @GetMapping("/getUserInfo/{email}")
    public CcairbagUsers getUserInfo(@PathVariable("email") String email)
    {
        return ccairbagUsersService.getUserInfo(email);
    }

    @ApiOperation(value = "通过邮箱查询用户信息Ext")
    @GetMapping("/getUserInfoExt")
    public AppResult getUserInfoExt(@ApiParam("email") String email)
    {
        CcairbagUsers ccairbagUsers = ccairbagUsersService.getUserInfo(email);
        if (oConvertUtils.isNotEmpty(ccairbagUsers)){
            return new AppResult(ccairbagUsers);
        }else {
            return  AppResult.error("用户不存在");
        }
    }

    @ApiOperation(value = "注册用户信息")
    @PostMapping("/addExt")
    public CcairbagUsers addExt(@RequestBody CcairbagUsers ccairbagUsers)
    {
        return ccairbagUsersService.addExt(ccairbagUsers);
    }


    @PostMapping("/editExt")
    public int editExt(@RequestBody CcairbagUsers ccairbagUsers)
    {
        return ccairbagUsersService.updateCcairbagUsers(ccairbagUsers);
    }

    @ApiOperation(value = "更改用户信息")
    @PostMapping("/editUser")
    public AppResult editUser(@RequestBody CcairbagUsers ccairbagUsers)
    {
        return ccairbagUsersService.editUser(ccairbagUsers);
    }

    @ApiOperation(value = "添加用户认证信息")
    @PostMapping("/registerVerification")
    public AppResult<CcairbagUsers> registerVerification(@RequestBody CcairbagUserRegistration registration) {
        return ccairbagUsersService.registerUserVerification(registration);
    }

    @ApiOperation(value = "普通用户 申请 成为个人卖家的（暂时直接通过）")
    @PostMapping("/applyForSeller")
    public AppResult applyForSeller(@RequestBody CcairbagShops shops) {
        return ccairbagUsersService.applyForSeller(shops);
    }

    @ApiOperation(value = "根据token 获取最新用户信息")
    @GetMapping("/getUsersInfo")
    public AppResult<UsersInfo> getUsersInfo()
    {
        return ccairbagUsersService.getUsersInfo();
    }


    @ApiOperation(value = "更改登录密码")
    @PostMapping("/changeLoginPassword")
    public AppResult changeLoginPassword(@RequestBody CcairbagUsers ccairbagUsers)
    {
        return ccairbagUsersService.changeLoginPassword(ccairbagUsers);
    }


}
