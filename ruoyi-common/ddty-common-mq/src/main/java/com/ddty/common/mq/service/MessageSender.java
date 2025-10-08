package com.ddty.common.mq.service;

import com.ruoyi.common.core.domain.MqMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 发送、删除   消息
 */
@Component
@Slf4j
public class MessageSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 发送消息
     * @param queue
     * @param message
     * @param message
     */

    public void sendMessage( String queue, MqMessage message) {
//        amqpTemplate.convertAndSend(exchange, routingKey, message);
        amqpTemplate.convertAndSend(queue,message);
        log.info("消息发送成功");
    }


    /**
     * 发送延迟队列消息
     * @param message
     * @param delayMillis
     */
    public void sendMessageWithDelay(String exchange,String routingKey, MqMessage message, long delayMillis) {
        amqpTemplate.convertAndSend(exchange, routingKey, message,
                new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        //设置消息持久化
                        //message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        log.info("delayMillis:"+delayMillis);
                        message.getMessageProperties().setExpiration(delayMillis+"");
                        return message;
                    }
                });

        log.info("延迟消息发送成功exchange={},routingKey={},message={}"+exchange,routingKey,message);
    }

//    @Autowired
//    private RabbitTemplate rabbitTemplate;




    /**
     * 重置延迟消息
     * @param
     * @param message
     * @param delayMillis
     */
    public void resetMessageDelay(String exchange,String routingKey, MqMessage message, long delayMillis) {
        amqpTemplate.convertAndSend(exchange,routingKey, message, messagePostProcessor -> {
            messagePostProcessor.getMessageProperties().setExpiration(delayMillis +"");
            return messagePostProcessor;
        });
        log.info("消息延迟时间重置成功");
    }

    /**
     * 删除消息
     * @param
     */
    public void deleteMessage(String queue, Object message) {
        amqpTemplate.convertAndSend(queue, message, messagePostProcessor -> {
            messagePostProcessor.getMessageProperties().setHeader("x-death", null); // 设置x-death header来删除消息
            return messagePostProcessor;
        });
        System.out.println("消息删除成功");
    }
    /**
     * 删除延迟消息
     * @param message
     */
    public void deleteMessage(String exchange,String routingKey, MqMessage message) {
        amqpTemplate.convertAndSend(exchange,routingKey, message);
        log.info("延迟消息删除成功");
    }




}