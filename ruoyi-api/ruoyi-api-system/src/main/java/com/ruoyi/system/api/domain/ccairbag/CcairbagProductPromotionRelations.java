package com.ruoyi.system.api.domain.ccairbag;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * 商品 - 活动关联对象 ccairbag_product_promotion_relations
 * 
 * @author lidabai
 * @date 2025-05-08
 */
@Data
public class CcairbagProductPromotionRelations extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 商品id */
    @Excel(name = "商品id")
    private Long productId;

    /** 活动id */
    @Excel(name = "活动id")
    private Long activityId;

    /** 排序优先级 */
    @Excel(name = "排序优先级")
    private Long priority;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private Integer deleted;

}
