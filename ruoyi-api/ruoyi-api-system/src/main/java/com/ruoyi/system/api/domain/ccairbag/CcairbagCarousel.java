package com.ruoyi.system.api.domain.ccairbag;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * 轮播图对象 ccairbag_carousel
 * 
 * @author lidabai
 * @date 2025-03-07
 */
@Data
public class CcairbagCarousel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long carouselId;

    /** 图片地址 */
    @Excel(name = "图片地址")
    private String imageUrl;

    /** 录轮播图点击后跳转的链接 */
    @Excel(name = "录轮播图点击后跳转的链接")
    private String linkUrl;

    private String appLinkUrl;

    /** 简短说明文字 */
    @Excel(name = "简短说明文字")
    private String caption;

    /** 轮播图的展示顺序 */
    @Excel(name = "轮播图的展示顺序")
    private Long displayOrder;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 对应的商品 ID */
    @Excel(name = "对应的商品 ID")
    private Long associatedProductId;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private Integer deleted;

    private Integer type;

    private Integer carouselType;



}
