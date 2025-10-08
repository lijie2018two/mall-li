package com.ccairbag.api.config;

import com.ccairbag.api.controller.WebSocketConversationsServerItemExt;
import com.ccairbag.api.mapper.CcairbagMessagesMapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class WebSocketMessageServerPostProcessorExt implements BeanPostProcessor {


    @Resource
    private CcairbagMessagesMapper ccairbagMessagesMapper;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof WebSocketConversationsServerItemExt) {
            WebSocketConversationsServerItemExt webSocketConversationsServerItemExt = (WebSocketConversationsServerItemExt) bean;
            webSocketConversationsServerItemExt.setCcairbagMessagesMapper(ccairbagMessagesMapper);
        }
        return bean;
    }


}
