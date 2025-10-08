package com.ddty.common.mq.service;

import com.ruoyi.common.core.domain.MqMessage;

public interface MessageReceiverService {

    public String getQueueType();



    public void receiveMessage(MqMessage mqMessage);
}
