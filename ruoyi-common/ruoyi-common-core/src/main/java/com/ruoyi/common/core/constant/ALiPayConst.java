package com.ruoyi.common.core.constant;

/**
 * 甯搁噺
 */
public class ALiPayConst {

	public enum SignType {
		MD5, HMACSHA256
	}

	public static final String FAILURE     = "failure";
	public static final String SUCCESS  = "success";
	public static final String HMACSHA256 = "HMAC-SHA256";
	public static final String MD5 = "MD5";

	public static final String FIELD_SIGN = "sign";
	public static final String FIELD_SIGN_TYPE = "sign_type";

	//退款订单状态
	public static final String REFUND_REQUEST_SUCCESS = "Y";
	public static final String REFUND_REQUEST_FAILURE = "N";

	public static final String REFUND_SUCCESS = "REFUND_SUCCESS";
	/**沙盒测试配置	 */
	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static final String SANDBOX_APP_ID = "9021000123602732";
	// 商户私钥，您的PKCS8格式RSA2私钥
	public static final String SANDBOX_MERCHANT_PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCxwMpckvMiMLseppPEnBLTsmHpG79dQTS5enHBTjlt6gyzTLf8RML4xBQQZtDEPoTNYKQMsrpZlzARLSak0k6Fgfg7LFMJWrAy9vzdfTsSsZZQkRGVtmZhcUH7Dib/e/rwYgrqaZMDunv8K+Uxh3isOq+YEIO91PlxxRqOTr26xQe0zPEQwF2Sbp6bS5Hsrhrc24yI6RB8sSqaeqNTCJYOCX62Q4X0IWKdapAZU9pqS/qvu2GBHZS7knfGkEfkPSnadwH87NgSOsikr6pgCJ6EA5hCOjYLOKjZZ0Qo+1gikNXR9b2Ae2QYBSe8ru8kE7EcBjTrSiG0r1iFKb/wv3ZxAgMBAAECggEBAKY+t4Spc3fwt7FM/jsN9ZXE6knIx8uCJruqRyYR8ShZlBNFl3X7IZbkXBeS8YH5Lt+emQ+/QQZ1nmESJ2hTwVigW/eL1wWSqeSgl5LXgx5StVEYvKeW9l4APUN+0v5ziBi2uZrPrs335enNbAhMuZHrOTsxhuVOgRXk0Jd0EYLRHdNh0en85NTf2kzilbyKKHb51S4PkrVyyqyti89AvlOks8WotEQBagGabO00pYFIcQRWPbq6cgIFfnSB2Zer5oWhhHMXe+4jH9C+3mziNX5aqpUZMk9E+8azJnILbF63we56Gxfmx3A8Ez6lEDEvdQGxIUaRc/jFW/clBcuuOfkCgYEA8g8QLs/lP+srWLbHda3YbApaDUQJdL+v0I4fMJ/DxKAvf5g0ImDlENt55trrCfnRt+6URzBpwrpMyjRe2QM4ZqdFEq1s+UGjru5aENo/mTqJR5LjWhUEsJ5/hkdd2UjDqCAKIlypusrTwAsqcYlnrkk2Lj78Z57pKxTWjciSYK8CgYEAu/2Y/CzUeLzJPIiz2J8p/uhqPcFmSrdtbLprgDZepdt3UIJjYWrECrWiUHwB8RnBS4OEiGocsQQsPZaaMKIQEmglTfDzOTsyj80+ij7jnZi7bKCWp6s6UE6wp/L/rVXt4wiEg0h/k833un7arN+yG6k62dN6HQtV381bzdt6It8CgYEAg5ko5SplqoCCSnLksdfX57UhDEFHGs/0F+CMmHTjjAzChg4Lmf0vzCBmf9nZGs1U9TiiKKnrddCvWudqAXW8viKGbQcYTdMf2EUBdlQRa47lf6IeNKVkrVkeX7SBE1Z5+PeiStgx9rm6sZfgdWWMlrZ63F2l//fnMGLd2quG2UUCgYA+UUHOjpnccFCRRYvAynpkau0VDbB4Ijge6+lI9j0H5Cm4QZDqMTdNiYqF/aWzI+OEwdNEWVCriYOU4wC4EHEdePKs1Kv8E9id/N63e0N+8atnxhBLAqLxDjTXZ+FDt5GFSkSLNJQ+DfAKHZUl632iV8YPgWAgkqpj5zKAmjnxpwKBgQCzdV6Z4qvlTKMKesQt+wizA/cBK91yNFiH9bzJkZJuA3CGI+6A045yN5kz0FR3u30UctrefAW72owjpBtwEZLQDZk1vC2YB+Hb9RdwkwacgTBl3xy28HZ/oWxhkY0rEWnjD1tzIHSxMely0MrCxgkv1prjhCKot6Ii1MUjGAYKkw==";
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
//	public static final String SANDBOX_ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnFwhsoTNWjNyuCpv8jEyvH65dCNVeMromw5A4S+TalZmri3N9uByWjRzh3uActnoM/uYleiv3tNlXinRdlVHhDcVAGdB5OrmG1uxDH1ek/Vjm0bvQDS52PTNM5MINJSbAO7mpKMkjfIffBgoLv3dOV9Oto9EBRsuDX/SAvf5pAYrxB5VMWvFwqtCizbK6XP7tAziypUP1Iv3u/3abOGUPIbaPU+uJndtcVm9OrdWlNLTLTGtBUfzKnrKhLMaUoKxu+Dm+To1rstFeTYrDhTb9JSMhWETxyqbrBoV/2BxPYAn3RsqaS053LRTXIKrBYpC6cCXB73hNEugUC5Vws5T4wIDAQAB";

//	public static final String SANDBOX_ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAscDKXJLzIjC7HqaTxJwS07Jh6Ru/XUE0uXpxwU45beoMs0y3/ETC+MQUEGbQxD6EzWCkDLK6WZcwES0mpNJOhYH4OyxTCVqwMvb83X07ErGWUJERlbZmYXFB+w4m/3v68GIK6mmTA7p7/CvlMYd4rDqvmBCDvdT5ccUajk69usUHtMzxEMBdkm6em0uR7K4a3NuMiOkQfLEqmnqjUwiWDgl+tkOF9CFinWqQGVPaakv6r7thgR2Uu5J3xpBH5D0p2ncB/OzYEjrIpK+qYAiehAOYQjo2Czio2WdEKPtYIpDV0fW9gHtkGAUnvK7vJBOxHAY060ohtK9YhSm/8L92cQIDAQAB";
	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
//	public static final String SANDBOX_HTTPHEADER = "https://2yx9173945.picp.vip";
	public static final String SANDBOX_HTTPHEADER = "http://8.131.79.124:8199";

//	public static final String SANDBOX_HTTPHEADER = "http://hyyt.natapp1.cc";


