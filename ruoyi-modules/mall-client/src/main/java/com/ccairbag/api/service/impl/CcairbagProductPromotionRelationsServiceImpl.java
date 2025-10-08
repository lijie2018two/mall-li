package com.ccairbag.api.service.impl;

import com.ccairbag.api.mapper.CcairbagProductPromotionRelationsMapper;
import com.ccairbag.api.mapper.CcairbagProductsMapper;
import com.ccairbag.api.mapper.CcairbagPromotionActivitiesMapper;
import com.ccairbag.api.service.ICcairbagProductPromotionRelationsService;
import com.ruoyi.common.core.utils.oConvertUtils;
import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.system.api.domain.ccairbag.CcairbagProductPromotionRelations;
import com.ruoyi.system.api.domain.ccairbag.CcairbagProducts;
import com.ruoyi.system.api.domain.ccairbag.CcairbagPromotionActivities;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 商品 - 活动关联Service业务层处理
 * 
 * @author lidabai
 * @date 2025-05-08
 */
@Service
public class CcairbagProductPromotionRelationsServiceImpl implements ICcairbagProductPromotionRelationsService
{
    @Resource
    private CcairbagProductPromotionRelationsMapper ccairbagProductPromotionRelationsMapper;
    @Resource
    private CcairbagPromotionActivitiesMapper ccairbagPromotionActivitiesMapper;
    @Resource
    private CcairbagProductsMapper ccairbagProductsMapper;
    @Override
    public AppResult setProductActivity(CcairbagProductPromotionRelations ccairbagProductPromotionRelations) {
        CcairbagPromotionActivities activitie = ccairbagPromotionActivitiesMapper.selectCcairbagPromotionActivitiesByActivityId(ccairbagProductPromotionRelations.getActivityId());
        if (oConvertUtils.isEmpty(activitie)){
            return AppResult.error("活动不存在");
        }
        CcairbagProducts products = ccairbagProductsMapper.selectCcairbagProductsByProductId(ccairbagProductPromotionRelations.getProductId());
        if (oConvertUtils.isEmpty(products)){
            return AppResult.error("商品不存在");
        }
        CcairbagProductPromotionRelations promotionRelations = ccairbagProductPromotionRelationsMapper.getPromotionRelationsInfo(ccairbagProductPromotionRelations.getProductId(), ccairbagProductPromotionRelations.getActivityId());
        if (oConvertUtils.isNotEmpty(promotionRelations)){
            return AppResult.error("该商品已参加该活动");
        }
        ccairbagProductPromotionRelations.setPriority(0L);
        ccairbagProductPromotionRelations.setDeleted(0);
        int i = ccairbagProductPromotionRelationsMapper.insertCcairbagProductPromotionRelations(ccairbagProductPromotionRelations);
        if (i>0){
            return AppResult.success("添加成功");
        }
        return AppResult.error("添加失败");
    }

    @Override
    public AppResult delProductActivity(CcairbagProductPromotionRelations ccairbagProductPromotionRelations) {
        CcairbagPromotionActivities activitie = ccairbagPromotionActivitiesMapper.selectCcairbagPromotionActivitiesByActivityId(ccairbagProductPromotionRelations.getActivityId());
        if (oConvertUtils.isEmpty(activitie)){
            return AppResult.error("活动不存在");
        }
        CcairbagProducts products = ccairbagProductsMapper.selectCcairbagProductsByProductId(ccairbagProductPromotionRelations.getProductId());
        if (oConvertUtils.isEmpty(products)){
            return AppResult.error("商品不存在");
        }
        CcairbagProductPromotionRelations promotionRelations = ccairbagProductPromotionRelationsMapper.getPromotionRelationsInfo(ccairbagProductPromotionRelations.getProductId(), ccairbagProductPromotionRelations.getActivityId());
        if (oConvertUtils.isEmpty(promotionRelations)){
            return AppResult.error("该商品未参加该活动");
        }
        promotionRelations.setDeleted(1);
        promotionRelations.setUpdateTime(new Date());
        ccairbagProductPromotionRelationsMapper.updateCcairbagProductPromotionRelations(promotionRelations);
        return AppResult.success("移除成功");
    }
}
