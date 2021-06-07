package com.caort.spring.aop;

import org.springframework.context.ApplicationEvent;

/**
 * @author Reed
 * @date 2021/6/3 上午8:43
 */
public class ExecutorEvent extends ApplicationEvent {
    public ExecutorEvent(Object source) {
        super(source);
    }
}
