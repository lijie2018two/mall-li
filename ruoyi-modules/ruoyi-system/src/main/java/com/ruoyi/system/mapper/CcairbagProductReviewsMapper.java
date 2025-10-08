package com.ruoyi.system.mapper;

import com.ruoyi.system.api.domain.ccairbag.CcairbagProductReviews;

import java.util.List;

/**
 * 商品评论Mapper接口
 * 
 * @author lidabai
 * @date 2025-02-20
 */
public interface CcairbagProductReviewsMapper 
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
     * 删除商品评论
     * 
     * @param reviewId 商品评论主键
     * @return 结果
     */
    public int deleteCcairbagProductReviewsByReviewId(Long reviewId);

    /**
     * 批量删除商品评论
     * 
     * @param reviewIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCcairbagProductReviewsByReviewIds(Long[] reviewIds);
}
