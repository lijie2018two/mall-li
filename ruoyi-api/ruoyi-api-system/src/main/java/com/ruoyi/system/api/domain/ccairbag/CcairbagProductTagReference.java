package com.ruoyi.system.api.domain.ccairbag;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 商品标签关系表对象 ccairbag_product_tag_reference
 * 
 * @author lidabai
 * @date 2025-02-20
 */
public class CcairbagProductTagReference extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 租户号 */
    @Excel(name = "租户号")
    private String referenceId;

    /** 乐观锁 */
    @Excel(name = "乐观锁")
    private Long revision;

    /** 店铺id */
    @Excel(name = "店铺id")
    private Long shopId;

    /** 标签id */
    @Excel(name = "标签id")
    private Long tagId;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private Integer deleted;

    /** 商品id */
    @Excel(name = "商品id")
    private Long prodId;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createdBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;

    /** 更新人 */
    @Excel(name = "更新人")
    private String updatedBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedTime;

    public void setReferenceId(String referenceId) 
    {
        this.referenceId = referenceId;
    }

    public String getReferenceId() 
    {
        return referenceId;
    }
    public void setRevision(Long revision) 
    {
        this.revision = revision;
    }

    public Long getRevision() 
    {
        return revision;
    }
    public void setShopId(Long shopId) 
    {
        this.shopId = shopId;
    }

    public Long getShopId() 
    {
        return shopId;
    }
    public void setTagId(Long tagId) 
    {
        this.tagId = tagId;
    }

    public Long getTagId() 
    {
        return tagId;
    }
    public void setDeleted(Integer deleted) 
    {
        this.deleted = deleted;
    }

    public Integer getDeleted() 
    {
        return deleted;
    }
    public void setProdId(Long prodId) 
    {
        this.prodId = prodId;
    }

    public Long getProdId() 
    {
        return prodId;
    }
    public void setCreatedBy(String createdBy) 
    {
        this.createdBy = createdBy;
    }

    public String getCreatedBy() 
    {
        return createdBy;
    }
    public void setCreatedTime(Date createdTime) 
    {
        this.createdTime = createdTime;
    }

    public Date getCreatedTime() 
    {
        return createdTime;
    }
    public void setUpdatedBy(String updatedBy) 
    {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedBy() 
    {
        return updatedBy;
    }
    public void setUpdatedTime(Date updatedTime) 
    {
        this.updatedTime = updatedTime;
    }

    public Date getUpdatedTime() 
    {
        return updatedTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("referenceId", getReferenceId())
            .append("revision", getRevision())
            .append("shopId", getShopId())
            .append("tagId", getTagId())
            .append("deleted", getDeleted())
            .append("prodId", getProdId())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("updatedBy", getUpdatedBy())
            .append("updatedTime", getUpdatedTime())
            .toString();
    }
}
