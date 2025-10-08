package com.ruoyi.system.api.domain.ccairbag;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.List;

/**
 * 活动对象 ccairbag_promotion_activities
 * 
 * @author lidabai
 * @date 2025-05-08
 */
@Data
public class CcairbagPromotionActivities extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long activityId;

    /** 活动名称 */
    @Excel(name = "活动名称")
    private String activityName;

    /** 简介 */
    @Excel(name = "简介")
    private String activityDescription;

    /** 活动图片 */
    @Excel(name = "活动图片")
    private String pic;

    /** 抽成比例 */
    @Excel(name = "抽成比例")
    private BigDecimal commissionRate;

    /** 活动状态 0：上线 1：下线 */
    @Excel(name = "活动状态 0：上线 1：下线")
    private Integer status;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private Integer deleted;

    private Integer productNum;

    private List<CcairbagProducts> productsList;

    public void setActivityId(Long activityId) 
    {
        this.activityId = activityId;
    }

    public Long getActivityId() 
    {
        return activityId;
    }
    public void setActivityName(String activityName) 
    {
        this.activityName = activityName;
    }

    public String getActivityName() 
    {
        return activityName;
    }
    public void setActivityDescription(String activityDescription) 
    {
        this.activityDescription = activityDescription;
    }

    public String getActivityDescription() 
    {
        return activityDescription;
    }
    public void setPic(String pic) 
    {
        this.pic = pic;
    }

    public String getPic() 
    {
        return pic;
    }
    public void setCommissionRate(BigDecimal commissionRate) 
    {
        this.commissionRate = commissionRate;
    }

    public BigDecimal getCommissionRate() 
    {
        return commissionRate;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
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
            .append("activityId", getActivityId())
            .append("activityName", getActivityName())
            .append("activityDescription", getActivityDescription())
            .append("pic", getPic())
            .append("commissionRate", getCommissionRate())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("deleted", getDeleted())
            .toString();
    }
}
