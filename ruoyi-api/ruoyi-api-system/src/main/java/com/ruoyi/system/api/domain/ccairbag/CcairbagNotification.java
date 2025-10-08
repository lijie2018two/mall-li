package com.ruoyi.system.api.domain.ccairbag;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * 通知对象 ccairbag_notification
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Data
public class CcairbagNotification extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long notificationId;

    /**  */
    @Excel(name = "")
    private String title;

    /**  */
    @Excel(name = "")
    private String content;

    /**  */
    @Excel(name = "通知类型，如 '2'（买家订单通知）、'1'（注册通知）、'0'（系统通知）3：卖家订单通知 " +
            "4 退款通知 商家同意 ，5 退款通知 商家拒绝 6 退款通知 买家申请  ,7 商家填写运单号 通知 买家" +
            "8 用户提交退货运单号 通知商家 9 商家主动申请退款 ")
    private String notificationType;

    /** 0 是群发；1不是群发 */
    @Excel(name = "0 是群发；1不是群发")
    private Integer isBroadcast;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private Integer deleted;

    private Integer readStatus;

}
