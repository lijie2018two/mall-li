package com.ccairbag.api.service.impl;

import com.ccairbag.api.mapper.CcairbagCarouselMapper;
import com.ccairbag.api.mapper.CcairbagProductPromotionRelationsMapper;
import com.ccairbag.api.mapper.CcairbagPromotionActivitiesMapper;
import com.ccairbag.api.service.ICcairbagIndexService;
import com.ccairbag.api.utils.EmailUtils;
import com.ruoyi.common.core.utils.oConvertUtils;
import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.system.api.domain.ccairbag.CcairbagCarousel;
import com.ruoyi.system.api.domain.ccairbag.CcairbagProducts;
import com.ruoyi.system.api.domain.ccairbag.CcairbagPromotionActivities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CcairbagIndexServiceImpl implements ICcairbagIndexService {

    @Resource
    private CcairbagCarouselMapper ccairbagCarouselMapper;

    @Resource
    private CcairbagPromotionActivitiesMapper ccairbagPromotionActivitiesMapper;
    @Resource
    private CcairbagProductPromotionRelationsMapper ccairbagProductPromotionRelationsMapper;
    @Autowired
    private EmailUtils emailUtils;
    @Override
    public AppResult index() {
        CcairbagCarousel ccairbagCarousel = new CcairbagCarousel();
        ccairbagCarousel.setCarouselType(0);
        List<CcairbagCarousel> ccairbagCarousels = ccairbagCarouselMapper.selectCcairbagCarouselList(ccairbagCarousel);
        CcairbagCarousel carousel = new CcairbagCarousel();
        carousel.setCarouselType(1);
        List<CcairbagCarousel> ccairbagCarouselList = ccairbagCarouselMapper.selectCcairbagCarouselList(carousel);
        Map<String, Object> map = new HashMap<>();
        map.put("carouselPcList", ccairbagCarousels);
        map.put("carouselAppList", ccairbagCarouselList);
        //查询 所有广告活动 以及 包含的 商品
        CcairbagPromotionActivities ccairbagPromotionActivities = new CcairbagPromotionActivities();
        ccairbagPromotionActivities.setStatus(0);
        List<CcairbagPromotionActivities> activitiesList = ccairbagPromotionActivitiesMapper.selectCcairbagPromotionActivitiesList(ccairbagPromotionActivities);
        // 处理活动列表，确保没有商品的活动对应的商品列表长度为 0
        for (CcairbagPromotionActivities activity : activitiesList) {
            List<CcairbagProducts> productsList = ccairbagProductPromotionRelationsMapper.selectProductList(activity.getActivityId());
            for (CcairbagProducts products : productsList){
                if (oConvertUtils.isNotEmpty(products.getDiscount())){
                    BigDecimal discountRate = new BigDecimal(products.getDiscount()).
                            divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                    BigDecimal price = products.getPrice().subtract(products.getPrice().
                            multiply(discountRate).setScale(2, RoundingMode.HALF_UP));
                    products.setPrice(price);
                }
            }
            activity.setProductsList(productsList);
            activity.setProductNum(productsList.size());
        }
        map.put("activitiesList", activitiesList);
        return new AppResult(map);
    }

    @Override
    public void test() {
        Map<String,Object> params = new HashMap<>();
        params.put("productName","name");
        params.put("orderNum","123");
        params.put("userName","abc");
        params.put("shopName","gddsss");
        params.put("productImg","https://file.ccairbag.com/9a4dd9ed61ea417db2cf2b7fb297ad45_1758182039030.jpg");
        params.put("money","434");
        emailUtils.sendTemplateMail("a185134478@gmail.com", "测试邮件", "orderEmail.html", params);
    }
}
