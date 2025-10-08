package com.ruoyi.common.core.domain;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 发送消息基类
 */
@Data
@ToString
public class MqMessage<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 队列名称
     */
    private String queueType;
    private T body;


}
