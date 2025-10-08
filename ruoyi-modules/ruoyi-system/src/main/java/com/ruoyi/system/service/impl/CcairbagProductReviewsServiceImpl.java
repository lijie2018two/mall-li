package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.system.api.domain.ccairbag.CcairbagProductReviews;
import com.ruoyi.system.mapper.CcairbagProductReviewsMapper;
import com.ruoyi.system.service.ICcairbagProductReviewsService;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

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
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 查询商品评论
     * 
     * @param reviewId 商品评论主键
     * @return 商品评论
     */
    @Override
    public CcairbagProductReviews selectCcairbagProductReviewsByReviewId(Long reviewId)
    {
        return ccairbagProductReviewsMapper.selectCcairbagProductReviewsByReviewId(reviewId);
    }

    /**
     * 查询商品评论列表
     * 
     * @param ccairbagProductReviews 商品评论
     * @return 商品评论
     */
    @Override
    public List<CcairbagProductReviews> selectCcairbagProductReviewsList(CcairbagProductReviews ccairbagProductReviews)
    {
        return ccairbagProductReviewsMapper.selectCcairbagProductReviewsList(ccairbagProductReviews);
    }

    /**
     * 新增商品评论
     * 
     * @param ccairbagProductReviews 商品评论
     * @return 结果
     */
    @Override
    public int insertCcairbagProductReviews(CcairbagProductReviews ccairbagProductReviews)
    {
        ccairbagProductReviews.setCreateTime(DateUtils.getNowDate());
        return ccairbagProductReviewsMapper.insertCcairbagProductReviews(ccairbagProductReviews);
    }

    /**
     * 修改商品评论
     * 
     * @param ccairbagProductReviews 商品评论
     * @return 结果
     */
    @Override
    public int updateCcairbagProductReviews(CcairbagProductReviews ccairbagProductReviews)
    {
        ccairbagProductReviews.setUpdateTime(DateUtils.getNowDate());
        return ccairbagProductReviewsMapper.updateCcairbagProductReviews(ccairbagProductReviews);
    }

    /**
     * 批量删除商品评论
     * 
     * @param reviewIds 需要删除的商品评论主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagProductReviewsByReviewIds(Long[] reviewIds)
    {
        return ccairbagProductReviewsMapper.deleteCcairbagProductReviewsByReviewIds(reviewIds);
    }

    /**
     * 删除商品评论信息
     * 
     * @param reviewId 商品评论主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagProductReviewsByReviewId(Long reviewId)
    {
        return ccairbagProductReviewsMapper.deleteCcairbagProductReviewsByReviewId(reviewId);
    }

    /**
     * 批量新增商品评论
     *
     * @param ccairbagProductReviewss 商品评论List
     * @return 结果
     */
    @Override
    public int batchInsertCcairbagProductReviews(List<CcairbagProductReviews> ccairbagProductReviewss)
    {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        int count = 0;
        if (!CollectionUtils.isEmpty(ccairbagProductReviewss)) {
            try {
                for (int i = 0; i < ccairbagProductReviewss.size(); i++) {
                    int row = ccairbagProductReviewsMapper.insertCcairbagProductReviews(ccairbagProductReviewss.get(i));
                    // 防止内存溢出，没100次提交一次,并清除缓存
                    boolean bool = (i >0 && i%3 == 0) || i == ccairbagProductReviewss.size() - 1;
                    if (bool){
                        sqlSession.commit();
                        sqlSession.clearCache();
                    }
                    count = i + 1;
                }
            }catch (Exception e){
                e.printStackTrace();
                // 没有提交的数据可以回滚
                sqlSession.rollback();
            }finally {
                sqlSession.close();
                return count;
            }
        }
        return count;
    }
}
