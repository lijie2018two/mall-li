package com.ccairbag.api.service.impl;

import com.ccairbag.api.mapper.CcairbagProductPromotionRelationsMapper;
import com.ccairbag.api.mapper.CcairbagPromotionActivitiesMapper;
import com.ccairbag.api.service.ICcairbagPromotionActivitiesService;
import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.system.api.domain.ccairbag.CcairbagProducts;
import com.ruoyi.system.api.domain.ccairbag.CcairbagPromotionActivities;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 活动Service业务层处理
 * 
 * @author lidabai
 * @date 2025-05-08
 */
@Service
public class CcairbagPromotionActivitiesServiceImpl implements ICcairbagPromotionActivitiesService
{
    @Resource
    private CcairbagPromotionActivitiesMapper ccairbagPromotionActivitiesMapper;
    @Resource
    private CcairbagProductPromotionRelationsMapper ccairbagProductPromotionRelationsMapper;

    @Override
    public AppResult getActivitiesAndProduct() {
        CcairbagPromotionActivities ccairbagPromotionActivities = new CcairbagPromotionActivities();
        ccairbagPromotionActivities.setStatus(0);
        List<CcairbagPromotionActivities> activitiesList = ccairbagPromotionActivitiesMapper.selectCcairbagPromotionActivitiesList(ccairbagPromotionActivities);
        // 处理活动列表，确保没有商品的活动对应的商品列表长度为 0
        for (CcairbagPromotionActivities activity : activitiesList) {
           List<CcairbagProducts> productsList = ccairbagProductPromotionRelationsMapper.selectProductList(activity.getActivityId());
            activity.setProductsList(productsList);
            activity.setProductNum(productsList.size());
        }
        return new AppResult(activitiesList);
    }

    @Override
    public AppResult getActivities() {
        CcairbagPromotionActivities ccairbagPromotionActivities = new CcairbagPromotionActivities();
        ccairbagPromotionActivities.setStatus(0);
        List<CcairbagPromotionActivities> activitiesList = ccairbagPromotionActivitiesMapper.selectCcairbagPromotionActivitiesList(ccairbagPromotionActivities);
        return new AppResult(activitiesList);
    }
}
