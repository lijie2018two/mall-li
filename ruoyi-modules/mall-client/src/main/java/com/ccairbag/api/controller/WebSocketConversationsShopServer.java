package com.ccairbag.api.controller;


import com.alibaba.nacos.shaded.com.google.gson.Gson;
import com.ccairbag.api.mapper.CcairbagConversationsMapper;
import com.ruoyi.system.api.domain.ccairbag.CcairbagConversations;
import com.ruoyi.system.api.domain.ccairbag.CcairbagMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
@ServerEndpoint("/socket/conversationsShopList/{userId}")
public class WebSocketConversationsShopServer  {

    private static CcairbagConversationsMapper ccairbagConversationsMapper;

    public static void setCcairbagConversationsMapper(CcairbagConversationsMapper ccairbagConversationsMapper) {
        WebSocketConversationsShopServer.ccairbagConversationsMapper = ccairbagConversationsMapper;
    }
    /**静态变量，用来记录当前在线连接数*/
    private static final AtomicInteger onlineCount = new AtomicInteger(0);
    /**concurrent包的线程安全Set，用来存放每个客户端对应的WebSocket对象。*/
    private static  ConcurrentHashMap<String, WebSocketConversationsShopServer> webSocketMap = new ConcurrentHashMap<>();
    /**与某个客户端的连接会话，需要通过它来给客户端发送数据*/

    public static ConcurrentHashMap<String, WebSocketConversationsShopServer> getWebSocketMap() {
        return webSocketMap;
    }
    private Session session;
    /**接收userId*/
    private String userId;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
        this.userId = userId;
        if (webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
            // 加入map中
            webSocketMap.put(userId, this);
        } else {
            // 加入map中
            webSocketMap.put(userId, this);
            // 在线数加1
            onlineCount.incrementAndGet();
        }
        CcairbagConversations ccairbagConversations = new CcairbagConversations();
        ccairbagConversations.setSellerId(Long.valueOf(userId));
        //外层 的用户头像 和名字 用的用户表 的数据
        List<CcairbagConversations> list = ccairbagConversationsMapper.selectSellerConversationsList(ccairbagConversations);
        extracted(list);
        Gson gson = new Gson();
        webSocketMap.get(userId).sendMessage(gson.toJson(list));

        log.info("商家用户连接:" + userId + ",当前商家用户在线人数为:" + onlineCount);
//        sendMessage("商家用户连接连接成功:"+userId);
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
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
            // 在线人数减1
            onlineCount.decrementAndGet();
        }
        log.info("用户退出:" + userId + ",当前在线人数为:" + onlineCount);
    }




    /**
     * 发生异常调用方法
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:" + this.userId + ",原因:" + error.getMessage());
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
//    public void sendMessage(String message) {
//        this.session.getAsyncRemote().sendText(message);
//    }



    public void sendMessage(String message) {
        if (session != null && session.isOpen()) {
            int chunkSize = 1024;
            for (int i = 0; i < message.length(); i += chunkSize) {
                int endIndex = Math.min(i + chunkSize, message.length());
                String chunk = message.substring(i, endIndex);

                // 最后一段添加EOF标记
                boolean isLastChunk = (endIndex == message.length());
                if (isLastChunk) {
                    chunk += "|EOF";
                }

                try {
                    session.getBasicRemote().sendText(chunk);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            log.error("Session is not valid or already closed");
        }
    }

}