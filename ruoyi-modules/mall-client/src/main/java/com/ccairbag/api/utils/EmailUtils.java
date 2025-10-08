package com.ccairbag.api.utils;

import com.ccairbag.api.config.EmailConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;

@Slf4j
@Component
public class EmailUtils {


    @Autowired
    private EmailConfig config;
    @Autowired
    private TemplateEngine templateEngine;


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

    /**
     * 发送HTML模板邮件
     * @param to 收件人邮箱
     * @param subject 邮件主题
     * @param templateName 模板名称（在templates目录下的HTML文件）
     * @param params 模板中需要替换的参数
     */
    public void sendTemplateMail(String to, String subject, String templateName, Map<String, Object> params) {
        try {
            Context context = new Context();
            if (params != null && !params.isEmpty()) {
                params.forEach(context::setVariable);
            }

            String content = templateEngine.process(templateName, context);
            // 关键修改：使用配置好的邮件发送器
            JavaMailSenderImpl javaMailSender = (JavaMailSenderImpl) buildMailSender(config);

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(config.getUsername());
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            javaMailSender.send(message);
            log.info("模板邮件发送成功，收件人：{}，模板：{}", to, templateName);
        } catch (MessagingException e) {
            log.error("发送模板邮件时发生异常", e);
            throw new RuntimeException("邮件发送失败", e);
        }
    }
}

