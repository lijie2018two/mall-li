package com.ruoyi.system.api.domain.ccairbag.dto;

import com.ruoyi.system.api.domain.ccairbag.CcairbagOrderDetails;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ShopOrderDTO {
    private String shopName;
    private Long shopId;
    private List<CcairbagOrderDetails> orderDetailsList;
    private BigDecimal shopTotalAmount;
}
