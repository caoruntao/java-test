package com.caort.spring.aop;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

/**
 * 手动发送 ExecutorEvent 事件
 *
 * @author Reed
 * @date 2021/6/3 上午8:45
 */
public class StaticExecutor implements ApplicationEventPublisherAware {
    private ApplicationEventPublisher applicationEventPublisher;

    public void execute() {
        System.out.println("StaticExecutor execute is running");
        applicationEventPublisher.publishEvent(new ExecutorEvent("static"));
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