	public static final String HTTPHEADER = "http://182.92.167.204:8081";

	//	public static final String HTTPHEADER = "http://hyyt.natapp1.cc";
	public static final String SANDBOX_NOTIFY_URL = SANDBOX_HTTPHEADER + ApiConst.API_HEADER + ApiConst.API_USER_ALIPAYNOTIFYNOTICE;//这里根据你项目中的controller进行配置
	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问

	public static final String SANDBOX_NOTIFY_URL_EXT = SANDBOX_HTTPHEADER + ApiConst.API_HEADER + ApiConst.API_USER_ALIPAYNOTIFYNOTICE_NATIVE;//这里根据你项目中的controller进行配置

	public static final String SANDBOX_RETURN_URL = SANDBOX_HTTPHEADER + ApiConst.API_HEADER + ApiConst.API_USER_ALIPAYRETURNNOTICE;//这里根据你项目中的controller进行配置
	// 签名方式
	public static final String SANDBOX_SIGN_TYPE = "RSA2";
	// 字符编码格式
	public static final String SANDBOX_CHARSET = "utf-8";
	// 卖家支付宝账号
	public static final String SANDBOX_SELLER_EMAIL = "rmxxli8277@sandbox.com";
	// 支付宝网关
	public static final String SANDBOX_GATEWAYURL = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";//使用沙箱环境时使用
	//windows
//	public static final String SANDBOX_APP_CERT_PATH = "D:\\ytgj\\sandbox_ali_cert\\appPublicCert.crt";
//	public static final String SANDBOX_ALIPAY_CERT_PATH = "D:\\ytgj\\sandbox_ali_cert\\alipayPublicCert.crt";
//	public static final String SANDBOX_ALIPAY_ROOT_CERT_PATH = "D:\\ytgj\\sandbox_ali_cert\\alipayRootCert.crt";
	//linux
	public static final String SANDBOX_APP_CERT_PATH = "/usr/ytgj/sandbox_ali_cert/appPublicCert.crt";
	public static final String SANDBOX_ALIPAY_CERT_PATH = "/usr/ytgj/sandbox_ali_cert/alipayPublicCert.crt";
	public static final String SANDBOX_ALIPAY_ROOT_CERT_PATH = "/usr/ytgj/sandbox_ali_cert/alipayRootCert.crt";

