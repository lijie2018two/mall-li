package com.ccairbag.api.controller;

import com.ccairbag.api.service.ICcairbagProductFavoriteService;
import com.ruoyi.common.core.utils.oConvertUtils;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.system.api.domain.ccairbag.CcairbagProductFavorite;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商品收藏Controller
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@RestController
@RequestMapping("ccairbag/favorite")
public class CcairbagProductFavoriteController extends BaseController
{
    @Autowired
    private ICcairbagProductFavoriteService ccairbagProductFavoriteService;


    @ApiOperation("买家收藏列表")
    @GetMapping("/buyerList")
    public AppResult buyerList()
    {
        return ccairbagProductFavoriteService.buyerList();
    }

    @ApiOperation("用户添加收藏")
    @PostMapping("/addFavorite")
    public AppResult addFavorite(@RequestBody CcairbagProductFavorite favorite)
    {
        if (oConvertUtils.isEmpty(favorite.getProdId())) {
            return AppResult.error("商品id不能为空");
        }
        return ccairbagProductFavoriteService.addFavorite(favorite);
    }

    @ApiOperation("用户根据商品id取消收藏")
    @PostMapping("/cancelFavorite")
    public AppResult cancelFavorite(@RequestBody CcairbagProductFavorite favorite)
    {
        if (oConvertUtils.isEmpty(favorite.getProdId())) {
            return AppResult.error("商品id不能为空");
        }
        return ccairbagProductFavoriteService.cancelFavorite(favorite);
    }

    @ApiOperation("用户根据收藏id取消收藏")
    @PostMapping("/cancelFavoriteId")
    public AppResult cancelFavoriteId(@RequestBody CcairbagProductFavorite favorite)
    {
        if (oConvertUtils.isEmpty(favorite.getFavoriteId())) {
            return AppResult.error("收藏id不能为空");
        }
        return ccairbagProductFavoriteService.cancelFavoriteId(favorite.getFavoriteId());
    }

}
