package com.ruoyi.common.core.utils.payment;


import com.github.wxpay.sdk.WXPayConfig;
import com.ruoyi.common.core.constant.WeChatConst;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * 配置当前系统微信支付信息
*/
public class WeChatPayConfig implements WXPayConfig {

	/** 加载证书 证书下载自微信商户平台*/
	private  byte[]  certData;
	
	public void OurWxPayConfig() throws  Exception{
				File file =  new File(WeChatConst.WECHAT_PAY_CERT_PATH);
				boolean a =  file.exists();
				InputStream certStream = new FileInputStream(file);
				this.certData = IOUtils.toByteArray(certStream);
		        certStream.close();
		     }
	
	
	@Override
	public String getAppID() {
		// TODO Auto-generated method stub
		return WeChatConst.APP_ID;
	}

	@Override
	public String getMchID() {
		// TODO Auto-generated method stub
		return WeChatConst.MCH_ID;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub download bill -use WECHAT_API_SECRET only
//		Map<String, String> param1 = new HashMap<String, String>();
//		param1.put("mch_id", ApplicationConst.MCH_ID);// 商户号
//		param1.put("nonce_str", WXPayUtil.generateNonceStr());// 随机字符串
//		try {
//			param1.put("sign", WXPayUtil.generateSignature(param1, ApplicationConst.WECHAT_API_SECRET,WXPayConstants.SignType.MD5));
//			WeChatPayConfig config = new WeChatPayConfig();
//			WXPay wxPay = new WXPay(config	, SignType.MD5	, true);
//			Map<String, String> map =  WXPayUtil.xmlToMap(wxPay.requestWithoutCert("https://api.mch.weixin.qq.com/sandboxnew/pay/getsignkey", param1, 10000, 10000));
//			if (String.valueOf(map.get("return_code")).equals(WXPayConstants.SUCCESS)) {
//				return map.get("sandbox_signkey");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}// 沙盒测试貌似只支持MD5加密
		return WeChatConst.WECHAT_API_SECRET;
	}

	@Override
	public InputStream getCertStream() {
		// TODO Auto-generated method stub
		return new ByteArrayInputStream(certData);
	}

	@Override
	public int getHttpConnectTimeoutMs() {
		// TODO Auto-generated method stub
		return 10000;
	}

	@Override
	public int getHttpReadTimeoutMs() {
		// TODO Auto-generated method stub
		return 10000;
	}

}
