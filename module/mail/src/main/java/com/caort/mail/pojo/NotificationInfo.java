package com.caort.mail.pojo;

/**
 * @author Caort
 * @date 2021/11/25 20:47
 */
public class NotificationInfo {
    private String partnerOrderId;
    private String from;
    private String subject;
    private String context;
    private String to;

    public NotificationInfo() {
    }

    public NotificationInfo(String partnerOrderId, String from, String subject, String context, String to) {
        this.partnerOrderId = partnerOrderId;
        this.from = from;
        this.subject = subject;
        this.context = context;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getPartnerOrderId() {
        return partnerOrderId;
    }

    public void setPartnerOrderId(String partnerOrderId) {
        this.partnerOrderId = partnerOrderId;
    }
}
