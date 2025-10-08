package com.ruoyi.auth.service;


import com.ruoyi.auth.config.RegisterUser;
import com.ruoyi.auth.utils.EmailUtils;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.utils.oConvertUtils;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.redis.service.RedisService;
import com.ruoyi.common.security.service.AppTokenService;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteAppUserService;
import com.ruoyi.system.api.RemoteUserService;
import com.ruoyi.system.api.domain.ccairbag.CcairbagShops;
import com.ruoyi.system.api.domain.ccairbag.CcairbagUserAddr;
import com.ruoyi.system.api.domain.ccairbag.CcairbagUserRegistration;
import com.ruoyi.system.api.domain.ccairbag.CcairbagUsers;
import com.ruoyi.system.api.model.LoginAppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 登录校验方法
 * 
 * @author ruoyi
 */
@Component
@Slf4j
public class AppLoginService
{
    @Resource
    private RemoteAppUserService remoteAppUserService;

    @Resource
    private RemoteUserService remoteUserService;

    @Resource
    private AppTokenService appTokenSerice;

    @Autowired
    private RedisService redisService;

    @Autowired
    private EmailUtils emailUtils;


    public boolean getUserInfo(String email){
        CcairbagUsers ccairbagUsers1 = remoteAppUserService.getUserInfo(email, SecurityConstants.INNER);
        if (oConvertUtils.isNotEmpty(ccairbagUsers1)){
            return true;
        }
        return false;
    }

    public AjaxResult mallClientLogin(CcairbagUsers ccairbagUsers){
        CcairbagUsers ccairbagUsers1 = remoteAppUserService.getUserInfo(ccairbagUsers.getEmail(), SecurityConstants.INNER);
        //验证 账号 + 密码 是否匹配正确 ，正确就返回 token，否则 返回错误提示
        if (oConvertUtils.isEmpty(ccairbagUsers1)){
            //登录失败，返回错误信息
            return AjaxResult.error("用户不存在");
        }
        if (!SecurityUtils.matchesPassword(ccairbagUsers.getPasswordHash(), ccairbagUsers1.getPasswordHash()))
        {
            //登录失败，返回错误信息
            return AjaxResult.error("密码错误");
        }else {
            Map<String, Object> map = new HashMap<>();
            LoginAppUser userInfo = new LoginAppUser();
            userInfo.setCcairbagUsers(ccairbagUsers1);
            map = appTokenSerice.createToken(userInfo);
            ccairbagUsers1.setPasswordHash("");
            map.put("ccairbagUser",ccairbagUsers1);
            map.put("categoryTree",remoteAppUserService.getCategoryTree(SecurityConstants.INNER));
            CcairbagShops shops = remoteAppUserService.getShopByUserId(ccairbagUsers1.getUserId(), SecurityConstants.INNER);
            CcairbagUserRegistration ccairbagUserRegistration = remoteAppUserService.getInfoByUserId(ccairbagUsers1.getUserId(), SecurityConstants.INNER);
            List<CcairbagUserAddr> ccairbagUserAddrList = remoteAppUserService.selectCcairbagUserAddrByUserId(ccairbagUsers1.getUserId(), SecurityConstants.INNER);
//            if (ccairbagUsers1.getUserType()==1 && oConvertUtils.isNotEmpty(shops.getShopId())){
//                map.put("ccairbagShops",shops);
//            }
            map.put("ccairbagShops",shops);
            map.put("addrList",ccairbagUserAddrList);
            if (oConvertUtils.isNotEmpty(ccairbagUserRegistration)){
                ccairbagUsers1.setCcairbagUserRegistration(ccairbagUserRegistration);
            }
            return AjaxResult.success(map);
        }
    }

