package com.ruoyi.system.api.domain.ccairbag;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 订单支付对象 ccairbag_order_payment
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Data
public class CcairbagOrderPayment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 支付结算单据ID */
    private Long paymentId;

    /** 支付单号 */
    @Excel(name = "支付单号")
    private String payNo;

    /** 外部订单流水号 */
    @Excel(name = "外部订单流水号")
    private String bizPayNo;

    /** order表中的订单号 */
    @Excel(name = "order表中的订单号")
    private Long orderId;

    /** 支付方式 1 微信支付 2 支付宝 */
    @Excel(name = "支付方式 0 paypal 1 支付宝 2 visa")
    private Integer payType;



    /** 支付金额 */
    @Excel(name = "支付金额")
    private BigDecimal payAmount;

    private String currency;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;


    @ApiParam(value = "商品数量")
    private Integer productNums;

    @ApiParam(value = "支付人账号")
    private String payerAccount;


    @ApiParam(value = "支付人账号ext")
    private String payerAccountExt;

    @ApiParam(value = "支付人名字")
    private String payerName;

    @ApiParam(value = "总物流运费")
    private BigDecimal totalFreightAmount;




    /** 支付状态 */
    @Excel(name = "支付状态 0 已支付 1 退款中 2已退款")
    private Integer payStatus;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private Integer deleted;

    public void setPaymentId(Long paymentId) 
    {
        this.paymentId = paymentId;
    }

    public Long getPaymentId() 
    {
        return paymentId;
    }
    public void setPayNo(String payNo) 
    {
        this.payNo = payNo;
    }

    public String getPayNo() 
    {
        return payNo;
    }
    public void setBizPayNo(String bizPayNo) 
    {
        this.bizPayNo = bizPayNo;
    }

    public String getBizPayNo() 
    {
        return bizPayNo;
    }



    public void setPayAmount(BigDecimal payAmount)
    {
        this.payAmount = payAmount;
    }

    public BigDecimal getPayAmount() 
    {
        return payAmount;
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
            .append("paymentId", getPaymentId())
            .append("payNo", getPayNo())
            .append("bizPayNo", getBizPayNo())
            .append("payType", getPayType())
            .append("payAmount", getPayAmount())
            .append("userId", getUserId())
            .append("createTime", getCreateTime())

            .append("payStatus", getPayStatus())
            .append("deleted", getDeleted())
            .toString();
    }
}
