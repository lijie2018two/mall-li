package com.ruoyi.common.core.web.domain;

import com.ruoyi.common.core.constant.HttpStatus;
import lombok.Data;
import lombok.ToString;

import java.util.Objects;

/**
 * 操作消息提醒
 * 
 * @author ruoyi
 */
@Data
@ToString
public class AppResult<T>
{
    private static final long serialVersionUID = 1L;

    /** 状态码 */
    private Integer code;
    /** 返回内容 */
    private String msg;
    /** 数据对象 */
    private T data;

    /**
     * 初始化一个新创建的 AjaxResult 对象，使其表示一个空消息。
     */
    public AppResult()
    {
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 状态码
     * @param msg 返回内容
     */
    public AppResult(int code, String msg)
    {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 状态码
     * @param msg 返回内容
     * @param data 数据对象
     */
    public AppResult(int code, String msg, T data)
    {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public AppResult(T data)
    {
        this.code = HttpStatus.SUCCESS;
        this.msg = "";
        this.data = data;

    }

    /**
     * 返回成功消息
     * 
     * @return 成功消息
     */
    public static AppResult success()
    {
        return new AppResult().success("操作成功");
    }

    /**
     * 返回成功数据
     * 
     * @return 成功消息
     */
    public AppResult<T> success(T data)
    {
        return new AppResult<T>().success("操作成功", data);
    }

    /**
     * 返回成功消息
     * 
     * @param msg 返回内容
     * @return 成功消息
     */
    public static AppResult success(String msg)
    {
        return new AppResult(HttpStatus.SUCCESS, msg);
    }

    /**
     * 返回成功消息
     * 
     * @param msg 返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public AppResult<T> success(String msg, T data)
    {
        return new AppResult(HttpStatus.SUCCESS, msg, data);
    }

    /**
     * 返回警告消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static AppResult warn(String msg)
    {
        return AppResult.warn(msg, null);
    }

    /**
     * 返回警告消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static AppResult warn(String msg, Object data)
    {
        return new AppResult(HttpStatus.WARN, msg, data);
    }

    /**
     * 返回错误消息
     * 
     * @return 错误消息
     */
    public static AppResult error()
    {
        return AppResult.error("操作失败");
    }

    /**
     * 返回错误消息
     * 
     * @param msg 返回内容
     * @return 错误消息
     */
    public static AppResult error(String msg)
    {
        return AppResult.error(msg, null);
    }

    /**
     * 返回错误消息
     * 
     * @param msg 返回内容
     * @param data 数据对象
     * @return 错误消息
     */
    public static AppResult error(String msg, Object data)
    {
        return new AppResult(HttpStatus.ERROR, msg, data);
    }

    /**
     * 返回错误消息
     * 
     * @param code 状态码
     * @param msg 返回内容
     * @return 错误消息
     */
    public static AppResult error(int code, String msg)
    {
        return new AppResult(code, msg, null);
    }

    /**
     * 是否为成功消息
     *
     * @return 结果
     */
    public boolean isSuccess()
    {
        return Objects.equals(HttpStatus.SUCCESS, this.code);
    }

    /**
     * 是否为警告消息
     *
     * @return 结果
     */
    public boolean isWarn()
    {
        return Objects.equals(HttpStatus.WARN, this.code);
    }

    /**
     * 是否为错误消息
     *
     * @return 结果
     */
    public boolean isError()
    {
        return Objects.equals(HttpStatus.ERROR, this.code);
    }


}
