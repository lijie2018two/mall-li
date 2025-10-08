package com.ccairbag.api.service;


import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.system.api.domain.ccairbag.CcairbagShopBank;

/**
 * 绑定的银行卡Service接口
 * 
 * @author lidabai
 * @date 2025-04-27
 */
public interface ICcairbagShopBankService 
{
    AppResult list();

    AppResult getInfo(Long id);

    AppResult addBankCars(CcairbagShopBank ccairbagShopBank);

    AppResult editBankCars(CcairbagShopBank ccairbagShopBank);

    AppResult delBankCars(Long id);

}
