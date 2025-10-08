package com.ruoyi.common.core.domain;

public class MQProperties {


	public static final String EXCHANGE_NAME = "order.orderExchangeExt";
	// 自动关单mq
	//任务队列名称
	public static final String QUEUE_NAME = "order.orderQueueExt";
	

	
	//任务交换器与任务队列绑定键
	public static final String ROUTE_KEY = "order.routeKeyExt";
	
	//死信队列名称
	public static final String DEAD_QUEUE_NAME = "order.dead.orderQueueExt";


	
	//死信交换器名称
	public static final String DEAD_EXCHANGE_NAME = "order.dead.orderExchangeExt";

	/**
	 * ===========================================================
	 */

	//任务交换器名称 24小时后 自动发邮件提醒商家

	public static final String EXCHANGE_NAME2 = "write_off.orderWriteOffExchangeExt";
	//任务队列名称
	public static final String QUEUE_NAME2 = "write_off.orderWriteOffQueueExt";
	

	
	//任务交换器与任务队列绑定键
	public static final String ROUTE_KEY2 = "write_off.routeWriteOffKeyExt";
	
	//死信队列名称
	public static final String DEAD_QUEUE_NAME2 = "write_off.dead.orderWriteOffQueueExt";

	//死信交换器名称
	public static final String DEAD_EXCHANGE_NAME2 = "write_off.dead.orderWriteOffExchangeExt";



	/**
	 * ===========================================================
	 */

	//任务交换器名称 48小时后 自动发邮件提醒商家
	public static final String order_date_write_EXCHANGE_NAME = "order.48.orderExchangeExt";
	//任务队列名称
	public static final String order_date_write_QUEUE_NAME = "order.48.orderQueueExt";



	//任务交换器与任务队列绑定键
	public static final String order_date_write_ROUTE_KEY = "order.48.routeKeyExt";

	//死信队列名称
	public static final String order_date_write_DEAD_QUEUE_NAME = "order.48.dead.orderQueueExt";


	//死信交换器名称
	public static final String order_date_write_DEAD_EXCHANGE_NAME = "order.48.dead.orderExchangeExt";


	/**
	 * ===========================================================
	 */

	//任务交换器名称 7天后自动收货
	public static final String order_signOrder_EXCHANGE_NAME = "order.signOrder.orderExchange";
	//任务队列名称
	public static final String order_signOrder_QUEUE_NAME = "order.signOrder.orderQueue";



	//任务交换器与任务队列绑定键
	public static final String order_signOrder_ROUTE_KEY = "order.signOrder.routeKey";

	//死信队列名称
	public static final String order_signOrder_DEAD_QUEUE_NAME = "order.signOrder.dead.orderQueue";


	//死信交换器名称
	public static final String order_signOrder_DEAD_EXCHANGE_NAME = "order.signOrder.dead.orderExchange";


	/**
	 * ===========================================================
	 */

	//议价数据 3天后自动过期
	public static final String negotiable_EXCHANGE_NAME = "order.negotiable.orderExchange";
	//任务队列名称
	public static final String negotiable_QUEUE_NAME = "order.negotiable.orderQueue";



	//任务交换器与任务队列绑定键
	public static final String negotiable_ROUTE_KEY = "order.negotiable.routeKey";

	//死信队列名称
	public static final String negotiable_DEAD_QUEUE_NAME = "order.negotiable.dead.orderQueue";


	//死信交换器名称
	public static final String negotiable_DEAD_EXCHANGE_NAME = "order.negotiable.dead.orderExchange";


	/**
	 * ===========================================================
	 */

	//任务交换器名称 3天后自动 把订单改成已完成 并结算店铺钱包
	public static final String order_sign3Day_EXCHANGE_NAME = "order.sign3Day.orderExchange";
	//任务队列名称
	public static final String order_sign3Day_QUEUE_NAME = "order.sign3Day.orderQueue";



	//任务交换器与任务队列绑定键
	public static final String order_sign3Day_ROUTE_KEY = "order.sign3Day.routeKey";

	//死信队列名称
	public static final String order_sign3Day_DEAD_QUEUE_NAME = "order.sign3Day.dead.orderQueue";


	//死信交换器名称
	public static final String order_sign3Day_DEAD_EXCHANGE_NAME = "order.sign3Day.dead.orderExchange";


	/**
	 * ===========================================================
	 */
}
