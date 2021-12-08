package com.caort.mail.service.impl;

import com.caort.mail.client.MpkiClient;
import com.caort.mail.conf.MPKIProperties;
import com.caort.mail.pojo.*;
import com.caort.mail.service.MailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Caort.
 * @date 2021/11/26 下午2:27
 */
@Service
public class MailServiceImpl implements MailService {
    private static final Logger log = LoggerFactory.getLogger(MailServiceImpl.class);

    @Value("${spring.mail.username}")
    private String senderName;

    @Autowired
    private MPKIProperties mpkiProperties;
    @Autowired
    private MpkiClient client;
    @Autowired
    private MailService mailService;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public NotificationInfo getMailContext(String id) {
        try {
            // 登陆MPKI
            login();
            OrderInfoResponse orderInfoResponse = client.getOrderInfo(id);
            OrderInfo orderInfo = orderInfoResponse.getData();
            // 参数校验
            verify(orderInfo);
            // 生成通知内容
            return generateNotificationInfo(orderInfo);
        } catch (Exception e) {
            log.error("获取邮件内容失败", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String sendMailContext(String id) {
        try {
            NotificationInfo notificationInfo = mailService.getMailContext(id);

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(notificationInfo.getFrom());
            helper.setSubject(notificationInfo.getSubject());
            helper.setText(notificationInfo.getContext(), true);
            helper.setTo(notificationInfo.getTo());
            helper.setCc(notificationInfo.getCc());
            javaMailSender.send(message);
            log.info("发送邮件成功，发件人[{}]，收件人[{}]，主题[{}]，内容[{}]",
                    notificationInfo.getFrom(), notificationInfo.getTo(), notificationInfo.getSubject(), notificationInfo.getContext());
            return notificationInfo.getContext();
        } catch (Exception e) {
            log.error("发送邮件失败", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<NotificationInfo> getAllMailContext(OrderListQueryRequest queryRequest) {
        List<NotificationInfo> resultList = new ArrayList<>();
        if (queryRequest == null) {
            queryRequest = new OrderListQueryRequest();
            MPKIProperties.QueryInfo query = mpkiProperties.getQuery();
            queryRequest.setOrderType(query.getOrderType());
            queryRequest.setOrderState(query.getOrderState());
            queryRequest.setUserName(query.getUserName());
            queryRequest.setConfirmor(query.getConfirmor());
            queryRequest.setPage(query.getPage());
            queryRequest.setPageSize(query.getPageSize());
        }
        try {
            // 登陆MPKI
            login();
            List<OrderListResponse> orderList = client.getOrderList(queryRequest);
            for (OrderListResponse orderListResponse : orderList) {
                if (orderListResponse.getCode().intValue() != 0) {
                    continue;
                }
                OrderList list = orderListResponse.getData();
                for (SimpleOrderInfo simpleOrderInfo : list.getList()) {
                    NotificationInfo notificationInfo = mailService.getMailContext(simpleOrderInfo.getPartnerOrderId());
                    resultList.add(notificationInfo);
                }
            }
            return resultList;
        } catch (Exception e) {
            log.error("获取订单列表失败", e);
            throw new RuntimeException(e);
        }
    }

    public void login() throws Exception {
        String username = mpkiProperties.getLogin().getUsername();
        String password = mpkiProperties.getLogin().getPassword();
        LoginRequest loginRequest = new LoginRequest(username, password);
        LoginResponse loginResponse = client.login(loginRequest);
        Integer code = loginResponse.getCode();
        Assert.state(code.intValue() == 0,
                "用户[" + username + "]登陆失败, 失败原因:" + loginResponse.getError());
        UserInfo userInfo = loginResponse.getData();
        if (log.isDebugEnabled()) {
            log.debug("用户[{}],用户ID[{}]登陆mpki成功", userInfo.getName(), userInfo.getUid());
        }
    }

    private void verify(OrderInfo orderInfo) {
        if (orderInfo == null) {
            throw new IllegalArgumentException("查找不到对应的订单信息");
        }
        Assert.hasText(orderInfo.getOrgName(), "组织信息名为空，无法生成邮件主题");
        Assert.state(orderInfo.getPki() != null, "PKI信息为空，无法生成邮件主题");
        Assert.hasText(orderInfo.getPki().getCsrCommonName(), "CN项为空，无法生成邮件主题");
        Assert.hasText(orderInfo.getAdminEmail(), "订单联系人邮箱为空，找不到收件人，无法发送邮件");
    }

    private NotificationInfo generateNotificationInfo(OrderInfo orderInfo) throws JsonProcessingException {
        NotificationInfo notificationInfo = new NotificationInfo();
        String subject = orderInfo.getOrgName() + orderInfo.getPki().getCsrCommonName() + "域名信息验证";
        String recipientEmail = orderInfo.getAdminEmail();

        Context context = new Context();
        context.setVariable("name", orderInfo.getAdminLastName() + orderInfo.getAdminFirstName());
        context.setVariable("orderId", orderInfo.getPartnerOrderId());
        // 拼接域名验证信息
        List<AuthInfo> authInfos = orderInfo.getAuths();
        if (CollectionUtils.isEmpty(authInfos)) {
            notificationInfo.setContext("无相关域名验证信息");
            return notificationInfo;
        }
        List<List> dcvInfoList = new ArrayList<>(authInfos.size());
        for (AuthInfo authInfo : authInfos) {
            List<String> dcvInfo = new ArrayList<>(4);
            dcvInfo.add("域名:" + authInfo.getDomain());
            String authMethod = StringUtils.hasText(authInfo.getAuthMethod())
                    ? authInfo.getAuthMethod() : orderInfo.getDvAuthMethod();
            if ("DNS".equalsIgnoreCase(authMethod)) {
                String authDomain = StringUtils.hasText(authInfo.getAuthDomain()) ?
                        authInfo.getAuthDomain() : authInfo.getAuthKey();
                dcvInfo.add("验证方式:" + authMethod);
                dcvInfo.add("TXT验证域名:" + authDomain);
                dcvInfo.add("记录值:" + authInfo.getAuthValue());
            } else if ("FILE".equalsIgnoreCase(authMethod)) {
                dcvInfo.add("验证方式:" + authMethod);
                String filePath = StringUtils.hasText(authInfo.getAuthPath())
                        ? authInfo.getAuthPath() + authInfo.getAuthKey() : authInfo.getAuthKey();
                dcvInfo.add("验证文件路径:" + filePath);
                dcvInfo.add("记录值:" + authInfo.getAuthValue());
            } else if ("EMAIL".equalsIgnoreCase(authMethod)) {
                dcvInfo.add("验证方式:" + authMethod);
                dcvInfo.add("邮箱" + authInfo.getAuthValue());
            } else if ("DNS_PROXY".equalsIgnoreCase(authMethod)) {
                dcvInfo.add("CNAME主机记录:" + authInfo.getDns_cname_record());
                dcvInfo.add("CNAME记录值:" + authInfo.getDns_proxy_cname());

                context.setVariable("dnsProxy", true);
            }
            dcvInfoList.add(dcvInfo);
        }
        context.setVariable("dcvInfoList", dcvInfoList);
        // Globalsign不用显示主机名
        context.setVariable("showable",
                "Globalsign".equalsIgnoreCase(orderInfo.getProductBrand()) ? false : true);
        String emailContext = templateEngine.process("dcvEmail", context);

        notificationInfo.setPartnerOrderId(orderInfo.getPartnerOrderId());
        notificationInfo.setFrom(senderName);
        notificationInfo.setSubject(subject);
        notificationInfo.setContext(emailContext);
        notificationInfo.setTo(recipientEmail);
        MPKIProperties.Mail mailInfo = mpkiProperties.getMail();
        if (mailInfo != null) {
            notificationInfo.setCc(mailInfo.getCc());
        }
        return notificationInfo;
    }
}