	/**正式环境配置	 */
	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static final String APP_ID = "2021002169669811";
	// 商户私钥，您的PKCS8格式RSA2私钥
	public static final String MERCHANT_PRIVATE_KEY  = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCyd3WGCW4wbqf+lVW+bSU4RkXZ5UYfJ55aGiGL0x/yfe2aELPy+A8AVvEzbpkxI6VqhgHrsF/0pXvNRRpC/FtXVXbgGaQ8nIs8+Uq4/3uOmCxUL5H67m8p0jmc6e/ToREj9ekXbNiXhniM/EWlF5RETN/UHh1wPita7ppQ2XDN+h50ZaVdoWeUpbNN2ee6iKvaDf9sN2xrdXvm0cPtIyGPYOJsVqL+cRZ7vN/oNcVtBLO2Ux7l2JDRKoB6+rbhu4oWYdv52GY9DC/yOGrerR/xYwltKTaYpee2Yl6CTBiK1IR/3AJNkLsMsxLDTTWfxlqgQ1vIhEFd3AiCBFu3PFwtAgMBAAECggEBAIotf+r1pkENjDjK+wZjj2vQzQh2QiKhFDQDA3I1Vwjzg2HECO/YLBVBO6NswGORi+O8OgR4HPNYpVA7bqfqp/Yoj8/XSpg7RHtDMR2ngczX+Yp9AnPc5GjnAgCCCFQ84v6KyZx/g/td/Dx8npfCd+Oh+bNMVdKv7/WJxCVRFcwDzLJyihuK6A/jdiZckXKXK3eLtuHDhBfLJvkhyz2J9jRxZLu16g0KfEc4uxSZ82FmSPmgOawnxH3aw9QnG3nTB/PP2yVI/9gAQNJqWVqaLfSXSYFYAbnXL3Q7xi7YScCenSM3iXODcF8ZrlIJqTTLH+pSGCUElxLN2AeOV6CMLsECgYEA8lXWYeROUUcAu7r9e/8Szhohml/80qFAgf7kRAJWm6WTCXNfx7QEDeRtwM/+W9GRemEJqPwhg9SyrSB3XIBDfu+tclNAJNSa/Ly6W8waUCjmkXSbctpBc29YkQd6DXz5XFtUyRNNqgeiXIubhXY+yuIMJepmjyw+djOYN1TlNwUCgYEAvIep45NfW98jZdnc0LhHY+LR1cI0aofTsS8cU6Jnw1c8KI8Ve1PY9mEuCLKVB7HSZ0tz9t1TuAxdLcchSaV5hyf/TDtdZLBGNhyQReDHyF99o3ytmBBSGoR4wMh5Bh2LaioDj/0KNVy4F4ofHFkDO/dlqePSyRd3jqqyLDMZSQkCgYEAmW2ZRu86WqE+qJ0SIjBzooPO5KODOYQYiHVt5lUoqnDhMCmY6aJisrz+dAX7//wmRDot2IyRV5sR6STWDX2zvilXQjvNL8RfwEZpIveNbVUH7e3LAaOHruM/SstWDjYi1sOIYapUEKO2uys0swITbXZCxLqsZldX0JSH+fhJC1UCgYB4ne8SW3IR7CPQgN8mZg++lxoawWIZmgbVAEgGNgxezIv/gvOCcLWJg8kY5XA1eQ4c6ggqM5XhVX+zzwe7/3NOBkfutLhet+xjqEihfFs1Feri0tyZQi/VKVW8OUY8pGBSrsiSsZh7ZXOeE88tqsFH91MCq3BrGWlzTEAryRFvSQKBgCORHv44yTG6TxBcZWjgL7YEh/YmXo2HP4hbs+0ISTuZ9R0SmQGRjDJo7n23FPu+GoQ35fRr+8D57+6fgtrxaHMOYZUsGwH04d44Dd2ygYnjAjK5iQKjX6/0Ji+/mzNK77E+M70LAsZqq749iHbAE+NRQhpfKTwYUEEdLkMGk3dQ";

//	public static final String MERCHANT_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC6safnUlNNXoTJjbLMlQyirTpZEIzuZkgDc/MHYCd92sQJRP4bRx1+BfLg2KDpaaDjuiupUcZH+N3BFuGaYkNYfACIfHvkLIEa+iBdZoWj3S+1G1R/cEcv30tjStdG7d7ZZ6jKcHB7zV/YvXNXLFGQFP+fN44Irk5KpqGQrk9DzIfpPrvWZDrSv7NJDCRsAyYpL9Rm9X6uPiXlu3oan8EkcFTwY7O3nc80rxXs0hL39zd2c8mR12ZYhJqXqIdvk5THZElxIgHO4k9k4GCYrT7B/nFEw28EBx1PEwMTKMikYjY2+5PkKo3oZhqt4kzCA0rtu7lFhxhE5F40k7bNf5zFAgMBAAECggEAFcfwTN6f9WDXoSQ0/gVNqhUHv6hcqNup/y0h53RiZx6EzIgOFWSgnjlXcsATVegK60anHWP6/B6e0I441+gq70HYVhQf6CkBDwKrj/oMEWgWzsxXeV7CD2W/x9/NaesKmtarLKMdnHQDgKfpsvvqsCKjCBJVMdIUNEVoiBauDLy27GYXPn/Z/mmvxxAkFe5GpgRluVkPQgJ/5JS36A4fL2lYCURHYHDmZVBFTCo9ar3i7KMmgHgaO/woSHMLQy5JQRGDUOfM93/yPMskIy0XYextixsBXOgQomdU9zT3OxQEz2kaXyVKs7sBOl/DJ2GXN3pB4ZsxOmjOFWcY4w8wAQKBgQDoCckLenxStsmOll13NS2wOkbo6eDBUTSAcJ92H0W0uo8433D9O4Tf7YjksoCUN2jYDiCK5796kjS61PsepHchgbrGPHl8cFmWHpdDTVSlHkxkoGmpDIJkLfFy7y3aBMnEmjFX4XNNI49pxXswMtEto5hvMrMRkPTwCdi8KJ56AQKBgQDN+SO87aXdUb5bUJWG6Kz+3O2/+JoLAT1HNETHn0bJ3i0yvwqqAWllde8gzqg58rweovt8JjkLT7UL3mwyaW1TwQysCghIUoRhQp+ZgBJnczh2U06TJRKRALjaleMC+o54U+/f/rWVgo4SKLifGWH5GAAFcOAeWqwdq5hIUOe6xQKBgEd77EHrBC3/Pez9ieHVsjOeYIYUtzcXK1N+XsHnNHRPHRxqScbZ9T9/UDMUguQmhdg50fC5IJZEQJZmUs7H4COkccPskYiynAbRecw0IKWVxfciLCr2PZ1bQg0kfj2yR+6XiigGvpy/Hcl6G09n+GVVBvsK5Xu96pegkPK2mMwBAoGBAMabOeudlzat8IgqTtQUHWNu8k0OULLV0hmLe9qkaWSTOC/baFP/Inqz2g1fV3Tw6pX7/9R5Ir4WcHNuWBPP9pww5KAq2EusTXte+3fOt3BK+KGXuIdH0vxAnv4eUxP3N1MtVHo/JyZwCb/WxhabKg4/Jx4X9fMu6RF++UqZwKglAoGBAOBSfKPS2LfLG/bPjNt3a9fu1MeTimkOAX2ZT5zc/aufGkBSm5stXznJfJCHYZ0zB0QI3uwQKAPBwMNXkwYWXmi3JQ3t7WPBuRC+K1iPuFxm41ORPbfRKtql9al5U8CojDexmUdRm5+J6o6cbn5ZWeYwIf50kbTxSoxNGPkdcK4c";
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
	public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAobiuRZ7syzisZ1iQ3ikCCEVMF9kCiA7kLxKxvN8ZxNBLeyI7ib+TVUAwmw19r1+d0F829UDbP1yqndyAvdzGUUpX1PdWiK+CFQw6Qd4oojxF3QssBbs7DBsVDu+hu90lwM6jGfIwppcpdNpwhRaL0eXbyxNeqczFVi1TMXBIMqYfuO4cE/rtLnmlMPddcjrjUZKcrQJzQYAjICIAsDjF+ObklgS9iT879FlgFOxj9ZWKIGAHx7oUpF+l6XmH5BQKz07ZljNV+KR2i9UUEbCD4v+08IBgV2THVBHFDJx5tG/Puk3o3/jmeb29zJObZlfZ0pmLdRzFBxWblHtTcqv17QIDAQAB";
	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问