    public AjaxResult mallClientRegister(RegisterUser registerUser){
        CcairbagUsers ccairbagUsers1 = remoteAppUserService.getUserInfo(registerUser.getCcairbagUsers().getEmail(), SecurityConstants.INNER);


        if (registerUser.getCcairbagUsers().getUserType()==0){
            if (oConvertUtils.isNotEmpty(ccairbagUsers1)){
                return AjaxResult.error("用户已存在");
            }else {
                //先验证 验证码
                if (!registerUser.getVerifyCode().equals(redisService.getCacheObject("register_"+registerUser.getCcairbagUsers().getEmail()))){
                    return AjaxResult.error("验证码错误");
                }
                registerUser.getCcairbagUsers().setPasswordHash(SecurityUtils.encryptPassword(registerUser.getCcairbagUsers().getPasswordHash()));
                CcairbagUsers ccairbagUsers2 = remoteAppUserService.addExt(registerUser.getCcairbagUsers(), SecurityConstants.INNER);
                if (oConvertUtils.isNotEmpty(ccairbagUsers2)){
                    Map<String, Object> map = new HashMap<>();
                    LoginAppUser userInfo = new LoginAppUser();
                    userInfo.setCcairbagUsers(ccairbagUsers2);
                    map = appTokenSerice.createToken(userInfo);
                    ccairbagUsers2.setPasswordHash("");
                    map.put("ccairbagUser",ccairbagUsers2);
                    return AjaxResult.success("User registration is successful.",map);
                }
                return AjaxResult.error("Registration failed.");
            }
        }else {
            if (oConvertUtils.isNotEmpty(ccairbagUsers1)) {
                return AjaxResult.error("Application has been submitted. Please wait for review.");
            }else {
                //先验证 验证码
                if (!registerUser.getVerifyCode().equals(redisService.getCacheObject("register_"+registerUser.getCcairbagUsers().getEmail()))){
                    return AjaxResult.error("验证码错误");
                }
                registerUser.getCcairbagUsers().setPasswordHash(SecurityUtils.encryptPassword(registerUser.getCcairbagUsers().getPasswordHash()));
                CcairbagUsers ccairbagUsers2 = remoteAppUserService.addExt(registerUser.getCcairbagUsers(), SecurityConstants.INNER);
                if (oConvertUtils.isNotEmpty(ccairbagUsers2)){
                    Map<String, Object> map = new HashMap<>();
                    LoginAppUser userInfo = new LoginAppUser();
                    userInfo.setCcairbagUsers(ccairbagUsers2);
                    map = appTokenSerice.createToken(userInfo);
                    ccairbagUsers2.setPasswordHash("");
                    map.put("ccairbagUser",ccairbagUsers2);
                    //添加商铺表 ，并把状态改成 待审核
                    registerUser.getCcairbagShops().setUserId(ccairbagUsers2.getUserId());
                    registerUser.getCcairbagShops().setWalletBalance(new BigDecimal(0));
                    registerUser.getCcairbagShops().setWalletBalanceExt(new BigDecimal(0));
                    registerUser.getCcairbagShops().setApplicationStatus(1);

                    registerUser.getCcairbagShops().setScale(new BigDecimal(0));
                    registerUser.getCcairbagShops().setDealScale(new BigDecimal(0));

                    CcairbagShops shops = remoteAppUserService.shopAddExt(registerUser.getCcairbagShops(), SecurityConstants.INNER);
                    map.put("ccairbagShops",shops);
                    map.put("categoryTree",remoteAppUserService.getCategoryTree(SecurityConstants.INNER));

                    if (oConvertUtils.isNotEmpty(shops)){
                        return AjaxResult.success("Store registration is successful. Please wait for review.",map);
                    }else {
                        return AjaxResult.error("Store registration failed.");
                    }

                }
                return AjaxResult.error("Registration failed.");
            }
        }

    }



    public AjaxResult mallClientSentEmail(String email,String type) {
        CcairbagUsers ccairbagUsers1 = remoteAppUserService.getUserInfo(email, SecurityConstants.INNER);


        if ("register".equals(type)){
            if (oConvertUtils.isNotEmpty(ccairbagUsers1)){
                return AjaxResult.error("This email address has already been registered.");
            }
        }

        String verifyCode = String.valueOf((new Random().nextInt(899999) + 100000));
        redisService.setCacheObject(type+"_"+email,verifyCode, 5L, TimeUnit.MINUTES);

//        emailUtil.sendSimpleEmail(email,"验证邮箱","You are performing an email verification operation, and your verification code is "+verifyCode+"。The verification code is valid for 5 minutes.");
        emailUtils.send("验证邮箱","You are performing an email verification operation, and your verification code is "+verifyCode+"。The verification code is valid for 5 minutes.",email);
        return AjaxResult.success("发送成功");
    }

    public AjaxResult mallClientRetrieve(CcairbagUsers ccairbagUsers){
        CcairbagUsers ccairbagUsers1 = remoteAppUserService.getUserInfo(ccairbagUsers.getEmail(), SecurityConstants.INNER);
        if (!ccairbagUsers.getConfirmedPassword().equals(ccairbagUsers.getPasswordHash())){
            return AjaxResult.error("两个密码不一致");
        }

        if (!ccairbagUsers.getVerifyCode().equals(redisService.getCacheObject("retrieve_"+ccairbagUsers.getEmail()))){
            return AjaxResult.error("验证码错误");
        }
        //修改密码
        ccairbagUsers1.setPasswordHash(SecurityUtils.encryptPassword(ccairbagUsers.getPasswordHash()));
        int i = remoteAppUserService.editExt(ccairbagUsers1, SecurityConstants.INNER);
        if (i>0){
            return AjaxResult.success("修改成功");
        }else {
            return AjaxResult.error("修改失败");
        }
    }

















}
