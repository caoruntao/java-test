package com.caort.spring.aop.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author Reed
 * @date 2021/10/14 上午10:49
 */
@Service
public class HelloService {
    private static final Logger log = LoggerFactory.getLogger(HelloService.class);
    public String hello(){
        log();
        return say("Hello World");
    }
    public String say(String msg){
        return msg;
    }

    @Async
    public void log(){
        log.info("log info");
    }
}
