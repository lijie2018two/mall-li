package com.ruoyi.system.api.domain.ccairbag;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;
import java.util.Map;

/**
 * 消息会话对象 ccairbag_conversations
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Data
public class CcairbagConversations extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long conversationId;

    /** 买家id;买家id */
    @Excel(name = "买家id;买家id")
    private Long buyerId;

    /** 卖家id;卖家id */
    @Excel(name = "卖家id;卖家id")
    private Long sellerId;

    /** 订单id */
    @Excel(name = "子订单id")
    private Long detailId;

    /** 头像 */
    @Excel(name = "头像")
    private String headimgurl;

    /** 用户名;用户名 */
    @Excel(name = "用户名;用户名")
    private String userName;

    @Excel(name = "未读消息数量")
    private Integer unreadCount ;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private Integer deleted;

    private List<CcairbagMessages> messages;

    private Map<String, List<CcairbagMessages>> messagesByDate;


    public void setConversationId(Long conversationId) 
    {
        this.conversationId = conversationId;
    }

    public Long getConversationId() 
    {
        return conversationId;
    }
    public void setBuyerId(Long buyerId) 
    {
        this.buyerId = buyerId;
    }

    public Long getBuyerId() 
    {
        return buyerId;
    }
    public void setSellerId(Long sellerId) 
    {
        this.sellerId = sellerId;
    }

    public Long getSellerId() 
    {
        return sellerId;
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
            .append("conversationId", getConversationId())
            .append("buyerId", getBuyerId())
            .append("sellerId", getSellerId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("deleted", getDeleted())
            .toString();
    }
}
