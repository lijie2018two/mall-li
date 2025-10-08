package com.ddty.common.mq.configure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.core.domain.MQProperties;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MQConfig {


    @Bean
    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);

    }
    /**
     * 自动取消任务
     * @return
     */
    @Bean//主题任务交换器
    public TopicExchange exchange() {
        return new TopicExchange (MQProperties.EXCHANGE_NAME);
    }

    // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
    // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
    // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
    @Bean//主题任务队列
    public Queue queueMessage() {
        Map<String, Object> args = new HashMap<String, Object>();
//		args.put("x-message-ttl", ApplicationConst.THREE_MINUTE);//设置消息在MQ中过期的时间订单持续时间3分钟(毫秒)
        args.put("x-dead-letter-exchange", MQProperties.DEAD_EXCHANGE_NAME);//绑定死信交换器
        args.put("x-dead-letter-routing-key", MQProperties.ROUTE_KEY);
//		args.put("x-delayed-type", "direct");
        //一般设置一下队列的持久化就好,其余两个就是默认false
        return new Queue(MQProperties.QUEUE_NAME, true, false, false, args);
    }

    @Bean//主题死信交换器
    public FanoutExchange exchangeDead() {
        return new FanoutExchange (MQProperties.DEAD_EXCHANGE_NAME);
    }

    @Bean//主题死信任务队列
    public Queue deadQueueMessage() {
        return new Queue(MQProperties.DEAD_QUEUE_NAME);
    }

    //绑定  将队列和交换机绑定, 并设置用于匹配键：MQProperties.ROUTE_KEY
    @Bean
    Binding bindingExchangeMessage() {
        return BindingBuilder.bind(queueMessage()).to(exchange()).with(MQProperties.ROUTE_KEY);
    }

    @Bean
    Binding bindingExchangeMessageDead() {
        return BindingBuilder.bind(deadQueueMessage()).to(exchangeDead());
    }

    /*********************************************分割线***************************************************************************/

    /**
     * 推送消息给附近达人
     */
    @Bean//主题任务交换器
    public TopicExchange exchange2() {
        return new TopicExchange (MQProperties.EXCHANGE_NAME2);
    }

    // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
    // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
    // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
    @Bean//主题任务队列
    public Queue queueMessage2() {
        Map<String, Object> args = new HashMap<String, Object>();
//		args.put("x-message-ttl", ApplicationConst.THREE_MINUTE);//设置消息在MQ中过期的时间订单持续时间3分钟(毫秒)
        args.put("x-dead-letter-exchange", MQProperties.DEAD_EXCHANGE_NAME2);//绑定死信交换器
        args.put("x-dead-letter-routing-key", MQProperties.ROUTE_KEY2);
//		args.put("x-delayed-type", "direct");
        //一般设置一下队列的持久化就好,其余两个就是默认false
        return new Queue(MQProperties.QUEUE_NAME2, true, false, false, args);
    }

    @Bean//主题死信交换器
    public FanoutExchange exchangeDead2() {
        return new FanoutExchange (MQProperties.DEAD_EXCHANGE_NAME2);
    }

    @Bean//主题死信任务队列
    public Queue deadQueueMessage2() {
        return new Queue(MQProperties.DEAD_QUEUE_NAME2);
    }

    //绑定  将队列和交换机绑定, 并设置用于匹配键：MQProperties.ROUTE_KEY
    @Bean
    Binding bindingExchangeMessage2() {
        return BindingBuilder.bind(queueMessage2()).to(exchange2()).with(MQProperties.ROUTE_KEY2);
    }

    @Bean
    Binding bindingExchangeMessageDead2() {
        return BindingBuilder.bind(deadQueueMessage2()).to(exchangeDead2());
    }

    /**==============================================================================
     *
     */




    /*********************************************分割线***************************************************************************/



    /**
     * 加入延迟队列 一天后订单状态变成已完成 ， 钱包总表 计算可提现金额， 钱包明细表 修改类型type
     */
    @Bean//主题任务交换器
    public TopicExchange exchange3() {
        return new TopicExchange ("order.48.orderExchangeExt");
    }

    // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
    // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
    // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
    @Bean//主题任务队列
    public Queue queueMessage3() {
        Map<String, Object> args = new HashMap<String, Object>();
//		args.put("x-message-ttl", ApplicationConst.THREE_MINUTE);//设置消息在MQ中过期的时间订单持续时间3分钟(毫秒)
        args.put("x-dead-letter-exchange", MQProperties.order_date_write_DEAD_EXCHANGE_NAME);//绑定死信交换器
        args.put("x-dead-letter-routing-key", MQProperties.order_date_write_ROUTE_KEY);

//		args.put("x-delayed-type", "direct");
        //一般设置一下队列的持久化就好,其余两个就是默认false
        return new Queue(MQProperties.order_date_write_QUEUE_NAME, true, false, false, args);
    }

    @Bean//主题死信交换器
    public FanoutExchange exchangeDead3() {
        return new FanoutExchange (MQProperties.order_date_write_DEAD_EXCHANGE_NAME);
    }

    @Bean//主题死信任务队列
    public Queue deadQueueMessage3() {
        return new Queue(MQProperties.order_date_write_DEAD_QUEUE_NAME);
    }

    //绑定  将队列和交换机绑定, 并设置用于匹配键：MQProperties.ROUTE_KEY
    @Bean
    Binding bindingExchangeMessage3() {
        return BindingBuilder.bind(queueMessage3()).to(exchange3()).with(MQProperties.order_date_write_ROUTE_KEY);
    }

    @Bean
    Binding bindingExchangeMessageDead3() {
        return BindingBuilder.bind(deadQueueMessage3()).to(exchangeDead3());
    }



