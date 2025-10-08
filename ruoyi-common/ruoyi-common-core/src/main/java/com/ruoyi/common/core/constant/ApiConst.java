package com.ruoyi.common.core.constant;

public class ApiConst {
	/**-- app接口  --*/
	//API app头部接口
	public static final String API_HEADER = "/api/v1";
	//API 头部接口
	public static final String API_REQUEST_HEADER_TOKEN = "X-MALL-API-TOKEN";

	public static final String API_BUSINESS_HEADER_TOKEN = "X-BUSINESS-TOKEN";

	public static final String API_USER_ALIPAYNOTIFYNOTICE = "/order/alipayNotifyNotice";

	public static final String API_USER_ALIPAYNOTIFYNOTICE_NATIVE = "/order/alipayNotifyNoticeNative";
	public static final String API_USER_WXPAYNOTIFYNOTICE = "/order/wxpayNotifyNotice";

	public static final String API_USER_WXPAYNOTIFYNOTICEExt = "/order/wxpayNotifyNoticeExt";
	public static final String API_USER_WXPAYREFUNDNOTIFYNOTICE = "/order/wxpayRefundNotifyNotice";
	//银联接收通知
	public static final String API_USER_YINLIANNOTIFYNOTICE = "/user/yinlianNotifyNotice";
	//支付结果同步回调
	public static final String API_USER_ALIPAYRETURNNOTICE = "/user/alipayReturnNotice";


}
