package com.ccairbag.api.mapper;

import com.ruoyi.system.api.domain.ccairbag.CcairbagProducts;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品Mapper接口
 * 
 * @author lidabai
 * @date 2025-02-20
 */
public interface CcairbagProductsMapper 
{
    /**
     * 查询商品
     * 
     * @param productId 商品主键
     * @return 商品
     */
    public CcairbagProducts selectCcairbagProductsByProductId(Long productId);

    public CcairbagProducts selectCcairbagProductsByProductIdExt(Long productId);


    /**
     * 查询商品列表
     * 
     * @param ccairbagProducts 商品
     * @return 商品集合
     */
    public List<CcairbagProducts> selectCcairbagProductsList(CcairbagProducts ccairbagProducts);

    int ishaveProductByAddrId(Long addrId);
    /**
     * 新增商品
     * 
     * @param ccairbagProducts 商品
     * @return 结果
     */
    public int insertCcairbagProducts(CcairbagProducts ccairbagProducts);


    /**
     * 修改商品
     * 
     * @param ccairbagProducts 商品
     * @return 结果
     */
    public int updateCcairbagProducts(CcairbagProducts ccairbagProducts);

    int updateCcairbagProductsNum(CcairbagProducts ccairbagProducts);

    List<CcairbagProducts> selectCcairbagProductsListByCategoryIds(CcairbagProducts ccairbagProducts);

    List<CcairbagProducts> searchProducts(CcairbagProducts ccairbagProducts);

    List<CcairbagProducts> searchProductsExt(CcairbagProducts ccairbagProducts);


    List<CcairbagProducts> getOffShelveProducts(CcairbagProducts query);

    public int deleteCcairbagProductsByProductId(Long productId);

    int updateStock(@Param("productId") Long productId, @Param("num") Integer num);

    int updateStockExt(@Param("productId") Long productId, @Param("num") Integer num);

    // 商店 好评率
    public BigDecimal shopGoodRate(Long shopId);

    //商店 销量
    public Integer shopSales(Long shopId);

    List<CcairbagProducts> findRandomByCategoryId(@Param("categoryId") Long categoryId,@Param("count") int count);


}
