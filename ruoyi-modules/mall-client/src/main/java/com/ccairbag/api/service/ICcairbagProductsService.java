package com.ccairbag.api.service;

import com.ccairbag.api.domain.dto.ProductSaveDTO;
import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.common.core.web.page.PageDataInfo;
import com.ruoyi.system.api.domain.ccairbag.CcairbagNegotiationRecord;
import com.ruoyi.system.api.domain.ccairbag.CcairbagProducts;

import java.util.List;

/**
 * 商品Service接口
 * 
 * @author lidabai
 * @date 2025-02-20
 */
public interface ICcairbagProductsService 
{


    AppResult saveProduct(ProductSaveDTO productSaveDTO);

    AppResult<ProductSaveDTO> getProductInfo(Long productId);

    AppResult<PageDataInfo<CcairbagProducts>> getProductsByShopId();

    AppResult<PageDataInfo<CcairbagProducts>> getProductsByCategoryId(Long categoryId);
    AppResult updateProductStatus(Long productId, Integer status);

    AppResult<PageDataInfo<CcairbagProducts>> searchProducts(Long activityId, String search,String make,Long shopId,
                                                             String brand,
                                                              Long categoryId,
                                                              Integer conditionType,
                                                            Integer deliveryFeeMode,
                                                             Double minPrice,
                                                             Double maxPrice,
                                                             Integer sortBy);

    AppResult<PageDataInfo<CcairbagProducts>> getOffShelveProducts(String productName, Integer prodShopStatus);

    AppResult<List<CcairbagProducts>> getRecommendProducts();

    AppResult deleteProduct(Long productId);

    AppResult saveNegotiableMoney(CcairbagProducts products);

    AppResult checkNegotiableMoney(CcairbagNegotiationRecord record);
    AppResult listNegotiableMoney();

    AppResult buyerListNegotiableMoney();

    void systemcloseNegotiableTaskExt(String id);
}
