package com.ruoyi.system.api.domain.ccairbag;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 商品类目对象 ccairbag_category
 * 
 * @author lidabai
 * @date 2025-02-21
 */
@Data
public class CcairbagCategory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 类目ID */
    private Long categoryId;

    /** 父节点 */
    @Excel(name = "父节点")
    private Long parentId;

    /** 产品类目名称 */
    @Excel(name = "产品类目名称")
    private String categoryName;

    @Excel(name = "产品节点类目名称")
    private String fullCategoryName;

    @Excel(name = "产品类目名称")
    private String categoryNameEn;

    @Excel(name = "产品节点类目名称")
    private String fullCategoryNameEn;

    /** 排序 */
    @Excel(name = "排序")
    private Integer seq;

    /** 默认是1，表示正常状态,0为下线状态 */
    @Excel(name = "默认是1，表示正常状态,0为下线状态")
    private Integer status;

    /** 记录时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "记录时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date recTime;

    /** 分类层级 */
    @Excel(name = "分类层级")
    private Integer grade;

    /** 是否删除 */
    private Integer deleted;

    private List<CcairbagCategory> children = new ArrayList<>();



    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() 
    {
        return categoryId;
    }
    public void setParentId(Long parentId) 
    {
        this.parentId = parentId;
    }

    public Long getParentId() 
    {
        return parentId;
    }
    public void setCategoryName(String categoryName) 
    {
        this.categoryName = categoryName;
    }

    public String getCategoryName() 
    {
        return categoryName;
    }
    public void setSeq(Integer seq) 
    {
        this.seq = seq;
    }

    public Integer getSeq() 
    {
        return seq;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setRecTime(Date recTime) 
    {
        this.recTime = recTime;
    }

    public Date getRecTime() 
    {
        return recTime;
    }
    public void setGrade(Integer grade) 
    {
        this.grade = grade;
    }

    public Integer getGrade() 
    {
        return grade;
    }
    public void setDeleted(Integer deleted) 
    {
        this.deleted = deleted;
    }

    public Integer getDeleted() 
    {
        return deleted;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("categoryId", getCategoryId())
            .append("parentId", getParentId())
            .append("categoryName", getCategoryName())
            .append("seq", getSeq())
            .append("status", getStatus())
            .append("recTime", getRecTime())
            .append("grade", getGrade())
            .append("deleted", getDeleted())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
