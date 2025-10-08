package com.ruoyi.common.core.utils.payment;


import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.wxpay.sdk.WXPayUtil;
import com.ruoyi.common.core.constant.WeChatConst;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.*;
import com.wechat.pay.contrib.apache.httpclient.cert.CertificatesManager;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 直连商户的下单工具类
 * @author liaozan8888@163.com
 */

public class PayUtil {

    private Logger logger = LoggerFactory.getLogger(getClass());




    /**
     * 统一下单，获取到 prepay_id ，然后获取签名，调起
     * @param total
     * @param description
     * @return
     * @throws Exception
     */
    public JSONObject requestwxChatPay(String extime, String orderSn, int total, String description) throws Exception {
        logger.info("开始获取证书===============");
        CloseableHttpClient httpClient = PayUtil.checkSign();
        //app下单
        HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/v3/pay/transactions/app");
        httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("Content-type", "application/json; charset=utf-8");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();

        //组合请求参数JSON格式
        ObjectNode rootNode = objectMapper.createObjectNode();
        rootNode.put("mchid", WeChatConst.MCH_ID)
                .put("appid", WeChatConst.APP_ID)
                .put("notify_url", WeChatConst.WECHAT_NOTIFY_URL)
                .put("description", description)
//                .put("time_expire",extime)
                .put("out_trade_no", orderSn);
                 rootNode.putObject("amount")
                // total：金额，以分为单位，假如是10块钱，那就要写 1000
                .put("total", total)
                .put("currency", "CNY");

        try {

            objectMapper.writeValue(bos, rootNode);
            httpPost.setEntity(new StringEntity(bos.toString("UTF-8"), "UTF-8"));
            //获取预支付ID
            CloseableHttpResponse response = httpClient.execute(httpPost);
            String bodyAsString = EntityUtils.toString(response.getEntity());
            logger.info("statusCode："+bodyAsString);

            //微信成功响应
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                //时间戳
                String timestamp = System.currentTimeMillis() / 1000 + "";
                //随机字符串
                String nonce = RandomUtil.randomString(32);
                StringBuilder builder = new StringBuilder();

                // Appid
                builder.append(WeChatConst.APP_ID).append("\n");
                // 时间戳
                builder.append(timestamp).append("\n");
                // 随机字符串
                builder.append(nonce).append("\n");
                JsonNode jsonNode = objectMapper.readTree(bodyAsString);
                // 预支付会话ID
                builder.append("prepay_id=").append(jsonNode.get("prepay_id").textValue()).append("\n");
                //获取签名
                String prepayId = jsonNode.get("prepay_id").textValue();

                String nonceStr = WXPayUtil.generateNonceStr();
                String paySign = PayUtil.appPaySign(String.valueOf(timestamp), nonceStr, prepayId);

                JSONObject jsonMap = new JSONObject();
                jsonMap.put("nonceStr", nonceStr);
                jsonMap.put("timeStamp", timestamp);
                jsonMap.put("prepayId", prepayId);
                jsonMap.put("paySign", paySign);
                jsonMap.put("appId", WeChatConst.APP_ID);
                jsonMap.put("partnerId", WeChatConst.MCH_ID);
                jsonMap.put("package", "Sign=WXPay");
                jsonMap.put("signType", "RSA");
                jsonMap.put("sign", "RSA");



                return jsonMap;//响应签名数据，前端拿着响应数据调起微信SDK
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 证书验证
     * 自动更新的签名验证器
     */
    public static CloseableHttpClient checkSign() throws IOException {
        //验签
        CloseableHttpClient httpClient = null;
        PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(new ByteArrayInputStream(WeChatConst.privateKey.getBytes("utf-8")));
        httpClient = WechatPayHttpClientBuilder.create()
                .withMerchant(WeChatConst.MCH_ID, WeChatConst.WECHAT_PAY_CERT_ZS, merchantPrivateKey)
                .withValidator(new WechatPay2Validator(PayUtil.getVerifier(WeChatConst.WECHAT_PAY_CERT_ZS)))
                .build();

        return httpClient;
    }

    /**
     * 保存微信平台证书
     */
    private static final ConcurrentHashMap<String, AutoUpdateCertificatesVerifier> verifierMap = new ConcurrentHashMap<>();

    /**
     * 功能描述:获取平台证书，自动更新
     * 注意：这个方法内置了平台证书的获取和返回值解密
     */


    public static AutoUpdateCertificatesVerifier getVerifier(String mchSerialNo) {
        AutoUpdateCertificatesVerifier verifier = null;
        if (verifierMap.isEmpty() || !verifierMap.containsKey(mchSerialNo)) {
            verifierMap.clear();
            try {
                //传入证书
                PrivateKey privateKey = getPrivateKey();
                //刷新
                PrivateKeySigner signer = new PrivateKeySigner(mchSerialNo, privateKey);
                WechatPay2Credentials credentials = new WechatPay2Credentials(WeChatConst.MCH_ID, signer);
                verifier = new AutoUpdateCertificatesVerifier(credentials
                        , WeChatConst.API_3KEY.getBytes("utf-8"));
                verifierMap.put(verifier.getValidCertificate().getSerialNumber()+"", verifier);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            verifier = verifierMap.get(mchSerialNo);
        }
        return verifier;
    }


    /**
     * 生成带签名支付信息
     *
     * @param timestamp 时间戳
     * @param nonceStr  随机数
     * @param prepayId  预付单
     * @return 支付信息
     * @throws Exception
     */
    public static String appPaySign(String timestamp, String nonceStr, String prepayId) throws Exception {
        //上传私钥
        PrivateKey privateKey = getPrivateKey();
        String signatureStr = Stream.of(WeChatConst.APP_ID, timestamp, nonceStr, prepayId)
                .collect(Collectors.joining("\n", "", "\n"));
        Signature sign = Signature.getInstance("SHA256withRSA");
        sign.initSign(privateKey);
        sign.update(signatureStr.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(sign.sign());
    }

    /**
     * 生成带签名支付信息
     *
     * @param timestamp 时间戳
     * @param nonceStr  随机数
     * @param prepayId  预付单
     * @return 支付信息
     * @throws Exception
     */
    public static String jsApiPaySign(String timestamp, String nonceStr, String prepayId) throws Exception {
        //上传私钥
        PrivateKey privateKey = getPrivateKey();
        String signatureStr = Stream.of(WeChatConst.APP_ID, timestamp, nonceStr, "prepay_id="+prepayId)
                .collect(Collectors.joining("\n", "", "\n"));
        Signature sign = Signature.getInstance("SHA256withRSA");
        sign.initSign(privateKey);
        sign.update(signatureStr.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(sign.sign());
    }


    /**
     * 获取私钥。
     * 证书路径 本地使用如： D:\\微信平台证书工具\\7.9\\apiclient_key.pem
     * 证书路径 线上使用如： /usr/apiclient_key.pem
     * String filename 私钥文件路径  (required)
     * @return 私钥对象
     */
    public static PrivateKey getPrivateKey() throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(WeChatConst.APICLIENT_PAY_KEY_PATH)), "utf-8");
        try {
            String privateKey = content.replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s+", "");

            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(
                    new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey)));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("当前Java环境不支持RSA", e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("无效的密钥格式");
        }
    }



    /**
     /**
     * 计算签名
     *
     * @param message
     * @param yourPrivateKey
     * @return
     */
    private String sign(byte[] message, PrivateKey yourPrivateKey) {
        try {
            Signature sign = Signature.getInstance("SHA256withRSA");
            sign.initSign(yourPrivateKey);
            sign.update(message);
            return Base64.getEncoder().encodeToString(sign.sign());
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 回调验证签名
     *
     * @param serial
     * @param message
     * @param signatrue
     * @return
     */
    public static boolean signVerify(String serial, String message, String signatrue) {
        CertificatesManager certificatesManager = null;

        Verifier verifier = null;
        try {
            PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(new ByteArrayInputStream(WeChatConst.privateKey.getBytes("utf-8")));
            // 获取证书管理器实例
            certificatesManager = CertificatesManager.getInstance();
            // 向证书管理器增加需要自动更新平台证书的商户信息
            certificatesManager.putMerchant(WeChatConst.MCH_ID, new WechatPay2Credentials(WeChatConst.MCH_ID,
                            new PrivateKeySigner(WeChatConst.WECHAT_PAY_CERT_ZS, merchantPrivateKey)),
                    WeChatConst.API_3KEY.getBytes(StandardCharsets.UTF_8));

            return verifier.verify(serial, message.getBytes("utf-8"), signatrue);
        } catch (Exception e) {

        }
        return false;
    }






}
