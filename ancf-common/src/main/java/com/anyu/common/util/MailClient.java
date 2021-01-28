package com.anyu.common.util;


import io.micrometer.core.lang.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.List;

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
     * @param sender    发件人
     * @param receivers 收件人
     * @param subject   主题
     * @param content   内容
     */
    public void sendMail(@Nullable String sender, @NotNull List<String> receivers, String subject, String content) {
        receivers.forEach(receiver -> {
            try {
                final var mimeMessage = javaMailSender.createMimeMessage();
                final var helper = new MimeMessageHelper(mimeMessage);
                //sender 不存在默认发送者为系统
                if (StringUtils.isBlank(sender)) {
                    helper.setFrom(from);
                } else {
                    helper.setFrom(sender);
                }
                helper.setTo(receiver);
                helper.setText(content, true);
                helper.setSubject(subject);
                javaMailSender.send(mimeMessage);
                logger.info("{}向{}发送{}的邮件成功", from, receiver, content.substring(0, 15));
            } catch (MailException | MessagingException e) {
                logger.warn("{}向{}发送{}邮件发送失败！", from, receiver, content.substring(0, 15));
            }
        });
    }


}
