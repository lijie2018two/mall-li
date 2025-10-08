package com.ccairbag.api.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;
import java.util.TreeMap;

/**
 * X-Pay-Token 生成工具类
 */
public class XPayTokenUtil {

    private final String apiKey;
    private final String sharedSecret;

    public XPayTokenUtil(String apiKey, String sharedSecret) {
        this.apiKey = apiKey;
        this.sharedSecret = sharedSecret;
    }

    /**
     * 生成 x-pay-token
     *
     * @param params 请求参数
     * @return x-pay-token
     */
    public String generateXPayToken(Map<String, String> params) {
        // 1. 对参数进行排序
        Map<String, String> sortedParams = new TreeMap<>(params);

        // 2. 拼接 key=value 形式的字符串
        StringBuilder paramStrBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : sortedParams.entrySet()) {
            if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                paramStrBuilder.append(entry.getKey()).append("=").append(entry.getValue());
            }
        }

        // 3. 拼接签名字符串
        String dataToSign = apiKey + paramStrBuilder.toString() + sharedSecret;

        // 4. 使用 HMAC-SHA256 算法生成签名
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(sharedSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] signatureBytes = mac.doFinal(dataToSign.getBytes(StandardCharsets.UTF_8));
            String signature = Base64.getEncoder().encodeToString(signatureBytes);

            // 5. 返回 x-pay-token 格式
            return "xv2:" + apiKey + ":" + signature;
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to generate X-Pay-Token", e);
        }
    }
}

