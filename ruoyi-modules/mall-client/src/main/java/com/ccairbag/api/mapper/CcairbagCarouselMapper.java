package com.ccairbag.api.mapper;


import com.ruoyi.system.api.domain.ccairbag.CcairbagCarousel;

import java.util.List;

/**
 * 轮播图Mapper接口
 * 
 * @author lidabai
 * @date 2025-04-30
 */
public interface CcairbagCarouselMapper 
{
    /**
     * 查询轮播图
     * 
     * @param carouselId 轮播图主键
     * @return 轮播图
     */
    public CcairbagCarousel selectCcairbagCarouselByCarouselId(Long carouselId);

    /**
     * 查询轮播图列表
     * 
     * @param ccairbagCarousel 轮播图
     * @return 轮播图集合
     */
    public List<CcairbagCarousel> selectCcairbagCarouselList(CcairbagCarousel ccairbagCarousel);

    /**
     * 新增轮播图
     * 
     * @param ccairbagCarousel 轮播图
     * @return 结果
     */
    public int insertCcairbagCarousel(CcairbagCarousel ccairbagCarousel);

    /**
     * 修改轮播图
     * 
     * @param ccairbagCarousel 轮播图
     * @return 结果
     */
    public int updateCcairbagCarousel(CcairbagCarousel ccairbagCarousel);

    /**
     * 删除轮播图
     * 
     * @param carouselId 轮播图主键
     * @return 结果
     */
    public int deleteCcairbagCarouselByCarouselId(Long carouselId);

    /**
     * 批量删除轮播图
     * 
     * @param carouselIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCcairbagCarouselByCarouselIds(Long[] carouselIds);
}
