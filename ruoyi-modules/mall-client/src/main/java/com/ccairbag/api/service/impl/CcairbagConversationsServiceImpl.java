package com.ccairbag.api.service.impl;

import com.alibaba.nacos.shaded.com.google.gson.Gson;
import com.ccairbag.api.controller.WebSocketConversationsServer;
import com.ccairbag.api.controller.WebSocketConversationsShopServer;
import com.ccairbag.api.controller.WebSocketMessageServer;
import com.ccairbag.api.mapper.*;
import com.ccairbag.api.service.ICcairbagConversationsService;
import com.ruoyi.common.core.utils.PageUtils;
import com.ruoyi.common.core.utils.oConvertUtils;
import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.common.core.web.page.PageDataInfo;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.domain.ccairbag.*;
import com.ruoyi.system.api.domain.ccairbag.dto.CcairbagConversationsDTO;
import com.ruoyi.system.api.model.LoginAppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息会话Service业务层处理
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Service
@Slf4j

public class CcairbagConversationsServiceImpl implements ICcairbagConversationsService
{
    @Resource
    private CcairbagConversationsMapper ccairbagConversationsMapper;
    @Resource
    private CcairbagUsersMapper ccairbagUsersMapper;
    @Resource
    private CcairbagShopsMapper ccairbagShopsMapper;
    @Resource
    private CcairbagMessagesMapper ccairbagMessagesMapper;
    @Resource
    private CcairbagOrdersMapper ccairbagOrdersMapper;

    @Resource
    private CcairbagNotificationMapper ccairbagNotificationMapper;

    //客户消息会话列表
    @Override
    public AppResult list() {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        Long userId = loginAppUser.getCcairbagUsers().getUserId();
        CcairbagConversations ccairbagConversations = new CcairbagConversations();
        ccairbagConversations.setBuyerId(userId);
        //外层 的用户头像 和名字 用的shops 商店表的数据
        PageUtils.startPage();
        List<CcairbagConversations> list = ccairbagConversationsMapper.selectBuyerConversationsList(ccairbagConversations);
        extracted(list);
        PageDataInfo<CcairbagConversations> pageDataInfo = new PageDataInfo<>(list);
        PageUtils.clearPage();
        return new AppResult(pageDataInfo);
    }

    private void extracted(List<CcairbagConversations> list) {
        // 按日期分组
        for (CcairbagConversations conversation : list) {
            Map<String, List<CcairbagMessages>> groupedMessages = new HashMap<>();

            for (CcairbagMessages message : conversation.getMessages()) {
                // 从消息对象中获取分组日期（需在 CcairbagMessages 中新增字段）
                String dateKey = message.getGroupDate();

                groupedMessages
                        .computeIfAbsent(dateKey, k -> new ArrayList<>())
                        .add(message);
            }

            conversation.setMessagesByDate(groupedMessages);
        }
    }

    @Override
    public AppResult ShopList() {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        Long userId = loginAppUser.getCcairbagUsers().getUserId();
        CcairbagConversations ccairbagConversations = new CcairbagConversations();
        ccairbagConversations.setSellerId(userId);
        //外层 的用户头像 和名字 用的用户表 的数据
        PageUtils.startPage();
        List<CcairbagConversations> list = ccairbagConversationsMapper.selectSellerConversationsList(ccairbagConversations);
        extracted(list);
        PageDataInfo<CcairbagConversations> pageDataInfo = new PageDataInfo<>(list);
        PageUtils.clearPage();
        return new AppResult(pageDataInfo);
    }

