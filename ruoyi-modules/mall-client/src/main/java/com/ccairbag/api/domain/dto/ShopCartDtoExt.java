package com.ccairbag.api.domain.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.List;

@Data
public class ShopCartDtoExt {
    private List<ShopCartDto> shopCartDtoList;
    @ApiParam(value = "备注")
    private String remarks;
    @ApiParam(value = "支付方式")
    private Integer payType;
    @ApiParam(value = "用户地址id")
    private Long addrId;
}
