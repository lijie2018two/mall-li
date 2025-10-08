package com.ccairbag.api.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisStockService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 扣减库存
     *
     * @param productId 商品ID
     * @param quantity  扣减数量
     * @return 是否扣减成功
     */
    public boolean reduceStock(Long productId, Integer quantity) {
        String key = "product_stock:" + productId;
        Long stock = redisTemplate.opsForValue().decrement(key, quantity);
        if (stock != null && stock >= 0) {
            return true; // 扣减成功
        } else {
            // 库存不足，回滚
            redisTemplate.opsForValue().increment(key, quantity);
            return false; // 扣减失败
        }
    }

    /**
     * 释放库存
     *
     * @param productId 商品ID
     * @param quantity  释放数量
     */
    public void releaseStock(Long productId, Integer quantity) {
        String key = "product_stock:" + productId;
        redisTemplate.opsForValue().increment(key, quantity);
    }


    public boolean lock(String orderId, String requestId, int expireTime) {
        Boolean success = redisTemplate.opsForValue()
                .setIfAbsent(orderId, requestId, expireTime, TimeUnit.SECONDS);
        return success != null && success;
    }

    public void unlock(String orderId, String requestId) {
        String currentValue = redisTemplate.opsForValue().get(orderId);
        if (requestId.equals(currentValue)) {
            redisTemplate.delete(orderId);
        }
    }


}