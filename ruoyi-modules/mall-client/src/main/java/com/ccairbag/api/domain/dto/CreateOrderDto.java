package com.ccairbag.api.domain.dto;

import com.ruoyi.system.api.domain.ccairbag.CcairbagUserAddr;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateOrderDto {
    @ApiParam(value = "商品id")
    private Long productId;
    @ApiParam(value = "数量")
    private int quantity;
    @ApiParam(value = "备注")
    private String remarks;
    @ApiParam(value = "支付方式")
    private Integer payType;
    @ApiParam(value = "用户地址id")
    private Long addrId;
    @ApiParam(value = "议价金额")
    private BigDecimal negotiablePrice;
    @ApiParam(value = "地址信息")
    private CcairbagUserAddr ccairbagUserAddr;

    @ApiParam(value = "随机数")
    private String randomNum;
}
