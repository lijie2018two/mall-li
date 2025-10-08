package com.ruoyi.system.api.domain.ccairbag;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

import java.math.BigDecimal;

/**
 * 购物车对象 ccairbag_shopping_carts
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Data
public class CcairbagShoppingCarts extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 购物车项唯一标识，自增主键 */
    private Long cartId;

    /** 用户 ID */
    @Excel(name = "用户 ID")
    private Long userId;


    /** 商品id */
    @Excel(name = "商品id")
    private Long prodId;

    /** 店铺id */
    @Excel(name = "店铺id")
    private Long shopId;

    /** 数量 */
    @Excel(name = "数量")
    private Integer quantity;

    @TableField(exist = false)
    @ApiParam(value = "店铺名称")
    private String shopName;

    @TableField(exist = false)
    @ApiParam(value = "店铺logo")
    private String shopLogo;


    @TableField(exist = false)
    @ApiParam(value = "商品名称")
    private String productName;

    @TableField(exist = false)
    @ApiParam(value = "商品图片")
    private String pic;

    @TableField(exist = false)
    @ApiParam(value = "商品价格")
    private BigDecimal price;

    @TableField(exist = false)
    @ApiParam(value = "新旧状态 全新 二手 翻新")
    private Integer conditionType;

    @TableField(exist = false)
    @ApiParam(value = "退换政策")
    private Integer returnPolicy;

    @TableField(exist = false)
    @ApiParam(value = "物流模式 0 包邮；1 到付")
    private Integer deliveryFeeMode;

    @TableField(exist = false)
    @ApiParam(value = "总库存")
    private Integer totalQuantity;
    public void setCartId(Long cartId) 
    {
        this.cartId = cartId;
    }

    public Long getCartId() 
    {
        return cartId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    public void setProdId(Long prodId)
    {
        this.prodId = prodId;
    }

    public Long getProdId() 
    {
        return prodId;
    }
    public void setShopId(Long shopId) 
    {
        this.shopId = shopId;
    }

    public Long getShopId() 
    {
        return shopId;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("cartId", getCartId())
            .append("userId", getUserId())
            .append("prodId", getProdId())
            .append("shopId", getShopId())
            .append("quantity", getQuantity())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