    @Override
    @Transactional
    public AppResult sendMessagesByShop(CcairbagConversationsDTO ccairbagConversationsDTO) {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        Long userId = loginAppUser.getCcairbagUsers().getUserId();
        CcairbagShops shops = ccairbagShopsMapper.selectCcairbagShopsByShopId(ccairbagConversationsDTO.getShopId());
        if (oConvertUtils.isEmpty(shops)) {
            throw new RuntimeException("商家不存在");
        }

        CcairbagConversations conversation = ccairbagConversationsMapper.selectConversation(userId,shops.getUserId());
        if (oConvertUtils.isEmpty(conversation)) {
            CcairbagConversations ccairbagConversations = new CcairbagConversations();
            ccairbagConversations.setBuyerId(userId);
            ccairbagConversations.setSellerId(shops.getUserId());
            ccairbagConversations.setCreateTime(new Date());
            ccairbagConversations.setDeleted(0);
            ccairbagConversationsMapper.insertCcairbagConversations(ccairbagConversations);
        }else {
            conversation.setUpdateTime(new Date());
            ccairbagConversationsMapper.updateCcairbagConversations(conversation);
        }

        //发送站内通知给xxx
        return new AppResult("创建消息会话成功");
    }

    @Override
    public AppResult sendMessagesByOrderId(CcairbagConversationsDTO ccairbagConversationsDTO) {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        Long userId = loginAppUser.getCcairbagUsers().getUserId();
        CcairbagShops shops = ccairbagShopsMapper.getShopByUserId(userId);
        if (oConvertUtils.isEmpty(shops)) {
            throw new RuntimeException("商家不存在");
        }
        CcairbagOrders orders =ccairbagOrdersMapper.selectCcairbagOrdersByOrderId(ccairbagConversationsDTO.getOrderId());
        CcairbagConversations conversation = ccairbagConversationsMapper.selectConversation(orders.getUserId(),shops.getUserId());
        if (oConvertUtils.isEmpty(conversation)) {
            CcairbagConversations ccairbagConversations = new CcairbagConversations();
            ccairbagConversations.setBuyerId(userId);
            ccairbagConversations.setSellerId(shops.getUserId());
            ccairbagConversations.setCreateTime(new Date());
            ccairbagConversations.setDeleted(0);
            ccairbagConversationsMapper.insertCcairbagConversations(ccairbagConversations);
        }else {
            conversation.setUpdateTime(new Date());
            ccairbagConversationsMapper.updateCcairbagConversations(conversation);
        }
        //发送站内通知给xxx
        return new AppResult("创建消息会话成功");
    }

