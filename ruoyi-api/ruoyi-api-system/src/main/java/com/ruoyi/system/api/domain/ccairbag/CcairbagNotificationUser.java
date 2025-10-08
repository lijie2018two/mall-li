package com.ruoyi.system.api.domain.ccairbag;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * 用户通知关联对象 ccairbag_notification_user
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Data
public class CcairbagNotificationUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /**  */
    @Excel(name = "")
    private Long notificationId;

    /**  */
    @Excel(name = "")
    private Long userId;


    /**  */
    @Excel(name = "")
    private Integer readStatus;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private Integer deleted;

}
