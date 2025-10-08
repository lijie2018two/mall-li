package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.system.api.domain.ccairbag.CcairbagNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.CcairbagNotificationMapper;
import com.ruoyi.system.service.ICcairbagNotificationService;

/**
 * 通知Service业务层处理
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Service
public class CcairbagNotificationServiceImpl implements ICcairbagNotificationService 
{
    @Autowired
    private CcairbagNotificationMapper ccairbagNotificationMapper;

    /**
     * 查询通知
     * 
     * @param notificationId 通知主键
     * @return 通知
     */
    @Override
    public CcairbagNotification selectCcairbagNotificationByNotificationId(Long notificationId)
    {
        return ccairbagNotificationMapper.selectCcairbagNotificationByNotificationId(notificationId);
    }

    /**
     * 查询通知列表
     * 
     * @param ccairbagNotification 通知
     * @return 通知
     */
    @Override
    public List<CcairbagNotification> selectCcairbagNotificationList(CcairbagNotification ccairbagNotification)
    {
        return ccairbagNotificationMapper.selectCcairbagNotificationList(ccairbagNotification);
    }

    /**
     * 新增通知
     * 
     * @param ccairbagNotification 通知
     * @return 结果
     */
    @Override
    public int insertCcairbagNotification(CcairbagNotification ccairbagNotification)
    {
        ccairbagNotification.setCreateTime(DateUtils.getNowDate());
        return ccairbagNotificationMapper.insertCcairbagNotification(ccairbagNotification);
    }

    /**
     * 修改通知
     * 
     * @param ccairbagNotification 通知
     * @return 结果
     */
    @Override
    public int updateCcairbagNotification(CcairbagNotification ccairbagNotification)
    {
        ccairbagNotification.setUpdateTime(DateUtils.getNowDate());
        return ccairbagNotificationMapper.updateCcairbagNotification(ccairbagNotification);
    }

    /**
     * 批量删除通知
     * 
     * @param notificationIds 需要删除的通知主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagNotificationByNotificationIds(Long[] notificationIds)
    {
        return ccairbagNotificationMapper.deleteCcairbagNotificationByNotificationIds(notificationIds);
    }

    /**
     * 删除通知信息
     * 
     * @param notificationId 通知主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagNotificationByNotificationId(Long notificationId)
    {
        return ccairbagNotificationMapper.deleteCcairbagNotificationByNotificationId(notificationId);
    }
}