    @Override
    @Transactional
    public AppResult sendMessages(CcairbagConversationsDTO ccairbagConversationsDTO) {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        Long userId = loginAppUser.getCcairbagUsers().getUserId();
        CcairbagMessages ccairbagMessages = new CcairbagMessages();
        ccairbagMessages.setSenderType(ccairbagConversationsDTO.getSenderType());
        if (ccairbagConversationsDTO.getSenderType()==1){
            //卖家

            CcairbagShops shops = ccairbagShopsMapper.getShopByUserId(userId);
            if (oConvertUtils.isEmpty(shops)) {
                throw new RuntimeException("商家不存在");
            }
            ccairbagMessages.setHeadimgurl(shops.getShopLogo());
            ccairbagMessages.setUserName(shops.getShopName());
            ccairbagMessages.setSenderId(shops.getUserId());
            ccairbagMessages.setIsReadBuyer(0);
            ccairbagMessages.setIsReadSeller(1);
        }else {

            CcairbagUsers users = ccairbagUsersMapper.selectCcairbagUsersByUserId(userId);
            if (oConvertUtils.isEmpty(users)) {
                throw new RuntimeException("用户不存在");
            }
            ccairbagMessages.setHeadimgurl(users.getHeadimgurl());
            ccairbagMessages.setUserName(users.getUserName());
            ccairbagMessages.setSenderId(userId);
            ccairbagMessages.setIsReadSeller(0);
            ccairbagMessages.setIsReadBuyer(1);
        }

        ccairbagMessages.setConversationsId(ccairbagConversationsDTO.getConversationId());
        ccairbagMessages.setMessageContent(ccairbagConversationsDTO.getMessageContent());
        ccairbagMessages.setMessageType(ccairbagConversationsDTO.getMessageType());
        ccairbagMessages.setDeleted(0);

        ccairbagMessagesMapper.insertCcairbagMessages(ccairbagMessages);
        CcairbagConversations conversations = ccairbagConversationsMapper.selectCcairbagConversationsByConversationId(ccairbagConversationsDTO.getConversationId());
        ConcurrentHashMap<String, WebSocketMessageServer> map = WebSocketMessageServer.getWebSocketMap();

        ConcurrentHashMap<String, WebSocketConversationsServer> mapx = WebSocketConversationsServer.getWebSocketMap();

        ConcurrentHashMap<String, WebSocketConversationsShopServer> mapshop = WebSocketConversationsShopServer.getWebSocketMap();

        conversations.setUpdateTime(new Date());
        ccairbagConversationsMapper.updateCcairbagConversations(conversations);

        CcairbagConversations ccairbagConversations = new CcairbagConversations();
        ccairbagConversations.setSellerId(Long.valueOf(conversations.getSellerId()));
        //外层 的用户头像 和名字 用的用户表 的数据
        List<CcairbagConversations> selllist = ccairbagConversationsMapper.selectSellerConversationsList(ccairbagConversations);

        CcairbagConversations ccairbagConversationsx = new CcairbagConversations();
        ccairbagConversationsx.setBuyerId(conversations.getBuyerId());
        //外层 的用户头像 和名字 用的shops 商店表的数据
        List<CcairbagConversations> buylist = ccairbagConversationsMapper.selectBuyerConversationsList(ccairbagConversationsx);
        extracted(buylist);
        extracted(selllist);
        Gson gson = new Gson();

        //有新消息 [{未读消息条数，type}]
        //先查 买家未读消息 数量 ，后查卖家未读消息数量 ，还有通知未读消息数量
        // ccairbagNotificationMapper
//        List<Map<String, Object>> maps =ccairbagConversationsMapper.getUserUnreadMessageCount(userId);
//        int i = ccairbagNotificationMapper.getUserNotificationUnreadCount(userId);
//        Map<String,Object> map1 = new HashMap<>();
//        map1.put("user_id",userId);
//        map1.put("user_type","notification");
//        map1.put("unread_count",i);
//        maps.add(map1);
//        WebSocketMessageServer webSocket = map.get(String.valueOf(conversations.getBuyerId()));

//        webSocket.sendMessage(gson.toJson(maps));
        List<Map<String, Object>> maps = null;
        //发送站内通知给 买家
        if (ccairbagConversationsDTO.getSenderType()==1) {

            maps = ccairbagConversationsMapper.getUserUnreadMessageCount(userId);
            int i = ccairbagNotificationMapper.getUserNotificationUnreadCount(conversations.getBuyerId());
            Map<String,Object> map1 = new HashMap<>();
            map1.put("user_id",conversations.getSellerId());
            map1.put("user_type","notification");
            map1.put("unread_count",i);
            maps.add(map1);

            //卖家 买家有提醒
            WebSocketMessageServer webSocket = map.get(String.valueOf(conversations.getBuyerId()));
            if (webSocket != null) {
                //告诉买家 有新消息 [{未读消息条数，type}]
                webSocket.sendMessage(gson.toJson(maps));
            } else {
                log.warn("卖家 WebSocket 连接不存在，用户ID: {}", conversations.getBuyerId());
            }
        }else {
            maps =ccairbagConversationsMapper.getUserUnreadMessageCount(conversations.getSellerId());
            int i = ccairbagNotificationMapper.getUserNotificationUnreadCount(conversations.getSellerId());
            Map<String,Object> map1 = new HashMap<>();
            map1.put("user_id",conversations.getSellerId());
            map1.put("user_type","notification");
            map1.put("unread_count",i);
            maps.add(map1);
            WebSocketMessageServer webSocket = map.get(String.valueOf(conversations.getSellerId()));
            if (webSocket != null) {
                webSocket.sendMessage(gson.toJson(maps));
            } else {
                log.warn("卖家 WebSocket 连接不存在，用户ID: {}", conversations.getBuyerId());
            }

        }


        if (ccairbagConversationsDTO.getSenderType()==1){


            WebSocketConversationsServer webcSocket = mapx.get(String.valueOf(conversations.getBuyerId()));
            if (webcSocket != null) {
                webcSocket.sendMessage(gson.toJson(buylist));
            } else {
                log.warn("卖家 WebSocket 连接不存在，用户ID: {}", conversations.getBuyerId());
            }


            return new AppResult(selllist);

        }else {
            WebSocketConversationsShopServer webcxSocket = mapshop.get(String.valueOf(conversations.getSellerId()));
            if (webcxSocket != null) {
                webcxSocket.sendMessage(gson.toJson(buylist));
            } else {
                log.warn("卖家 WebSocket 连接不存在，用户ID: {}", conversations.getBuyerId());
            }

            return new AppResult(buylist);
        }

    }

