package com.ccairbag.api.listener;

import com.ccairbag.api.service.ICcairbagNegotiationRecordService;
import com.ccairbag.api.service.ICcairbagOrderDetailsService;
import com.ccairbag.api.service.ICcairbagOrdersService;
import com.ruoyi.common.core.domain.MQProperties;
import com.ruoyi.common.core.domain.MqMessage;
import com.ruoyi.common.core.utils.TimeProvider;
import com.ruoyi.common.core.web.domain.AppResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class OrderListener {

    @Resource
    private ICcairbagOrdersService orderService;
    @Autowired
    private ICcairbagOrderDetailsService ccairbagOrderDetailsService;
    @Autowired
    private ICcairbagNegotiationRecordService ccairbagNegotiationRecordService;


    //没接单情况下 十分钟后 修改订单状态 退回钱包金额，发送通知
    @RabbitListener(queues = MQProperties.DEAD_QUEUE_NAME)
    @RabbitHandler
    public void cancelOrder(MqMessage<String> mqMessage) {
        String orderSeq = mqMessage.getBody();
        log.info(TimeProvider.getCurrentTimestamp() + "自动取消的订单号{}: ",orderSeq);
        AppResult result = orderService.closeOrder(orderSeq);
        log.info("处理完成！code:{},msg:{},data:{}",result.getCode(),result.getMsg(),result.getData());
    }

    //客户支付成功后 24小时后 自动发邮件提醒商家
    @RabbitListener(queues = MQProperties.DEAD_QUEUE_NAME2)
    @RabbitHandler
    public void send24Email(MqMessage<String> mqMessage) {
        String orderSeq = mqMessage.getBody();
        log.info(TimeProvider.getCurrentTimestamp() + "自动24发邮件的订单号{}: ",orderSeq);
        AppResult result = orderService.sendEmail(orderSeq);
        log.info("处理完成！code:{},msg:{},data:{}",result.getCode(),result.getMsg(),result.getData());
    }

    @RabbitListener(queues = MQProperties.order_date_write_DEAD_QUEUE_NAME)
    @RabbitHandler
    public void send48Email(MqMessage<String> mqMessage) {
        String orderSeq = mqMessage.getBody();
        log.info(TimeProvider.getCurrentTimestamp() + "自动48发邮件的订单号{}: ",orderSeq);
        AppResult result = orderService.sendEmail(orderSeq);
        log.info("处理完成！code:{},msg:{},data:{}",result.getCode(),result.getMsg(),result.getData());
    }

    @RabbitListener(queues = MQProperties.order_signOrder_DEAD_QUEUE_NAME)
    @RabbitHandler
    public void signOrder7Day(MqMessage<String> mqMessage) {
        String orderSeq = mqMessage.getBody();
        log.info(TimeProvider.getCurrentTimestamp() + "7天后自动收货的子订单号{}: ",orderSeq);
        AppResult result = ccairbagOrderDetailsService.signOrder7Day(orderSeq);
        log.info("处理完成！code:{},msg:{},data:{}",result.getCode(),result.getMsg(),result.getData());
    }

    /**
     * 超过3天 自动取消议价
     */
    @RabbitListener(queues = MQProperties.negotiable_DEAD_QUEUE_NAME)
    @RabbitHandler
    public void cancelNegotiable(MqMessage<String> mqMessage) {
        String id = mqMessage.getBody();
        log.info(TimeProvider.getCurrentTimestamp() + "超过3天 自动取消议价 id号{}: ",id);
        AppResult result = ccairbagNegotiationRecordService.cancelNegotiable(Long.valueOf(id));
        log.info("处理完成！code:{},msg:{},data:{}",result.getCode(),result.getMsg(),result.getData());
    }

    /**
     * 3天后自动 把订单改成已完成 并结算店铺钱包
     */
    @RabbitListener(queues = MQProperties.order_sign3Day_DEAD_QUEUE_NAME)
    @RabbitHandler
    public void cancelorder5Day(MqMessage<String> mqMessage) {
        String id = mqMessage.getBody();
        log.info(TimeProvider.getCurrentTimestamp() + "3天后自动 把订单改成已完成 并结算店铺钱包 id号{}: ",id);
        AppResult result = ccairbagOrderDetailsService.cancelorder5Day(id);
        log.info("处理完成！code:{},msg:{},data:{}",result.getCode(),result.getMsg(),result.getData());
    }



}
