package com.ccairbag.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "visa")
public class VisapayConfig {
    private String stripeApiKey;
    private String trackingApi;
    private String apiKey;
    private String sharedSecret;

}