    @Override
    public AppResult readMessages(CcairbagConversationsDTO ccairbagConversationsDTO) {
        CcairbagConversations ccairbagConversations = ccairbagConversationsMapper.selectCcairbagConversationsByConversationId(ccairbagConversationsDTO.getConversationId());
        if (oConvertUtils.isEmpty(ccairbagConversations)){
            return  AppResult.error("会话不存在");
        }
        if (ccairbagConversationsDTO.getSenderType()==1 || ccairbagConversationsDTO.getSenderType()==0){
            CcairbagMessages ccairbagMessages = new CcairbagMessages();
            ccairbagMessages.setConversationsId(ccairbagConversationsDTO.getConversationId());
            ccairbagMessages.setSenderType(ccairbagConversationsDTO.getSenderType());
            ccairbagMessagesMapper.updateReadedBySenderType(ccairbagMessages);
        }else {
            return AppResult.error("发送者类型错误");
        }


        return new AppResult("更新已读成功");
    }

    @Override
    public AppResult numberOfUnreadMessageTypesOnMobile() {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        Long userId = loginAppUser.getCcairbagUsers().getUserId();
        List<Map<String, Object>> list = null;
        list = ccairbagConversationsMapper.getUserUnreadMessageCount(Long.valueOf(userId));
        for (Map<String, Object> map : list){
            if (map.get("user_type").equals("buyer")){
                CcairbagConversations ccairbagConversations = new CcairbagConversations();
                ccairbagConversations.setBuyerId(userId);
                //外层 的用户头像 和名字 用的shops 商店表的数据
                PageUtils.startPage();
                List<CcairbagConversations> userList = ccairbagConversationsMapper.selectBuyerConversationsList(ccairbagConversations);
                extracted(userList);
                PageDataInfo<CcairbagConversations> pageDataInfo = new PageDataInfo<>(userList);
                PageUtils.clearPage();
                map.put("data",pageDataInfo);
            }else if (map.get("user_type").equals("seller")){
                CcairbagConversations ccairbagConversations = new CcairbagConversations();
                ccairbagConversations.setSellerId(userId);
                //外层 的用户头像 和名字 用的用户表 的数据
                PageUtils.startPage();
                List<CcairbagConversations> shopList = ccairbagConversationsMapper.selectSellerConversationsList(ccairbagConversations);
                extracted(shopList);
                PageDataInfo<CcairbagConversations> pageDataInfo = new PageDataInfo<>(shopList);
                PageUtils.clearPage();
                map.put("data",pageDataInfo);
            }else if (map.get("user_type").equals("notification")){
                List<CcairbagNotification> notificatList = ccairbagNotificationMapper.selectNotificationList(Long.valueOf(userId));
                map.put("data",notificatList);
            }
        }

        return new AppResult(list);
    }


}
