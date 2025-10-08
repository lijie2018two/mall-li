package com.ruoyi.common.core.constant;

public class WeChatConst {
	//线上
	public static final String WECHAT_PAY_CERT_PATH = "/usr/ytgj/wx_cert/apiclient_cert.p12";

	//线下
//	public static final String WECHAT_PAY_CERT_PATH = "D:\\ytgj\\wx_cert\\apiclient_cert.p12";
	//线上
	public static final String APICLIENT_PAY_CERT_PATH = "/usr/ytgj/wx_cert/apiclient_cert.pem";
	//线上
	public static final String APICLIENT_PAY_KEY_PATH = "/usr/ytgj/wx_cert/apiclient_key.pem";
	//线下
//	public static final String APICLIENT_PAY_KEY_PATH = "D:\\ytgj\\wx_cert\\apiclient_cert.pem";

	//本地
//	public static final String APICLIENT_PAY_KEY_PATH = "D:\\ytgj\\wx_cert\\apiclient_cert.p12";

	public static final String WECHAT_PAY_CERT_ZS = "4FE9315E72B7CBAE5F195FD05F784496D12EA27D";

	public static final String WECHAT_PAY_SERIALNO = "D:\\ytgj\\wx_cert\\wechatpay_13C6275CEDED16126A886485470B2DF09F3E3C03.pem";


	//本地
//	public static final String APICLIENT_PAY_CERT_PATH = "D:\\ytgj\\wx_cert\\apiclient_cert.pem";
	//本地
//	public static final String APICLIENT_PAY_KEY_PATH = "D:\\ytgj\\wx_cert\\apiclient_key.pem";

	public static final String APP_ID = "wx2e1a38f026571b30";

	public static final String APP_GZH_ID = "wx75db9e5d186ac1a9";

	public static final String WECHAT_GZH_SECRET = "3623a037d6da3c60334a59cebe996a45";


	public static final String MCH_ID = "1604508105";
	public static final String WECHAT_API_SECRET = "bLYSdaOS9zBu9sATR1xUxbnBh1oO547u";
	public static final String WECHAT_APP_SECRET = "bda64df6ea97389b779f37b32d41a9ff";
	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
//	public static final String SANDBOX_HTTPHEADER = "https://2yx9173945.picp.vip";
	//线上
//	public static final String SANDBOX_HTTPHEADER = "http://8.131.79.124:8088";

	public static final String SANDBOX_HTTPHEADER = "http://182.92.167.204:8081";



	//本地
//	public static final String SANDBOX_HTTPHEADER = "http://hyyt.natapp1.cc";


	public static final String SANDBOX_NOTIFY_URL = SANDBOX_HTTPHEADER + ApiConst.API_HEADER + ApiConst.API_USER_WXPAYNOTIFYNOTICE;//这里根据你项目中的controller进行配置

	public static final String WECHAT_NOTIFY_URL = SANDBOX_HTTPHEADER + ApiConst.API_HEADER + ApiConst.API_USER_WXPAYNOTIFYNOTICEExt;//这里根据你项目中的controller进行配置


	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static final String SANDBOX_RETURN_URL = SANDBOX_HTTPHEADER + ApiConst.API_HEADER + ApiConst.API_USER_WXPAYNOTIFYNOTICE;//这里根据你项目中的controller进行配置
	public static final String SANDBOX_REFUND_RETURN_URL = SANDBOX_HTTPHEADER + ApiConst.API_HEADER + ApiConst.API_USER_WXPAYREFUNDNOTIFYNOTICE;//这里根据你项目中的controller进行配置
	// 签名方式
	public static final String SANDBOX_SIGN_TYPE = "RSA2";
	// 字符编码格式
	public static final String SANDBOX_CHARSET = "utf-8";
	// 卖家支付宝账号
	public static final String SANDBOX_SELLER_EMAIL = "lflejr1095@sandbox.com";

