package com.ccairbag.api.service;

import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.system.api.domain.ccairbag.CcairbagProductFavorite;

/**
 * 商品收藏Service接口
 * 
 * @author lidabai
 * @date 2025-02-20
 */
public interface ICcairbagProductFavoriteService 
{
    AppResult buyerList();

    AppResult addFavorite(CcairbagProductFavorite favorite);

    AppResult cancelFavorite(CcairbagProductFavorite favorite);

    AppResult cancelFavoriteId(Long id);
}
