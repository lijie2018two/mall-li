package com.ruoyi.system.api.domain;


import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 短信日志表对象 s_hy_sms_log
 * 
 * @author houyuan
 * @date 2023-08-02
 */
public class SHySmsLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 短信类型 */
    private String bizId;

    /** 错误消息 */
    private String messages;

    /** 发送手机号 */
    @Excel(name = "发送手机号")
    private String phone;

    /** 参数 */
    private String param;

    /** 模板id */
    @Excel(name = "模板id")
    private String type;

    /** $column.columnComment */
    private Integer delFlag;

    /** 短信发送日期 格式为yyyyMMdd */
    @Excel(name = "短信发送日期 格式为yyyyMMdd")
    private String sendDate;

    /** 错误码 */
    private String errCode;

    /** 短信内容 */
    @Excel(name = "短信内容")
    private String content;

    /** 运营商短信状态 */
    @Excel(name = "运营商短信状态")
    private Long sendStatus;

    /** 运营商回执状态字符串 */
    @Excel(name = "运营商回执状态字符串")
    private String sendStatusString;

    private Integer retry;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setBizId(String bizId) 
    {
        this.bizId = bizId;
    }

    public String getBizId() 
    {
        return bizId;
    }
    public void setMessages(String messages) 
    {
        this.messages = messages;
    }

    public String getMessages() 
    {
        return messages;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }
    public void setParam(String param) 
    {
        this.param = param;
    }

    public String getParam() 
    {
        return param;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setDelFlag(Integer delFlag) 
    {
        this.delFlag = delFlag;
    }

    public Integer getDelFlag() 
    {
        return delFlag;
    }
    public void setSendDate(String sendDate) 
    {
        this.sendDate = sendDate;
    }

    public String getSendDate() 
    {
        return sendDate;
    }
    public void setErrCode(String errCode) 
    {
        this.errCode = errCode;
    }

    public String getErrCode() 
    {
        return errCode;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setSendStatus(Long sendStatus) 
    {
        this.sendStatus = sendStatus;
    }

    public Long getSendStatus() 
    {
        return sendStatus;
    }
    public void setSendStatusString(String sendStatusString) 
    {
        this.sendStatusString = sendStatusString;
    }

    public String getSendStatusString() 
    {
        return sendStatusString;
    }

    public Integer getRetry() {
        return retry;
    }

    public void setRetry(Integer retry) {
        this.retry = retry;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("bizId", getBizId())
            .append("messages", getMessages())
            .append("phone", getPhone())
            .append("param", getParam())
            .append("type", getType())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("delFlag", getDelFlag())
            .append("sendDate", getSendDate())
            .append("errCode", getErrCode())
            .append("content", getContent())
            .append("sendStatus", getSendStatus())
            .append("sendStatusString", getSendStatusString())
            .toString();
    }
}
