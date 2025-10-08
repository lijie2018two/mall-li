package com.ccairbag.api.service.impl;

import com.ccairbag.api.domain.dto.ProductSaveDTO;
import com.ccairbag.api.mapper.*;
import com.ccairbag.api.service.ICcairbagProductsService;
import com.ccairbag.api.service.ICcairbagUserBrowseHistoryService;
import com.ccairbag.api.service.ICcairbagUserCategoryService;
import com.ddty.common.mq.service.MessageSender;
import com.ruoyi.common.core.domain.MQProperties;
import com.ruoyi.common.core.domain.MqMessage;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.PageUtils;
import com.ruoyi.common.core.utils.oConvertUtils;
import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.common.core.web.page.PageDataInfo;
import com.ruoyi.common.redis.service.RedisService;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.domain.ccairbag.*;
import com.ruoyi.system.api.domain.ccairbag.dto.CcairbagNegotiationRecordDTO;
import com.ruoyi.system.api.model.LoginAppUser;
import org.springframework.amqp.AmqpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * 商品Service业务层处理
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Service
public class CcairbagProductsServiceImpl implements ICcairbagProductsService
{
    @Resource
    private CcairbagProductsMapper ccairbagProductsMapper;

    @Resource
    private CcairbagShopsMapper ccairbagShopsMapper;

    @Resource
    private CcairbagProductParameterValuesMapper productParameterValuesMapper;
    @Resource
    private CcairbagParametersMapper ccairbagParametersMapper;
    @Resource
    private CcairbagProductCarsMapper ccairbagProductCarsMapper;
    @Resource
    private CcairbagCategoryMapper ccairbagCategoryMapper;
    @Autowired
    private RedisService redisService;
    @Resource
    private CcairbagProductReviewsMapper ccairbagProductReviewsMapper;
    @Resource
    private CcairbagUserAddrMapper ccairbagUserAddrMapper;
    @Resource
    private CcairbagPromotionActivitiesMapper ccairbagPromotionActivitiesMapper;
    @Autowired
    private ICcairbagUserCategoryService ccairbagUserCategoryService;
    @Resource
    private CcairbagUserCategoryMapper ccairbagUserCategoryMapper;
    @Resource
    private CcairbagNegotiationRecordMapper ccairbagNegotiationRecordMapper;

    @Resource
    private CcairbagConversationsMapper ccairbagConversationsMapper;
    @Resource
    private CcairbagMessagesMapper ccairbagMessagesMapper;
    @Resource
    private MessageSender messageSender;
    @Resource
    private CcairbagProductFavoriteMapper ccairbagProductFavoriteMapper;
    @Autowired
    private ICcairbagUserBrowseHistoryService browseHistoryService;
    @Override
    @Transactional
    public AppResult saveProduct(ProductSaveDTO productSaveDTO) {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        CcairbagShops shops = ccairbagShopsMapper.getShopByUserId(loginAppUser.getCcairbagUsers().getUserId());
        if ( oConvertUtils.isEmpty(shops)){
            return AppResult.error("您无权限编辑商品哦");
        }
        CcairbagProducts products =  productSaveDTO.getProducts();
        products.setDeleted(0);
        products.setStatus(0);
        if (oConvertUtils.isEmpty(products.getProductId())){
            products.setStatus(0);
            products.setSalesVolume(0L);
            ccairbagProductsMapper.insertCcairbagProducts(products);
        }else {
            products.setStatus(0);
            products.setUpdateTime(DateUtils.getNowDate());
            ccairbagProductsMapper.updateCcairbagProducts(products);
        }


        productSaveDTO.getParameters().forEach(item->{
            if (oConvertUtils.isNotEmpty(item.getValueId())){
                //编辑 或者 删除 ： value 为null
                if (oConvertUtils.isEmpty(item.getValue())){
                    productParameterValuesMapper.deleteCcairbagProductParameterValuesByValueId(item.getValueId());
                }else {
                    CcairbagProductParameterValues parameterValues = new CcairbagProductParameterValues();
                    parameterValues.setProductId(products.getProductId());
                    parameterValues.setParameterId(item.getParameterId());
                    parameterValues.setDeleted(0);
                    parameterValues.setValue(item.getValue());
                    parameterValues.setValueId(item.getValueId());
                    productParameterValuesMapper.updateCcairbagProductParameterValues(parameterValues);
                }
            }else {
                //新增
                if (oConvertUtils.isNotEmpty(item.getValue())){
                    CcairbagProductParameterValues parameterValues = new CcairbagProductParameterValues();
                    parameterValues.setProductId(products.getProductId());
                    parameterValues.setParameterId(item.getParameterId());
                    parameterValues.setDeleted(0);
                    parameterValues.setValue(item.getValue());
                    productParameterValuesMapper.insertCcairbagProductParameterValues(parameterValues);
                }
            }
        });

        ccairbagProductCarsMapper.deleteCcairbagProductCarsByProductId(products.getProductId());
        productSaveDTO.getProductCars().forEach(item->{
            item.setProductId(products.getProductId());
            ccairbagProductCarsMapper.insertCcairbagProductCars(item);
        });

        String key = "product_stock:" + products.getProductId();
        redisService.setCacheObject(key, products.getTotalQuantity());
        return AppResult.success("保存成功");
    }

