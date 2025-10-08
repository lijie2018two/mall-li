package com.ccairbag.api.service.impl;

import com.ccairbag.api.mapper.CcairbagUserBrowseHistoryMapper;
import com.ccairbag.api.service.ICcairbagUserBrowseHistoryService;
import com.ruoyi.common.core.utils.PageUtils;
import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.common.core.web.page.PageDataInfo;
import com.ruoyi.common.redis.service.RedisService;
import com.ruoyi.system.api.domain.ccairbag.CcairbagProducts;
import com.ruoyi.system.api.domain.ccairbag.CcairbagUserBrowseHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * 用户最近浏览记录Service业务层处理
 *
 * @author ruoyi
 * @date 2025-07-01
 */
@Service
public class CcairbagUserBrowseHistoryServiceImpl implements ICcairbagUserBrowseHistoryService
{
    @Resource
    private CcairbagUserBrowseHistoryMapper browseHistoryMapper;

    @Autowired
    private RedisService redisService;

    @Override
    @Transactional
    public void addBrowseHistory(Long userId,CcairbagProducts products,String shopName) {
        // 检查是否已有记录
        CcairbagUserBrowseHistory existingHistory = browseHistoryMapper.selectByUserIdAndProductId(userId, products.getProductId());

        if (existingHistory == null) {
            // 不存在记录，插入新记录
            CcairbagUserBrowseHistory history = new CcairbagUserBrowseHistory();
            history.setUserId(userId);
            history.setProductId(products.getProductId());
            history.setShopId(products.getShopId());
            history.setPic(products.getPic());
            history.setProductName(products.getProductName());
            history.setShopName(shopName);
            history.setPrice(products.getPrice());
            history.setImgs(products.getImgs());
            history.setBrowseTime(new Date());

            browseHistoryMapper.insert(history);
        } else {
            // 存在记录，更新浏览时间
            existingHistory.setBrowseTime(new Date());
            browseHistoryMapper.updateByPrimaryKey(existingHistory);
        }

        // 异步更新Redis
//        updateRedisAsync(userId, products.getProductId());

        // 异步清理旧的浏览记录
//        cleanOldHistoryAsync(userId);
    }

    @Override
    public AppResult getRecentlyViewedProducts(Long userId, Integer pageNum, Integer pageSize) {
        // 优先从Redis获取

        PageUtils.startPage();
        List<CcairbagUserBrowseHistory> historyList = browseHistoryMapper.selectByUserId(userId);
        PageDataInfo<CcairbagUserBrowseHistory> pageDataInfo = new PageDataInfo<>(historyList);
        PageUtils.clearPage();
        return new AppResult(pageDataInfo);

    }

    @Async("asyncExecutor")
    public void cleanOfflineProductsAsync(Long userId, List<Long> productIds) {
        // 从Redis中删除
        String historyIdKey = "user:browse_history_ids:" + userId;
        for (Long productId : productIds) {
            String productKey = "product:" + productId;
            redisService.zrem(historyIdKey, productKey);
            redisService.deleteObject("browse_history:" + productId);
        }

        // 从数据库中删除
        browseHistoryMapper.deleteByUserAndProductIds(userId, productIds);
    }

    @Async("asyncExecutor")
    public void updateRedisAsync(Long userId, Long productId) {
        String historyIdKey = "user:browse_history_ids:" + userId;
        String productKey = "product:" + productId;

        // 获取当前时间戳作为分数
        double score = System.currentTimeMillis();

        // 先移除旧的记录（如果存在）
        redisService.zrem(historyIdKey, productKey);

        // 再添加新记录，确保排序正确
        redisService.zadd(historyIdKey, score, productKey);

        // 更新商品详情缓存
        updateProductCache(userId, productId);

        // 更新用户浏览历史列表
        updateUserBrowseHistoryList(userId);

        // 限制列表长度为10
        Set<Object> allProducts = redisService.zrangeByScore(historyIdKey, 0, Double.MAX_VALUE);
        if (allProducts != null && allProducts.size() > 10) {
            List<Object> productsToRemove = new ArrayList<>(allProducts);
            productsToRemove = productsToRemove.subList(0, productsToRemove.size() - 10);
            redisService.zrem(historyIdKey, productsToRemove.toArray());
            updateUserBrowseHistoryList(userId);
        }

        // 更新缓存有效期
        redisService.expire(historyIdKey, 1, TimeUnit.DAYS);
    }

