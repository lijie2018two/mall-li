package com.ruoyi.system.api.domain.ccairbag.dto;

import com.ruoyi.common.core.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CcairbagNegotiationRecordDTO {
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 关联商品表的商品ID */
    @Excel(name = "关联商品表的商品ID")
    private Long productId;

    /** 买家ID，关联用户表 */
    @Excel(name = "买家ID，关联用户表")
    private Long buyerId;

    /** 卖家ID，关联用户表 */
    @Excel(name = "卖家ID，关联用户表")
    private Long shopId;

    /** 买家出价 */
    @Excel(name = "买家出价")
    private BigDecimal offerPrice;

    /** 议价状态，0-待处理，1-同意，2-拒绝，3-自动拒绝 */
    @Excel(name = "议价状态，0-待处理，1-同意，2-拒绝，3-自动拒绝")
    private Integer status;


    private Integer negotiationCount;

    private String remarks;

    /** 商品名称，不能为空 */
    @Excel(name = "商品名称，不能为空")
    private String productName;

    private String shopName;

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

}
