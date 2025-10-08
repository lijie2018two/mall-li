package com.ccairbag.api.service;

import com.ccairbag.api.domain.dto.UsersInfo;
import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.system.api.domain.ccairbag.CcairbagShops;
import com.ruoyi.system.api.domain.ccairbag.CcairbagUserRegistration;
import com.ruoyi.system.api.domain.ccairbag.CcairbagUsers;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 用户管理Service接口
 * 
 * @author lidabai
 * @date 2025-02-20
 */
public interface ICcairbagUsersService 
{
    CcairbagUsers getUserInfo(@PathVariable("email") String email);
    CcairbagUsers addExt(@RequestBody CcairbagUsers ccairbagUsers);

    public int updateCcairbagUsers(CcairbagUsers ccairbagUsers);

    AppResult editUser(CcairbagUsers ccairbagUsers);

    AppResult<CcairbagUsers> registerUserVerification(CcairbagUserRegistration registration);

    AppResult applyForSeller(CcairbagShops shops);

    AppResult<UsersInfo> getUsersInfo();

    AppResult changeLoginPassword(@RequestBody CcairbagUsers ccairbagUsers);
}
