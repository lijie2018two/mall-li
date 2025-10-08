package com.ccairbag.api.utils;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.ccairbag.api.config.AlipayCertConfig;
import com.ijpay.alipay.AliPayApiConfig;
import com.ijpay.alipay.AliPayApiConfigKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Security;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
@Component
public class AlipayUtil {

    @Autowired
    private AlipayCertConfig config; // 从Nacos映射的配置类


    /**
     * 构建支付宝基本参数
     *
     * @return 对象
     * @throws AlipayApiException 支付异常
     */
    public AlipayClient buildAliClient() throws AlipayApiException {
        //构造client
        CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
        //设置网关地址
        certAlipayRequest.setServerUrl(config.getGatewayUrl());
        //设置应用Id
        certAlipayRequest.setAppId(config.getAppId());
        //设置应用私钥
        certAlipayRequest.setPrivateKey(config.getPrivateKey());
        //设置请求格式，固定值json
        certAlipayRequest.setFormat("json");
        //设置字符集
        certAlipayRequest.setCharset("UTF-8");
        //设置签名类型
        certAlipayRequest.setSignType(config.getSignType());
        //设置应用公钥证书路径
        certAlipayRequest.setCertPath(config.getAppCertPath());
        //设置支付宝公钥证书路径
        certAlipayRequest.setAlipayPublicCertPath(config.getAlipayPublicCertPath());
        //设置支付宝根证书路径
        certAlipayRequest.setRootCertPath(config.getRootCertPath());
        //构造client
        AlipayClient alipayClient = new DefaultAlipayClient(certAlipayRequest);
        return alipayClient;
    }

    public void setConfig() {
        AliPayApiConfig aliPayApiConfig;
        try {
            aliPayApiConfig = AliPayApiConfigKit.getApiConfig(config.getAppId());
        } catch (Exception e) {
            aliPayApiConfig = AliPayApiConfig.builder()
                    .setAppId(config.getAppId())
                    .setAliPayCertPath(config.getAlipayPublicCertPath())
                    .setAppCertPath(config.getAppCertPath())
                    .setAliPayRootCertPath(config.getRootCertPath())
                    .setCharset("UTF-8")
                    .setPrivateKey(config.getPrivateKey())
                    .setServiceUrl(config.getGatewayUrl())
                    .setSignType(config.getSignType())
                    // 普通公钥方式
                    .build();

        }
        AliPayApiConfigKit.setThreadLocalAliPayApiConfig(aliPayApiConfig);
    }

    /**
     * 校验签名 证书模式
     *
     * @param request 请求对象
     * @return 是 /否
     * @throws AlipayApiException 支付异常
     */
    public boolean rsaCheckV1(HttpServletRequest request) throws AlipayApiException {
        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
        //boolean AlipaySignature.rsaCertCheckV1(Map<String, String> params, String publicKeyCertPath, String charset,String signType)
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());//解决 no such provider: BC异常
        boolean flag = AlipaySignature.rsaCertCheckV1(params, config.getAlipayPublicCertPath(), "UTF-8", config.getSignType());
        return flag;
    }


}



