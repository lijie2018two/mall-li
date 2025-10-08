package com.ccairbag.api.controller;

import com.ccairbag.api.service.ICcairbagProductReviewsService;
import com.ruoyi.common.core.utils.oConvertUtils;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.system.api.domain.ccairbag.CcairbagProductReviews;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商品评论Controller
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@RestController
@RequestMapping("ccairbag/reviews")

public class CcairbagProductReviewsController extends BaseController
{
    @Autowired
    private ICcairbagProductReviewsService ccairbagProductReviewsService;



    @ApiOperation(value = "根据店铺id 获取该店铺的带分页评论")
    @GetMapping("/listByShopId")
    public AppResult listByShopId(@ApiParam("商家id") Long shopId)
    {
        return ccairbagProductReviewsService.listByShopId(shopId);
    }

    @ApiOperation(value = "根据商品id 获取评论")
    @GetMapping("/listByProdId")
    public AppResult listByProdId(@ApiParam("商品id") Long productId)
    {
        return ccairbagProductReviewsService.listByProdId(productId);
    }


    @ApiOperation(value = "用户发起评论" )
    @PostMapping("/addReviews")
    public AppResult addReviews(@RequestBody CcairbagProductReviews review)
    {
        if (oConvertUtils.isEmpty(review.getOrderItemId())){
            return AppResult.error("请传入订单id");
        }
        if (oConvertUtils.isEmpty(review.getAccuracyScore())){
            return AppResult.error("请传入描述准确性评分");
        }
        if (oConvertUtils.isEmpty(review.getLogisticsScore())){
            return AppResult.error("请传入物流速度评分");
        }
        if (oConvertUtils.isEmpty(review.getCommunicationScore())){
            return AppResult.error("请传入沟通评分");
        }

        if (oConvertUtils.isEmpty(review.getProdId())){
            return AppResult.error("商品id不能为空");
        }
        return ccairbagProductReviewsService.addReviews(review);
    }


}
