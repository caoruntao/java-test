package com.caort.resilience.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Caort
 * @date 2022/7/17 10:16
 */
@Service
public class TestService {
    private static final Logger LOG = LoggerFactory.getLogger(TestService.class);
    private static final String BACKEND_A = "backendA";
    private AtomicInteger number = new AtomicInteger(0);

    @CircuitBreaker(name = BACKEND_A, fallbackMethod = "fallBack")
    public String circuitBreaker(){
        int num = number.getAndIncrement();
        if(num % 2 == 0){
            LOG.info("模拟请求失败");
            throw new IllegalArgumentException("manual throw exception");
        }
        LOG.info("模拟请求成功");
        return String.valueOf(num);
    }

    private String fallBack(IllegalArgumentException e){
        LOG.info("服务降级成功");
        return "服务降级 from IllegalArgumentException";
    }

    private String fallBack(Exception e){
        LOG.info("服务降级成功");
        return "服务降级 from Exception";
    }


}
