package com.ruoyi.auth.service;

import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.UserConstants;
import com.ruoyi.common.core.domain.SmsSendMessage;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.oConvertUtils;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.redis.service.RedisService;
import com.ruoyi.common.security.service.BusinessTokenService;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteBusinessUserService;
import com.ruoyi.system.api.model.LoginAppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class BusinessLoginService {
    @Resource
    private RemoteBusinessUserService remoteBusinessUserService;




    @Resource
    private RedisService redisService;



    public AjaxResult getRegisterSmsCode(String phone){
        if (!StringUtils.isValidPhoneNumber(phone)) {
            return AjaxResult.error("手机号不在指定范围");
        }
        if (remoteBusinessUserService.existByPhone(phone,SecurityConstants.INNER)>0){
            return AjaxResult.error("手机号已注册");
        }
        String verifyCode = String.valueOf((new Random().nextInt(899999) + 100000));
        redisService.setCacheObject("business_register_"+phone,verifyCode, 5L, TimeUnit.MINUTES);
        SmsSendMessage smsSendMessage = new SmsSendMessage();
        smsSendMessage.setMobile(phone);
        smsSendMessage.setMessage("您正在进行手机验证操作，您的验证码为"+verifyCode+"。验证码有效期为5分钟。【淘老猫】");
        return AjaxResult.success("发送成功");
    }

    public AjaxResult getForgetSmsCode(String phone){
        if (!StringUtils.isValidPhoneNumber(phone)) {
            return AjaxResult.error("手机号不在指定范围");
        }
        if (remoteBusinessUserService.existByPhone(phone,SecurityConstants.INNER)==0){
            return AjaxResult.error("该手机号在平台没有注册，请确认手机号");
        }
        String verifyCode = String.valueOf((new Random().nextInt(899999) + 100000));
        redisService.setCacheObject("business_forget_"+phone,verifyCode, 5L, TimeUnit.MINUTES);
        SmsSendMessage smsSendMessage = new SmsSendMessage();
        smsSendMessage.setMobile(phone);
        smsSendMessage.setMessage("您正在进行手机验证操作，您的验证码为"+verifyCode+"。验证码有效期为5分钟。【淘老猫】");
        return AjaxResult.success("发送成功");
    }







}
