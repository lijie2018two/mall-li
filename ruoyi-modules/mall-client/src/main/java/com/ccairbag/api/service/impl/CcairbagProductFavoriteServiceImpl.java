package com.ccairbag.api.service.impl;

import com.ccairbag.api.mapper.CcairbagProductFavoriteMapper;
import com.ccairbag.api.mapper.CcairbagProductsMapper;
import com.ccairbag.api.service.ICcairbagProductFavoriteService;
import com.ruoyi.common.core.utils.PageUtils;
import com.ruoyi.common.core.utils.oConvertUtils;
import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.common.core.web.page.PageDataInfo;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.domain.ccairbag.CcairbagProductFavorite;
import com.ruoyi.system.api.domain.ccairbag.CcairbagProducts;
import com.ruoyi.system.api.domain.ccairbag.dto.CcairbagProductFavoriteDTO;
import com.ruoyi.system.api.model.LoginAppUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 商品收藏Service业务层处理
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Service
public class CcairbagProductFavoriteServiceImpl implements ICcairbagProductFavoriteService
{
    @Resource
    private CcairbagProductFavoriteMapper ccairbagProductFavoriteMapper;
    @Resource
    private CcairbagProductsMapper ccairbagProductsMapper;

    @Override
    public AppResult buyerList() {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        Long userId = loginAppUser.getCcairbagUsers().getUserId();
        PageUtils.startPage();
        List<CcairbagProductFavoriteDTO> list = ccairbagProductFavoriteMapper.buyerList(userId);
        PageDataInfo<CcairbagProductFavoriteDTO> pageDataInfo = new PageDataInfo<>(list);
        PageUtils.clearPage();
        return new AppResult(pageDataInfo);
    }

    @Override
    public AppResult addFavorite(CcairbagProductFavorite favorite) {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        Long userId = loginAppUser.getCcairbagUsers().getUserId();
        CcairbagProducts product = ccairbagProductsMapper.selectCcairbagProductsByProductIdExt(favorite.getProdId());
        if (oConvertUtils.isEmpty(product)) {
            return AppResult.error("商品不存在");
        }
        favorite.setUserId(userId);
        favorite.setStatus(1);
        List<CcairbagProductFavorite> favorites = ccairbagProductFavoriteMapper.selectCcairbagProductFavoriteList(favorite);
        if (!favorites.isEmpty()){
            return AppResult.error("商品已收藏,无法再次收藏");
        }
        CcairbagProductFavorite favoritex = new CcairbagProductFavorite();
        favoritex.setUserId(userId);
        favoritex.setProdId(favorite.getProdId());
        List<CcairbagProductFavorite> favoritesx = ccairbagProductFavoriteMapper.selectCcairbagProductFavoriteList(favoritex);
        if (!favoritesx.isEmpty()){
            favoritesx.get(0).setStatus(1);
            ccairbagProductFavoriteMapper.updateCcairbagProductFavorite(favoritesx.get(0));
        }else {
            favorite.setDeleted(0);
            favorite.setCreateTime(new Date());
            ccairbagProductFavoriteMapper.insertCcairbagProductFavorite(favorite);
        }
        return AppResult.success("收藏成功");
    }

    @Override
    public AppResult cancelFavorite(CcairbagProductFavorite favorite) {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        Long userId = loginAppUser.getCcairbagUsers().getUserId();
        CcairbagProducts product = ccairbagProductsMapper.selectCcairbagProductsByProductIdExt(favorite.getProdId());
        if (oConvertUtils.isEmpty(product)) {
            return AppResult.error("商品不存在");
        }
        favorite.setUserId(userId);
        favorite.setStatus(1);
        List<CcairbagProductFavorite> favorites = ccairbagProductFavoriteMapper.selectCcairbagProductFavoriteList(favorite);
        if (favorites.isEmpty()){
            return AppResult.error("商品未收藏,无法取消");
        }else {
            if (!favorites.get(0).getUserId().equals(userId)){
                return AppResult.error("用户没收藏");
            }
            //可以取消
            favorites.get(0).setStatus(0);
            ccairbagProductFavoriteMapper.updateCcairbagProductFavorite(favorites.get(0));
            return AppResult.success("取消收藏成功");
        }
    }

    @Override
    public AppResult cancelFavoriteId(Long id) {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        Long userId = loginAppUser.getCcairbagUsers().getUserId();
        CcairbagProductFavorite favorite =ccairbagProductFavoriteMapper.selectCcairbagProductFavoriteByFavoriteId(id);
        if (oConvertUtils.isEmpty(favorite)) {
            return AppResult.error("收藏不存在");
        }
        if (!favorite.getUserId().equals(userId)){
            return AppResult.error("用户没收藏");
        }
        favorite.setStatus(0);
        ccairbagProductFavoriteMapper.updateCcairbagProductFavorite(favorite);
        return AppResult.success("取消收藏成功");
    }
}
