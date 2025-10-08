package com.ccairbag.api.controller;

import com.alibaba.nacos.shaded.com.google.gson.Gson;
import com.ccairbag.api.mapper.CcairbagNotificationMapper;
import com.ruoyi.system.api.domain.ccairbag.CcairbagNotification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
@ServerEndpoint("/socket/notificationListExt/{userId}")
public class WebSocketNotificationServerExt {


    private static CcairbagNotificationMapper ccairbagNotificationMapper;

    public static void setCcairbagNotificationMapper(CcairbagNotificationMapper notificationMapper) {
        WebSocketNotificationServerExt.ccairbagNotificationMapper = notificationMapper;
    }



    /**静态变量，用来记录当前在线连接数*/
    private static final AtomicInteger onlineCount = new AtomicInteger(0);
    /**concurrent包的线程安全Set，用来存放每个客户端对应的WebSocket对象。*/
    private static  ConcurrentHashMap<String, WebSocketNotificationServerExt> webMessagetMap = new ConcurrentHashMap<>();
    /**与某个客户端的连接会话，需要通过它来给客户端发送数据*/

    public static ConcurrentHashMap<String, WebSocketNotificationServerExt> getWebSocketMap() {
        return webMessagetMap;
    }
    private Session session;
    /**接收userId*/
    private String userId;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
//        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
//        Long userIds = loginAppUser.getCcairbagUsers().getUserId();
        this.session = session;
        this.userId = userId;
        if (webMessagetMap.containsKey(userId)) {
            webMessagetMap.remove(userId);
            // 加入map中
            webMessagetMap.put(userId, this);
        } else {
            // 加入map中
            webMessagetMap.put(userId, this);
            // 在线数加1
            onlineCount.incrementAndGet();
        }


        //外层 的用户头像 和名字 用的shops 商店表的数据
        List<CcairbagNotification> list = ccairbagNotificationMapper.selectNotificationListExt(Long.valueOf(userId));
//        extracted(list);
        Gson gson = new Gson();
        String message  = gson.toJson(list);
        log.info("message:{}",message);
        webMessagetMap.get(userId).sendMessage(message);

        log.info("初始通知连接:" + userId + ",当前在线人数为:" + onlineCount);
//        sendMessage("初始通知用户连接连接成功:"+userId);
    }


    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (webMessagetMap.containsKey(userId)) {
            webMessagetMap.remove(userId);
            // 在线人数减1
            onlineCount.decrementAndGet();
        }
        log.info("用户退出:" + userId + ",当前在线人数为:" + onlineCount);
    }

    /**
     * 收到客户端消息后调用的方法
     **/
//    @OnMessage
//    public void onMessage(String message, Session session) {
//        log.info("用户消息:" + userId + ",报文:" + message);
//        // 解析发送的报文
//        JSONObject jsonObject = JSON.parseObject(message);
//        // 获取需要转发的用户id
//        String toUserId = jsonObject.getString("toUserId");
//        // 传送给对应toUserId用户的websocket
//        if (StringUtils.isNotBlank(toUserId) && webSocketMap.containsKey(toUserId)) {
//            AppResult appResult = new AppResult();
//            appResult.setData(list);
//            webSocketMap.get(toUserId).sendMessage(message);
//        } else {
//            log.error("请求的userId:" + toUserId + "不在该服务器上");
//        }
//    }


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

//    /**
//     *发送自定义消息
//     **/
//    public static void sendInfo(String message, String userId) {
//        log.info("发送消息到:" + userId + "，报文:" + message);
//        if (StringUtils.isNotBlank(userId) && webSocketMap.containsKey(userId)) {
//            webSocketMap.get(userId).sendMessage(message);
//        } else {
//            log.error("用户" + userId + ",不在线！");
//        }
//    }
}
