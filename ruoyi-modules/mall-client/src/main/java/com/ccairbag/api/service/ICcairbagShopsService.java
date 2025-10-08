package com.ccairbag.api.service;

import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.common.core.web.page.PageDataInfo;
import com.ruoyi.system.api.domain.ccairbag.CcairbagShops;

/**
 * 店铺Service接口
 * 
 * @author lidabai
 * @date 2025-02-20
 */
public interface ICcairbagShopsService 
{


    CcairbagShops addExt( CcairbagShops ccairbagShops);

    AppResult editShop( CcairbagShops ccairbagShops);

    AppResult addShopBusinessQualification(CcairbagShops ccairbagShops);
    CcairbagShops getShopByUserId(Long userId);

    AppResult<CcairbagShops> getShopById(Long shopId);

    AppResult<PageDataInfo<CcairbagShops>> getAllShopInfo();

    AppResult salesDashboard();

    AppResult getShopMoney();


}
