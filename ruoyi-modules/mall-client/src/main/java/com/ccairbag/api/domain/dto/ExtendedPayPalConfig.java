package com.ccairbag.api.domain.dto;

import com.ijpay.paypal.PayPalApiConfig;

public class ExtendedPayPalConfig extends PayPalApiConfig {
    private String webhookId;

    public String getWebhookId() {
        return webhookId;
    }

    public ExtendedPayPalConfig setWebhookId(String webhookId) {
        this.webhookId = webhookId;
        return this;
    }

    // 保留父类所有功能
    public static ExtendedPayPalConfig builder() {
        return new ExtendedPayPalConfig();
    }
}
