package com.ruoyi.auth.utils;


import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.JSONObject;

import com.ruoyi.common.core.domain.WechatTokenEntity;
import com.ruoyi.common.core.domain.WechatUserEntity;
import com.ruoyi.common.core.utils.HttpUtils;
import com.ruoyi.common.redis.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

public class WechatUtils {
    private final static Logger log = LoggerFactory.getLogger(WechatUtils.class);



    // 凭证获取（GET）
    public final static String tokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    //userinfo
    public final static String userInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    /**
     * 获取接口访问凭证
     *
     * @param code app授权后传回
     * @return
     */
    public static WechatTokenEntity getToken(String code) {
        WechatTokenEntity token = new WechatTokenEntity();

        String requestUrl = tokenUrl.replace("APPID", "sdsf").replace("SECRET", "dsfsdf").replace("CODE", code);
        // 发起GET请求获取凭证
        String str = HttpUtils.sendGet(requestUrl,  null);
        JSONObject jsonObject = JSONObject.parseObject(str);
        if (null != jsonObject) {
            try {
                token.setUnionId(jsonObject.getString("unionid"));
                token.setOpenid(jsonObject.getString("openid"));
                token.setAccessToken(jsonObject.getString("access_token"));
                token.setRefreshToken(jsonObject.getString("refresh_token"));
                token.setExpiresIn(Integer.parseInt(jsonObject.getString("expires_in")));

            } catch (JSONException e) {
                token = null;
                // 获取token失败
                log.error("获取token失败 errcode:{} errmsg:{}", Integer.parseInt(jsonObject.getString("errcode")) , jsonObject.getString("errmsg"));
            }
        }
        return token;
    }

    public static void main(String args[]) {
        // 获取接口访问凭证
//        我这里是直接从前端传入accessToken，openid，所以可以直接拿到，下面是通过code生成
        String accessToken = getToken("code").getAccessToken();
        String openid = getToken("code").getOpenid();



//        /**
//         * 获取用户信息
//         */
        WechatUserEntity user = getUserInfo(accessToken, openid);
        //做这个测试的时候可以先关注，或者取消关注，控制台会打印出来此用户的openid
        System.out.println("OpenID：" + user.getOpenId());
        System.out.println("关注状态：" + user.getSubscribe());
        System.out.println("关注时间：" + user.getSubscribeTime());
        System.out.println("昵称：" + user.getNickname());
        System.out.println("性别：" + user.getSex());
        System.out.println("国家：" + user.getCountry());
        System.out.println("省份：" + user.getProvince());
        System.out.println("城市：" + user.getCity());
        System.out.println("语言：" + user.getLanguage());
        System.out.println("头像：" + user.getHeadImgUrl());
        getToken("02315v0w35TA603fsF0w3Q0fov415v0B");


    }

    /**
     * 获取用户信息
     *
     * @param accessToken 接口访问凭证
     * @param openId      用户标识
     * @return WeixinUserInfo
     */
    public static WechatUserEntity getUserInfo(String accessToken, String openId) {

        WechatUserEntity wechatUserEntity = null;
        // 拼接请求地址
        String requestUrl = userInfoUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        // 获取用户信息
        String str = HttpUtils.sendGet(requestUrl,  null);
        JSONObject jsonObject = JSONObject.parseObject(str);
        if (null != jsonObject) {
            try {
                wechatUserEntity = new WechatUserEntity();
                // 用户的标识
                wechatUserEntity.setOpenId(jsonObject.getString("openid"));
                wechatUserEntity.setUnionId(jsonObject.getString("unionid"));
                // 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
//                wechatUserEntity.setSubscribe(jsonObject.getInt("subscribe"));
                // 用户关注时间
//                wechatUserEntity.setSubscribeTime(jsonObject.getString("subscribe_time"));
                // 昵称
                wechatUserEntity.setNickname(jsonObject.getString("nickname"));
                // 用户的性别（1是男性，2是女性，0是未知）
//                wechatUserEntity.setSex(jsonObject.getInt("sex"));
                // 用户所在国家
//                wechatUserEntity.setCountry(jsonObject.getString("country"));
                // 用户所在省份
//                wechatUserEntity.setProvince(jsonObject.getString("province"));
                // 用户所在城市
//                wechatUserEntity.setCity(jsonObject.getString("city"));
                // 用户的语言，简体中文为zh_CN
                wechatUserEntity.setLanguage(jsonObject.getString("language"));
                // 用户头像
                wechatUserEntity.setHeadImgUrl(jsonObject.getString("headimgurl"));
            } catch (Exception e) {
                int errorCode = Integer.parseInt(jsonObject.getString("errcode"));
                String errorMsg = jsonObject.getString("errmsg");
//                System.err.printf("获取用户信息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        return wechatUserEntity;
    }


}

