package com.ruoyi.gateway.filter;

import com.ruoyi.common.core.constant.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import com.ruoyi.common.core.utils.JwtUtils;
import com.ruoyi.common.core.utils.ServletUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.redis.service.RedisService;
import com.ruoyi.gateway.config.properties.IgnoreWhiteProperties;
import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;

/**
 * 网关鉴权
 * 
 * @author ruoyi
 */
@Component
public class AuthFilter implements GlobalFilter, Ordered
{
    private static final Logger log = LoggerFactory.getLogger(AuthFilter.class);

    // 排除过滤的 uri 地址，nacos自行添加
    @Autowired
    private IgnoreWhiteProperties ignoreWhite;

    @Autowired
    private RedisService redisService;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain)
    {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpRequest.Builder mutate = request.mutate();

        String url = request.getURI().getPath();
        // 跳过不需要验证的路径
        if (StringUtils.matches(url, ignoreWhite.getWhites()))
        {
            return chain.filter(exchange);
        }
        String token = getToken(request);
        String appToken = getAppToken(request);
        String businessToken = getBusinessToken(request);
        if(StringUtils.isNotEmpty(appToken)){
            Claims appClaims = null;
            try {
                appClaims = JwtUtils.parseAppToken(appToken);
                if (appClaims == null)
                {
                    return unauthorizedResponse(exchange, "令牌已过期或验证不正确！");
                }
            }
            catch (RuntimeException e){
                log.error("令牌验证错误：{}",e.getMessage());
                return unauthorizedResponse(exchange, "令牌已过期或验证不正确！");
            }

            String userkey = JwtUtils.getUserKey(appClaims);
            boolean islogin = redisService.hasKey(getAppTokenKey(userkey));
            if (!islogin)
            {
                return unauthorizedResponse(exchange, "登录状态已过期");
            }
            String openId = JwtUtils.getValue(appClaims,SecurityConstants.APP_USER_ID);
            String username = JwtUtils.getUserName(appClaims);
            if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(username))
            {
                return unauthorizedResponse(exchange, "令牌验证失败");
            }

            // 设置用户信息到请求
            addHeader(mutate, SecurityConstants.USER_KEY, userkey);
            addHeader(mutate, SecurityConstants.DETAILS_USERNAME, username);
            addHeader(mutate, SecurityConstants.APP_OPEN_ID, openId);

//            addHeader()
            // 内部请求来源参数清除
            removeHeader(mutate, SecurityConstants.FROM_SOURCE);
            return chain.filter(exchange.mutate().request(mutate.build()).build());
        }

        if(StringUtils.isNotEmpty(businessToken)){
            Claims appClaims = null;
            try {
                appClaims = JwtUtils.parseAppToken(businessToken);
                if (appClaims == null)
                {
                    return unauthorizedResponse(exchange, "令牌已过期或验证不正确！");
                }
            }
            catch (RuntimeException e){
                log.error("令牌验证错误：{}",e.getMessage());
                return unauthorizedResponse(exchange, "令牌已过期或验证不正确！");
            }

            String userkey = JwtUtils.getUserKey(appClaims);
            boolean islogin = redisService.hasKey(getBusinessTokenKey(userkey));
            if (!islogin)
            {
                return unauthorizedResponse(exchange, "登录状态已过期");
            }
            String businessId = JwtUtils.getValue(appClaims,SecurityConstants.APP_USER_ID);
            String username = JwtUtils.getUserName(appClaims);
            if (StringUtils.isEmpty(businessId) || StringUtils.isEmpty(username))
            {
                return unauthorizedResponse(exchange, "令牌验证失败");
            }

            // 设置用户信息到请求
            addHeader(mutate, SecurityConstants.USER_KEY, userkey);
            addHeader(mutate, SecurityConstants.DETAILS_USERNAME, username);
            addHeader(mutate, SecurityConstants.APP_OPEN_ID, businessId);

//            addHeader()
            // 内部请求来源参数清除
            removeHeader(mutate, SecurityConstants.FROM_SOURCE);
            return chain.filter(exchange.mutate().request(mutate.build()).build());
        }

        if (StringUtils.isEmpty(token)) {
            return unauthorizedResponse(exchange, "请先登陆！");
        }
        Claims claims = JwtUtils.parseToken(token);
        if (claims == null)
        {
            return unauthorizedResponse(exchange, "令牌已过期或验证不正确！");
        }
        String userkey = JwtUtils.getUserKey(claims);
        boolean islogin = redisService.hasKey(getTokenKey(userkey));
        if (!islogin)
        {
            return unauthorizedResponse(exchange, "登录状态已过期");
        }
        String userid = JwtUtils.getUserId(claims);
        String username = JwtUtils.getUserName(claims);
        if (StringUtils.isEmpty(userid) || StringUtils.isEmpty(username))
        {
            return unauthorizedResponse(exchange, "令牌验证失败");
        }

        // 设置用户信息到请求
        addHeader(mutate, SecurityConstants.USER_KEY, userkey);
        addHeader(mutate, SecurityConstants.DETAILS_USER_ID, userid);
        addHeader(mutate, SecurityConstants.DETAILS_USERNAME, username);
        // 内部请求来源参数清除
        removeHeader(mutate, SecurityConstants.FROM_SOURCE);
        return chain.filter(exchange.mutate().request(mutate.build()).build());
    }

    private void addHeader(ServerHttpRequest.Builder mutate, String name, Object value)
    {
        if (value == null)
        {
            return;
        }
        String valueStr = value.toString();
        String valueEncode = ServletUtils.urlEncode(valueStr);
        mutate.header(name, valueEncode);
    }

    private void removeHeader(ServerHttpRequest.Builder mutate, String name)
    {
        mutate.headers(httpHeaders -> httpHeaders.remove(name)).build();
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String msg)
    {
        log.error("[鉴权异常处理]请求路径:{}", exchange.getRequest().getPath());
        return ServletUtils.webFluxResponseWriter(exchange.getResponse(), msg, HttpStatus.UNAUTHORIZED);
    }


    private String getAppTokenKey(String token)
    {
        return CacheConstants.APP_LOGIN_TOKEN_KEY + token;
    }

    private String getAppToken(ServerHttpRequest request)
    {
        String token = request.getHeaders().getFirst(ApiConst.API_REQUEST_HEADER_TOKEN);
        return token;
    }

    private String getBusinessTokenKey(String token)
    {
        return CacheConstants.BUSINESS_LOGIN_TOKEN_KEY + token;
    }

    private String getBusinessToken(ServerHttpRequest request)
    {
        String token = request.getHeaders().getFirst(ApiConst.API_BUSINESS_HEADER_TOKEN);
        return token;
    }

    /**
     * 获取缓存key
     */
    private String getTokenKey(String token)
    {
        return CacheConstants.LOGIN_TOKEN_KEY + token;
    }

    /**
     * 获取请求token
     */
    private String getToken(ServerHttpRequest request)
    {
        String token = request.getHeaders().getFirst(TokenConstants.AUTHENTICATION);
        // 如果前端设置了令牌前缀，则裁剪掉前缀
        if (StringUtils.isNotEmpty(token) && token.startsWith(TokenConstants.PREFIX))
        {
            token = token.replaceFirst(TokenConstants.PREFIX, StringUtils.EMPTY);
        }
        return token;
    }

    @Override
    public int getOrder()
    {
        return -200;
    }
}