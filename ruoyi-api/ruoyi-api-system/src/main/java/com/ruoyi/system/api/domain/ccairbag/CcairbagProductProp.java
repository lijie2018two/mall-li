package com.ruoyi.system.api.domain.ccairbag;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 商品属性对象 ccairbag_product_prop
 * 
 * @author lidabai
 * @date 2025-02-20
 */
public class CcairbagProductProp extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long propId;

    /** 属性名称 */
    @Excel(name = "属性名称")
    private String propName;

    /** ProdPropRule 1:销售属性(规格); 2:参数属性;;ProdPropRule 1:销售属性(规格); 2:参数属性; */
    @Excel(name = "ProdPropRule 1:销售属性(规格); 2:参数属性;;ProdPropRule 1:销售属性(规格); 2:参数属性;")
    private Integer rule;

    /** 店铺id */
    @Excel(name = "店铺id")
    private Long shopId;

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

    /** 是否删除 */
    @Excel(name = "是否删除")
    private Integer deleted;

    public void setPropId(Long propId) 
    {
        this.propId = propId;
    }

    public Long getPropId() 
    {
        return propId;
    }
    public void setPropName(String propName) 
    {
        this.propName = propName;
    }

    public String getPropName() 
    {
        return propName;
    }
    public void setRule(Integer rule) 
    {
        this.rule = rule;
    }

    public Integer getRule() 
    {
        return rule;
    }
    public void setShopId(Long shopId) 
    {
        this.shopId = shopId;
    }

    public Long getShopId() 
    {
        return shopId;
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
            .append("propId", getPropId())
            .append("propName", getPropName())
            .append("rule", getRule())
            .append("shopId", getShopId())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("updatedBy", getUpdatedBy())
            .append("updatedTime", getUpdatedTime())
            .append("deleted", getDeleted())
            .toString();
    }
}
