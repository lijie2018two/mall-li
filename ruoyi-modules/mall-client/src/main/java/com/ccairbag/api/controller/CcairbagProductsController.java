package com.ccairbag.api.controller;

import com.ccairbag.api.domain.dto.ProductSaveDTO;
import com.ccairbag.api.service.ICcairbagProductsService;
import com.ruoyi.common.core.utils.oConvertUtils;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.common.core.web.page.PageDataInfo;
import com.ruoyi.system.api.domain.ccairbag.CcairbagNegotiationRecord;
import com.ruoyi.system.api.domain.ccairbag.CcairbagProducts;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品Controller
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@RestController
@RequestMapping("ccairbag/products")
public class CcairbagProductsController extends BaseController
{
    @Autowired
    private ICcairbagProductsService ccairbagProductsService;




    @ApiOperation(value = "保存商品")
    @PostMapping("/saveProduct")
    public AppResult saveProduct(@RequestBody ProductSaveDTO productSaveDTO)
    {
        if (oConvertUtils.isEmpty(productSaveDTO.getProducts())){
            return AppResult.error("请填写商品信息");
        }
        if (oConvertUtils.isEmpty(productSaveDTO.getProducts().getIsNegotiable())){
            return AppResult.error("商品的是否议价请填写");
        }
//        if (oConvertUtils.isEmpty(productSaveDTO.getProducts().getMinPriceNegotiable())){
//            return AppResult.error("商品的自动拒绝价格请填写");
//        }
        return ccairbagProductsService.saveProduct(productSaveDTO);
    }

    @ApiOperation(value = "根据productId 获取商品信息")
    @GetMapping("/getProductInfo")
    public AppResult<ProductSaveDTO> getProductInfo(@ApiParam(value = "商品id", required = true) Long productId)
    {
        return ccairbagProductsService.getProductInfo(productId);
    }

    @ApiOperation(value = "商家 查询自己店铺下所有商品列表 ")
    @GetMapping("/getProductsByShopId")
    public AppResult<PageDataInfo<CcairbagProducts>> getProductsByShopId()
    {
        return ccairbagProductsService.getProductsByShopId();
    }

    @ApiOperation(value = " 根据分类id查询商品列表 ")
    @GetMapping("/getProductsByCategoryId")
    public AppResult<PageDataInfo<CcairbagProducts>> getProductsByCategoryId(@ApiParam(value = "分类id", required = true) Long categoryId)
    {
        if (oConvertUtils.isEmpty(categoryId)){
            return AppResult.error("分类id不能为空");
        }
        return ccairbagProductsService.getProductsByCategoryId(categoryId);
    }




    @ApiOperation(value = "商家给商品上下架 1 上架 0 下架")
    @GetMapping("/updateProductStatus")
    public AppResult updateProductStatus(
            @ApiParam(value = "商品id", required = true) Long productId,
            @ApiParam(value = "状态 0 上架 1 下架", required = true) Integer status)
    {
        if (oConvertUtils.isEmpty(productId)) {
            return AppResult.error("商品id不能为空");
        }
        if (status == null || (status != 1 && status != 0)) {
            return AppResult.error("状态必须为 1 (上架) 或 0 (下架)");
        }
        return ccairbagProductsService.updateProductStatus(productId, status);
    }


    @ApiOperation(value = "根据多条件 查询商品列表")
    @GetMapping("/searchProducts")
    public AppResult<PageDataInfo<CcairbagProducts>> searchProducts(
            @ApiParam(value = "名字模糊搜索", required = false) String search,
            @ApiParam(value = "汽车品牌", required = false) String make,
            @ApiParam(value = "商店id", required = false) Long shopId,
            @ApiParam(value = "品牌", required = false) String brand,
            @ApiParam(value = "分类id", required = false) Long categoryId,
            @ApiParam(value = "活动id", required = false) Long activityId,
            @ApiParam(value = "新旧状态", required = false) Integer conditionType,
            @ApiParam(value = "包邮 到付", required = false) Integer deliveryFeeMode,
            @ApiParam(value = "最低价格", required = false) Double minPrice,
            @ApiParam(value = "最高价格", required = false) Double maxPrice,
            @ApiParam(value = "排序方式 1：价格升序，2：价格降序，3：销量升序，4：销量降序", required = false) Integer sortBy)
    {
        return ccairbagProductsService.searchProducts(activityId,search,make,shopId, brand, categoryId, conditionType, deliveryFeeMode, minPrice, maxPrice, sortBy);
    }

    @ApiOperation("获取登录商家的上下架商品列表")
    @GetMapping("/offShelveProducts")
    public AppResult<PageDataInfo<CcairbagProducts>> getOffShelveProducts(
            @ApiParam(value = "商品名称模糊搜索",required = false) String productName,
            @ApiParam(value = "状态 0 上架  1下架",required = false) Integer prodShopStatus) {

        return ccairbagProductsService.getOffShelveProducts(productName,prodShopStatus);
    }

    @ApiOperation("获取登录用户的推荐商品")
    @GetMapping("/getRecommendProducts")
    public AppResult<List<CcairbagProducts>> getRecommendProducts() {

        return ccairbagProductsService.getRecommendProducts();
    }


    @ApiOperation("商家删除商品")
    @GetMapping("/deleteProduct")
    public AppResult deleteProduct(
            @ApiParam(value = "商品id", required = true) @RequestParam Long productId) {
        if (oConvertUtils.isEmpty(productId)) {
            return AppResult.error("商品id不能为空");
        }
        return ccairbagProductsService.deleteProduct(productId);
    }


    @ApiOperation("买家 提交议价金额")
    @PostMapping("/saveNegotiableMoney")
    public AppResult saveNegotiableMoney(@RequestBody CcairbagProducts products) {
        if (oConvertUtils.isEmpty(products.getProductId())) {
            return AppResult.error("商品id不能为空");
        }
        if (oConvertUtils.isEmpty(products.getMoney())) {
            return AppResult.error("价格不能为空");
        }
        return ccairbagProductsService.saveNegotiableMoney(products);
    }


    @ApiOperation("卖家 审批议价 ")
    @PostMapping("/checkNegotiableMoney")
    public AppResult checkNegotiableMoney(@RequestBody CcairbagNegotiationRecord record) {
        if (oConvertUtils.isEmpty(record.getId())) {
            return AppResult.error("议价id不能为空");
        }

        if (oConvertUtils.isEmpty(record.getStatus())) {
            return AppResult.error("状态不能为空");
        }
        if (oConvertUtils.isEmpty(record.getStatus()==0)) {
            return AppResult.error("状态不能为待审核");
        }
        return ccairbagProductsService.checkNegotiableMoney(record);
    }


    @ApiOperation("卖家查看议价商品列表")
    @GetMapping("/listNegotiableMoney")
    public AppResult listNegotiableMoney() {

        return ccairbagProductsService.listNegotiableMoney();
    }

    @ApiOperation("买家查看议价商品列表")
    @GetMapping("/buyerListNegotiableMoney")
    public AppResult buyerListNegotiableMoney() {

        return ccairbagProductsService.buyerListNegotiableMoney();
    }

    @GetMapping("/systemcloseNegotiableTaskExt")
    public void systemcloseNegotiableTaskExt(String id) {

        ccairbagProductsService.systemcloseNegotiableTaskExt(id);
    }




}
