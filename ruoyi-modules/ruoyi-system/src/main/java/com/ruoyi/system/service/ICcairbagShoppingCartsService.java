package com.ruoyi.system.service;

import com.ruoyi.system.api.domain.ccairbag.CcairbagShoppingCarts;

import java.util.List;

/**
 * 购物车Service接口
 * 
 * @author lidabai
 * @date 2025-02-20
 */
public interface ICcairbagShoppingCartsService 
{
    /**
     * 查询购物车
     * 
     * @param cartId 购物车主键
     * @return 购物车
     */
    public CcairbagShoppingCarts selectCcairbagShoppingCartsByCartId(Long cartId);

    /**
     * 查询购物车列表
     * 
     * @param ccairbagShoppingCarts 购物车
     * @return 购物车集合
     */
    public List<CcairbagShoppingCarts> selectCcairbagShoppingCartsList(CcairbagShoppingCarts ccairbagShoppingCarts);

    /**
     * 新增购物车
     * 
     * @param ccairbagShoppingCarts 购物车
     * @return 结果
     */
    public int insertCcairbagShoppingCarts(CcairbagShoppingCarts ccairbagShoppingCarts);

    /**
     * 修改购物车
     * 
     * @param ccairbagShoppingCarts 购物车
     * @return 结果
     */
    public int updateCcairbagShoppingCarts(CcairbagShoppingCarts ccairbagShoppingCarts);

    /**
     * 批量删除购物车
     * 
     * @param cartIds 需要删除的购物车主键集合
     * @return 结果
     */
    public int deleteCcairbagShoppingCartsByCartIds(Long[] cartIds);

    /**
     * 删除购物车信息
     * 
     * @param cartId 购物车主键
     * @return 结果
     */
    public int deleteCcairbagShoppingCartsByCartId(Long cartId);
}
