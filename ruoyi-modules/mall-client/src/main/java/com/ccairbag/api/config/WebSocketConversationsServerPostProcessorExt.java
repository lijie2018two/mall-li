package com.ccairbag.api.config;

import com.ccairbag.api.controller.WebSocketConversationsServerExt;
import com.ccairbag.api.mapper.CcairbagConversationsMapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class WebSocketConversationsServerPostProcessorExt implements BeanPostProcessor {

    @Resource
    private CcairbagConversationsMapper ccairbagConversationsMapper;


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof WebSocketConversationsServerExt) {
            WebSocketConversationsServerExt webSocketConversationsServer = (WebSocketConversationsServerExt) bean;
            webSocketConversationsServer.setCcairbagConversationsMapper(ccairbagConversationsMapper);
        }
        return bean;
    }


}
