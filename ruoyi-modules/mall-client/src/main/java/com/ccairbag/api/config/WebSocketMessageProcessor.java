package com.ccairbag.api.config;

import com.ccairbag.api.controller.WebSocketMessageServer;
import com.ccairbag.api.mapper.CcairbagConversationsMapper;
import com.ccairbag.api.mapper.CcairbagNotificationMapper;
import com.ccairbag.api.mapper.CcairbagShopsMapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class WebSocketMessageProcessor implements BeanPostProcessor {


    @Resource
    private CcairbagConversationsMapper ccairbagConversationsMapper;
    @Resource
    private CcairbagNotificationMapper ccairbagNotificationMapper;
    @Resource
    private CcairbagShopsMapper ccairbagShopsMapper;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof WebSocketMessageServer) {
            WebSocketMessageServer webSocketMessageServer = (WebSocketMessageServer) bean;
            webSocketMessageServer.setCcairbagConversationsMapper(ccairbagConversationsMapper);
            webSocketMessageServer.setCcairbagNotificationMapper(ccairbagNotificationMapper);
            webSocketMessageServer.setCcairbagShopsMapper(ccairbagShopsMapper);
        }
        return bean;
    }






}
