package com.ruoyi.system.api.domain.ccairbag.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CcairbagProductFavoriteDTO {
    /** 收藏id */
    private Long favoriteId;
    /** 1 收藏  0 取消收藏 */
    private int status;
    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 商品id */
    @Excel(name = "商品id")
    private Long prodId;

    /** 是否删除;0 正常  1 删除 */
    @Excel(name = "是否删除;0 正常  1 删除")
    private Integer deleted;

    @Excel(name = "商品名称，不能为空")
    private String productName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
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

    private Integer totalQuantity;

    private Integer conditionType;

    private String description;

    private Long shopId;

    private String shopName;
}
