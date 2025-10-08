package com.ccairbag.api.config;

import com.ccairbag.api.controller.WebSocketNotificationServer;
import com.ccairbag.api.mapper.CcairbagNotificationMapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class WebSocketNotificationSrverPostProcessor implements BeanPostProcessor {


    @Resource
    private CcairbagNotificationMapper ccairbagNotificationMapper;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof WebSocketNotificationServer) {
            WebSocketNotificationServer webSocketNotificationServer = (WebSocketNotificationServer) bean;
            webSocketNotificationServer.setCcairbagNotificationMapper(ccairbagNotificationMapper);
        }
        return bean;
    }


}
