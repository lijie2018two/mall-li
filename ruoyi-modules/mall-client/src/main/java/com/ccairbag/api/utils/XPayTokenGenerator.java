package com.ccairbag.api.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class XPayTokenGenerator {

    /**
     * 生成X-Pay Token
     * @param apiKey Visa开发者平台的API Key
     * @param sharedSecret 从Visa平台获取的Shared Secret（解密后的值）
     * @param resourcePath API请求的资源路径（如：/visadirect/fundstransfer/v1/pushfundstransactions）
     * @param queryParams API请求的查询参数（包含apiKey等，如：{ "apikey": "your-api-key", "param1": "value1" }）
     * @return 生成的X-Pay Token
     * @throws Exception 加密过程中可能抛出的异常
     */
    public static String generateXPayToken(String apiKey, String sharedSecret, String resourcePath, Map<String, String> queryParams) throws Exception {
        // 1. 生成当前时间戳（UTC时间，格式：yyyy-MM-dd'T'HH:mm:ss'Z'）
        String timestamp = java.time.ZonedDateTime.now(java.time.ZoneOffset.UTC)
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));

        // 2. 拼接查询参数（按key升序排序，格式：key1=value1&key2=value2）
        String queryString = queryParams.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));

        // 3. 构建待加密的原始字符串（格式严格遵循Visa要求）
        // 格式：timestamp + resourcePath + queryString（注意：queryString为空时也需保留占位）
        String rawData = timestamp + resourcePath + (queryString.isEmpty() ? "" : "?" + queryString);

        // 4. 使用Shared Secret对原始字符串进行HMAC-SHA256加密
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(sharedSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] encryptedBytes = mac.doFinal(rawData.getBytes(StandardCharsets.UTF_8));

        // 5. 将加密后的字节数组转换为Base64编码，即为X-Pay Token
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // 测试示例
    public static void main(String[] args) {
        try {

            String apiKey = "SHU6O6R36JBHVALHVNZW21P8S2gJhrpS-k10zJw1HUcjcZc48";
            String sharedSecret = "ZoYOVMFtD#hH4A{+#nvW+dhep+6m$TM#3}69m26C";

            String resourcePath = "/visadirect/fundstransfer/v1/pushfundstransactions"; // API路径
            Map<String, String> queryParams = new HashMap<>();
            queryParams.put("apikey", apiKey); // 必须包含apikey
            queryParams.put("countryCode", "US"); // 其他查询参数（如有）

            // 生成X-Pay Token
            String xPayToken = generateXPayToken(apiKey, sharedSecret, resourcePath, queryParams);
            System.out.println("生成的X-Pay Token: " + xPayToken);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}