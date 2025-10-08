package com.ruoyi.common.core.utils.payment;


import com.ruoyi.common.core.domain.EncryptCertificate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Random;


@Slf4j
public class VechatPayV3Util {





    /**
     *
     * @param method 请求方法 post
     * @param canonicalUrl 请求地址
     * @param body 请求参数
     * @param merchantId 这里用的商户号
     * @param certSerialNo 商户证书序列号
     * @param keyPath 商户证书地址
     * @return
     * @throws Exception
     */
    public static String getToken(
            String method,
            String canonicalUrl,
            String body,
            String merchantId,
            String certSerialNo,
            String keyPath) throws Exception {
        String signStr = "";
        //获取32位随机字符串
        String nonceStr = getRandomString(32);
        //当前系统运行时间
        long timestamp = System.currentTimeMillis() / 1000;
        if (StringUtils.isEmpty(body)) {
            body = "";
        }
        //签名操作
        String message = buildMessage(method, canonicalUrl, timestamp, nonceStr, body);
        //签名操作
        String signature = sign(message.getBytes("utf-8"), keyPath);
        //组装参数
        signStr = "mchid=\"" + merchantId + "\",timestamp=\"" +  timestamp+ "\",nonce_str=\"" + nonceStr
                + "\",serial_no=\"" + certSerialNo + "\",signature=\"" + signature + "\"";

        return signStr;
    }

    public static String buildMessage(String method, String canonicalUrl, long timestamp, String nonceStr, String body) {
//		String canonicalUrl = url.encodedPath();
//		if (url.encodedQuery() != null) {
//			canonicalUrl += "?" + url.encodedQuery();
//		}
        return method + "\n" + canonicalUrl + "\n" + timestamp + "\n" + nonceStr + "\n" + body + "\n";
    }

    public static String sign(byte[] message, String keyPath) throws Exception {
        Signature sign = Signature.getInstance("SHA256withRSA");
        sign.initSign(getPrivateKey(keyPath));
        sign.update(message);
        return Base64.encodeBase64String(sign.sign());
    }

    /**
     * 微信支付-前端唤起支付参数-获取商户私钥
     *
     * @param filename 私钥文件路径  (required)
     * @return 私钥对象
     */
    public static PrivateKey getPrivateKey(String filename) throws IOException {
        log.error("签名 证书地址是 "+filename);
        Path path = Paths.get(filename);
        String content = new String(Files.readAllBytes(path), "utf-8");
        try {
            String privateKey = content.replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s+", "");
            //System.out.println("--------privateKey---------:"+privateKey);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(
                    new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey)));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("当前Java环境不支持RSA", e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("无效的密钥格式");
        }
    }
    /**
     * 获取随机位数的字符串
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }


    /**
     * 解密响应体.   得到微信平台证书公钥,解密后的字符串即为公钥字符串
     *

     * @return the string
     * @throws GeneralSecurityException the general security exception
     */
    public static String decryptResponseBody(EncryptCertificate tempWxpublicKeyData) {

        //tempWxpublicKeyData 这个对象就是取回来的公钥字符串转换的，有时回取回多条公钥，取时间最新的
        String apiV3Key="020c4cff39b34fd2bd60f0593eb0c7df";
        String associatedData=tempWxpublicKeyData.getAssociated_data();
        String nonce=tempWxpublicKeyData.getNonce();
        String ciphertext=tempWxpublicKeyData.getCiphertext();

        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

            SecretKeySpec key = new SecretKeySpec(apiV3Key.getBytes(StandardCharsets.UTF_8), "AES");

            GCMParameterSpec spec = new GCMParameterSpec(128, nonce.getBytes(StandardCharsets.UTF_8));

            cipher.init(Cipher.DECRYPT_MODE, key, spec);
            cipher.updateAAD(associatedData.getBytes(StandardCharsets.UTF_8));

            byte[] bytes;
            try {
                bytes = cipher.doFinal(Base64Utils.decodeFromString(ciphertext));
            } catch (GeneralSecurityException e) {
                throw new IllegalArgumentException(e);
            }
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new IllegalStateException(e);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new IllegalArgumentException(e);
        }
    }



    /**
     * 获取证书
     *
     * @param inputStream 证书文件
     * @return {@link X509Certificate} 获取证书
     */
    public static X509Certificate getCertificate(InputStream inputStream) {
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X509");
            X509Certificate cert = (X509Certificate) cf.generateCertificate(inputStream);
            cert.checkValidity();
            return cert;
        } catch (CertificateExpiredException e) {
            throw new RuntimeException("证书已过期", e);
        } catch (CertificateNotYetValidException e) {
            throw new RuntimeException("证书尚未生效", e);
        } catch (CertificateException e) {
            throw new RuntimeException("无效的证书", e);
        }
    }



    /**
     * 公钥加密   加密隐私信息数据
     *
     * @param data        待加密数据
     * @param certificate 平台公钥证书
     * @return 加密后的数据
     * @throws Exception 异常信息
     */
    public static String rsaEncryptOAEP(String data, X509Certificate certificate) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, certificate.getPublicKey());
            byte[] dataByte = data.getBytes(StandardCharsets.UTF_8);
            byte[] cipherData = cipher.doFinal(dataByte);
            // String s = new String(cipherData);

            return java.util.Base64.getEncoder().encodeToString(cipherData);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("当前Java环境不支持RSA v1.5/OAEP", e);
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException("无效的证书", e);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new IllegalBlockSizeException("加密原串的长度不能超过214字节");
        }
    }


}
