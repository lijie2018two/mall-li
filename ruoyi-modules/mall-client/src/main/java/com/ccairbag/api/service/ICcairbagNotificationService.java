package com.ccairbag.api.service;

import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.system.api.domain.ccairbag.CcairbagNotification;

import java.util.List;

/**
 * 通知Service接口
 * 
 * @author lidabai
 * @date 2025-02-20
 */
public interface ICcairbagNotificationService 
{
    /**
     * 查询通知
     * 
     * @param notificationId 通知主键
     * @return 通知
     */
    public CcairbagNotification selectCcairbagNotificationByNotificationId(Long notificationId);

    /**
     * 查询通知列表
     * 
     * @param ccairbagNotification 通知
     * @return 通知集合
     */
    public List<CcairbagNotification> selectCcairbagNotificationList(CcairbagNotification ccairbagNotification);

    /**
     * 新增通知
     * 
     * @param ccairbagNotification 通知
     * @return 结果
     */
    public int insertCcairbagNotification(CcairbagNotification ccairbagNotification);

    /**
     * 修改通知
     * 
     * @param ccairbagNotification 通知
     * @return 结果
     */
    public int updateCcairbagNotification(CcairbagNotification ccairbagNotification);

    /**
     * 批量删除通知
     * 
     * @param notificationIds 需要删除的通知主键集合
     * @return 结果
     */
    public int deleteCcairbagNotificationByNotificationIds(Long[] notificationIds);

    /**
     * 删除通知信息
     * 
     * @param notificationId 通知主键
     * @return 结果
     */
    public int deleteCcairbagNotificationByNotificationId(Long notificationId);

    AppResult markNotificationsAsRead(Long notificationIds);

    AppResult getNotificationItem(Long notificationId);


}
