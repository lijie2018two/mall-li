package com.ruoyi.system.api.domain.ccairbag;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import com.ruoyi.system.api.domain.ccairbag.dto.LogisticsInfoDTO;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单对象 ccairbag_orders
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Data
public class CcairbagOrders extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单唯一标识，自增主键 */
    private Long orderId;

    private Long orderTotalId;

    /** 下单用户的 ID */
    @Excel(name = "下单用户的 ID")
    private Long userId;

    private String userName;

    @ApiParam(value = "商家id")
    private Long shopId;
    @ApiParam(value = "商家名称")
    private String shopName;
    @ApiParam(value = "商家管理人id")
    private Long shopUserId;

    /** 订单总金额 */
    @Excel(name = "订单总金额")
    private BigDecimal totalAmount;

    /** 优惠金额 */
    @Excel(name = "优惠金额")
    private BigDecimal reduceAmount;

    /** 订单最终金额，扣除折扣后的金额 */
    @Excel(name = "订单最终金额，扣除折扣后 + 各种税的金额")
    private BigDecimal finalAmount;

    @Excel(name = "0 - 待付款、1 - 待发货、2 - 待收货、3 - 退款申请中、" +
            "4 - 待退货、5 - 退货运输中、6 - 平台介入中、" +
            "7 - 已收货、8 - 已完成、9 - 已取消、" +
            "10 - 退款成功、" +
            "99 - 已关单")
    private Integer orderStatus;

    /** 订单流水号 */
    @Excel(name = "订单流水号")
    private String orderNumber;

    /** 支付方式 */


    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    @ApiParam(value = "关税")
    private BigDecimal tariff;

    /** 订单商品总数 */
    @Excel(name = "订单商品总数")
    private Integer productNums;

    /** 支付时间 */
    @Excel(name = "支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;


    /** 取消时间 */
    @Excel(name = "取消时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cancelTime;

    /** 用户订单删除状态;用户订单删除状态，0：没有删除， 1：回收站， 2：永久删除 */
    @Excel(name = "用户订单删除状态;用户订单删除状态，0：没有删除， 1：回收站， 2：永久删除")
    private String deleteStatus;

    /** 处理状态;0:默认,1:在处理,2:处理完成 */
    @Excel(name = "处理状态;0:默认,1:在处理,2:处理完成")
    private String refundSts;

    /** 订单关闭原因;订单关闭原因 1-超时未支付 2-退款关闭 4-买家取消 15-已通过货到付款交易 */
    @Excel(name = "订单关闭原因;订单关闭原因 1-超时未支付 2-退款关闭 4-买家取消 15-已通过货到付款交易")
    private Integer closeType;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private Integer deleted;
    @ApiModelProperty(name = "用户订单地址Id",notes = "用户订单地址Id")
    private Long addrId ;

    @ApiParam(name = "买家删除标记")
    private Integer userDelete;

    @ApiParam(name = "卖家删除标记")
    private Integer shopDelete;

    private List<CcairbagOrderDetails> orderDetails;

    //物流信息

    private List<LogisticsInfoDTO> logisticsInfoDTOList;

    //发货地址列表
    private List<CcairbagUserAddr> addrList;


    private CcairbagOrderPayment payment;

    private BigDecimal paymentFee;

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

    @ApiParam(value = "广告总费用")
    private BigDecimal commissionSumMoney;


    @ApiParam(value = "资金状态 0暂停 1 可用 ")
    private int fundStatus;

    private String buyerName;

    @ApiParam(value = "是否评价 1 有评价 0 没评价")
    private Integer isEvaluate;

    @ApiParam(value = "查询的商品名")
    private String search;

}
