package com.ccairbag.api.config;

import com.ccairbag.api.controller.WebSocketNotificationServerExt;
import com.ccairbag.api.mapper.CcairbagNotificationMapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class WebSocketNotificationSrverPostProcessorExt implements BeanPostProcessor {


    @Resource
    private CcairbagNotificationMapper ccairbagNotificationMapper;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof WebSocketNotificationServerExt) {
            WebSocketNotificationServerExt webSocketNotificationServerExt = (WebSocketNotificationServerExt) bean;
            webSocketNotificationServerExt.setCcairbagNotificationMapper(ccairbagNotificationMapper);
        }
        return bean;
    }


}
