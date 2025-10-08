package com.ruoyi.system.api.domain.ccairbag.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品评论对象 ccairbag_product_reviews
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Data
public class CcairbagProductReviewsDTO extends BaseEntity
{

    /** 评论唯一标识，自增主键 */
    private Long reviewId;

    /** 被评论的商品id */
    @Excel(name = "被评论的商品id")
    private Long prodId;

    /** 评论用户的 ID */
    @Excel(name = "评论用户的 ID")
    private Long userId;

    private String userName;

    private String headimgurl;


    /** 订单项id */
    @Excel(name = "订单项id")
    private Long orderItemId;

    /** 掌柜回复 */
    @Excel(name = "掌柜回复")
    private String replyContent;

    /** 用户的评论内容 */
    @Excel(name = "用户的评论内容")
    private String comment;

    /** 记录时间 */
    @JsonIgnore // 忽略原始Date字段的序列化
    private Date recTime;

    /** 回复时间 */
    @JsonIgnore // 忽略原始Date字段的序列化
    private Date replyTime;


    /** 用户的打分 */
    @Excel(name = "用户的打分")
    private Integer rating;

    /** 是否匿名(1:是  0:否);是否匿名(1:是  0:否) */
    @Excel(name = "是否匿名(1:是  0:否);是否匿名(1:是  0:否)")
    private Integer isAnonymous;

    /** 状态;是否显示，1:为显示，0:待审核， -1：不通过审核，不显示。 如果需要审核评论，则是0,，否则1 */
    @Excel(name = "状态;是否显示，1:为显示，0:待审核， -1：不通过审核，不显示。 如果需要审核评论，则是0,，否则1")
    private Integer status;

    /** 评价(0好评 1中评 2差评);评价(0好评 1中评 2差评) */
    @Excel(name = "评价(0好评 1中评 2差评);评价(0好评 1中评 2差评)")
    private Integer evaluate;

    /** 用户晒图 */
    @Excel(name = "用户晒图")
    private String pics;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private Integer deleted;

    @Excel(name = "商品名称，不能为空")
    private String productName;

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

    private String userNameExt;
    public String getUserNameExt() {
        if (userNameExt == null || userNameExt.length() <= 1) {
            return userNameExt; // 空值或单字符时保持原样
        }

        // 首字符 + 中间全* + 末字符
        return userNameExt.charAt(0)
                + StringUtils.repeat("*", userNameExt.length() - 2)
                + userNameExt.charAt(userNameExt.length() - 1);
    }
}
