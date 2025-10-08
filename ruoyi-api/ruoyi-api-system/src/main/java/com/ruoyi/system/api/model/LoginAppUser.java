package com.ruoyi.system.api.model;

import com.ruoyi.system.api.domain.ccairbag.CcairbagUsers;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Set;

/**
 * 用户信息
 *
 * @author ruoyi
 */
@ToString
@Data
public class LoginAppUser implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * 用户唯一标识
     */
    private String token;

    /**
     * 用户名id
     */
    private Long userid;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 用户名
     */
    private String username;

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;

    /**
     * 登录IP地址
     */
    private String ipaddr;

    /**
     * 权限列表
     */
    private Set<String> permissions;

    /**
     * 角色列表
     */
    private Set<String> roles;

    private CcairbagUsers ccairbagUsers;


    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public Long getUserid()
    {
        return userid;
    }

    public void setUserid(Long userid)
    {
        this.userid = userid;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public Long getLoginTime()
    {
        return loginTime;
    }

    public void setLoginTime(Long loginTime)
    {
        this.loginTime = loginTime;
    }

    public Long getExpireTime()
    {
        return expireTime;
    }

    public void setExpireTime(Long expireTime)
    {
        this.expireTime = expireTime;
    }

    public String getIpaddr()
    {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr)
    {
        this.ipaddr = ipaddr;
    }

    public Set<String> getPermissions()
    {
        return permissions;
    }

    public void setPermissions(Set<String> permissions)
    {
        this.permissions = permissions;
    }

    public Set<String> getRoles()
    {
        return roles;
    }

    public void setRoles(Set<String> roles)
    {
        this.roles = roles;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


}
