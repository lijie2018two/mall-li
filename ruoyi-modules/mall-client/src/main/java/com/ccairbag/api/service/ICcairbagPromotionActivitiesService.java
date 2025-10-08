package com.ccairbag.api.service;


import com.ruoyi.common.core.web.domain.AppResult;

/**
 * 活动Service接口
 * 
 * @author lidabai
 * @date 2025-05-08
 */
public interface ICcairbagPromotionActivitiesService 
{
    AppResult getActivitiesAndProduct();
    AppResult getActivities();

}
