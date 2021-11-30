package com.caort.spring.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.event.EventListener;
import org.springframework.context.event.EventPublicationInterceptor;

import java.lang.reflect.Method;

/**
 * @author Caort.
 * @date 2021/6/3 上午8:56
 */
@EnableAspectJAutoProxy
public class ExecutorDemo {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ExecutorDemo.class, StaticExecutor.class, ProxyExecutor.class);
        applicationContext.refresh();

        StaticExecutor staticExecutor = applicationContext.getBean(StaticExecutor.class);
        staticExecutor.execute();

        ProxyExecutor proxyExecutor = applicationContext.getBean(ProxyExecutor.class);
        proxyExecutor.execute();

        applicationContext.close();
    }

    @Bean
    public Pointcut pointcut() {
        return new StaticMethodMatcherPointcut() {
            @Override
            public boolean matches(Method method, Class<?> aClass) {
                return "execute".equals(method.getName()) && ProxyExecutor.class.equals(aClass);
            }
        };
    }

    @Bean
    public MethodInterceptor methodInterceptor() {
        EventPublicationInterceptor eventPublicationInterceptor = new EventPublicationInterceptor();
        eventPublicationInterceptor.setApplicationEventClass(ExecutorEvent.class);
        return eventPublicationInterceptor;
    }

    @Bean
    public PointcutAdvisor pointcutAdvisor(Pointcut pointcut, MethodInterceptor methodInterceptor) {
        return new DefaultPointcutAdvisor(pointcut, methodInterceptor);
    }

    @EventListener(ExecutorEvent.class)
    public void onEvent(ExecutorEvent executorEvent) {
        System.out.println("listener executorEvent : " + executorEvent);
    }
}
