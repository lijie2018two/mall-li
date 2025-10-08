package com.ruoyi.system.api;


import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.system.api.factory.RemoteUserFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 用户服务
 *
 * @author ruoyi
 */
@FeignClient(contextId = "remoteBusinessUserService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteUserFallbackFactory.class)
public interface RemoteBusinessUserService
{
    /**
     * 通过用户名查询用户信息
     *
     * @param source 请求来源
     * @return 结果
     */
//    @GetMapping("/businessUser/getUserInfo/{phone}")
//    public TlmUser getUserInfo(@PathVariable("phone") String phone, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 注册用户信息
     *
     * @param source 请求来源
     * @return 结果
     */
//    @PostMapping("/businessUser/addExt")
//    public TlmUser addExt(@RequestBody TlmUser user, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 登陆
     *
     * @param source 请求来源
     * @return 结果
     */
//    @PostMapping("/businessUser/login")
//    public AjaxResult login(@RequestBody TlmUser user, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @GetMapping("/businessUser/existByPhone/{phone}")

    int existByPhone(@PathVariable("phone") String phone, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);


//    @PostMapping("/businessUser/editExt")
//    int editExt(@RequestBody TlmUser user, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);



}

