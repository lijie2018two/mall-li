package com.ruoyi.system.api.domain;
/**
 * 描述: 凭证 </br>
 * 发布版本：V1.0 </br>
 */
public class WechatTokenEntity {
    /**
     * 接口访问凭证֤
     */
    private String accessToken;
    /**
     * 接口访问凭证֤，刷新
     */
    private String refreshToken;
    /**
     * 凭证有效期单位：second
     */
    private int expiresIn;
    /**
     * 授权用户唯一标识
     */
    private String openid;
    /**
     * 微信用户唯一标识
     */
    private String unionId;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}


