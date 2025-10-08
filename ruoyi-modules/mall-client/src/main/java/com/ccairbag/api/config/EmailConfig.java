package com.ccairbag.api.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "mail")
public class EmailConfig {
    /** 邮箱名称 */
    private String name;

    /** SMTP服务 */
    private String host;

    /** 端口 */
    private Integer port;

    /** 账号 */
    private String username;

    /** 密码 */
    private String password;

    /** 是否开启debug） */
    private Boolean debug;

    /** 启用SSL */
    private Boolean enableSsl;


}