	public static final String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDXap9aACbzDyaG\n" +
			"VuxU0kqZUakim2gbgGtnhWH8FT7gcR5jwzZ94E2Vk/yA96koNq7gP1S2lrzCngZA\n" +
			"msr+IAynHiPGZ65bCTkNjMMvHzcYlQf1qybqh4yK6lBeLkIEf9gKWPzmnkSniho9\n" +
			"bDJ8Mn3lrYvGEXYFrJbmURjRBlOR93AnvJwLKuz9Sy70rPdHR/1t+0yxjRHGG7Ex\n" +
			"gMd/cf5NXhRYq+w68pIlrvIlQOpN9Hsn2a2EEiFV82iPSqwgoDrjuRgvB8IIXHIz\n" +
			"bbO4M2VGi2rj4pJAY8Ev62yqkbdOFyZ+8c1gH54Ceyy5lujUW+A4mQpQ94Czmci2\n" +
			"EGAE0SUTAgMBAAECggEAa6aV8dUnocnNd88SlaTtsMb1Xh0JnszDX91xRRec7264\n" +
			"QCS7I5anTvvAcPbTKwyAwgIEJKcZt6xe9YQls29yw0U1nZ4MrOPkBhh1JiRUVU2M\n" +
			"x8F7VjLdEkLcXOSDC1qULT+hSuKG7q7+Q/nBFP3qmo/qzNXHBDjmxDx8yWB+/O8e\n" +
			"8YPdK0yauOt8FnTPnbwhsqaCtySfv5Kn3va3NAFLiVhsTv/AnBDD2uRurWQZF/44\n" +
			"sAdvsmZKW9JVLtOOhjPJtmw7C6M9LREINdTTVHUKAaaH4zERPo0JIQZRodP8Ozv5\n" +
			"PHZoLjU7OdUbVoVwlV9RFcWlA59Sor9PW38+cUh8uQKBgQDwFOyO1PRG1jChy/Y0\n" +
			"chJ0J2h5mp3hqEVmwKw7UUlAUPcq62ThVUC5jxUaT51dy0DvAswVpPKJixDh1pTJ\n" +
			"AQdV9jrxZvCXGlGTy5QyGLZ8IiPd0fugpnQla7h7e9eVmLb9SNBvnE4+ht0sgSIe\n" +
			"8T8Tu5xRdBCj5aOWZe8JCGBn/QKBgQDlswm3gID3+uxE/z9018JhHaz51MW405mf\n" +
			"fuM9zE6/82HLcHNXkD2/U+5p7WUnQRIzWsJ6cYsQjLLiYpD3hMm1lMh7t6Jpy8fm\n" +
			"9UI3NQoI9UA2xP7R5n03NrlBtSC/N9sUmIUluyVlxlNB8+4hyynRTXLy/haYoMRN\n" +
			"eh+25B+mTwKBgGaDiL80j1nv+7Qas3aRfu+6JBJIw0Gn6HqHqwNpIi4kZGYfPWQ6\n" +
			"Uvjo78ncVQXH1GI6f4p+dFHm376PMdjiZ499JI4PtovO4SaKtyztAH7g1Q/Sqe9f\n" +
			"JBkWdh/EMbnfC8P7Y4VE6OQqEib+vOMZgCWc5pKhTLmvRLrjq73fpcrBAoGAfQP9\n" +
			"tN7+0njCISdfDSPlCmBRZcQVPOwFv5Ya7eaFsdkwD76LOEwsHzb6GrL3C+9RjZvH\n" +
			"H0cu7fn13lrRFbYy5l4s45Dll3kuupgfytZWONvqcCS1n943plZla8q+IPSwizwb\n" +
			"s5Zw2zye0dWGdOQTRXRGrNP3zmgpjxV5r1vnkecCgYEArDniuHNNDgpGaEDoUAPX\n" +
			"MEz8nRGWNhxJFaa5BuKfK6Jof5UBEvSDuu1IN0BhzsY5aJYdfkXweo+R74q3hS0O\n" +
			"IA1KFz7xCLZSAp1Q0+LlLXSteSGBKiOdcMSqI4zVwg5Ea6bfbz++H1W46V1UG56Z\n" +
			"74fXYNS0ScP6auziQ+llxu0="; // 商户证书序列号对应的证书秘钥

	public static final String API_3KEY = "ytgj6666666666666666666666666666";   //V3密钥


}
