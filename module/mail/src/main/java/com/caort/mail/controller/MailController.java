package com.caort.mail.controller;

import com.caort.mail.pojo.NotificationInfo;
import com.caort.mail.pojo.OrderListQueryRequest;
import com.caort.mail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Caort
 * @date 2021/11/26 下午2:21
 */
@RestController
@RequestMapping("/mail")
public class MailController {
    @Autowired
    private MailService mailService;

    @GetMapping("/order")
    public List<NotificationInfo> getAllMailContext(@RequestParam(required = false) OrderListQueryRequest queryRequest) {
        return mailService.getAllMailContext(queryRequest);
    }

    @GetMapping("/order/{id}")
    public NotificationInfo getMailContext(@PathVariable("id") String id) {
        return mailService.getMailContext(id);
    }

    @GetMapping("/send/{id}")
    public String sendMailContext(@PathVariable("id") String id) {
        return mailService.sendMailContext(id);
    }

    @GetMapping("/cache/{ids}")
    public String addCacheKey(@PathVariable("ids") List<String> ids) {
        return mailService.addCacheKey(ids);
    }

}
