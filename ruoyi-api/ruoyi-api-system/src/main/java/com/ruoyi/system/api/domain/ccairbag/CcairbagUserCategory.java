package com.ruoyi.system.api.domain.ccairbag;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 用户喜好分类对象 ccairbag_user_category
 * 
 * @author lidabai
 * @date 2025-05-10
 */
@Data
public class CcairbagUserCategory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 商品分类ID */
    @Excel(name = "商品分类ID")
    private Long categoryId;

    /** 访问时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "访问时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date accessTime;




}
