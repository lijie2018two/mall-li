package com.ruoyi.auth.controller;

import com.ruoyi.auth.service.BusinessLoginService;
import com.ruoyi.common.core.utils.oConvertUtils;
import com.ruoyi.common.core.web.domain.AjaxResult;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/business")
public class TokenBusinessController {
    @Autowired
    private BusinessLoginService businessLoginService;

    /**
     * 登陆
     * @return
     */
//    @PostMapping("/businessLogin")
//    public AjaxResult businessLogin(@RequestBody TlmUser user)
//    {
//        // 获取登录token
//        return businessLoginService.businessLogin(user);
//    }

    @ApiOperation("业务员注册 发送验证码")
    @GetMapping("/getRegisterSmsCode")
    public AjaxResult getRegisterSmsCode(@ApiParam(name = "phone", value = "手机号", required = true)  String phone) {
        if (oConvertUtils.isEmpty(phone)) {
            return AjaxResult.error("手机号码不能为空");
        }

        return businessLoginService.getRegisterSmsCode(phone);
    }

    @ApiOperation("业务员找回密码 发送验证码")
    @GetMapping("/getForgetSmsCode")
    public AjaxResult getForgetSmsCode(@ApiParam(name = "phone", value = "手机号", required = true)  String phone) {
        if (oConvertUtils.isEmpty(phone)) {
            return AjaxResult.error("手机号码不能为空");
        }
        return businessLoginService.getForgetSmsCode(phone);
    }






}
