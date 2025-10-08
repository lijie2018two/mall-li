package com.ccairbag.api.service;


import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.system.api.domain.ccairbag.CcairbagWithdrawal;

/**
 * 提现记录Service接口
 * 
 * @author lidabai
 * @date 2025-04-27
 */
public interface ICcairbagWithdrawalService 
{
    AppResult list();

    AppResult getInfo(Long id);

    AppResult addWithdrawal(CcairbagWithdrawal ccairbagWithdrawal);

}
