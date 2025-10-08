package com.ccairbag.api.domain.dto;

import com.ruoyi.system.api.domain.ccairbag.CcairbagShops;
import com.ruoyi.system.api.domain.ccairbag.CcairbagUserAddr;
import com.ruoyi.system.api.domain.ccairbag.CcairbagUserRegistration;
import com.ruoyi.system.api.domain.ccairbag.CcairbagUsers;
import lombok.Data;

import java.util.List;

@Data
public class UsersInfo {
    private CcairbagUsers ccairbagUser;
    private List<CcairbagUserAddr> addrList;
    private CcairbagShops ccairbagShops;
    private CcairbagUserRegistration registration;
}
