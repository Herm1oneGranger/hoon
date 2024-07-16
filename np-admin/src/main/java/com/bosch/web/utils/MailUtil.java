package com.bosch.web.utils;

import com.bosch.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;

@Component
@Slf4j
public class MailUtil {


    @Resource
    private JavaMailSender javaMailSender;

    @Autowired
    JavaMailSenderImpl mailSender;
    /**
     * 发送文本邮件
     *
     * @param
     *
     */
    public void sendSimpleMail() {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("2424834965@qq.com"); //邮件发送者
            mailMessage.setTo("Zhijie.GUO@cn.bosch.com"); // 邮件发给的人
            mailMessage.setSubject("mail.getSubject()");  // 邮件主题
            mailMessage.setText("mail.getContent()");  // 邮件内容
            //mailMessage.copyTo(copyTo);

            mailSender.send(mailMessage);

        } catch (Exception e) {
            log.error("邮件发送失败 {}", e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }


}

