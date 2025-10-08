package com.ruoyi.system.api.domain.ccairbag;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * 商品收藏对象 ccairbag_product_favorite
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Data
public class CcairbagProductFavorite extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 收藏id */
    private Long favoriteId;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 商品id */
    @Excel(name = "商品id")
    private Long prodId;

    /** 是否删除;0 正常  1 删除 */
    @Excel(name = "是否删除;0 正常  1 删除")
    private Integer deleted;

    /** 1 收藏  0 取消收藏 */
    private int status;

}
