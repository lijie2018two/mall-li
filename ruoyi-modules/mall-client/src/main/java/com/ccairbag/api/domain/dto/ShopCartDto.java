package com.ccairbag.api.domain.dto;


import com.ruoyi.system.api.domain.ccairbag.CcairbagShoppingCarts;
import lombok.Data;

import java.util.List;

@Data
public class ShopCartDto {
    private String shopName;
    private String shopLogo;
    private Long shopId;
    private List<CcairbagShoppingCarts> products;


}
