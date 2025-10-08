package com.ruoyi.common.core.utils;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AesUtil {
    static final int KEY_LENGTH_BYTE = 32;
    static final int TAG_LENGTH_BIT = 128;
    private final byte[] aesKey;

    public AesUtil(byte[] key) {
        if (key.length != KEY_LENGTH_BYTE) {
            throw new IllegalArgumentException("无效的ApiV3Key，长度必须为32个字节");
        }
        this.aesKey = key;
    }
    public String decryptToString(byte[] associatedData, byte[] nonce, String ciphertext) throws GeneralSecurityException, IOException {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

            SecretKeySpec key = new SecretKeySpec(aesKey, "AES");
            GCMParameterSpec spec = new GCMParameterSpec(TAG_LENGTH_BIT, nonce);

            cipher.init(Cipher.DECRYPT_MODE, key, spec);
            cipher.updateAAD(associatedData);

            return new String(cipher.doFinal(Base64.getDecoder().decode(ciphertext)), "utf-8");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new IllegalStateException(e);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new IllegalArgumentException(e);
        }
    }

//    /**
//     * 回调验签
//     * https://pay.weixin.qq.com/wiki/doc/apiv3/wechatpay/wechatpay4_1.shtml
//     * @param wechatpaySignature 回调head头部
//     * @param wechatpayTimestamp 回调head头部
//     * @param wechatpayNonce 回调head头部
//     * @param body 请求数据
//     * @return
//     */
//    public static boolean  responseSignVerify( String wechatpaySignature, String wechatpayTimestamp, String wechatpayNonce, String body,String serialNo,String xjlMchId,String xjlSerialNo,String v3keyPath,String v3Key) {
//        FileInputStream fileInputStream = null;
//        try {
//            //获取签名
//            String signatureStr = buildMessage(wechatpayTimestamp, wechatpayNonce, body);
//            Signature signer = Signature.getInstance("SHA256withRSA");
//            if (CERTIFICATE_MAP.isEmpty() || !CERTIFICATE_MAP.containsKey(serialNo)) {
//                //获取证书
//                certificates(xjlMchId,xjlSerialNo,v3keyPath,v3Key);
//            }
//            signer.initVerify(CERTIFICATE_MAP.get(serialNo));
//            signer.update(signatureStr.getBytes(StandardCharsets.UTF_8));
//            return signer.verify(java.util.Base64.getDecoder().decode(wechatpaySignature));
//        } catch (Exception e ) {
//            e.printStackTrace();
//        } finally {
//            if (fileInputStream != null) {
//                try {
//                    fileInputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return false;
//    }

    /**
     * 回调验签-构建签名数据
     * @param
     * @return
     */
    public static String buildMessage(String wechatpayTimestamp, String wechatpayNonce, String body) {
        return Stream.of(wechatpayTimestamp, wechatpayNonce, body)
                .collect(Collectors.joining("\n", "", "\n"));
    }
}
