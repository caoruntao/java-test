package com.caort.mail.event;

import org.springframework.context.ApplicationEvent;
import com.caort.mail.pojo.NotificationInfo;

/**
 * @author Caort
 * @date 2021/11/25 20:01
 */
public class MailNotificationEvent extends ApplicationEvent {
    public MailNotificationEvent(NotificationInfo source) {
        super(source);
    }
}
