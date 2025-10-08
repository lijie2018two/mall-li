package com.ccairbag.api.service;


import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.system.api.domain.ccairbag.CcairbagProductPromotionRelations;

/**
 * 商品 - 活动关联Service接口
 * 
 * @author lidabai
 * @date 2025-05-08
 */
public interface ICcairbagProductPromotionRelationsService 
{

    AppResult setProductActivity(CcairbagProductPromotionRelations ccairbagProductPromotionRelations);

    AppResult delProductActivity(CcairbagProductPromotionRelations ccairbagProductPromotionRelations);

}
