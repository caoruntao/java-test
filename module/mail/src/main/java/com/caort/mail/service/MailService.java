package com.caort.mail.service;

import com.caort.mail.pojo.NotificationInfo;
import com.caort.mail.pojo.OrderListQueryRequest;

import java.util.List;

/**
 * @author Caort
 * @date 2021/11/26 下午2:24
 */
public interface MailService {
    NotificationInfo getMailContext(String id);

    String sendMailContext(String id);

    List<NotificationInfo> getAllMailContext(OrderListQueryRequest queryRequest);
}
