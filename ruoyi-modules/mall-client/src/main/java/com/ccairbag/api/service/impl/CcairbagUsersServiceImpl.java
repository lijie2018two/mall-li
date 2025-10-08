package com.ccairbag.api.service.impl;

import com.ccairbag.api.domain.dto.UsersInfo;
import com.ccairbag.api.mapper.CcairbagShopsMapper;
import com.ccairbag.api.mapper.CcairbagUserAddrMapper;
import com.ccairbag.api.mapper.CcairbagUserRegistrationMapper;
import com.ccairbag.api.mapper.CcairbagUsersMapper;
import com.ccairbag.api.service.ICcairbagUsersService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.oConvertUtils;
import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.common.redis.service.RedisService;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.domain.ccairbag.CcairbagShops;
import com.ruoyi.system.api.domain.ccairbag.CcairbagUserAddr;
import com.ruoyi.system.api.domain.ccairbag.CcairbagUserRegistration;
import com.ruoyi.system.api.domain.ccairbag.CcairbagUsers;
import com.ruoyi.system.api.model.LoginAppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * 用户管理Service业务层处理
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Service
@Slf4j
public class CcairbagUsersServiceImpl implements ICcairbagUsersService
{
    @Resource
    private CcairbagUsersMapper ccairbagUsersMapper;
    @Resource
    private CcairbagUserRegistrationMapper ccairbagUserRegistrationMapper;
    @Autowired
    private RedisService redisService;
    @Resource
    private CcairbagUserAddrMapper ccairbagUserAddrMapper;
    @Resource
    private CcairbagShopsMapper ccairbagShopsMapper;
    @Override
    public CcairbagUsers getUserInfo(String email) {
        CcairbagUsers ccairbagUsers = ccairbagUsersMapper.getUserInfo(email);
        if (!oConvertUtils.isEmpty(ccairbagUsers)){
            return ccairbagUsers;
        }
        return null;
    }

    @Override
    public CcairbagUsers addExt(CcairbagUsers ccairbagUsers) {
        ccairbagUsers.setDeleted(0);
        ccairbagUsers.setIsVerified(0);
        ccairbagUsers.setQualificationStatus(0);
        if (oConvertUtils.isEmpty(ccairbagUsers.getShowPhone())){
            ccairbagUsers.setShowPhone(0);
        }
        int i = ccairbagUsersMapper.insertCcairbagUsers(ccairbagUsers);
        if (i>0){
            return ccairbagUsers;
        }
        return null;
    }

    @Override
    public int updateCcairbagUsers(CcairbagUsers ccairbagUsers)
    {
        ccairbagUsers.setUpdateTime(DateUtils.getNowDate());
        return ccairbagUsersMapper.updateCcairbagUsers(ccairbagUsers);
    }

