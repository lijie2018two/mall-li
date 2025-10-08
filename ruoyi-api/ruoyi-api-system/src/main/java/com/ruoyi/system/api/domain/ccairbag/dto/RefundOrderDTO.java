package com.ruoyi.system.api.domain.ccairbag.dto;

import com.ruoyi.common.core.annotation.Excel;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RefundOrderDTO {
    private Long detailId;

    @Excel(name = "申请类型名")
    private String buyerType;

    @Excel(name = "申请类型值")
    private String buyerValue;
    @Excel(name = "买家申请原因")
    private String buyerMsg;

    @Excel(name = "文件凭证json")
    private String photoFiles;

    @Excel(name = "卖家备注")
    private String sellerMsg;

    @Excel(name = "退款金额")
    private BigDecimal refundAmount;

    @Excel(name = "申请类型:1,仅退款,2退款退货")
    private Long applyType;


    @Excel(name = "处理状态:1为 初次待审核,2为 初次同意,3为初次不同意 4 商家取消 5：退货待审核 ；6 退货同意 ，7 退货不同意")
    private Integer refundSts;

    @ApiParam(name = "退货物流单号")
    private String refundDvyFlowNum;

    @ApiParam(name = "申请介入图片")
    private String photoFilesExt;

    /** 申请原因 */
    @ApiParam(name = "申请介入原因")
    private String buyerMsgExt;

}
