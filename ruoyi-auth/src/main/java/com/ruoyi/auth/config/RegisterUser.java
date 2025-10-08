package com.ruoyi.auth.config;

import com.ruoyi.system.api.domain.ccairbag.CcairbagShops;
import com.ruoyi.system.api.domain.ccairbag.CcairbagUsers;
import lombok.Data;

@Data
public class RegisterUser {
    private CcairbagUsers ccairbagUsers;
    private CcairbagShops ccairbagShops;
    private String verifyCode;
}
