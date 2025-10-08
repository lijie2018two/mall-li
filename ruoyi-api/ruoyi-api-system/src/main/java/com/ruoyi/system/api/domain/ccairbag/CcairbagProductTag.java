package com.ruoyi.system.api.domain.ccairbag;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 商品标签对象 ccairbag_product_tag
 * 
 * @author lidabai
 * @date 2025-02-20
 */
public class CcairbagProductTag extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 分组标题 */
    @Excel(name = "分组标题")
    private String title;

    /** 商铺id */
    @Excel(name = "商铺id")
    private Long shopId;

    /** 默认类型;默认类型(0:商家自定义,1:系统默认) */
    @Excel(name = "默认类型;默认类型(0:商家自定义,1:系统默认)")
    private Integer isDefault;

    /** 商品数量 */
    @Excel(name = "商品数量")
    private Integer prodCount;

    /** 样式;列表样式(0:一列一个,1:一列两个,2:一列三个) */
    @Excel(name = "样式;列表样式(0:一列一个,1:一列两个,2:一列三个)")
    private Integer style;

    /** 排序 */
    @Excel(name = "排序")
    private Integer seq;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private Long deleted;

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

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setShopId(Long shopId) 
    {
        this.shopId = shopId;
    }

    public Long getShopId() 
    {
        return shopId;
    }
    public void setIsDefault(Integer isDefault) 
    {
        this.isDefault = isDefault;
    }

    public Integer getIsDefault() 
    {
        return isDefault;
    }
    public void setProdCount(Integer prodCount) 
    {
        this.prodCount = prodCount;
    }

    public Integer getProdCount() 
    {
        return prodCount;
    }
    public void setStyle(Integer style) 
    {
        this.style = style;
    }

    public Integer getStyle() 
    {
        return style;
    }
    public void setSeq(Integer seq) 
    {
        this.seq = seq;
    }

    public Integer getSeq() 
    {
        return seq;
    }
    public void setDeleted(Long deleted) 
    {
        this.deleted = deleted;
    }

    public Long getDeleted() 
    {
        return deleted;
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
            .append("id", getId())
            .append("title", getTitle())
            .append("shopId", getShopId())
            .append("isDefault", getIsDefault())
            .append("prodCount", getProdCount())
            .append("style", getStyle())
            .append("seq", getSeq())
            .append("deleted", getDeleted())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("updatedBy", getUpdatedBy())
            .append("updatedTime", getUpdatedTime())
            .toString();
    }
}
