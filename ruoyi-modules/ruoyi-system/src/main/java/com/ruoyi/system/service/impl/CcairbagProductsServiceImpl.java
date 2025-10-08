package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.system.api.domain.ccairbag.CcairbagProducts;
import com.ruoyi.system.mapper.CcairbagProductsMapper;
import com.ruoyi.system.service.ICcairbagProductsService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 商品Service业务层处理
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Service
public class CcairbagProductsServiceImpl implements ICcairbagProductsService 
{
    @Autowired
    private CcairbagProductsMapper ccairbagProductsMapper;
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    /**
     * 查询商品
     * 
     * @param productId 商品主键
     * @return 商品
     */
    @Override
    public CcairbagProducts selectCcairbagProductsByProductId(Long productId)
    {
        return ccairbagProductsMapper.selectCcairbagProductsByProductId(productId);
    }

    /**
     * 查询商品列表
     * 
     * @param ccairbagProducts 商品
     * @return 商品
     */
    @Override
    public List<CcairbagProducts> selectCcairbagProductsList(CcairbagProducts ccairbagProducts)
    {
        return ccairbagProductsMapper.selectCcairbagProductsList(ccairbagProducts);
    }

    /**
     * 新增商品
     * 
     * @param ccairbagProducts 商品
     * @return 结果
     */
    @Override
    public int insertCcairbagProducts(CcairbagProducts ccairbagProducts)
    {
        ccairbagProducts.setCreateTime(DateUtils.getNowDate());
        return ccairbagProductsMapper.insertCcairbagProducts(ccairbagProducts);
    }

    /**
     * 修改商品
     * 
     * @param ccairbagProducts 商品
     * @return 结果
     */
    @Override
    public int updateCcairbagProducts(CcairbagProducts ccairbagProducts)
    {
        ccairbagProducts.setUpdateTime(DateUtils.getNowDate());
        return ccairbagProductsMapper.updateCcairbagProducts(ccairbagProducts);
    }

    /**
     * 批量删除商品
     * 
     * @param productIds 需要删除的商品主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagProductsByProductIds(Long[] productIds)
    {
        return ccairbagProductsMapper.deleteCcairbagProductsByProductIds(productIds);
    }

    /**
     * 删除商品信息
     * 
     * @param productId 商品主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagProductsByProductId(Long productId)
    {
        return ccairbagProductsMapper.deleteCcairbagProductsByProductId(productId);
    }

    /**
     * 批量新增商品
     *
     * @param ccairbagProductss 商品List
     * @return 结果
     */
    @Override
    public int batchInsertCcairbagProducts(List<CcairbagProducts> ccairbagProductss)
    {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        int count = 0;
        if (!CollectionUtils.isEmpty(ccairbagProductss)) {
            try {
                for (int i = 0; i < ccairbagProductss.size(); i++) {
                    int row = ccairbagProductsMapper.insertCcairbagProducts(ccairbagProductss.get(i));
                    // 防止内存溢出，没100次提交一次,并清除缓存
                    boolean bool = (i >0 && i%3 == 0) || i == ccairbagProductss.size() - 1;
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
