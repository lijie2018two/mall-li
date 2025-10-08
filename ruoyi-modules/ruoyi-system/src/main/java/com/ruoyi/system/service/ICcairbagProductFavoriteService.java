package com.ruoyi.system.service;

import com.ruoyi.system.api.domain.ccairbag.CcairbagProductFavorite;

import java.util.List;

/**
 * 商品收藏Service接口
 * 
 * @author lidabai
 * @date 2025-02-20
 */
public interface ICcairbagProductFavoriteService 
{
    /**
     * 查询商品收藏
     * 
     * @param favoriteId 商品收藏主键
     * @return 商品收藏
     */
    public CcairbagProductFavorite selectCcairbagProductFavoriteByFavoriteId(Long favoriteId);

    /**
     * 查询商品收藏列表
     * 
     * @param ccairbagProductFavorite 商品收藏
     * @return 商品收藏集合
     */
    public List<CcairbagProductFavorite> selectCcairbagProductFavoriteList(CcairbagProductFavorite ccairbagProductFavorite);

    /**
     * 新增商品收藏
     * 
     * @param ccairbagProductFavorite 商品收藏
     * @return 结果
     */
    public int insertCcairbagProductFavorite(CcairbagProductFavorite ccairbagProductFavorite);

    /**
     * 修改商品收藏
     * 
     * @param ccairbagProductFavorite 商品收藏
     * @return 结果
     */
    public int updateCcairbagProductFavorite(CcairbagProductFavorite ccairbagProductFavorite);

    /**
     * 批量删除商品收藏
     * 
     * @param favoriteIds 需要删除的商品收藏主键集合
     * @return 结果
     */
    public int deleteCcairbagProductFavoriteByFavoriteIds(Long[] favoriteIds);

    /**
     * 删除商品收藏信息
     * 
     * @param favoriteId 商品收藏主键
     * @return 结果
     */
    public int deleteCcairbagProductFavoriteByFavoriteId(Long favoriteId);
}