    @Override
    public AppResult<ProductSaveDTO> getProductInfo(Long productId) {
        //每次查看详情 都在redis 把productViewCount 浏览量+1
        CcairbagProducts products = ccairbagProductsMapper.selectCcairbagProductsByProductId(productId);
        if (oConvertUtils.isEmpty(products)){
            return AppResult.error("商品不存在");
        }
        redisService.incr("productViewCount:"+productId,1);

        if (oConvertUtils.isNotEmpty(products.getDiscount())){
            BigDecimal price = BigDecimal.ZERO;
            BigDecimal discountRate = new BigDecimal(products.getDiscount()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            price = products.getPrice().multiply(discountRate).setScale(2, RoundingMode.HALF_UP);
            price = products.getPrice().subtract(price);
            products.setPrice(price);
        }
        CcairbagShops shops = ccairbagShopsMapper.selectCcairbagShopsByShopId(products.getShopId());
        // 初始化用户相关数据
        Long userId = null;
        Integer isCollect = 0; // 默认未收藏
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();

        // 用户已登录的情况
        if (oConvertUtils.isNotEmpty(loginAppUser)) {
            Long userIdx = loginAppUser.getCcairbagUsers().getUserId();

            // 收藏状态检查
            CcairbagProductFavorite favorite = new CcairbagProductFavorite();
            favorite.setProdId(productId);
            favorite.setUserId(userIdx);
            favorite.setStatus(1);
            List<CcairbagProductFavorite> favorites = ccairbagProductFavoriteMapper.selectCcairbagProductFavoriteList(favorite);
            isCollect = favorites.isEmpty() ? 0 : 1;

            // 异步记录用户喜好
            CompletableFuture.runAsync(() ->
                    ccairbagUserCategoryService.recordUserPreference(userIdx, products.getCategoryId())
            );

            browseHistoryService.addBrowseHistory(userIdx, products,shops.getShopName());

            userId = userIdx;
        }

        // 构建返回对象
        ProductSaveDTO productSaveDTO = new ProductSaveDTO();
        productSaveDTO.setIsCollect(isCollect);

        // 基础商品信息处理（以下为原有核心逻辑保持不变）
//        CcairbagShops shops = ccairbagShopsMapper.selectCcairbagShopsByShopId(products.getShopId());
        productSaveDTO.setShops(shops);
        productSaveDTO.setProducts(products);

        // 参数处理逻辑
        List<CcairbagProductParameterValues> parameterValues = productParameterValuesMapper.getParameterValuesByProductId(productId);
        CcairbagParameters queryParams = new CcairbagParameters();
        queryParams.setCategoryId(products.getCategoryId());

        // 其他参数处理逻辑保持不变...

        // 推荐商品处理（仅登录用户需要）
        List<CcairbagProducts> tjproductsList = new ArrayList<>();
        if (oConvertUtils.isNotEmpty(userId)) {
            // 获取用户最近10次访问记录
            List<CcairbagUserCategory> recentPreferences = ccairbagUserCategoryMapper.findRecentByUserId(userId);

            // 统计各分类访问次数
            Map<Long, Long> categoryCountMap = recentPreferences.stream()
                    .collect(Collectors.groupingBy(
                            CcairbagUserCategory::getCategoryId,
                            Collectors.counting()
                    ));

            // 计算推荐数量（复用原有算法）
            Map<Long, Integer> recommendationCounts = calculateRecommendationCounts(categoryCountMap, 10);

            // 获取推荐商品
            recommendationCounts.forEach((categoryId, count) -> {
                List<CcairbagProducts> recommendProducts = ccairbagProductsMapper.findRandomByCategoryId(categoryId, count);
                tjproductsList.addAll(recommendProducts);
            });

            Collections.shuffle(tjproductsList);
        } else {
            // 未登录用户推荐同分类商品
            CcairbagProducts query = new CcairbagProducts();
            query.setCategoryId(products.getCategoryId());
            List<CcairbagProducts> sameCategory = ccairbagProductsMapper.selectCcairbagProductsList(query);

            sameCategory.stream()
                    .filter(p -> !p.getProductId().equals(productId))
                    .limit(10)
                    .forEach(tjproductsList::add);
        }

        tjproductsList.forEach(recommendProduct -> {
            if (oConvertUtils.isNotEmpty(recommendProduct.getDiscount())){
                BigDecimal discountRate = new BigDecimal(recommendProduct.getDiscount()).
                        divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                BigDecimal price = recommendProduct.getPrice().subtract(recommendProduct.getPrice().
                        multiply(discountRate).setScale(2, RoundingMode.HALF_UP));
                recommendProduct.setPrice(price);
            }
        });
        productSaveDTO.setTjproductsList(tjproductsList);


        CcairbagParameters ccairbagParameters = new CcairbagParameters();
        ccairbagParameters.setCategoryId(products.getCategoryId());
        List<CcairbagParameters> ccairbagParametersList = ccairbagParametersMapper.selectCcairbagParametersList(ccairbagParameters);

        for (CcairbagProductParameterValues parameterValue : parameterValues){
            ccairbagParametersList.forEach(item->{
                if (parameterValue.getParameterId().equals(item.getParameterId())){
                    item.setValue(parameterValue.getValue());
                    item.setValueId(parameterValue.getValueId());
                }
            });
        }
        List<CcairbagParameters> ccairbagParametersListExt = new ArrayList<>();
        ccairbagParametersList.forEach(item->{
            if (oConvertUtils.isNotEmpty(item.getValue())){
                ccairbagParametersListExt.add(item);
            }
        });


        productSaveDTO.setParameters(ccairbagParametersListExt);


        CcairbagProductCars ccairbagProductCars = new CcairbagProductCars();
        ccairbagProductCars.setProductId(productId);
        List<CcairbagProductCars> productCarsList =  ccairbagProductCarsMapper.selectCcairbagProductCarsList(ccairbagProductCars);
        productSaveDTO.setProductCars(productCarsList);


        int i = redisService.getCacheObject("productViewCount:"+productId);
        products.setProductViewCount(i);


        //商品评价列表带分页
        CcairbagProductReviews reviews = new CcairbagProductReviews();
        reviews.setProdId(productId);
        PageUtils.startPage();
        List<CcairbagProductReviews> reviewsList = ccairbagProductReviewsMapper.selectCcairbagProductReviewsList(reviews);
        PageDataInfo<CcairbagProductReviews> pageDataInfo = new PageDataInfo<>(reviewsList);
        PageUtils.clearPage();
        productSaveDTO.setCcairbagProductReviewsList(pageDataInfo);

        Double avgScore = ccairbagProductReviewsMapper.selectAvgScoreByProductId(productId);
        productSaveDTO.setProductScore(avgScore);

        CcairbagUserAddr addr =ccairbagUserAddrMapper.selectCcairbagUserAddrByAddrId(products.getAddrId());
        if (oConvertUtils.isNotEmpty(addr)){
            productSaveDTO.setCountry(addr.getCountry());
            productSaveDTO.setStateProvince(addr.getStateProvince());
            productSaveDTO.setCity(addr.getCity());
        }




        Map<String, BigDecimal> avgmap = ccairbagProductReviewsMapper.selectAvgScoreByShopIdExt(shops.getShopId());
        if (oConvertUtils.isNotEmpty(avgmap)){
            shops.setAccuracyScore(avgmap.get("avgAccuracyScore"));
            shops.setLogisticsScore(avgmap.get("avglogisticsScore"));
            shops.setCommunicationScore(avgmap.get("avgCommunicationScore"));
        }
        BigDecimal rate = ccairbagProductsMapper.shopGoodRate(shops.getShopId());
        Integer sales = ccairbagProductsMapper.shopSales(shops.getShopId());

        shops.setSalesVolume(sales);
        shops.setGoodRate(rate);
        List<CcairbagCategory> ccairbagCategoryList = ccairbagCategoryMapper.getShopPopularCategoriesBySales(shops.getShopId());
        shops.setCcairbagCategoryList(ccairbagCategoryList);
        return new AppResult<>(productSaveDTO);
    }

    @Override
    public AppResult<PageDataInfo<CcairbagProducts>> getProductsByShopId() {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        CcairbagShops shops = ccairbagShopsMapper.getShopByUserId(loginAppUser.getCcairbagUsers().getUserId());
        if (oConvertUtils.isEmpty(shops)){
            return AppResult.error("您无权限编辑商品哦");
        }
        CcairbagProducts products = new CcairbagProducts();
        products.setShopId(shops.getShopId());
        PageUtils.startPage();
        List<CcairbagProducts> list = ccairbagProductsMapper.selectCcairbagProductsList(products);
        for (CcairbagProducts products1 : list){
            int ix = redisService.getCacheObject("productViewCount:"+products1.getProductId());
            products1.setProductViewCount(ix);

            String i = "product_stock:" + products1.getProductId();
            int key = redisService.getCacheObject(i);
            products1.setRedisQuantity(key);
        }
        PageDataInfo<CcairbagProducts> pageDataInfo = new PageDataInfo<>(list);
        PageUtils.clearPage();
        return new AppResult<>(pageDataInfo);
    }

    @Override
    public AppResult<PageDataInfo<CcairbagProducts>> getProductsByCategoryId(Long categoryId) {
        List<Long> categoryIds = getAllSubCategoryIds(categoryId);
        if (categoryIds.isEmpty()) {
            return AppResult.error("没有找到相关商品");
        }
        CcairbagProducts products = new CcairbagProducts();
        products.setCategoryIds(categoryIds);
        PageUtils.startPage();
        List<CcairbagProducts> list = ccairbagProductsMapper.selectCcairbagProductsListByCategoryIds(products);
        Collections.shuffle(list);
        PageDataInfo<CcairbagProducts> pageDataInfo = new PageDataInfo<>(list);
        PageUtils.clearPage();
        return new AppResult<>(pageDataInfo);
    }



    @Override
    public AppResult updateProductStatus(Long productId, Integer status) {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        CcairbagShops shops = ccairbagShopsMapper.getShopByUserId(loginAppUser.getCcairbagUsers().getUserId());
        if ( oConvertUtils.isEmpty(shops)){
            return AppResult.error("您无权限编辑商品哦");
        }
        CcairbagProducts product = ccairbagProductsMapper.selectCcairbagProductsByProductId(productId);
        if (product == null) {
            return AppResult.error("商品不存在");
        }
        if (!product.getShopId().equals(shops.getShopId())){
            return AppResult.error("您无权限编辑该商户的商品哦");
        }
        product.setStatus(status);
        int result = ccairbagProductsMapper.updateCcairbagProducts(product);
        if (result > 0) {
            return AppResult.success("商品状态更新成功");
        } else {
            return AppResult.error("商品状态更新失败");
        }

    }

    @Override
    public AppResult<PageDataInfo<CcairbagProducts>> searchProducts(Long activityId,String search,String make,Long shopId, String brand, Long categoryId, Integer conditionType, Integer deliveryFeeMode, Double minPrice, Double maxPrice, Integer sortBy) {
        CcairbagProducts products = new CcairbagProducts();

        if (oConvertUtils.isNotEmpty(categoryId)){
            List<Long> categoryIds = getAllSubCategoryIds(categoryId);
            if (categoryIds.size()==0) {
                return AppResult.error("没有找到相关商品");
            }
            products.setCategoryIds(categoryIds);

        }

        if (oConvertUtils.isNotEmpty(search)){
            products.setProductName(search);
        }
        if (oConvertUtils.isNotEmpty(brand)){
            products.setBrand(brand);
        }
        if (oConvertUtils.isNotEmpty(activityId)){
            products.setActivityId(activityId);
        }
        products.setConditionType(conditionType);
        products.setDeliveryFeeMode(deliveryFeeMode);
        products.setMinPrice(minPrice);
        products.setShopId(shopId);
        products.setMaxPrice(maxPrice);
        products.setSortBy(sortBy);
        products.setMake(make);
        PageUtils.startPage();
        List<CcairbagProducts> list = ccairbagProductsMapper.searchProductsExt(products);


        PageDataInfo<CcairbagProducts> pageDataInfo = new PageDataInfo<>(list);
        PageUtils.clearPage();
        for (CcairbagProducts products1 : list){
            List<CcairbagPromotionActivities> activities = ccairbagPromotionActivitiesMapper.selectCcairbagPromotionActivitiesListByProductId(products1.getProductId());
            products1.setActivities(activities);
            String i = "product_stock:" + products1.getProductId();
            int key = redisService.getCacheObject(i);
            products1.setRedisQuantity(key);

            CcairbagShops shops = ccairbagShopsMapper.selectCcairbagShopsByShopId(products1.getShopId());
            BigDecimal price = BigDecimal.ZERO;
            if (oConvertUtils.isNotEmpty(products1.getDiscount())) {
                // 如果存在折扣，使用折扣价：原价 * (discount / 100)
                BigDecimal discountRate = new BigDecimal(products1.getDiscount()).
                        divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                price = products1.getPrice().multiply(discountRate).setScale(2, RoundingMode.HALF_UP);
                price = products1.getPrice().subtract(price);
            } else {
                // 如果没有折扣，直接使用原价
                price = products1.getPrice();
            }

            products1.setPrice(price);
            BigDecimal rate = ccairbagProductsMapper.shopGoodRate(shops.getShopId());
            Integer sales = ccairbagProductsMapper.shopSales(shops.getShopId());
            shops.setGoodRate(rate);
            shops.setSalesVolume(sales);

            products1.setShops(shops);

        }
        return new AppResult<>(pageDataInfo);
    }

    private List<Long> getAllSubCategoryIds(Long categoryId) {
        List<Long> categoryIds = new ArrayList<>();
        categoryIds.add(categoryId);
        List<CcairbagCategory> subCategories = ccairbagCategoryMapper.selectCcairbagCategoriesByParentId(categoryId);
        for (CcairbagCategory subCategory : subCategories) {
            categoryIds.addAll(getAllSubCategoryIds(subCategory.getCategoryId()));
        }
        return categoryIds;
    }

    @Override
    public AppResult<PageDataInfo<CcairbagProducts>> getOffShelveProducts(
            String productName, Integer prodShopStatus) {

        // 获取当前登录商家
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        Long userId = loginAppUser.getCcairbagUsers().getUserId();
        CcairbagShops shops = ccairbagShopsMapper.getShopByUserId(userId);
        // 获取商家关联的店铺ID（需根据实际业务获取）
        if (oConvertUtils.isEmpty(shops)) {
            return AppResult.error("当前用户未关联店铺");
        }
        CcairbagProducts ccairbagProducts = new CcairbagProducts();
        ccairbagProducts.setStatus(prodShopStatus);
        ccairbagProducts.setShopId(shops.getShopId());
        ccairbagProducts.setProductName(productName);
        // 构建查询条件
        PageUtils.startPage();
        List<CcairbagProducts> list = ccairbagProductsMapper.getOffShelveProducts(ccairbagProducts);
        PageDataInfo<CcairbagProducts> pageDataInfo = new PageDataInfo<>(list);
        PageUtils.clearPage();
        for (CcairbagProducts products1 : list){
            List<CcairbagPromotionActivities> activities = ccairbagPromotionActivitiesMapper.selectCcairbagPromotionActivitiesListByProductId(products1.getProductId());
            products1.setActivities(activities);
            String i = "product_stock:" + products1.getProductId();
            Integer key = redisService.getCacheObject(i);
            if (oConvertUtils.isNotEmpty(key)){
                products1.setRedisQuantity(key);
            }
            Integer x = redisService.getCacheObject("productViewCount:"+products1.getProductId());
            if (oConvertUtils.isNotEmpty(x)){
                products1.setProductViewCount(x);
            }else {
                products1.setProductViewCount(0);
            }

        }
        return new AppResult(pageDataInfo);
    }

    @Override
    public AppResult<List<CcairbagProducts>> getRecommendProducts() {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        List<CcairbagProducts> productsList = new ArrayList<>();
        if (oConvertUtils.isEmpty(loginAppUser)){
            return new AppResult<>(productsList);
        }else {
            getRecommendProductsExt(loginAppUser.getCcairbagUsers().getUserId());

            return new AppResult<>(productsList);
        }

    }

    private List<CcairbagProducts> getRecommendProductsExt(Long userId){
        List<CcairbagProducts> productsList = new ArrayList<>();
        // 1. 获取用户最近10次访问记录
        List<CcairbagUserCategory> recentPreferences = ccairbagUserCategoryMapper.findRecentByUserId(userId);

        // 2. 统计各分类的访问次数
        Map<Long, Long> categoryCountMap = recentPreferences.stream()
                .collect(Collectors.groupingBy(
                        CcairbagUserCategory::getCategoryId,
                        Collectors.counting()
                ));

        // 3. 计算每个分类应推荐的商品数量
        Map<Long, Integer> recommendationCounts = calculateRecommendationCounts(
                categoryCountMap, 10);

        // 4. 从每个分类中随机选择商品

        recommendationCounts.forEach((categoryId, count) -> {
            List<CcairbagProducts> products = ccairbagProductsMapper.findRandomByCategoryId(categoryId, count);
            productsList.addAll(products);
        });

        // 5. 随机打乱商品顺序
        Collections.shuffle(productsList);
        return productsList;
    }

    /**
     * 计算每个分类应推荐的商品数量
     */
    private Map<Long, Integer> calculateRecommendationCounts(
            Map<Long, Long> categoryCountMap, int totalCount) {

        // 计算总访问次数
        long totalVisits = categoryCountMap.values().stream().mapToLong(Long::longValue).sum();

        Map<Long, Integer> result = new HashMap<>();
        int remainingCount = totalCount;

        // 按比例分配推荐数量
        List<Map.Entry<Long, Long>> entries = new ArrayList<>(categoryCountMap.entrySet());
        entries.sort(Map.Entry.<Long, Long>comparingByValue().reversed());

        for (int i = 0; i < entries.size(); i++) {
            Map.Entry<Long, Long> entry = entries.get(i);
            Long categoryId = entry.getKey();
            Long count = entry.getValue();

            // 计算理论推荐数量
            double ratio = (double) count / totalVisits;
            int recommendedCount = (int) Math.round(ratio * totalCount);

            // 最后一个分类处理余数
            if (i == entries.size() - 1) {
                recommendedCount = remainingCount;
            } else {
                // 确保不超过剩余数量
                recommendedCount = Math.min(recommendedCount, remainingCount);
                remainingCount -= recommendedCount;
            }

            if (recommendedCount > 0) {
                result.put(categoryId, recommendedCount);
            }
        }

        return result;
    }

    @Override
    public AppResult deleteProduct(Long productId) {
        // 获取当前登录商家
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        Long userId = loginAppUser.getCcairbagUsers().getUserId();
        CcairbagShops shops = ccairbagShopsMapper.getShopByUserId(userId);

        // 获取商家关联的店铺ID（需根据实际业务获取）
        if (oConvertUtils.isEmpty(shops)) {
            return AppResult.error("当前用户未关联店铺");
        }

        // 检查商品是否存在且属于该商家
        CcairbagProducts product = ccairbagProductsMapper.selectCcairbagProductsByProductId(productId);
        if (product == null) {
            return AppResult.error("商品不存在");
        }
        if (!product.getShopId().equals(shops.getShopId())) {
            return AppResult.error("您无权限删除该商户的商品");
        }

        if (product.getStatus()==0){
            return AppResult.error("商品未下架，无法删除");
        }

        // 删除商品
        int result = ccairbagProductsMapper.deleteCcairbagProductsByProductId(productId);
        if (result > 0) {
            // 清除Redis缓存
            redisService.deleteObject("product_stock:" + productId);
            return AppResult.success("商品删除成功");
        } else {
            return AppResult.error("商品删除失败");
        }
    }

    @Override
    public AppResult saveNegotiableMoney(CcairbagProducts products) {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        Long userId = loginAppUser.getCcairbagUsers().getUserId();
        CcairbagShops shops = ccairbagShopsMapper.getShopByUserId(userId);
        List<CcairbagUserAddr> addrList = ccairbagUserAddrMapper.selectCcairbagUserAddrByUserIdExt(userId);
        if (addrList.isEmpty()) {
            return AppResult.error("请先添加收货地址");
        }
        CcairbagProducts product = ccairbagProductsMapper.selectCcairbagProductsByProductIdExt(products.getProductId());
        if (oConvertUtils.isEmpty(product)) {
            return AppResult.error("商品不存在");
        }
        if (product.getIsNegotiable()==1){
            //不可以
            return AppResult.error("该商品不可议价");

        }
        if (oConvertUtils.isNotEmpty(shops)){
            if (product.getShopId().equals(shops.getShopId())){
                return AppResult.error("不能议价自己的商品");
            }
        }


        //不同同时发起两个议价
        int i = ccairbagNegotiationRecordMapper.checkNegotiableMoney(userId,products.getProductId());
        if (i>0){
            return AppResult.error("不同同时发起两个议价");
        }
        //先判断该用户对这个商品是否 议价3回了
        CcairbagNegotiationRecord record = new CcairbagNegotiationRecord();
        record.setProductId(products.getProductId());
        record.setBuyerId(userId);
        List<CcairbagNegotiationRecord> negotiationRecords = ccairbagNegotiationRecordMapper.selectCcairbagNegotiationRecordList(record);

        if (negotiationRecords.size()>0){
            CcairbagNegotiationRecord record1 = negotiationRecords.get(0);
            if (record1.getNegotiationCount()==0){
                return AppResult.error("您已对这个商品已经3次议价了");
            }
            record1.setOfferPrice(products.getMoney());
            record1.setRemarks(products.getRemarks());
            record1.setStatus(0);
            record1.setNegotiationCount(record1.getNegotiationCount()-1);
            ccairbagNegotiationRecordMapper.updateCcairbagNegotiationRecord(record1);
            systemcloseNegotiableTask(String.valueOf(record1.getId()));
            return AppResult.success("议价成功，等待商家回复");
        }else {
            CcairbagNegotiationRecord recordx = new CcairbagNegotiationRecord();
            if (oConvertUtils.isNotEmpty(product.getMinPriceNegotiable())){
                if (products.getMoney().compareTo(product.getMinPriceNegotiable()) == -1) {
                    //议价失败 议价金额 必须大于 商品定义的最低议价金额 否则直接 拒绝
                    recordx.setProductId(products.getProductId());
                    recordx.setBuyerId(userId);
                    recordx.setStatus(3);
                    recordx.setShopId(product.getShopId());
                    recordx.setNegotiationCount(3);
                    recordx.setRemarks(products.getRemarks());
                    recordx.setOfferPrice(products.getMoney());
                    recordx.setCreateTime(DateUtils.getNowDate());
                    ccairbagNegotiationRecordMapper.insertCcairbagNegotiationRecord(recordx);

                }else {
                    recordx.setProductId(products.getProductId());
                    recordx.setBuyerId(userId);
                    recordx.setStatus(0);
                    recordx.setNegotiationCount(3);
                    recordx.setRemarks(products.getRemarks());

                    recordx.setShopId(product.getShopId());
                    recordx.setOfferPrice(products.getMoney());
                    recordx.setCreateTime(DateUtils.getNowDate());
                    ccairbagNegotiationRecordMapper.insertCcairbagNegotiationRecord(recordx);

                }
            }else {
                recordx.setProductId(products.getProductId());
                recordx.setBuyerId(userId);
                recordx.setStatus(0);
                recordx.setNegotiationCount(3);
                recordx.setRemarks(products.getRemarks());

                recordx.setShopId(product.getShopId());
                recordx.setOfferPrice(products.getMoney());
                recordx.setCreateTime(DateUtils.getNowDate());
                ccairbagNegotiationRecordMapper.insertCcairbagNegotiationRecord(recordx);

            }

            systemcloseNegotiableTask(String.valueOf(recordx.getId()));
            return AppResult.success("议价成功，等待商家回复");
        }

    }

    @Override
    @Transactional
    public AppResult checkNegotiableMoney(CcairbagNegotiationRecord record) {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        Long userId = loginAppUser.getCcairbagUsers().getUserId();
        CcairbagShops shops = ccairbagShopsMapper.getShopByUserId(userId);
        if (oConvertUtils.isEmpty(shops)) {
            return AppResult.error("商家不存在");
        }
        CcairbagNegotiationRecord record1 = ccairbagNegotiationRecordMapper.selectCcairbagNegotiationRecordById(record.getId());

        if (oConvertUtils.isEmpty(record1)) {
            return AppResult.error("议价记录不存在");

        }
        CcairbagConversations conversation = ccairbagConversationsMapper.selectConversation(record1.getBuyerId(),shops.getUserId());
        if (oConvertUtils.isEmpty(conversation)) {
            CcairbagConversations ccairbagConversations = new CcairbagConversations();
            ccairbagConversations.setBuyerId(record1.getBuyerId());
            ccairbagConversations.setSellerId(shops.getUserId());
            ccairbagConversations.setCreateTime(new Date());
            ccairbagConversations.setDeleted(0);
            ccairbagConversationsMapper.insertCcairbagConversations(ccairbagConversations);
        }else {
            conversation.setUpdateTime(new Date());
            ccairbagConversationsMapper.updateCcairbagConversations(conversation);
        }

        record1.setStatus(record.getStatus());
        ccairbagNegotiationRecordMapper.updateCcairbagNegotiationRecord(record1);

        CcairbagMessages ccairbagMessages = new CcairbagMessages();
        if (record.getStatus()==1){
            //发送邮件 或者站内消息
            ccairbagMessages.setMessageContent("议价商品价格已通过，请去创建订单");

        }else if (record.getStatus()==2){
            //拒绝 就改变状态 通知买家
            //发送邮件 或者站内消息
            ccairbagMessages.setMessageContent("议价商品价格不通过");
        }
        ccairbagMessages.setSenderType(0);
        ccairbagMessages.setHeadimgurl(shops.getShopLogo());
        ccairbagMessages.setUserName(shops.getShopName());
        ccairbagMessages.setSenderId(shops.getUserId());
        ccairbagMessages.setIsReadBuyer(0);
        ccairbagMessages.setIsReadSeller(1);
        ccairbagMessages.setConversationsId(conversation.getConversationId());

        ccairbagMessages.setMessageType(0);
        ccairbagMessages.setDeleted(0);
        ccairbagMessagesMapper.insertCcairbagMessages(ccairbagMessages);
        return new AppResult("卖家审批成功");
    }

    @Override
    public AppResult listNegotiableMoney() {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        Long userId = loginAppUser.getCcairbagUsers().getUserId();
        CcairbagShops shops = ccairbagShopsMapper.getShopByUserId(userId);
        if (oConvertUtils.isEmpty(shops)) {
            return AppResult.error("当前用户未关联店铺");
        }
        PageUtils.startPage();
        List<CcairbagNegotiationRecordDTO> ccairbagNegotiationRecords = ccairbagNegotiationRecordMapper.listNegotiableMoney(shops.getShopId());
        PageDataInfo<CcairbagNegotiationRecordDTO> pageDataInfo = new PageDataInfo<>(ccairbagNegotiationRecords);
        PageUtils.clearPage();
        return new AppResult(pageDataInfo);
    }

    @Override
    public AppResult buyerListNegotiableMoney() {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        Long userId = loginAppUser.getCcairbagUsers().getUserId();
        PageUtils.startPage();
        List<CcairbagNegotiationRecordDTO> ccairbagNegotiationRecords = ccairbagNegotiationRecordMapper.buyerListNegotiableMoney(userId);
        PageDataInfo<CcairbagNegotiationRecordDTO> pageDataInfo = new PageDataInfo<>(ccairbagNegotiationRecords);
        PageUtils.clearPage();

        return new AppResult(pageDataInfo);
    }

    public void systemcloseNegotiableTaskExt(String id) throws AmqpException {
        systemcloseNegotiableTask(id);
    }

    private void systemcloseNegotiableTask(String id) throws AmqpException {
        MqMessage<String> m = new MqMessage<>();
        m.setQueueType(MQProperties.negotiable_EXCHANGE_NAME);
        m.setBody(id);
        //测试3分钟
        messageSender.sendMessageWithDelay(MQProperties.negotiable_EXCHANGE_NAME,
                MQProperties.negotiable_ROUTE_KEY,m , Long.valueOf(15*1000));

    }
}
