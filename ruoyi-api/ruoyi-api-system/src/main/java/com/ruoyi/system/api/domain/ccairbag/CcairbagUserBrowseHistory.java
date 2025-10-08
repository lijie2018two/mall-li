package com.ruoyi.system.api.domain.ccairbag;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户最近浏览记录对象 ccairbag_user_browse_history
 * 
 * @author ruoyi
 * @date 2025-07-01
 */
@Data
public class CcairbagUserBrowseHistory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 商品ID */
    private Long productId;

    private String productName;

    private Long shopId;

    private String shopName;

    private BigDecimal price;

    private String pic;

    private String imgs;

    /** 浏览时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "浏览时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date browseTime;


}
