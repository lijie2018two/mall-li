package com.ruoyi.common.security.interceptor;

import com.alibaba.fastjson2.JSONObject;

import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.security.auth.AuthUtil;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.model.LoginAppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@WebFilter( urlPatterns = "/*")
@Slf4j
public class LoggingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);
        //请求时的系统时间
        LocalTime time1 = LocalTime.now();
        try {
            chain.doFilter(requestWrapper, responseWrapper);
        } finally {
            String appToken = SecurityUtils.getAppToken();
            String token = SecurityUtils.getToken();
            String businessToken = SecurityUtils.getBusinessToken();
            LoginAppUser loginUser = AuthUtil.authLogic.appTokenSerice.getLoginUser(appToken);
            LoginAppUser businessUser = AuthUtil.authLogic.appTokenSerice.getLoginUser(businessToken);
            String method = ((HttpServletRequest) request).getMethod();
            //响应时的系统时间
            LocalTime time2 = LocalTime.now();
            //计算请求响应耗时
            Duration total = Duration.between(time1, time2);
            Long userid = 0l;
            if(loginUser != null){
                userid = loginUser.getUserid();
            }
            if(businessUser != null){
                userid = businessUser.getUserid();
            }
            Map<String, String> map =new HashMap<>();
            Enumeration<String> er = request.getParameterNames();
            while (er.hasMoreElements()) {
                String name = (String) er.nextElement();
                String value = request.getParameter(name);
                map.put(name, value);
            }
            String jsonPostParam = new JSONObject(map).toJSONString();
            String requestBody = new String(requestWrapper.getContentAsByteArray());
            String responseBody = new String(responseWrapper.getContentAsByteArray());
            requestBody = StringUtils.isNotEmpty(requestBody) ? requestBody : jsonPostParam;
            if (StringUtils.isNotEmpty(appToken)){
                log.info("用户{}访问接口URL:{}\r\n X-HYYT-API-TOKEN:{}\r\n 请求参数request：{},总耗时{}ms\r\n 响应参数response：{}", userid, ((HttpServletRequest) request).getRequestURI(),appToken,requestBody,total.toMillis(),responseBody);
            }
            else if(StringUtils.isNotEmpty(token)){
                log.info("用户{}访问接口URL:{}\r\n Authorization:{}\r\n 请求参数request：{},总耗时{}ms\r\n 响应参数response：{}", userid, ((HttpServletRequest) request).getRequestURI(),token,requestBody,total.toMillis(),responseBody);
            }
            else if(StringUtils.isNotEmpty(businessToken)){
                log.info("用户{}访问接口URL:{}\r\n X-BUSINESS-TOKEN:{}\r\n 请求参数request：{},总耗时{}ms\r\n 响应参数response：{}", userid, ((HttpServletRequest) request).getRequestURI(),businessToken,requestBody,total.toMillis(),responseBody);
            }
            responseWrapper.copyBodyToResponse();
        }

    }

}

