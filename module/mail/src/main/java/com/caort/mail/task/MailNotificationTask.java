package com.caort.mail.task;

import com.caort.mail.event.MailNotificationEvent;
import com.caort.mail.pojo.NotificationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Caort
 * @date 2021/11/25 19:42
 */
@Component
public class MailNotificationTask implements ApplicationContextAware {
    private static final Logger log = LoggerFactory.getLogger(MailNotificationTask.class);
    @Autowired
    private JavaMailSender mailSender;

    private ApplicationContext applicationContext;

    @Value("${spring.mail.username}")
    private String form;

    @Scheduled(cron = "0/10 * * * * ?")
    public void notification() {
        NotificationInfo info = new NotificationInfo(form, "test", "test", "495902563@qq.com");
        MailNotificationEvent event = new MailNotificationEvent(info);
        applicationContext.publishEvent(event);
    }


    @EventListener(MailNotificationEvent.class)
    public void listener(MailNotificationEvent event) {
        NotificationInfo info = NotificationInfo.class.cast(event.getSource());

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(info.getForm());
        mailMessage.setSubject(info.getSubject());
        mailMessage.setText(info.getContext());
        mailMessage.setTo(info.getTo());
        mailSender.send(mailMessage);

        log.info("发送邮件成功，发件人[{}]，收件人[{}]，主题[{}]，内容[{}]",
                info.getForm(), info.getTo(), info.getSubject(), info.getContext());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
