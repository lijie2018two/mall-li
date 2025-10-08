package com.ruoyi.auth.controller;


import com.ruoyi.auth.config.RegisterUser;
import com.ruoyi.auth.form.LoginAppBody;
import com.ruoyi.auth.service.AppLoginService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.oConvertUtils;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.security.service.AppTokenService;
import com.ruoyi.system.api.domain.ccairbag.CcairbagUsers;
import com.ruoyi.system.api.model.LoginAppUser;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * token 控制
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/auth")
public class TokenAppController
{


    @Autowired
    private AppLoginService appLoginService;


    @ApiOperation(value = "客户商户端登录接口")
    @PostMapping("/mallClientLogin")
    public AjaxResult mallClientLogin(@RequestBody CcairbagUsers ccairbagUsers)
    {

        if (oConvertUtils.isEmpty(ccairbagUsers.getEmail())){
            return AjaxResult.error("邮箱不能为空");
        }
        if (oConvertUtils.isEmpty(ccairbagUsers.getPasswordHash())){
            return AjaxResult.error("密码不能为空");
        }
        return appLoginService.mallClientLogin(ccairbagUsers);
    }


    @ApiOperation(value = "客户商家注册接口")
    @PostMapping("/mallClientRegister")
    public AjaxResult mallClientRegister(@RequestBody RegisterUser registerUser)
    {


        if (oConvertUtils.isEmpty(registerUser.getCcairbagUsers().getEmail())){
            return AjaxResult.error("邮箱不能为空");
        }
        if (oConvertUtils.isEmpty(registerUser.getVerifyCode())){
            return AjaxResult.error("验证码不能为空");
        }
        if (oConvertUtils.isEmpty(registerUser.getCcairbagUsers().getPasswordHash())){
            return AjaxResult.error("密码不能为空");
        }
        if (oConvertUtils.isEmpty(registerUser.getCcairbagUsers().getUserName())){
            return AjaxResult.error("用户名不能为空");
        }
        if (registerUser.getCcairbagUsers().getUserType()==1){
            if (oConvertUtils.isEmpty(registerUser.getCcairbagShops().getIntro())){
                return AjaxResult.error("店铺简介不能为空");
            }
            if (oConvertUtils.isEmpty(registerUser.getCcairbagShops().getShopName())){
                return AjaxResult.error("店铺名称不能为空");
            }
            if (oConvertUtils.isEmpty(registerUser.getCcairbagShops().getShopPhone())){
                return AjaxResult.error("店铺联系方式不能为空");
            }
        }
        return appLoginService.mallClientRegister(registerUser);
    }


    @ApiOperation(value = "客户商户端注册获取邮箱验证码")
    @GetMapping("/mallClientRegisterSentEmail")
    public AjaxResult mallClientSentEmail(@ApiParam(name = "email", value = "邮箱", required = true)  String email)
    {
        if (oConvertUtils.isEmpty(email)){
            return AjaxResult.error("邮箱不能为空");
        }
        return appLoginService.mallClientSentEmail(email,"register");
    }

    @ApiOperation(value = "客户商户端更新用户信息的邮箱时 获取邮箱验证码")
    @GetMapping("/mallClientUpdateEmailSentEmail")
    public AjaxResult mallClientUpdateEmailSentEmail(@ApiParam(name = "email", value = "邮箱", required = true)  String email)
    {
        if (oConvertUtils.isEmpty(email)){
            return AjaxResult.error("邮箱不能为空");
        }
        return appLoginService.mallClientSentEmail(email,"updateEmail");
    }

    @ApiOperation(value = "客户商户端找回密码获取邮箱验证码")
    @GetMapping("/mallClientRetrieveSentEmail")
    public AjaxResult mallClientRetrieveSentEmail(@ApiParam(name = "email", value = "邮箱", required = true)  String email)
    {
        if (oConvertUtils.isEmpty(email)){
            return AjaxResult.error("邮箱不能为空");
        }

        if (!appLoginService.getUserInfo(email)){
            return AjaxResult.error("账号不存在不存在");
        }
        return appLoginService.mallClientSentEmail(email,"retrieve");
    }

    @ApiOperation(value = "客户商户端找回密码提交表单")
    @PostMapping("/mallClientRetrieve")
    public AjaxResult mallClientRetrieve(@RequestBody CcairbagUsers ccairbagUsers)
    {
        if (oConvertUtils.isEmpty(ccairbagUsers.getEmail())){
            return AjaxResult.error("邮箱不能为空");
        }
        if (oConvertUtils.isEmpty(ccairbagUsers.getPasswordHash())){
            return AjaxResult.error("密码不能为空");
        }
        if (oConvertUtils.isEmpty(ccairbagUsers.getConfirmedPassword())){
            return AjaxResult.error("确认密码不能为空");
        }
        if (oConvertUtils.isEmpty(ccairbagUsers.getVerifyCode())){
            return AjaxResult.error("验证码不能为空");
        }
        return appLoginService.mallClientRetrieve(ccairbagUsers);
    }









}