    @Override
    public AppResult editUser(CcairbagUsers ccairbagUsers) {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        Long userId = loginAppUser.getCcairbagUsers().getUserId();
        CcairbagUsers users = ccairbagUsersMapper.selectCcairbagUsersByUserId(userId);
        if (oConvertUtils.isNotEmpty(ccairbagUsers.getEmail())){
            CcairbagUsers ccairbagUsersE = ccairbagUsersMapper.getUserInfo(ccairbagUsers.getEmail());
            if(oConvertUtils.isNotEmpty(ccairbagUsersE)){
                return AppResult.error("邮箱已存在");
            }
            if (!users.getEmail().equals(ccairbagUsers.getEmail())){
                //如果改变邮箱了，则需要验证更改邮箱的验证码
                String verifyCode = redisService.getCacheObject("updateEmail_"+ccairbagUsers.getEmail());
                if (!ccairbagUsers.getVerifyCode().equals(verifyCode)){
                    return AppResult.error("验证码错误");
                }
            }
        }

        ccairbagUsers.setUpdateTime(DateUtils.getNowDate());
        int i = ccairbagUsersMapper.updateCcairbagUsers(ccairbagUsers);
        CcairbagUserRegistration registration1 = ccairbagUsers.getCcairbagUserRegistration();
        if (ccairbagUsers.getUserType() == 1 &&
                oConvertUtils.isNotEmpty(registration1)){
            if(oConvertUtils.isEmpty(registration1.getCompanyAddress()) ||
                    oConvertUtils.isEmpty(registration1.getCompanyCode()) ||
                    oConvertUtils.isEmpty(registration1.getCompanyLocation()) ||
                    oConvertUtils.isEmpty(registration1.getCompanyName())
            ){
                return AppResult.error("请完善公司信息");
            }
        }else if (ccairbagUsers.getUserType() == 2 &&
                oConvertUtils.isNotEmpty(registration1)){
            if(oConvertUtils.isEmpty(registration1.getIdCardNumber()) ||
                    oConvertUtils.isEmpty(registration1.getHomeAddress()) ||
                    oConvertUtils.isEmpty(registration1.getIdCardType()) ||
                    oConvertUtils.isEmpty(registration1.getRealName())
            ){
                return AppResult.error("请完善个人信息");
            }
        }
        if (oConvertUtils.isNotEmpty(registration1)){
            CcairbagUserRegistration registration = ccairbagUserRegistrationMapper.getInfoByUserId(ccairbagUsers.getUserId());
            log.info("registration1:{}",registration1);
            if (oConvertUtils.isEmpty(registration)){
                //新增
                registration1.setUserId(ccairbagUsers.getUserId());
                registration1.setUserType(ccairbagUsers.getUserType());
                registration1.setDeleted(0);
                ccairbagUserRegistrationMapper.insertCcairbagUserRegistration(registration1);
            }else {
                registration1.setUserId(ccairbagUsers.getUserId());
                registration1.setUserType(ccairbagUsers.getUserType());
                registration1.setId(registration.getId());
                log.info("registration1xxxx:{}",registration1);
                ccairbagUserRegistrationMapper.updateCcairbagUserRegistration(registration1);
            }

        }
        return new AppResult("修改成功");


//        if (i > 0){
//
//        }
//        return new AppResult("修改成功1");
    }


    @Override
    public AppResult<CcairbagUsers> registerUserVerification(CcairbagUserRegistration registration) {
        // 这里可以添加对 verificationCode 的验证逻辑
        if (registration == null || registration.getUserId() == null) {
            throw new RuntimeException("用户ID不能为空");
        }
        //判断用户id 和登录人信息匹配
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        Long userId = loginAppUser.getCcairbagUsers().getUserId();
        if (!registration.getUserId().equals(userId)) {
            throw new RuntimeException("非法请求");
        }
        CcairbagUsers ccairbagUsers = ccairbagUsersMapper.selectCcairbagUsersByUserId(registration.getUserId());
        if (registration.getUserType() == 1) {
            //企业卖家
            // TODO: 添加企业卖家的认证逻辑
            if ( oConvertUtils.isEmpty(registration.getCompanyCode())){
                throw new RuntimeException("企业代码不能为空");
            }
            if (oConvertUtils.isEmpty(registration.getCompanyName())){
                throw new RuntimeException("企业名称不能为空");
            }
            if (oConvertUtils.isEmpty(registration.getCompanyAddress())){
                throw new RuntimeException("企业地址不能为空");
            }
            if (oConvertUtils.isEmpty(registration.getPhone())){
                throw new RuntimeException("企业电话不能为空");
            }
            if (oConvertUtils.isEmpty(registration.getCompanyLocation())){
                throw new RuntimeException("企业所在地不能为空");
            }
        }else if (registration.getUserType() == 2) {
            //个人卖家
            // TODO: 添加个人卖家的认证逻辑
            if (oConvertUtils.isEmpty(registration.getPhone())){
                throw new RuntimeException("个人电话不能为空");
            }
            if (oConvertUtils.isEmpty(registration.getHomeAddress())){
                throw new RuntimeException("个人地址不能为空");
            }
            if (oConvertUtils.isEmpty(registration.getIdCardNumber())){
                throw new RuntimeException("身份证号不能为空");
            }
            if (oConvertUtils.isEmpty(registration.getRealName())){
                throw new RuntimeException("真实姓名不能为空");
            }
        }
        registration.setDeleted(0);
        int result = ccairbagUserRegistrationMapper.insertCcairbagUserRegistration(registration);
        if (result > 0){
            ccairbagUsers.setIsVerified(1);
            ccairbagUsersMapper.updateCcairbagUsers(ccairbagUsers);
        }
        //添加 手机号的混淆
        registration.setPhoneMix(registration.getPhone());
        ccairbagUsers.setCcairbagUserRegistration(registration);
        return new AppResult<>(ccairbagUsers);
    }