    private void updateProductCache(Long userId, Long productId) {
        // 从数据库获取完整的浏览记录信息
        CcairbagUserBrowseHistory history = browseHistoryMapper.selectFullInfoByUserIdAndProductId(userId, productId);
        if (history != null) {
            String historyHashKey = "browse_history:" + productId;

            // 将浏览记录存入Hash
            Map<String, Object> historyMap = new HashMap<>();
            historyMap.put("userId", history.getUserId());
            historyMap.put("productId", history.getProductId());
            historyMap.put("productName", history.getProductName());
            historyMap.put("shopId", history.getShopId());
            historyMap.put("shopName", history.getShopName());
            historyMap.put("price", history.getPrice() != null ? history.getPrice().toString() : "");
            historyMap.put("pic", history.getPic());
            historyMap.put("imgs", history.getImgs());
            historyMap.put("browseTime", history.getBrowseTime().getTime());

            redisService.setCacheMap(historyHashKey, historyMap);
            redisService.expire(historyHashKey, 7, TimeUnit.DAYS);
        }
    }

    private void updateUserBrowseHistoryList(Long userId) {
        String historyIdKey = "user:browse_history_ids:" + userId;
        String historyListKey = "user:browse_history:" + userId;

        // 获取最新的10个浏览记录ID（按分数倒序排列）
        Set<Object> historyIds = redisService.zrevrangeByScore(historyIdKey, Double.MAX_VALUE, 0, 0, 10);

        // 构建完整的浏览记录列表
        List<CcairbagUserBrowseHistory> historyList = new ArrayList<>();
        for (Object idObj : historyIds) {
            String productKey = (String) idObj;
            Long productId = Long.valueOf(productKey.substring("product:".length()));

            String historyHashKey = "browse_history:" + productId;

            // 从Hash中获取浏览记录
            Map<String, Object> historyMap = redisService.getCacheMap(historyHashKey);
            if (historyMap != null && !historyMap.isEmpty()) {
                CcairbagUserBrowseHistory history = new CcairbagUserBrowseHistory();
                history.setUserId(Long.valueOf(historyMap.get("userId").toString()));
                history.setProductId(productId);
                history.setProductName((String) historyMap.get("productName"));
                history.setShopId(historyMap.get("shopId") != null ? Long.valueOf(historyMap.get("shopId").toString()) : null);
                history.setShopName((String) historyMap.get("shopName"));
                history.setPrice(historyMap.get("price") != null ? new BigDecimal(historyMap.get("price").toString()) : null);
                history.setPic((String) historyMap.get("pic"));
                history.setImgs((String) historyMap.get("imgs"));

                if (historyMap.get("browseTime") != null) {
                    long timestamp = Long.parseLong(historyMap.get("browseTime").toString());
                    history.setBrowseTime(new Date(timestamp));
                }

                historyList.add(history);
            }
        }
        // 只在列表不为空时更新缓存
        if (!historyList.isEmpty()) {
            redisService.setCacheList(historyListKey, historyList);
            redisService.expire(historyListKey, 1, TimeUnit.DAYS);
        } else {
            // 如果列表为空，删除现有缓存（可选）
            redisService.deleteObject(historyListKey);
        }

    }



    @Async("asyncExecutor")
    @Transactional
    public void cleanOldHistoryAsync(Long userId) {
        // 查询用户的浏览记录总数
        int count = browseHistoryMapper.countByUserId(userId);

        // 如果记录数超过10条，删除最早的记录
        if (count > 10) {
            // 获取第10条记录的ID
            Long thresholdId = browseHistoryMapper.selectThresholdId(userId, 10);
            if (thresholdId != null) {
                // 删除早于该ID的记录
                browseHistoryMapper.deleteOldRecords(userId, thresholdId);
            }
        }
    }


}
