package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 物流商品关联对象 ccairbag_product_logistics
 * 
 * @author lidabai
 * @date 2025-03-06
 */
public class CcairbagProductLogistics extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 商品id */
    @Excel(name = "商品id")
    private Long productId;

    /** 物流服务id */
    @Excel(name = "物流服务id")
    private Long serviceId;

    /** 该物流服务对于此商品的运费计算规则 */
    @Excel(name = "该物流服务对于此商品的运费计算规则")
    private String shippingFeeCalculation;

    /** 该物流服务的预计送达时间 */
    @Excel(name = "该物流服务的预计送达时间")
    private Long estimatedDeliveryDays;

    /** 记录不同物流类型的额外限制或规则 */
    @Excel(name = "记录不同物流类型的额外限制或规则")
    private String shippingTypeRules;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private Integer deleted;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
    }
    public void setServiceId(Long serviceId) 
    {
        this.serviceId = serviceId;
    }

    public Long getServiceId() 
    {
        return serviceId;
    }
    public void setShippingFeeCalculation(String shippingFeeCalculation) 
    {
        this.shippingFeeCalculation = shippingFeeCalculation;
    }

    public String getShippingFeeCalculation() 
    {
        return shippingFeeCalculation;
    }
    public void setEstimatedDeliveryDays(Long estimatedDeliveryDays) 
    {
        this.estimatedDeliveryDays = estimatedDeliveryDays;
    }

    public Long getEstimatedDeliveryDays() 
    {
        return estimatedDeliveryDays;
    }
    public void setShippingTypeRules(String shippingTypeRules) 
    {
        this.shippingTypeRules = shippingTypeRules;
    }

    public String getShippingTypeRules() 
    {
        return shippingTypeRules;
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
            .append("id", getId())
            .append("productId", getProductId())
            .append("serviceId", getServiceId())
            .append("shippingFeeCalculation", getShippingFeeCalculation())
            .append("estimatedDeliveryDays", getEstimatedDeliveryDays())
            .append("shippingTypeRules", getShippingTypeRules())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("deleted", getDeleted())
            .toString();
    }
}