    @Override
    public AppResult applyForSeller(CcairbagShops shops) {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        Long userId = loginAppUser.getCcairbagUsers().getUserId();
        CcairbagUsers ccairbagUsers = ccairbagUsersMapper.selectCcairbagUsersByUserId(userId);
        if (oConvertUtils.isEmpty(ccairbagUsers)){
            return AppResult.error("用户不存在");
        }
        if (shops.getUserType() != 2){
            return AppResult.error("用户类型选择错误！");
        }
        CcairbagShops shops1 = ccairbagShopsMapper.getShopByUserId(userId);
        if (oConvertUtils.isNotEmpty(shops1)){
            return AppResult.error("您已申请成为个人卖家了");
        }
        ccairbagUsers.setUserType(2);
        ccairbagUsersMapper.updateCcairbagUsers(ccairbagUsers);
        CcairbagShops ccairbagShops = new CcairbagShops();
        ccairbagShops.setShopLogo(shops.getShopLogo());
        ccairbagShops.setIntro(shops.getIntro());
        ccairbagShops.setShopName(shops.getShopName());
        ccairbagShops.setCreateTime(DateUtils.getNowDate());
        ccairbagShops.setWalletBalance(BigDecimal.ZERO);
        ccairbagShops.setWalletBalanceExt(BigDecimal.ZERO);
        ccairbagShops.setWalletOrderIng(BigDecimal.ZERO);
        ccairbagShops.setDeleted(0);
        ccairbagShops.setApplicationStatus(1);
        ccairbagShops.setUserId(userId);
        ccairbagShops.setScale(new BigDecimal(0));
        ccairbagShops.setDealScale(new BigDecimal(0));
        ccairbagShopsMapper.insertCcairbagShops(ccairbagShops);
        return AppResult.success("申请成功");
    }

    @Override
    public AppResult<UsersInfo> getUsersInfo() {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        Long userId = loginAppUser.getCcairbagUsers().getUserId();
        CcairbagUsers ccairbagUsers = ccairbagUsersMapper.selectCcairbagUsersByUserId(userId);
        ccairbagUsers.setPhoneMix(ccairbagUsers.getPhone());
        ccairbagUsers.setPhone("");
        UsersInfo usersInfo = new UsersInfo();
        CcairbagUserRegistration registration = ccairbagUserRegistrationMapper.getInfoByUserId(userId);
        CcairbagShops shops = ccairbagShopsMapper.getShopByUserId(userId);
        List<CcairbagUserAddr> userAddrList = ccairbagUserAddrMapper.selectCcairbagUserAddrByUserId(userId);
        usersInfo.setCcairbagUser(ccairbagUsers);
        usersInfo.setAddrList(userAddrList);
        usersInfo.setRegistration(registration);
        usersInfo.setCcairbagShops(shops);
        return new AppResult<>(usersInfo);
    }

    @Override
    public AppResult changeLoginPassword(CcairbagUsers ccairbagUsers) {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        Long userId = loginAppUser.getCcairbagUsers().getUserId();
        CcairbagUsers users = ccairbagUsersMapper.selectCcairbagUsersByUserId(userId);
        if (!SecurityUtils.matchesPassword(ccairbagUsers.getPasswordOld(), users.getPasswordHash()))
        {
            //旧密码错误，返回错误信息
            return AppResult.error("旧密码错误");
        }else {
            //旧密码正确，修改密码
            String password = SecurityUtils.encryptPassword(ccairbagUsers.getPasswordHash());
            users.setPasswordHash(password);
            users.setUpdateTime(DateUtils.getNowDate());
            ccairbagUsersMapper.updateCcairbagUsers(users);
            return AppResult.success("密码修改成功");
        }
    }


}
