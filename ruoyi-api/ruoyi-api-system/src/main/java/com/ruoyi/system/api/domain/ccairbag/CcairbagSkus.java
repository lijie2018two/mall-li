package com.ruoyi.system.api.domain.ccairbag;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * sku对象 ccairbag_skus
 * 
 * @author lidabai
 * @date 2025-02-20
 */
public class CcairbagSkus extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** SKU 唯一标识，自增主键 */
    private Long skuId;

    /** 关联的商品 ID，该 SKU 所属商品 */
    @Excel(name = "关联的商品 ID，该 SKU 所属商品")
    private Long productId;

    /** SKU 名称;如颜色、尺寸等组合 */
    @Excel(name = "SKU 名称;如颜色、尺寸等组合")
    private String skuName;

    /** 原价;原价 */
    @Excel(name = "原价;原价")
    private BigDecimal oriPrice;

    /** SKU 价格，精确到小数点后两位 */
    @Excel(name = "SKU 价格，精确到小数点后两位")
    private BigDecimal price;

    /** SKU 库存数量 */
    @Excel(name = "SKU 库存数量")
    private Long quantity;

    /** 商品名称;商品名称 */
    @Excel(name = "商品名称;商品名称")
    private String prodName;

    /** sku图片;sku图片 */
    @Excel(name = "sku图片;sku图片")
    private String pic;

    /** 销售属性组合字符串 格式是p1:v1;p2:v2;销售属性组合字符串 格式是p1:v1;p2:v2 */
    @Excel(name = "销售属性组合字符串 格式是p1:v1;p2:v2;销售属性组合字符串 格式是p1:v1;p2:v2")
    private String properties;

    /** 重量 */
    @Excel(name = "重量")
    private Long weight;

    /** 体积 */
    @Excel(name = "体积")
    private Long volume;

    /** 状态;0 禁用 1启用 */
    @Excel(name = "状态;0 禁用 1启用")
    private Integer status;

    /** 是否删除;1：删除  0：默认 */
    @Excel(name = "是否删除;1：删除  0：默认")
    private Integer deleted;

    /** 乐观锁版本号，用于并发更新控制，初始为 0 */
    @Excel(name = "乐观锁版本号，用于并发更新控制，初始为 0")
    private Long version;

    public void setSkuId(Long skuId) 
    {
        this.skuId = skuId;
    }

    public Long getSkuId() 
    {
        return skuId;
    }
    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
    }
    public void setSkuName(String skuName) 
    {
        this.skuName = skuName;
    }

    public String getSkuName() 
    {
        return skuName;
    }
    public void setOriPrice(BigDecimal oriPrice) 
    {
        this.oriPrice = oriPrice;
    }

    public BigDecimal getOriPrice() 
    {
        return oriPrice;
    }
    public void setPrice(BigDecimal price) 
    {
        this.price = price;
    }

    public BigDecimal getPrice() 
    {
        return price;
    }
    public void setQuantity(Long quantity) 
    {
        this.quantity = quantity;
    }

    public Long getQuantity() 
    {
        return quantity;
    }
    public void setProdName(String prodName) 
    {
        this.prodName = prodName;
    }

    public String getProdName() 
    {
        return prodName;
    }
    public void setPic(String pic) 
    {
        this.pic = pic;
    }

    public String getPic() 
    {
        return pic;
    }
    public void setProperties(String properties) 
    {
        this.properties = properties;
    }

    public String getProperties() 
    {
        return properties;
    }
    public void setWeight(Long weight) 
    {
        this.weight = weight;
    }

    public Long getWeight() 
    {
        return weight;
    }
    public void setVolume(Long volume) 
    {
        this.volume = volume;
    }

    public Long getVolume() 
    {
        return volume;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setDeleted(Integer deleted) 
    {
        this.deleted = deleted;
    }

    public Integer getDeleted() 
    {
        return deleted;
    }
    public void setVersion(Long version) 
    {
        this.version = version;
    }

    public Long getVersion() 
    {
        return version;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("skuId", getSkuId())
            .append("productId", getProductId())
            .append("skuName", getSkuName())
            .append("oriPrice", getOriPrice())
            .append("price", getPrice())
            .append("quantity", getQuantity())
            .append("prodName", getProdName())
            .append("pic", getPic())
            .append("properties", getProperties())
            .append("weight", getWeight())
            .append("volume", getVolume())
            .append("status", getStatus())
            .append("deleted", getDeleted())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("version", getVersion())
            .toString();
    }
}
