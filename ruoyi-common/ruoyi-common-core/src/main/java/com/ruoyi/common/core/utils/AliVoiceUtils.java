package com.ruoyi.common.core.utils;


import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dyvmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dyvmsapi20170525.models.SingleCallByVoiceRequest;
import com.aliyun.sdk.service.dyvmsapi20170525.models.SingleCallByVoiceResponse;
import com.google.gson.Gson;
import darabonba.core.client.ClientOverrideConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class AliVoiceUtils {

    private String accessKeyId;
    private String accessKeySecret;

    public  String sendVoice(String phone,String voiceCode) throws ExecutionException, InterruptedException {

        StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                .accessKeyId(accessKeyId)
                .accessKeySecret(accessKeySecret)
                .build());

        AsyncClient client = AsyncClient.builder()
                .region("cn-shenzhen") // Region ID
                .credentialsProvider(provider)

                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                // Endpoint 请参考 https://api.aliyun.com/product/Dyvmsapi
                                .setEndpointOverride("dyvmsapi.aliyuncs.com")
                        //.setConnectTimeout(Duration.ofSeconds(30))
                )
                .build();

        SingleCallByVoiceRequest smartCallRequest = SingleCallByVoiceRequest.builder()
                .calledShowNumber("0571000014034")
                .calledNumber(phone)
                .voiceCode(voiceCode)
                .build();

        CompletableFuture<SingleCallByVoiceResponse> response = client.singleCallByVoice(smartCallRequest);
        SingleCallByVoiceResponse resp = response.get();
        System.out.println(new Gson().toJson(resp));
        client.close();
        return new Gson().toJson(resp);

    }
}
