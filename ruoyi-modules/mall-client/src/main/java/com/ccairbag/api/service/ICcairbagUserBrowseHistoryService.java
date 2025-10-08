package com.ccairbag.api.service;

import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.system.api.domain.ccairbag.CcairbagProducts;

/**
 * 用户最近浏览记录Service接口
 * 
 * @author ruoyi
 * @date 2025-07-01
 */
public interface ICcairbagUserBrowseHistoryService 
{
    void addBrowseHistory(Long userId, CcairbagProducts products,String shopName);


    public AppResult getRecentlyViewedProducts(Long userId, Integer pageNum, Integer pageSize);
}
