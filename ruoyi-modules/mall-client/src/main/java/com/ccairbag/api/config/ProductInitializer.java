package com.ccairbag.api.config;

import com.ccairbag.api.mapper.CcairbagProductsMapper;
import com.ruoyi.common.redis.service.RedisService;
import com.ruoyi.system.api.domain.ccairbag.CcairbagProducts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class ProductInitializer implements CommandLineRunner {
    @Autowired
    public RedisTemplate redisTemplate;
    @Resource
    private CcairbagProductsMapper ccairbagProductsMapper;
    @Autowired
    private RedisService redisService;



    private static final String PRODUCT_STOCK_PREFIX = "product_stock:*";
    @Override
    public void run(String... args) throws Exception {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();

        Set<String> keys = redisTemplate.keys(PRODUCT_STOCK_PREFIX);
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
            log.info("已清除{}个历史库存缓存", keys.size());
        }

        // 从数据库中获取所有商品信息
        CcairbagProducts ccairbagProducts = new CcairbagProducts();
        List<CcairbagProducts> products = ccairbagProductsMapper.selectCcairbagProductsList(ccairbagProducts);
        for (CcairbagProducts product : products) {
            // 将商品信息存储到 Redis 中，以商品 ID 作为键
            String key = "product_stock:" + product.getProductId();
            redisService.setCacheObject(key, product.getTotalQuantity());
            log.debug("已缓存商品{}库存:{}", product.getProductId(), product.getTotalQuantity());
        }
        log.info("成功加载{}个商品库存到Redis", products.size());
        System.out.println("商品信息已加载到 Redis 中");
    }
}