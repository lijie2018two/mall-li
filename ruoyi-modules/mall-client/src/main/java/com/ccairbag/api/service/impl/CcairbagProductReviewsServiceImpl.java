package com.ccairbag.api.service.impl;

import com.ccairbag.api.mapper.CcairbagOrderDetailsMapper;
import com.ccairbag.api.mapper.CcairbagProductReviewsMapper;
import com.ccairbag.api.mapper.CcairbagProductsMapper;
import com.ccairbag.api.mapper.CcairbagUsersMapper;
import com.ccairbag.api.service.ICcairbagProductReviewsService;
import com.ruoyi.common.core.utils.PageUtils;
import com.ruoyi.common.core.utils.oConvertUtils;
import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.common.core.web.page.PageDataInfo;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.domain.ccairbag.CcairbagOrderDetails;
import com.ruoyi.system.api.domain.ccairbag.CcairbagProductReviews;
import com.ruoyi.system.api.domain.ccairbag.CcairbagProducts;
import com.ruoyi.system.api.domain.ccairbag.CcairbagUsers;
import com.ruoyi.system.api.domain.ccairbag.dto.CcairbagProductReviewsDTO;
import com.ruoyi.system.api.model.LoginAppUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品评论Service业务层处理
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Service
public class CcairbagProductReviewsServiceImpl implements ICcairbagProductReviewsService
{
    @Resource
    private CcairbagProductReviewsMapper ccairbagProductReviewsMapper;
    @Resource
    private CcairbagUsersMapper ccairbagUsersMapper;
    @Resource
    private CcairbagProductsMapper ccairbagProductsMapper;
    @Resource
    private CcairbagOrderDetailsMapper ccairbagOrderDetailsMapper;

    @Override
    public AppResult listByShopId(Long shopId) {
        PageUtils.startPage();
        List<CcairbagProductReviewsDTO> reviewsDTOS = ccairbagProductReviewsMapper.listByShopId(shopId);
        for (CcairbagProductReviewsDTO reviews : reviewsDTOS){
            reviews.setUserNameExt(reviews.getUserName());
        }
        PageDataInfo<CcairbagProductReviewsDTO> pageDataInfo = new PageDataInfo<>(reviewsDTOS);
        PageUtils.clearPage();
        return new AppResult(pageDataInfo);
    }

    @Override
    public AppResult listByProdId(Long productId) {
        CcairbagProducts products = ccairbagProductsMapper.selectCcairbagProductsByProductIdExt(productId);
        if (oConvertUtils.isEmpty(products)){
            return AppResult.error("商品不存在");
        }
        CcairbagProductReviews review = new CcairbagProductReviews();
        review.setProdId(productId);
        review.setStatus(1);
        PageUtils.startPage();
        List<CcairbagProductReviews> productReviews = ccairbagProductReviewsMapper.selectCcairbagProductReviewsList(review);
        for (CcairbagProductReviews reviews : productReviews){
            reviews.setUserNameExt(reviews.getUserName());
        }
        PageDataInfo<CcairbagProductReviews> pageDataInfo = new PageDataInfo<>(productReviews);
        PageUtils.clearPage();
        Map<String, Object> map = new HashMap<>();
        map.put("productReviews", pageDataInfo);
        //平均分 获取该商品平均分
        Double avgScore = ccairbagProductReviewsMapper.selectAvgScoreByProductId(productId);
        map.put("avgScore",avgScore);
        return new AppResult(map);
    }

    @Override
    public AppResult addReviews(CcairbagProductReviews review) {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        Long userId = loginAppUser.getCcairbagUsers().getUserId();
        CcairbagUsers users = ccairbagUsersMapper.selectCcairbagUsersByUserId(userId);
        if (oConvertUtils.isEmpty(users)){
            return AppResult.error("用户不存在");
        }
        CcairbagProducts products = ccairbagProductsMapper.selectCcairbagProductsByProductIdExt(review.getProdId());
        if (oConvertUtils.isEmpty(products)){
            return AppResult.error("商品不存在");
        }
        CcairbagOrderDetails details = ccairbagOrderDetailsMapper.selectCcairbagOrderDetailsByDetailId(review.getOrderItemId());
        if (oConvertUtils.isEmpty(details)){
            return AppResult.error("订单不存在");
        }
        if (!details.getUserId().equals(userId)){
            return AppResult.error("订单不属于当前用户");
        }
        review.setUserId(userId);
        review.setUserName(users.getUserName());
        review.setHeadimgurl(users.getHeadimgurl());
        review.setStatus(1);
        review.setDeleted(0);
        review.setRecTime(new Date());
        double score = review.getAccuracyScore()+review.getLogisticsScore()+review.getCommunicationScore();
        double scorex = score/3;
        BigDecimal bd = new BigDecimal(scorex);
        double result = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        review.setTotalScore(result);
        if (score>=12){
            review.setEvaluate(0);
        }else if (6<=score && score<12){
            review.setEvaluate(1);
        }else if (score<6){
            review.setEvaluate(2);
        }
        review.setIsAnonymous(1);
        ccairbagProductReviewsMapper.insertCcairbagProductReviews(review);
        return AppResult.success("添加成功");
    }
}
