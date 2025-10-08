package com.ccairbag.api.mapper;

import com.ruoyi.system.api.domain.ccairbag.CcairbagProductReviews;
import com.ruoyi.system.api.domain.ccairbag.dto.CcairbagProductReviewsDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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

    List<CcairbagProductReviewsDTO> listByShopId(Long shopId);

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

    //根据商品id 查出商品的平均分
    public Double selectAvgScoreByProductId(Long productId);

    public Map<String, BigDecimal> selectAvgScoreByShopIdExt(Long shopId);

}