	public static final String NOTIFY_URL = HTTPHEADER + ApiConst.API_HEADER + ApiConst.API_USER_ALIPAYNOTIFYNOTICE;//这里根据你项目中的controller进行配置

	public static final String NOTIFY_URL_EXT = HTTPHEADER + ApiConst.API_HEADER + ApiConst.API_USER_ALIPAYNOTIFYNOTICE_NATIVE;//这里根据你项目中的controller进行配置

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static final String RETURN_URL = HTTPHEADER + ApiConst.API_HEADER + ApiConst.API_USER_ALIPAYRETURNNOTICE;//这里根据你项目中的controller进行配置
	// 签名方式
	public static final String SIGN_TYPE = "RSA2";
	// 字符编码格式
	public static final String CHARSET = "utf-8";
	// 卖家支付宝账号
	public static final String SELLER_EMAIL = "admin@houyuan100.com";
	// 支付宝网关
	public static final String GATEWAYURL = "https://openapi.alipay.com/gateway.do";//使用沙箱环境时使用
	//windows
//	public static final String APP_CERT_PATH = "D:\\ytgj\\ali_cert\\appCertPublicKey_2021002169669811.crt";
//	public static final String ALIPAY_CERT_PATH = "D:\\ytgj\\ali_cert\\alipayCertPublicKey_RSA2.crt";
//	public static final String ALIPAY_ROOT_CERT_PATH = "D:\\ytgj\\ali_cert\\alipayRootCert.crt";
	//linux
	public static final String APP_CERT_PATH = "/usr/ytgj/ali_cert/appCertPublicKey_2021002169669811.crt";
	public static final String ALIPAY_CERT_PATH = "/usr/ytgj/ali_cert/alipayCertPublicKey_RSA2.crt";
	public static final String ALIPAY_ROOT_CERT_PATH = "/usr/ytgj/ali_cert/alipayRootCert.crt";
//	//public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";//项目正式使用后使用
	// 支付宝网关

}