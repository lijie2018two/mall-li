package com.ccairbag.api.controller;

import com.ccairbag.api.domain.dto.ShopCartDto;
import com.ccairbag.api.service.ICcairbagShoppingCartsService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.system.api.domain.ccairbag.CcairbagShoppingCarts;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 购物车Controller
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@RestController
@RequestMapping("ccairbag/carts")
public class CcairbagShoppingCartsController extends BaseController
{
    @Autowired
    private ICcairbagShoppingCartsService ccairbagShoppingCartsService;

    @ApiOperation(value = "添加购物车")
    @PostMapping("/addShopCars")
    public AppResult addShopCars(@RequestBody CcairbagShoppingCarts ccairbagShoppingCarts)
    {
        return ccairbagShoppingCartsService.addShopCars(ccairbagShoppingCarts);
    }

    @ApiOperation(value = "根据购物车id 移除购物车")
    @GetMapping("/delShopCars")
    public AppResult delShopCars(@ApiParam(value = "购物车id", required = true) Long cartId,
                                @ApiParam(value = "数量", required = true) Integer quantity )
    {
        return ccairbagShoppingCartsService.delShopCars(cartId,quantity);
    }

    @ApiOperation(value = "根据购物车id删除购物车某个记录 ")
    @GetMapping("/delShopCarsExt")
    public AppResult delShopCarsExt(@ApiParam(value = "购物车id", required = true) Long cartId)
    {
        return ccairbagShoppingCartsService.delShopCarsExt(cartId);
    }

    @ApiOperation(value = "用户查看购物车")
    @GetMapping("/selectShopCars")
    public AppResult<List<ShopCartDto>> selectShopCars()
    {
        return ccairbagShoppingCartsService.selectShopCars();
    }
}
