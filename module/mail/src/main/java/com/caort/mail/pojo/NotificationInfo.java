package com.caort.mail.pojo;

/**
 * @author Caort
 * @date 2021/11/25 20:47
 */
public class NotificationInfo {
    private String form;
    private String subject;
    private String context;
    private String to;

    public NotificationInfo() {
    }

    public NotificationInfo(String form, String subject, String context, String to) {
        this.form = form;
        this.subject = subject;
        this.context = context;
        this.to = to;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
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
}
