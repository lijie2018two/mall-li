package com.ruoyi.system.service;

import com.ruoyi.system.api.domain.ccairbag.CcairbagPromotionActivities;

import java.util.List;

/**
 * 活动Service接口
 * 
 * @author lidabai
 * @date 2025-07-15
 */
public interface ICcairbagPromotionActivitiesService 
{
    /**
     * 查询活动
     * 
     * @param activityId 活动主键
     * @return 活动
     */
    public CcairbagPromotionActivities selectCcairbagPromotionActivitiesByActivityId(Long activityId);

    /**
     * 查询活动列表
     * 
     * @param ccairbagPromotionActivities 活动
     * @return 活动集合
     */
    public List<CcairbagPromotionActivities> selectCcairbagPromotionActivitiesList(CcairbagPromotionActivities ccairbagPromotionActivities);

    /**
     * 新增活动
     * 
     * @param ccairbagPromotionActivities 活动
     * @return 结果
     */
    public int insertCcairbagPromotionActivities(CcairbagPromotionActivities ccairbagPromotionActivities);

    /**
     * 修改活动
     * 
     * @param ccairbagPromotionActivities 活动
     * @return 结果
     */
    public int updateCcairbagPromotionActivities(CcairbagPromotionActivities ccairbagPromotionActivities);

    /**
     * 批量删除活动
     * 
     * @param activityIds 需要删除的活动主键集合
     * @return 结果
     */
    public int deleteCcairbagPromotionActivitiesByActivityIds(Long[] activityIds);

    /**
     * 删除活动信息
     * 
     * @param activityId 活动主键
     * @return 结果
     */
    public int deleteCcairbagPromotionActivitiesByActivityId(Long activityId);
}
