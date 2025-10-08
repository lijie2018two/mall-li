package com.ccairbag.api.mapper;

import com.ruoyi.system.api.domain.ccairbag.CcairbagUserBrowseHistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户最近浏览记录Mapper接口
 * 
 * @author ruoyi
 * @date 2025-07-01
 */
public interface CcairbagUserBrowseHistoryMapper 
{
    int insert(CcairbagUserBrowseHistory record);

    int updateByPrimaryKey(CcairbagUserBrowseHistory record);

    CcairbagUserBrowseHistory selectByUserIdAndProductId(
            @Param("userId") Long userId,
            @Param("productId") Long productId);

    List<CcairbagUserBrowseHistory> selectByUserId(Long userId);

    // 查询商品状态（status != 0 表示已下架）
    List<Map<String, Object>> selectProductStatusByIds(@Param("productIds") List<Long> productIds);

    // 批量删除用户的浏览记录
    int deleteByUserAndProductIds(@Param("userId") Long userId, @Param("productIds") List<Long> productIds);

    int countByUserId(Long userId);

    Long selectThresholdId(@Param("userId") Long userId, @Param("limit") int limit);

    int deleteOldRecords(@Param("userId") Long userId, @Param("thresholdId") Long thresholdId);

    CcairbagUserBrowseHistory selectFullInfoByUserIdAndProductId(
            @Param("userId") Long userId,
            @Param("productId") Long productId);
}
