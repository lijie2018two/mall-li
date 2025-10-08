package com.ccairbag.api.domain.dto;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.page.PageDataInfo;
import com.ruoyi.system.api.domain.ccairbag.*;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.List;

@Data
public class ProductSaveDTO {

    @ApiParam(value = "商店信息")
    private CcairbagShops shops;

    @ApiParam(value = "商品信息")
    private CcairbagProducts products;

    @ApiParam(value = "商品属性")
    private List<CcairbagParameters> parameters;

    @ApiParam(value = "商品适用车辆")
    private List<CcairbagProductCars> productCars;

    @ApiParam(value = "是否收藏")
    private Integer isCollect;


    @ApiParam(value = "商品评论列表")
    private PageDataInfo<CcairbagProductReviews> ccairbagProductReviewsList;

    @ApiParam(value = "商品评分")
    private Double productScore;

    @Excel(name = "国家名")
    private String country;


    @Excel(name = "城市")
    private String city;


    /** 州 / 省 / 大区名称;州 / 省 / 大区名称，不同国家可能有不同称呼 */
    @Excel(name = "州 / 省 / 大区名称;州 / 省 / 大区名称，不同国家可能有不同称呼")
    private String stateProvince;

    @ApiParam(value = "推荐商品信息")
    private List<CcairbagProducts> tjproductsList;

}
