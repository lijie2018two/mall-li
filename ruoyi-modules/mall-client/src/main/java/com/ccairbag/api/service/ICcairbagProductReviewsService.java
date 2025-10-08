package com.ccairbag.api.service;

import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.system.api.domain.ccairbag.CcairbagProductReviews;

/**
 * 商品评论Service接口
 * 
 * @author lidabai
 * @date 2025-02-20
 */
public interface ICcairbagProductReviewsService 
{
    AppResult listByShopId(Long shopId);

    AppResult listByProdId(Long productId);
    AppResult addReviews(CcairbagProductReviews review);
}
