package com.ruoyi.common.core.domain;

import lombok.Data;

@Data
public class EncryptCertificate {
    public String algorithm;
    public String associated_data;
    public String ciphertext;
    public String nonce;
}
