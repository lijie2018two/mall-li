package com.ruoyi.system.api.domain.ccairbag;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 消息对象 ccairbag_messages
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Data
public class CcairbagMessages extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 消息的唯一标识，自增主键，用于在系统中唯一区分每条消息 */
    private Long messageId;

    /** 头像 */
    @Excel(name = "头像")
    private String headimgurl;

    /** 用户名;用户名 */
    @Excel(name = "用户名;用户名")
    private String userName;

    /** 会话表id */
    @Excel(name = "会话表id")
    private Long conversationsId;

    @Excel(name = "发送人id ")
    private Long senderId;

    /** 消息内容 */
    @Excel(name = "消息内容")
    private String messageContent;

    @ApiParam(name = "消息类型 0：文字 1：图片 2：语音 3：视频 4：文件 5 自动回复  确认收货地址")
    private Integer messageType;

    /** 消息是否已读的标记，默认为 FALSE 表示未读 */
    @ApiParam(name = "买家是否已读（默认未读）")
    private Integer isReadBuyer;

    @ApiParam(name = "卖家是否已读（默认未读）")
    private Integer isReadSeller;

    @ApiParam(name = "发送人类型 0：买家 1 卖家")
    private Integer senderType;

    private String groupDate;
    /** 是否删除 */
    @Excel(name = "是否删除")
    private Integer deleted;

    public void setMessageId(Long messageId) 
    {
        this.messageId = messageId;
    }

    public Long getMessageId() 
    {
        return messageId;
    }
    public void setConversationsId(Long conversationsId) 
    {
        this.conversationsId = conversationsId;
    }

    public Long getConversationsId() 
    {
        return conversationsId;
    }
    public void setSenderId(Long senderId) 
    {
        this.senderId = senderId;
    }

    public Long getSenderId() 
    {
        return senderId;
    }
    public void setMessageContent(String messageContent) 
    {
        this.messageContent = messageContent;
    }

    public String getMessageContent() 
    {
        return messageContent;
    }

    public void setDeleted(Integer deleted) 
    {
        this.deleted = deleted;
    }

    public Integer getDeleted() 
    {
        return deleted;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("messageId", getMessageId())
            .append("conversationsId", getConversationsId())
            .append("senderId", getSenderId())
            .append("messageContent", getMessageContent())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("deleted", getDeleted())
            .toString();
    }
}
