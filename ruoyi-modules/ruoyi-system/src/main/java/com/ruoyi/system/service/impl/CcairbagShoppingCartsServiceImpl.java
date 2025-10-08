package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.system.api.domain.ccairbag.CcairbagShoppingCarts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.CcairbagShoppingCartsMapper;
import com.ruoyi.system.service.ICcairbagShoppingCartsService;

/**
 * 购物车Service业务层处理
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Service
public class CcairbagShoppingCartsServiceImpl implements ICcairbagShoppingCartsService 
{
    @Autowired
    private CcairbagShoppingCartsMapper ccairbagShoppingCartsMapper;

    /**
     * 查询购物车
     * 
     * @param cartId 购物车主键
     * @return 购物车
     */
    @Override
    public CcairbagShoppingCarts selectCcairbagShoppingCartsByCartId(Long cartId)
    {
        return ccairbagShoppingCartsMapper.selectCcairbagShoppingCartsByCartId(cartId);
    }

    /**
     * 查询购物车列表
     * 
     * @param ccairbagShoppingCarts 购物车
     * @return 购物车
     */
    @Override
    public List<CcairbagShoppingCarts> selectCcairbagShoppingCartsList(CcairbagShoppingCarts ccairbagShoppingCarts)
    {
        return ccairbagShoppingCartsMapper.selectCcairbagShoppingCartsList(ccairbagShoppingCarts);
    }

    /**
     * 新增购物车
     * 
     * @param ccairbagShoppingCarts 购物车
     * @return 结果
     */
    @Override
    public int insertCcairbagShoppingCarts(CcairbagShoppingCarts ccairbagShoppingCarts)
    {
        ccairbagShoppingCarts.setCreateTime(DateUtils.getNowDate());
        return ccairbagShoppingCartsMapper.insertCcairbagShoppingCarts(ccairbagShoppingCarts);
    }

    /**
     * 修改购物车
     * 
     * @param ccairbagShoppingCarts 购物车
     * @return 结果
     */
    @Override
    public int updateCcairbagShoppingCarts(CcairbagShoppingCarts ccairbagShoppingCarts)
    {
        ccairbagShoppingCarts.setUpdateTime(DateUtils.getNowDate());
        return ccairbagShoppingCartsMapper.updateCcairbagShoppingCarts(ccairbagShoppingCarts);
    }

    /**
     * 批量删除购物车
     * 
     * @param cartIds 需要删除的购物车主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagShoppingCartsByCartIds(Long[] cartIds)
    {
        return ccairbagShoppingCartsMapper.deleteCcairbagShoppingCartsByCartIds(cartIds);
    }

    /**
     * 删除购物车信息
     * 
     * @param cartId 购物车主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagShoppingCartsByCartId(Long cartId)
    {
        return ccairbagShoppingCartsMapper.deleteCcairbagShoppingCartsByCartId(cartId);
    }
}
