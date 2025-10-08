package com.ccairbag.api.service.impl;

import com.ccairbag.api.mapper.CcairbagCarouselMapper;
import com.ccairbag.api.service.ICcairbagCarouselService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.system.api.domain.ccairbag.CcairbagCarousel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 轮播图Service业务层处理
 * 
 * @author lidabai
 * @date 2025-04-30
 */
@Service
public class CcairbagCarouselServiceImpl implements ICcairbagCarouselService
{
    @Resource
    private CcairbagCarouselMapper ccairbagCarouselMapper;

    /**
     * 查询轮播图
     * 
     * @param carouselId 轮播图主键
     * @return 轮播图
     */
    @Override
    public CcairbagCarousel selectCcairbagCarouselByCarouselId(Long carouselId)
    {
        return ccairbagCarouselMapper.selectCcairbagCarouselByCarouselId(carouselId);
    }

    /**
     * 查询轮播图列表
     * 
     * @param ccairbagCarousel 轮播图
     * @return 轮播图
     */
    @Override
    public List<CcairbagCarousel> selectCcairbagCarouselList(CcairbagCarousel ccairbagCarousel)
    {
        return ccairbagCarouselMapper.selectCcairbagCarouselList(ccairbagCarousel);
    }

    /**
     * 新增轮播图
     * 
     * @param ccairbagCarousel 轮播图
     * @return 结果
     */
    @Override
    public int insertCcairbagCarousel(CcairbagCarousel ccairbagCarousel)
    {
        ccairbagCarousel.setCreateTime(DateUtils.getNowDate());
        return ccairbagCarouselMapper.insertCcairbagCarousel(ccairbagCarousel);
    }

    /**
     * 修改轮播图
     * 
     * @param ccairbagCarousel 轮播图
     * @return 结果
     */
    @Override
    public int updateCcairbagCarousel(CcairbagCarousel ccairbagCarousel)
    {
        ccairbagCarousel.setUpdateTime(DateUtils.getNowDate());
        return ccairbagCarouselMapper.updateCcairbagCarousel(ccairbagCarousel);
    }

    /**
     * 批量删除轮播图
     * 
     * @param carouselIds 需要删除的轮播图主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagCarouselByCarouselIds(Long[] carouselIds)
    {
        return ccairbagCarouselMapper.deleteCcairbagCarouselByCarouselIds(carouselIds);
    }

    /**
     * 删除轮播图信息
     * 
     * @param carouselId 轮播图主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagCarouselByCarouselId(Long carouselId)
    {
        return ccairbagCarouselMapper.deleteCcairbagCarouselByCarouselId(carouselId);
    }
}
