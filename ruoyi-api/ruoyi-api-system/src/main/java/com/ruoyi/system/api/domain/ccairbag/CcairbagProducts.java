package com.ruoyi.system.api.domain.ccairbag;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品对象 ccairbag_products
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Data
public class CcairbagProducts extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 商品唯一标识，自增主键 */
    private Long productId;

    /** 商品名称，不能为空 */
    @Excel(name = "商品名称，不能为空")
    private String productName;

    /** 关联的店铺 ID，该商品所属店铺 */
    @Excel(name = "关联的店铺 ID，该商品所属店铺")
    private Long shopId;

    @TableField(exist = false)
    private String shopName;

    /** 商品简要描述 */
    @Excel(name = "退换政策")
    private Integer returnPolicy;

    /** 关联的商品分类 ID，该商品所属分类 */
    @Excel(name = "关联的商品分类 ID，该商品所属分类")
    private Long categoryId;

    @TableField(exist = false)
    private String fullCategoryName;

    /** 商品详细描述 */
    @Excel(name = "商品详细描述")
    private String description;

    /** 商品状态;1正常  0 下架 */
    @Excel(name = "商品状态;0：上架，1：用户操作下架，2：商品售罄，3：商品违反平台规则，请联系平台客服处理")
    @ApiParam(value = "商品状态;0：上架，1：用户操作下架，2：商品售罄，3：商品违反平台规则，请联系平台客服处理")
    private Integer status;

    @ApiParam(value = "商品状态;0：上架，1：用户操作下架，2：商品售罄，3：商品违反平台规则，请联系平台客服处理")
    private Integer applicationReason;

    private Long activityId;

    @ApiParam(value = "新旧状态 全新 二手 翻新")
    private Integer conditionType;
    /** 原价 */
    @Excel(name = "原价")
    private BigDecimal oriPrice;

    /** 商品价格，精确到小数点后两位 */
    @Excel(name = "商品价格，精确到小数点后两位")
    private BigDecimal price;

    /** 商品主图 */
    @Excel(name = "商品主图")
    private String pic;

    /** 商品图片;商品图片，以,分割 */
    @Excel(name = "商品图片;商品图片，以,分割")
    private String imgs;

    /** 总库存 */
    @Excel(name = "总库存")
    private Integer totalQuantity;
    @Excel(name = "redis库存")
    private Integer redisQuantity;


    /** 商品销量，默认为 0 */
    @Excel(name = "商品销量，默认为 0")
    private Long salesVolume;

    @ApiParam(value = "0 包邮；1 到付")
    private Integer deliveryFeeMode;

    @Excel(name = "乐观锁版本号，用于并发更新控制，初始为 0")
    private Long version;

    @TableField(exist = false)
    private int num;


    @TableField(exist = false)
    @ApiParam(value = "浏览量")
    private int productViewCount;

    @TableField(exist = false)
    @ApiParam(value = "分类ids")
    private List<Long> categoryIds;

    @TableField(exist = false)
    @ApiParam(value = "汽车品牌")
    private String brand;

    @TableField(exist = false)
    @ApiParam(value = "最小值")
    private Double minPrice;

    @TableField(exist = false)
    @ApiParam(value = "最大值")
    private Double maxPrice;

    @TableField(exist = false)
    @ApiParam(value = "排序方式")
    private Integer sortBy;

    @TableField(exist = false)
    @ApiParam(value = "汽车品牌")
    private String make;


    @ApiParam(value = "商家查询商品接口专用 0：上架  1：下架")
    @TableField(exist = false)
    private Integer prodShopStatus ;
    @Excel(name = "发货地址id")
    private Long addrId;

    @TableField(exist = false)
    private String showProductId;

    @Excel(name = "折扣值")
    private Integer discount;

    @TableField(exist = false)
    private BigDecimal money;

    private List<CcairbagPromotionActivities> activities;

    private CcairbagShops shops;
    public String getShowProductId() {
        return String.format("%06d", this.productId);
    }

    /** 是否删除 */
    @Excel(name = "是否删除")
    private Integer deleted;

    @ApiParam(value = "是否可以议价")
    @Excel(name = "是否可以议价")
    private Integer isNegotiable ;

    private BigDecimal minPriceNegotiable;

    private String remarks;


    private String videos;

}
