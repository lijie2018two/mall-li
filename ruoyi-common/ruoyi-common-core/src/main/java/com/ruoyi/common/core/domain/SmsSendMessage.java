package com.ruoyi.common.core.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * The persistent class for the s_user database table.
 * 
 */
@Getter
@Setter
@ToString
public class SmsSendMessage implements Serializable{

	private static final long serialVersionUID = 2391911058512873861L;
	private int sendId;
	
	private Boolean status;
	
	//短信类型
	private int productType;
	
	//手机号码
	private String mobile;
	
	//订单ID
	private int orderId;
	
	//相关参数
	private String note;

	private String bizId;

	private String message;

	private String sendDate;


}
