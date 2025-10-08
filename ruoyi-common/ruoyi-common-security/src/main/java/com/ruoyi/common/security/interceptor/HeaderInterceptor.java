package com.ruoyi.common.security.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.api.model.LoginAppUser;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.context.SecurityContextHolder;
import com.ruoyi.common.core.utils.ServletUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.security.auth.AuthUtil;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.model.LoginUser;

/**
 * 自定义请求头拦截器，将Header数据封装到线程变量中方便获取
 * 注意：此拦截器会同时验证当前用户有效期自动刷新有效期
 *
 * @author ruoyi
 */
public class HeaderInterceptor implements AsyncHandlerInterceptor
{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        if (!(handler instanceof HandlerMethod))
        {
            return true;
        }
        String userId = ServletUtils.getHeader(request, SecurityConstants.DETAILS_USER_ID);
        if (StringUtils.isEmpty(userId)) {
            userId = ServletUtils.getHeader(request, SecurityConstants.APP_USER_ID);
        }
        SecurityContextHolder.setUserId(ServletUtils.getHeader(request, SecurityConstants.DETAILS_USER_ID));
        SecurityContextHolder.setUserName(ServletUtils.getHeader(request, SecurityConstants.DETAILS_USERNAME));
        SecurityContextHolder.setUserKey(ServletUtils.getHeader(request, SecurityConstants.USER_KEY));

        String token = SecurityUtils.getToken();
        String appToken = SecurityUtils.getAppToken();

        if (StringUtils.isNotEmpty(token))
        {
            LoginUser loginUser = AuthUtil.getLoginUser(token);
            if (StringUtils.isNotNull(loginUser))
            {
                AuthUtil.verifyLoginUserExpire(loginUser);
                SecurityContextHolder.set(SecurityConstants.LOGIN_USER, loginUser);
            }
        }else if (StringUtils.isNotEmpty(appToken)) {
            LoginAppUser loginUser = AuthUtil.authLogic.appTokenSerice.getLoginUser(appToken);
//            try {
//                StringBuilder sb = new StringBuilder(1000);
//                String method = request.getMethod();
//                if ("GET".equals(method)) {
//                    sb.append("请求参数: " + JSON.toJSONString(request.getParameterMap()) + "\n");
//                } else {
//                    StringBuilder responseStrBuilder = new StringBuilder();
//                    BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
//                    String inputStr;
//                    while ((inputStr = streamReader.readLine()) != null)
//                        responseStrBuilder.append(inputStr);
//                    sb.append("请求参数: " + responseStrBuilder + "\n");
//                }
//                log.info(sb.toString());
//            } catch (Exception e) {
//                log.error("记录请求日志失败" + e.getMessage());
//            }

            if (StringUtils.isNotNull(loginUser)) {
                AuthUtil.authLogic.appTokenSerice.verifyToken(loginUser);
                SecurityContextHolder.set(SecurityConstants.LOGIN_APP_USER, loginUser);
                if (StringUtils.isEmpty(userId)) {
                    SecurityContextHolder.setUserId(loginUser.getUserid() + "");
                    SecurityContextHolder.setUserName(loginUser.getUsername() + "");
                }

            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception
    {
        SecurityContextHolder.remove();
    }
}
