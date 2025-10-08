package com.ruoyi.common.core.utils;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Mobile3MetaUtil {

    //身份证手机三要素验证
    public static String checkIdCard(String idCard,String mobile,String name){
        String url = "https://mobilecert.market.alicloudapi.com";
        String path = "/mobile3Meta";
        String method = "GET";
        String appcode = "8fdda2643a1544a391a9c65795ca0bfa";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("identifyNum", idCard);
        querys.put("mobile", mobile);
        querys.put("userName", name);

        try {

            HttpResponse response = AliHttpUtils.doGet(url, path, method,headers, querys);
            System.out.println(response.toString());
            //获取response的body
            String reponseExt = EntityUtils.toString(response.getEntity(), "UTF-8");
            return reponseExt;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }




}