/*********************************************分割线***************************************************************************/



    /**
     * 加入延迟队列 3天后 自动过期 议价数据
     */
    @Bean//主题任务交换器
    public TopicExchange exchange4() {
        return new TopicExchange (MQProperties.negotiable_EXCHANGE_NAME);
    }

    // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
    // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
    // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
    @Bean//主题任务队列
    public Queue queueMessage4() {
        Map<String, Object> args = new HashMap<String, Object>();
//		args.put("x-message-ttl", ApplicationConst.THREE_MINUTE);//设置消息在MQ中过期的时间订单持续时间3分钟(毫秒)
        args.put("x-dead-letter-exchange", MQProperties.negotiable_DEAD_EXCHANGE_NAME);//绑定死信交换器
        args.put("x-dead-letter-routing-key", MQProperties.negotiable_ROUTE_KEY);

//		args.put("x-delayed-type", "direct");
        //一般设置一下队列的持久化就好,其余两个就是默认false
        return new Queue(MQProperties.negotiable_QUEUE_NAME, true, false, false, args);
    }

    @Bean//主题死信交换器
    public FanoutExchange exchangeDead4() {
        return new FanoutExchange (MQProperties.negotiable_DEAD_EXCHANGE_NAME);
    }

    @Bean//主题死信任务队列
    public Queue deadQueueMessage4() {
        return new Queue(MQProperties.negotiable_DEAD_QUEUE_NAME);
    }

    //绑定  将队列和交换机绑定, 并设置用于匹配键：MQProperties.ROUTE_KEY
    @Bean
    Binding bindingExchangeMessage4() {
        return BindingBuilder.bind(queueMessage4()).to(exchange4()).with(MQProperties.negotiable_ROUTE_KEY);
    }

    @Bean
    Binding bindingExchangeMessageDead4() {
        return BindingBuilder.bind(deadQueueMessage4()).to(exchangeDead4());
    }


    /****************************************************************************************************************************/

    /**
     * 7天后订单自动收货
     */
    @Bean//主题任务交换器
    public TopicExchange exchange5() {
        return new TopicExchange (MQProperties.order_signOrder_EXCHANGE_NAME);
    }

    // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
    // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
    // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
    @Bean//主题任务队列
    public Queue queueMessage5() {
        Map<String, Object> args = new HashMap<String, Object>();
//		args.put("x-message-ttl", ApplicationConst.THREE_MINUTE);//设置消息在MQ中过期的时间订单持续时间3分钟(毫秒)
        args.put("x-dead-letter-exchange", MQProperties.order_signOrder_DEAD_EXCHANGE_NAME);//绑定死信交换器
        args.put("x-dead-letter-routing-key", MQProperties.order_signOrder_ROUTE_KEY);

//		args.put("x-delayed-type", "direct");
        //一般设置一下队列的持久化就好,其余两个就是默认false
        return new Queue(MQProperties.order_signOrder_QUEUE_NAME, true, false, false, args);
    }

    @Bean//主题死信交换器
    public FanoutExchange exchangeDead5() {
        return new FanoutExchange (MQProperties.order_signOrder_DEAD_EXCHANGE_NAME);
    }

    @Bean//主题死信任务队列
    public Queue deadQueueMessage5() {
        return new Queue(MQProperties.order_signOrder_DEAD_QUEUE_NAME);
    }

    //绑定  将队列和交换机绑定, 并设置用于匹配键：MQProperties.ROUTE_KEY
    @Bean
    Binding bindingExchangeMessage5() {
        return BindingBuilder.bind(queueMessage5()).to(exchange5()).with(MQProperties.order_signOrder_ROUTE_KEY);
    }

    @Bean
    Binding bindingExchangeMessageDead5() {
        return BindingBuilder.bind(deadQueueMessage5()).to(exchangeDead5());
    }


    /****************************************************************************************************************************/



    /****************************************************************************************************************************/

    /**
     * 3天后自动 把订单改成已完成 并结算店铺钱包
     */
    @Bean//主题任务交换器
    public TopicExchange exchange6() {
        return new TopicExchange (MQProperties.order_sign3Day_EXCHANGE_NAME);
    }

    // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
    // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
    // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
    @Bean//主题任务队列
    public Queue queueMessage6() {
        Map<String, Object> args = new HashMap<String, Object>();
//		args.put("x-message-ttl", ApplicationConst.THREE_MINUTE);//设置消息在MQ中过期的时间订单持续时间3分钟(毫秒)
        args.put("x-dead-letter-exchange", MQProperties.order_sign3Day_DEAD_EXCHANGE_NAME);//绑定死信交换器
        args.put("x-dead-letter-routing-key", MQProperties.order_sign3Day_ROUTE_KEY);

//		args.put("x-delayed-type", "direct");
        //一般设置一下队列的持久化就好,其余两个就是默认false
        return new Queue(MQProperties.order_sign3Day_QUEUE_NAME, true, false, false, args);
    }

    @Bean//主题死信交换器
    public FanoutExchange exchangeDead6() {
        return new FanoutExchange (MQProperties.order_sign3Day_DEAD_EXCHANGE_NAME);
    }

    @Bean//主题死信任务队列
    public Queue deadQueueMessage6() {
        return new Queue(MQProperties.order_sign3Day_DEAD_QUEUE_NAME);
    }

    //绑定  将队列和交换机绑定, 并设置用于匹配键：MQProperties.ROUTE_KEY
    @Bean
    Binding bindingExchangeMessage6() {
        return BindingBuilder.bind(queueMessage6()).to(exchange6()).with(MQProperties.order_sign3Day_ROUTE_KEY);
    }

    @Bean
    Binding bindingExchangeMessageDead6() {
        return BindingBuilder.bind(deadQueueMessage6()).to(exchangeDead6());
    }


    /****************************************************************************************************************************/




    public static final String ACT_QUEUE= "act_queue";//活动立即消费队列
    public static final String ACT_DELAY_QUEUE= "act_delay_queue";//活动死信消费队列
    public static final String ACT_EXCHANGE = "act_exchage";         //交换器
    public static final String ACT_DEAD_LETTER_EXCHANGE = "act_dead_letter_exchange"; //死信交换器
    public static final String ACT_ROUTING_KEY = "act_routing_key";//立即消费路由键
    public static final String ACT_DELAY_ROUTING_KEY = "act_delay_routing_key"; //延迟路由键


    //立即消费队列
    @Bean
    public Queue actQueue() {
        return new Queue(ACT_QUEUE,true);
    }

    /**
     * 延迟队列: 创建一个延迟队列, 此队列中的消息没有消费者去消费, 到了过期时间之后变成死信, 变死信之后会根据
     *           绑定的DLX和routingKey重新发送到指定交换机再到指定队列。
     */
    @Bean
    public Queue delayQueue() {
        Map<String, Object> map = new HashMap<>();
        // x-dead-letter-exchange 声明了队列里的死信转发到的DLX名称，
        map.put("x-dead-letter-exchange", ACT_EXCHANGE);
        // x-dead-letter-routing-key 声明了这些死信在转发时携带的 routing-key 名称。
        map.put("x-dead-letter-routing-key", ACT_ROUTING_KEY);
        Queue queue = new Queue(ACT_DELAY_QUEUE, true, false, false, map);
        System.out.println("----------------------------deadLetterQueue :" + queue.getArguments());
        return queue;
    }

    /**
     *  声明立即消费队列交换机-direct类型
     * 注：把消息投递到那些binding key与routing key完全匹配的队列中。
     * */
    @Bean
    public DirectExchange actExchage(){
        // 一共有三种构造方法，可以只传exchange的名字， 第二种，可以传exchange名字，是否支持持久化，是否可以自动删除，
        //第三种在第二种参数上可以增加Map，Map中可以存放自定义exchange中的参数
        return new DirectExchange (ACT_EXCHANGE,true, false);
    }

    //声明死信队列交换机-direct类型
    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(ACT_DEAD_LETTER_EXCHANGE, true, false);
    }

    /**
     * @description   绑定队列与交换机
     *                  把立即消费的队列和立即消费交换机绑定, immediate_exchange, 路由键：immediate_routing_key
     * @author        songchengye
     * @date          2020/10/24 13:24
     */
    @Bean
    public Binding actBinding() {
        return BindingBuilder.bind(actQueue()).to(actExchage()).with(ACT_ROUTING_KEY);
    }

    /**
     * @description   死信队列的交换机绑定:把延迟消费的队列和死信交换机绑定, immediate_dead_exchange, 路由键：delay_routing_key
     * @author        songchengye
     * @date          2020/10/24 13:24
     */
    @Bean
    public Binding queueDeadBinding() {
        return BindingBuilder.bind(delayQueue()).to(deadLetterExchange()).with(ACT_DELAY_ROUTING_KEY);
    }

}
