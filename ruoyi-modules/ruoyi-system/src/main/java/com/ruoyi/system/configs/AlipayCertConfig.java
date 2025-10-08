package com.ruoyi.system.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "alipay")
public class AlipayCertConfig {
    private String appId;
    private String privateKey;
    private String appCertPath;
    private String alipayPublicCertPath;
    private String rootCertPath;
    private String notifyUrl;
    private String returnUrl;
    private String signType;
    private String charset;
    private String gatewayUrl;
}