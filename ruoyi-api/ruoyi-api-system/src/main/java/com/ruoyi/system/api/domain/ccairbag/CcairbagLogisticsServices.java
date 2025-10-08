package com.ruoyi.system.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 物流服务对象 ccairbag_logistics_services
 * 
 * @author lidabai
 * @date 2025-03-06
 */
public class CcairbagLogisticsServices extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long serviceId;

    /** 物流服务名称 */
    @Excel(name = "物流服务名称")
    private String serviceName;

    /** 提供商名称 */
    @Excel(name = "提供商名称")
    private String providerName;

    /** 详细描述 */
    @Excel(name = "详细描述")
    private String description;

    /** 基本费用 */
    @Excel(name = "基本费用")
    private BigDecimal baseFee;

    /** 额外费用 */
    @Excel(name = "额外费用")
    private String additionalFeeRules;

    /** 物流类型 */
    @Excel(name = "物流类型")
    private Integer shippingType;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private Integer deleted;

    public void setServiceId(Long serviceId) 
    {
        this.serviceId = serviceId;
    }

    public Long getServiceId() 
    {
        return serviceId;
    }
    public void setServiceName(String serviceName) 
    {
        this.serviceName = serviceName;
    }

    public String getServiceName() 
    {
        return serviceName;
    }
    public void setProviderName(String providerName) 
    {
        this.providerName = providerName;
    }

    public String getProviderName() 
    {
        return providerName;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setBaseFee(BigDecimal baseFee) 
    {
        this.baseFee = baseFee;
    }

    public BigDecimal getBaseFee() 
    {
        return baseFee;
    }
    public void setAdditionalFeeRules(String additionalFeeRules) 
    {
        this.additionalFeeRules = additionalFeeRules;
    }

    public String getAdditionalFeeRules() 
    {
        return additionalFeeRules;
    }
    public void setShippingType(Integer shippingType) 
    {
        this.shippingType = shippingType;
    }

    public Integer getShippingType() 
    {
        return shippingType;
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
            .append("serviceId", getServiceId())
            .append("serviceName", getServiceName())
            .append("providerName", getProviderName())
            .append("description", getDescription())
            .append("baseFee", getBaseFee())
            .append("additionalFeeRules", getAdditionalFeeRules())
            .append("shippingType", getShippingType())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("deleted", getDeleted())
            .toString();
    }
}
