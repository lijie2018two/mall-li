package com.ruoyi.common.sensitive.utils;

import com.ruoyi.common.core.utils.StringUtils;

/**
 * 脱敏工具类
 *
 * @author ruoyi
 */
public class DesensitizedUtil
{
    /**
     * 密码的全部字符都用*代替，比如：******
     *
     * @param password 密码
     * @return 脱敏后的密码
     */
    public static String password(String password)
    {
        if (StringUtils.isBlank(password))
        {
            return StringUtils.EMPTY;
        }
        return StringUtils.repeat('*', password.length());
    }


}
