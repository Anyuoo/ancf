package com.anyu.common.util;


import io.micrometer.core.lang.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * 邮件发送
 *
 * @author Anyu
 * @since 2020/10/31
 */
@Component
public class MailClient {

    private static final Logger logger = LoggerFactory.getLogger(MailClient.class);

    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String from;

    /**
     * @param sender  发件人
     * @param to      收件人
     * @param subject 主题
     * @param content 内容
     */
    public void sendMail(@Nullable String sender, String to, String subject, String content) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            //sender 不存在默认发送者为系统
            if (StringUtils.isBlank(sender)) {
                helper.setFrom(from);
            } else {
                helper.setFrom(sender);
            }

            helper.setTo(to);
            helper.setText(content, true);
            helper.setSubject(subject);
            javaMailSender.send(mimeMessage);
            logger.info("{}向{}发送{}的邮件成功", from, to, content);
        } catch (MailException | MessagingException e) {
            logger.warn("{}向{}发送{}邮件发送失败！", from, to, content);
        }
    }


}
