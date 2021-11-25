package com.caort.spring.cloud.alibaba.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Caort
 * @date 2021/8/11 20:34
 */
@RestController
@RequestMapping("/config")
@RefreshScope
public class ConfigController {

    @Value("${user.iphone}")
    private boolean useLocalCache;

    @RequestMapping("/get")
    public boolean get() {
        return useLocalCache;
    }
}
