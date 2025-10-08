package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.system.api.domain.ccairbag.CcairbagPromotionActivities;
import com.ruoyi.system.mapper.CcairbagPromotionActivitiesMapper;
import com.ruoyi.system.service.ICcairbagPromotionActivitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 活动Service业务层处理
 * 
 * @author lidabai
 * @date 2025-07-15
 */
@Service
public class CcairbagPromotionActivitiesServiceImpl implements ICcairbagPromotionActivitiesService 
{
    @Autowired
    private CcairbagPromotionActivitiesMapper ccairbagPromotionActivitiesMapper;

    /**
     * 查询活动
     * 
     * @param activityId 活动主键
     * @return 活动
     */
    @Override
    public CcairbagPromotionActivities selectCcairbagPromotionActivitiesByActivityId(Long activityId)
    {
        return ccairbagPromotionActivitiesMapper.selectCcairbagPromotionActivitiesByActivityId(activityId);
    }

    /**
     * 查询活动列表
     * 
     * @param ccairbagPromotionActivities 活动
     * @return 活动
     */
    @Override
    public List<CcairbagPromotionActivities> selectCcairbagPromotionActivitiesList(CcairbagPromotionActivities ccairbagPromotionActivities)
    {
        return ccairbagPromotionActivitiesMapper.selectCcairbagPromotionActivitiesList(ccairbagPromotionActivities);
    }

    /**
     * 新增活动
     * 
     * @param ccairbagPromotionActivities 活动
     * @return 结果
     */
    @Override
    public int insertCcairbagPromotionActivities(CcairbagPromotionActivities ccairbagPromotionActivities)
    {
        ccairbagPromotionActivities.setCreateTime(DateUtils.getNowDate());
        return ccairbagPromotionActivitiesMapper.insertCcairbagPromotionActivities(ccairbagPromotionActivities);
    }

    /**
     * 修改活动
     * 
     * @param ccairbagPromotionActivities 活动
     * @return 结果
     */
    @Override
    public int updateCcairbagPromotionActivities(CcairbagPromotionActivities ccairbagPromotionActivities)
    {
        ccairbagPromotionActivities.setUpdateTime(DateUtils.getNowDate());
        return ccairbagPromotionActivitiesMapper.updateCcairbagPromotionActivities(ccairbagPromotionActivities);
    }

    /**
     * 批量删除活动
     * 
     * @param activityIds 需要删除的活动主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagPromotionActivitiesByActivityIds(Long[] activityIds)
    {
        return ccairbagPromotionActivitiesMapper.deleteCcairbagPromotionActivitiesByActivityIds(activityIds);
    }

    /**
     * 删除活动信息
     * 
     * @param activityId 活动主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagPromotionActivitiesByActivityId(Long activityId)
    {
        return ccairbagPromotionActivitiesMapper.deleteCcairbagPromotionActivitiesByActivityId(activityId);
    }
}
