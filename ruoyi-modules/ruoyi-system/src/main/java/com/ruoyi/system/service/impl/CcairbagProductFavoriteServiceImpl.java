package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.system.api.domain.ccairbag.CcairbagProductFavorite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.CcairbagProductFavoriteMapper;
import com.ruoyi.system.service.ICcairbagProductFavoriteService;

/**
 * 商品收藏Service业务层处理
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Service
public class CcairbagProductFavoriteServiceImpl implements ICcairbagProductFavoriteService 
{
    @Autowired
    private CcairbagProductFavoriteMapper ccairbagProductFavoriteMapper;

    /**
     * 查询商品收藏
     * 
     * @param favoriteId 商品收藏主键
     * @return 商品收藏
     */
    @Override
    public CcairbagProductFavorite selectCcairbagProductFavoriteByFavoriteId(Long favoriteId)
    {
        return ccairbagProductFavoriteMapper.selectCcairbagProductFavoriteByFavoriteId(favoriteId);
    }

    /**
     * 查询商品收藏列表
     * 
     * @param ccairbagProductFavorite 商品收藏
     * @return 商品收藏
     */
    @Override
    public List<CcairbagProductFavorite> selectCcairbagProductFavoriteList(CcairbagProductFavorite ccairbagProductFavorite)
    {
        return ccairbagProductFavoriteMapper.selectCcairbagProductFavoriteList(ccairbagProductFavorite);
    }

    /**
     * 新增商品收藏
     * 
     * @param ccairbagProductFavorite 商品收藏
     * @return 结果
     */
    @Override
    public int insertCcairbagProductFavorite(CcairbagProductFavorite ccairbagProductFavorite)
    {
        ccairbagProductFavorite.setCreateTime(DateUtils.getNowDate());
        return ccairbagProductFavoriteMapper.insertCcairbagProductFavorite(ccairbagProductFavorite);
    }

    /**
     * 修改商品收藏
     * 
     * @param ccairbagProductFavorite 商品收藏
     * @return 结果
     */
    @Override
    public int updateCcairbagProductFavorite(CcairbagProductFavorite ccairbagProductFavorite)
    {
        ccairbagProductFavorite.setUpdateTime(DateUtils.getNowDate());
        return ccairbagProductFavoriteMapper.updateCcairbagProductFavorite(ccairbagProductFavorite);
    }

    /**
     * 批量删除商品收藏
     * 
     * @param favoriteIds 需要删除的商品收藏主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagProductFavoriteByFavoriteIds(Long[] favoriteIds)
    {
        return ccairbagProductFavoriteMapper.deleteCcairbagProductFavoriteByFavoriteIds(favoriteIds);
    }

    /**
     * 删除商品收藏信息
     * 
     * @param favoriteId 商品收藏主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagProductFavoriteByFavoriteId(Long favoriteId)
    {
        return ccairbagProductFavoriteMapper.deleteCcairbagProductFavoriteByFavoriteId(favoriteId);
    }
}
