package com.ruoyi.auth.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Data
@Component
public class EmailConfig {
    /** 邮箱名称 */
    @Value("${spring.mail.name}")
    private String name;

    /** SMTP服务 */
    @Value("${spring.mail.host}")
    private String host;

    /** 端口 */
    @Value("${spring.mail.port}")
    private Integer port;

    /** 账号 */
    @Value("${spring.mail.username}")
    private String username;

    /** 密码 */
    @Value("${spring.mail.password}")
    private String password;

    /** 是否开启debug） */
    @Value("${spring.mail.debug}")
    private Boolean debug;

    /** 启用SSL */
    @Value("${spring.mail.enableSsl}")
    private Boolean enableSsl;


}
