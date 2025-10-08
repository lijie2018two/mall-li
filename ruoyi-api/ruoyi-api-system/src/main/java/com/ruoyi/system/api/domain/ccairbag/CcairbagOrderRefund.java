package com.ruoyi.system.api.domain.ccairbag;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单退款对象 ccairbag_order_refund
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Data
public class CcairbagOrderRefund extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录ID */
    private Long refundId;

    /** 店铺ID */
    @Excel(name = "店铺ID")
    private Long shopId;



    /** 订单总金额 */
    @Excel(name = "订单总金额")
    private BigDecimal orderAmount;

    /** 订单项ID 全部退款是0 */
    @Excel(name = "订单项ID 全部退款是0")
    private Long orderItemId;

    /** 退款编号 */
    @Excel(name = "退款编号")
    private String refundSn;



    /** 第三方退款单号(微信退款单号) */
    @Excel(name = "第三方退款单号(微信退款单号)")
    private String outRefundNo;

    /** 订单支付方式 1 微信支付 2 支付宝 */
    @Excel(name = "订单支付方式 1 微信支付 2 支付宝")
    private Integer payType;



    /** 买家ID */
    @Excel(name = "买家ID")
    private Long userId;



    /** 退款金额 */
    @Excel(name = "退款金额")
    private BigDecimal refundAmount;

    /** 申请类型:1,仅退款,2退款退货 */
    @Excel(name = "申请类型:1,仅退款,2退款退货")
    private Long applyType;

    /** 处理状态:1为待审核,2为同意,3为不同意 */
    @Excel(name = "处理状态:1为 初次待审核,2为 初次同意,3为初次不同意 " +
            "4 商家取消 5：退货待审核 ；6 退货同意 ，7 退货不同意,8 平台介入中 ,9 平台介入过")
    private Integer refundSts;

    /** 处理退款状态: 0:退款处理中 1:退款成功 -1:退款失败 */
    @Excel(name = "处理退款状态: 0:退款处理中 1:退款成功 -1:退款失败；2：退款退货中")
    private Integer returnMoneySts;

    /** 申请时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "申请时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date applyTime;

    /** 卖家处理时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "卖家处理时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date handelTime;

    /** 退款时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "退款时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date refundTime;

    /** 文件凭证json */
    @Excel(name = "退款图片")
    private String photoFiles;

    /** 申请原因 */
    @Excel(name = "退款原因内容")
    private String buyerMsg;

    @Excel(name = "申请介入图片")
    private String photoFilesExt;

    /** 申请原因 */
    @Excel(name = "申请介入原因")
    private String buyerMsgExt;

    @Excel(name = "申请类型名")
    private String buyerType;

    @Excel(name = "申请类型值")
    private String buyerValue;

    /** 卖家备注 */
    @Excel(name = "卖家备注")
    private String sellerMsg;

    /** 物流公司名称 */
    @Excel(name = "物流公司名称")
    private String expressName;

    /** 物流单号 */
    private String expressNo;

    /** 发货时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发货时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date shipTime;

    /** 收货时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "收货时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date receiveTime;

    /** 收货备注 */
    @Excel(name = "收货备注")
    private String receiveMessage;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private Integer deleted;

    @ApiParam(name = "退货物流单号")
    private String refundDvyFlowNum;

}
