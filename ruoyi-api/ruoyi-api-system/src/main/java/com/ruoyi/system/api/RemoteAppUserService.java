package com.ruoyi.system.api;


import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.system.api.domain.ccairbag.*;
import com.ruoyi.system.api.factory.RemoteUserFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户服务
 * 
 * @author ruoyi
 */
@FeignClient(contextId = "remoteAppUserService", value = ServiceNameConstants.APP_SERVICE, fallbackFactory = RemoteUserFallbackFactory.class )
public interface RemoteAppUserService
{
    /**
     * 通过邮箱查询用户信息
     *
     * @param email 用户名
     * @param source 请求来源
     * @return 结果
     */
    @GetMapping("ccairbag/users/getUserInfo/{email}")
    public CcairbagUsers getUserInfo(@PathVariable("email") String email, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 注册用户信息
     *
     * @param ccairbagUsers 用户信息
     * @param source 请求来源
     * @return 结果
     */
    @PostMapping("ccairbag/users/addExt")
    public CcairbagUsers addExt(@RequestBody CcairbagUsers ccairbagUsers, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);




    @PostMapping("ccairbag/shops/shopAddExt")
    public CcairbagShops shopAddExt(@RequestBody CcairbagShops shops, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);


    @GetMapping("registration/getInfoByUserId/{userId}")
    public CcairbagUserRegistration getInfoByUserId(@PathVariable("userId") Long userId, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @GetMapping("ccairbag/shops/getShopByUserId/{userId}")
    public CcairbagShops getShopByUserId(@PathVariable("userId") Long userId, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);



    @GetMapping("ccairbag/addr/selectCcairbagUserAddrByUserId/{userId}")
    public List<CcairbagUserAddr> selectCcairbagUserAddrByUserId(@PathVariable("userId") Long userId, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);


    @PostMapping("ccairbag/users/editExt")
    public int editExt(@RequestBody CcairbagUsers ccairbagUsers, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);


    @GetMapping("ccairbag/category/getCategoryTree")
    public List<CcairbagCategory> getCategoryTree(@RequestHeader(SecurityConstants.FROM_SOURCE) String source);

}
