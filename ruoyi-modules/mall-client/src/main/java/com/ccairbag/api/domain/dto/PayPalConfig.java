
package com.ccairbag.api.domain.dto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * <p>IJPay 让支付触手可及，封装了微信支付、支付宝支付、银联支付等常用的支付方式以及各种常用的接口。</p>
 *
 * <p>不依赖任何第三方 mvc 框架，仅仅作为工具使用简单快速完成支付模块的开发，可轻松嵌入到任何系统里。 </p>
 *
 * <p>IJPay 交流群: 723992875、864988890</p>
 *
 * <p>Node.js 版: <a href="https://gitee.com/javen205/TNWX">https://gitee.com/javen205/TNWX</a></p>
 *
 * <p>PayPal 支付配置</p>
 *
 * @author Javen
 */
@Data
@Component
@ConfigurationProperties(prefix = "paypal")
public class PayPalConfig implements Serializable {

	private static final long serialVersionUID = -6012811778236113584L;
	/**
	 * 应用编号
	 */
	private String clientId;
	/**
	 * 应用密钥
	 */
	private String secret;
	/**
	 * 是否是沙箱环境
	 */
	private boolean sandBox;
	/**
	 * 域名
	 */
	private String domain;


	private String checkoutOrderApprovedWebhookId;



}
