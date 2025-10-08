package com.ruoyi.system.api.domain.ccairbag;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单详情对象 ccairbag_order_details
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Data
public class CcairbagOrderDetails extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单详情唯一标识，自增主键 */
    private Long detailId;

    /** 关联的订单 ID */
    @Excel(name = "关联的订单 ID")
    private Long orderId;



    /** 订单号 */
    @Excel(name = "订单号")
    private String orderNumber;

    /** 商品id */
    @Excel(name = "商品id")
    private Long prodId;

    @TableField(exist = false)
    private String showProductId;

    public String getShowProductId() {
        return String.format("%06d", this.prodId);
    }

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String prodName;



    /** 产品主图片路径 */
    @Excel(name = "产品主图片路径")
    private String pic;

    /** 购买的 SKU 数量 */
    @Excel(name = "购买的 数量")
    private Integer quantity;

    /** 购买时的 SKU 单价 */
    @Excel(name = "购买时的 单价")
    private BigDecimal unitPrice;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 评论状态;评论状态： 0 未评价  1 已评价 */
    @Excel(name = "评论状态;评论状态： 0 未评价  1 已评价")
    private Integer commSts;

    /** 该 SKU 在订单中的小计金额（单价 * 数量）;商品总金额 */
    @Excel(name = "该 SKU 在订单中的小计金额", readConverterExp = "单=价,*=,数=量")
    private BigDecimal subtotal;


    @ApiParam(value = "活动id")
    private Long activityId;


    @ApiParam(value = "广告活动抽成金额")
    private Long commissionMoney;

    @ApiParam(value = "活动id")
    private Long commissionRate;

    @ApiParam(value = "销售税比例")
    private BigDecimal scale;

    /** 交易费用比例 */
    @ApiParam(value = "交易费用比例")
    private BigDecimal dealScale;

    @ApiParam(value = "销售税金额")
    private BigDecimal scaleMoney;

    @ApiParam(value = "交易费用金额")
    private BigDecimal dealMoney;

    @ApiParam(value = "收益金额")
    private BigDecimal incomeMoney;



    @Excel(name = "0 - 待付款、1 - 待发货、2 - 待收货、3 - 退款申请中、" +
            "4 - 待退货、5 - 退货运输中、6 - 平台介入中、" +
            "7 - 已收货、8 - 已完成、9 - 已取消、" +
            "10 - 退款成功、" +
            "99 - 已关单")
    private Integer orderStatus;

    private Integer oldStatus;


//    @ApiParam(value = "商家id")
//    private Long shopId;
//    @ApiParam(value = "商家名称")
//    private String shopName;
//    @ApiParam(value = "商家管理人id")
//    private Long shopUserId;


    @TableField(exist = false)
    private Integer returnPolicy;

    @ApiParam(value = "新旧状态 全新 二手 翻新")
    @TableField(exist = false)
    private Integer conditionType;

    @ApiParam(value = "0 包邮；1 到付")
    @TableField(exist = false)
    private Integer deliveryFeeMode;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private Integer deleted;

    @ApiParam(name = "购买人名称")
    private String userName;

    @ApiParam(name = "购买人电话")
    private String userPhone;

    @ApiParam(name = "购买人地址")
    private String userAddress;
    @ApiParam(name = "邮编")
    private String postCode;

    @ApiParam(name = "购买人地址")
    private CcairbagUserAddr userAddressExt;

    /** 物流单号 */
    @ApiParam(name = "物流单号")
    private String dvyFlowNum;

    /** 物流运费 */
    @ApiParam(name = "物流运费")
    private BigDecimal freightAmount;

    @ApiParam(name = "物流公司")
    private String logisticsName;

    @ApiParam(name = "物流公司编码")
    private String logisticsCode;

    @ApiParam(name = "完成时间")
    private Date finallyTime;

    @ApiParam(name = "发货时间")
    private Date dvyTime;


    @ApiParam(name = "买家删除标记")
    private Integer userDelete;

    @ApiParam(name = "卖家删除标记")
    private Integer shopDelete;

    private Long shopId;

    private CcairbagOrderRefund ccairbagOrderRefund;

    //议价金额
    private BigDecimal negotiablePrice;


}
