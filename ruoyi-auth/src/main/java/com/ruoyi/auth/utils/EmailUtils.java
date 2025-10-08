package com.ruoyi.auth.utils;

import com.ruoyi.auth.config.EmailConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Slf4j
@Component
public class EmailUtils {


    @Autowired
    private EmailConfig config;


    /**
     * 发送文本邮件
     *
     * @param subject 邮件标题
     * @param text 内容
     * @param to 接收人
     */
    public void send(String subject, String text, String... to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(config.getUsername());
        message.setText(text);
        message.setSubject(subject);
        message.setTo(to);
        this.buildMailSender(config).send(message);
    }

    private JavaMailSender buildMailSender(EmailConfig config) {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(config.getHost());
        javaMailSender.setUsername(config.getUsername());
        javaMailSender.setPassword(config.getPassword());

        javaMailSender.setDefaultEncoding(StandardCharsets.UTF_8.name());
        if (null != config.getPort()) {
            javaMailSender.setPort(config.getPort());
        }

        Properties properties = new Properties();
        properties.put("mail.debug", Boolean.TRUE.equals(config.getDebug()));
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.connectiontimeout", 5000);
        properties.put("mail.smtp.writetimeout", 5000);
        properties.put("mail.smtp.starttls.enable", true);

        if (Boolean.TRUE.equals(config.getEnableSsl())) {
            properties.put("mail.smtp.ssl.enable", true);
            properties.put("mail.smtp.ssl.trust", config.getHost());
        }

        javaMailSender.setJavaMailProperties(properties);
        return javaMailSender;
    }
}

