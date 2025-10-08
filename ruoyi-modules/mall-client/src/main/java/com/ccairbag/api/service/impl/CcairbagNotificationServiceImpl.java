package com.ccairbag.api.service.impl;

import com.alibaba.nacos.shaded.com.google.gson.Gson;
import com.ccairbag.api.controller.WebSocketMessageServer;
import com.ccairbag.api.mapper.CcairbagConversationsMapper;
import com.ccairbag.api.mapper.CcairbagNotificationMapper;
import com.ccairbag.api.service.ICcairbagNotificationService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.domain.ccairbag.CcairbagNotification;
import com.ruoyi.system.api.model.LoginAppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 通知Service业务层处理
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Service
@Slf4j
public class CcairbagNotificationServiceImpl implements ICcairbagNotificationService
{
    @Resource
    private CcairbagNotificationMapper ccairbagNotificationMapper;
    @Resource
    private CcairbagConversationsMapper ccairbagConversationsMapper;
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

    public AppResult markNotificationsAsRead(Long notificationId ) {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        Long userId = loginAppUser.getCcairbagUsers().getUserId();
        int j = ccairbagNotificationMapper.updateReadStatusByNotificationIds(userId, notificationId);

        ConcurrentHashMap<String, WebSocketMessageServer> map = WebSocketMessageServer.getWebSocketMap();

        List<Map<String, Object>> maps =ccairbagConversationsMapper.getUserUnreadMessageCount(userId);
        int i = ccairbagNotificationMapper.getUserNotificationUnreadCount(userId);
        Map<String,Object> map1 = new HashMap<>();
        map1.put("user_id",userId);
        map1.put("user_type","notification");
        map1.put("unread_count",i);
        maps.add(map1);
        WebSocketMessageServer webSocket = map.get(String.valueOf(userId));
        Gson gson = new Gson();

        if (webSocket != null) {
            webSocket.sendMessage(gson.toJson(maps));
        } else {
            log.warn("卖家 WebSocket 连接不存在，用户ID: {}", userId);
        }



        if (j > 0){
            return AppResult.success();
        }else {
            return AppResult.error("标记失败");
        }

    }

    @Override
    public AppResult getNotificationItem(Long notificationId) {
        CcairbagNotification notification = ccairbagNotificationMapper.selectCcairbagNotificationByNotificationId(notificationId);
        return new AppResult(notification);

    }
}
