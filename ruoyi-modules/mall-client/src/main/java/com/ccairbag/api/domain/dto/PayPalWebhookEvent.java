package com.ccairbag.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class PayPalWebhookEvent {
    private String id;

    @JsonProperty("event_version")
    private String eventVersion;

    @JsonProperty("create_time")
    private String createTime;

    @JsonProperty("resource_type")
    private String resourceType;

    @JsonProperty("event_type")
    private String eventType;

    private String summary;
    private PayPalOrderResource resource;

    @Data
    public static class PayPalOrderResource {
        @JsonProperty("create_time")
        private String createTime;

        @JsonProperty("purchase_units")
        private List<PurchaseUnit> purchaseUnits;

        private List<Link> links;
        private String id;

        @JsonProperty("payment_source")
        private PaymentSource paymentSource;

        private String intent;
        private Payer payer;
        private String status;
    }

    @Data
    public static class PurchaseUnit {
        @JsonProperty("reference_id")
        private String referenceId;
        private Amount amount;
        private Payee payee;
        private String description;
    }

    @Data
    public static class Amount {
        @JsonProperty("currency_code")
        private String currencyCode;
        private String value;
    }

    @Data
    public static class Payee {
        @JsonProperty("email_address")
        private String emailAddress;

        @JsonProperty("merchant_id")
        private String merchantId;
    }

    @Data
    public static class Link {
        private String href;
        private String rel;
        private String method;
    }

    @Data
    public static class PaymentSource {
        private PayPalAccount paypal;
    }

    @Data
    public static class PayPalAccount {
        @JsonProperty("email_address")
        private String emailAddress;

        @JsonProperty("account_id")
        private String accountId;
        private Name name;
        private Address address;
    }

    @Data
    public static class Payer {
        @JsonProperty("email_address")
        private String emailAddress;

        @JsonProperty("payer_id")
        private String payerId;
        private Name name;
        private Address address;
    }

    @Data
    public static class Name {
        @JsonProperty("given_name")
        private String givenName;
        private String surname;
    }

    @Data
    public static class Address {
        @JsonProperty("country_code")
        private String countryCode;
    }
}