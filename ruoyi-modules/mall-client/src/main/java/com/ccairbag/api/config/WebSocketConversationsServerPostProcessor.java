package com.ccairbag.api.config;

import com.ccairbag.api.controller.WebSocketConversationsServer;
import com.ccairbag.api.mapper.CcairbagConversationsMapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class WebSocketConversationsServerPostProcessor implements BeanPostProcessor {

    @Resource
    private CcairbagConversationsMapper ccairbagConversationsMapper;


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof WebSocketConversationsServer) {
            WebSocketConversationsServer webSocketConversationsServer = (WebSocketConversationsServer) bean;
            webSocketConversationsServer.setCcairbagConversationsMapper(ccairbagConversationsMapper);
        }
        return bean;
    }


}
