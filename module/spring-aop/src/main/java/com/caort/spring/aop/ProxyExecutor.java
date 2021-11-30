package com.caort.spring.aop;

/**
 * 通过AOP拦截 自动发送ExecutorEvent 事件
 *
 * @author Caort.
 * @date 2021/6/3 上午8:53
 */
public class ProxyExecutor {
    public void execute() {
        System.out.println("StaticExecutor execute is running");
    }
}
