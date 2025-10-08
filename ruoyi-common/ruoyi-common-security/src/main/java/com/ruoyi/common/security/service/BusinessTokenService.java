package com.ruoyi.common.security.service;

import com.ruoyi.common.core.constant.CacheConstants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.TokenConstants;
import com.ruoyi.common.core.utils.JwtUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.ip.IpUtils;
import com.ruoyi.common.core.utils.uuid.IdUtils;
import com.ruoyi.common.redis.service.RedisService;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.model.LoginAppUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
public class BusinessTokenService {
    private static final long serialVersionUID = -3301605591108950415L;


    static final String AUDIENCE_UNKNOWN = "unknown";
    static final String AUDIENCE_WEB = "web";
    static final String AUDIENCE_MOBILE = "mobile";
    static final String AUDIENCE_TABLET = "tablet";



    private final static String ACCESS_TOKEN = CacheConstants.BUSINESS_LOGIN_TOKEN_KEY;

    @Autowired
    private RedisService redisService;

    //    @Value("${jwt.secret}")
//    private String secret;
    private static String secret = TokenConstants.APP_SECRET;

    private final static Integer MILLIS_MINUTE_TEN = 1000*60*60*24;

    @Value("2592000")
    private Long expiration;

    public String getAudienceFromToken(String token) {
        return getClaimFromToken(token, Claims::getAudience);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }


    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    /**
     * 创建令牌
     */
    public Map<String, Object> createToken(LoginAppUser loginUser)
    {

//        Long userId = loginUser.getTlmUser().getId();
////        String token = IdUtils.fastUUID() + userId;
//        String token = IdUtils.fastUUID() +"-"+ userId+"-"+System.currentTimeMillis();
//        String userName = loginUser.getTlmUser().getName();
//        loginUser.setToken(token);
//        loginUser.setUserid(userId);
//        loginUser.setUsername(userName);
//        loginUser.setIpaddr(IpUtils.getIpAddr());
//        refreshToken(loginUser);
//
//        // Jwt存储信息
//        Map<String, Object> claimsMap = new HashMap<String, Object>();
//        claimsMap.put(SecurityConstants.USER_KEY, token);
//        claimsMap.put(SecurityConstants.APP_USER_ID, loginUser.getTlmUser().getPhone());
//        claimsMap.put(SecurityConstants.DETAILS_USERNAME,userName);
        // 接口返回信息
        Map<String, Object> rspMap = new HashMap<String, Object>();
//        rspMap.put("token", JwtUtils.createAppToken(claimsMap));
//        rspMap.put("expires_in", expiration);
        return rspMap;
    }

    private Boolean ignoreTokenExpiration(String token) {
        String audience = getAudienceFromToken(token);
        return (AUDIENCE_TABLET.equals(audience) || AUDIENCE_MOBILE.equals(audience));
    }

    /**
     * 验证令牌有效期，相差不足24小时，自动刷新缓存
     *
     * @param loginUser
     */
    public void verifyToken(LoginAppUser loginUser)
    {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        refreshToken(loginUser);
        if (expireTime - currentTime <=  MILLIS_MINUTE_TEN)
            if (expireTime - currentTime <= 1000*60 )
            {
                refreshToken(loginUser);
            }
        refreshToken(loginUser);
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    public void refreshToken(LoginAppUser loginUser)
    {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expiration * 1000);
        // 根据uuid将loginUser缓存
        String userKey = getTokenKey(loginUser.getToken());
        redisService.setCacheObject(userKey, loginUser, expiration, TimeUnit.SECONDS);
    }

    private String getTokenKey(String token)
    {
        return ACCESS_TOKEN + token;
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginAppUser getLoginUser(String token)
    {
        LoginAppUser user = null;
        try
        {
            if (StringUtils.isNotEmpty(token))
            {
                String userkey = JwtUtils.getAppUserKey(token);
                user = redisService.getCacheObject(getTokenKey(userkey));
                return user;
            }
        }
        catch (Exception e)
        {
        }
        return user;
    }
    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginAppUser getLoginUser(HttpServletRequest request)
    {
        // 获取请求携带的令牌
        String token = SecurityUtils.getToken(request);
        return getLoginUser(token);
    }


    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + expiration * 1000);
    }
}
