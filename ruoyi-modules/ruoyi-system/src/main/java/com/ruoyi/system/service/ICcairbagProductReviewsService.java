package com.ruoyi.system.service;

import com.ruoyi.system.api.domain.ccairbag.CcairbagProductReviews;

import java.util.List;

/**
 * 商品评论Service接口
 * 
 * @author lidabai
 * @date 2025-02-20
 */
public interface ICcairbagProductReviewsService 
{
    /**
     * 查询商品评论
     * 
     * @param reviewId 商品评论主键
     * @return 商品评论
     */
    public CcairbagProductReviews selectCcairbagProductReviewsByReviewId(Long reviewId);

    /**
     * 查询商品评论列表
     * 
     * @param ccairbagProductReviews 商品评论
     * @return 商品评论集合
     */
    public List<CcairbagProductReviews> selectCcairbagProductReviewsList(CcairbagProductReviews ccairbagProductReviews);

    /**
     * 新增商品评论
     * 
     * @param ccairbagProductReviews 商品评论
     * @return 结果
     */
    public int insertCcairbagProductReviews(CcairbagProductReviews ccairbagProductReviews);

    /**
     * 修改商品评论
     * 
     * @param ccairbagProductReviews 商品评论
     * @return 结果
     */
    public int updateCcairbagProductReviews(CcairbagProductReviews ccairbagProductReviews);

    /**
     * 批量删除商品评论
     * 
     * @param reviewIds 需要删除的商品评论主键集合
     * @return 结果
     */
    public int deleteCcairbagProductReviewsByReviewIds(Long[] reviewIds);

    /**
     * 删除商品评论信息
     * 
     * @param reviewId 商品评论主键
     * @return 结果
     */
    public int deleteCcairbagProductReviewsByReviewId(Long reviewId);

    /**
     * 批量新增商品评论
     *
     * @param ccairbagProductReviewss 商品评论List
     * @return 结果
     */
    public int batchInsertCcairbagProductReviews(List<CcairbagProductReviews> ccairbagProductReviewss);
}
