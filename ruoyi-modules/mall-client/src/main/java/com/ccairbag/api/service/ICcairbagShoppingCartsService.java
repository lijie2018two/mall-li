package com.ccairbag.api.service;

import com.ccairbag.api.domain.dto.ShopCartDto;
import com.ruoyi.common.core.web.domain.AppResult;
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
    AppResult addShopCars(CcairbagShoppingCarts ccairbagShoppingCarts);

    AppResult delShopCars(Long cartId,Integer quantity);

    AppResult delShopCarsExt(Long cartId);




    public AppResult<List<ShopCartDto>> selectShopCars();

}
