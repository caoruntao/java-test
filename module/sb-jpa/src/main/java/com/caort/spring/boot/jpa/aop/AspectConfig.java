package com.caort.spring.boot.jpa.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author Caort
 * @date 2021/10/20 20:06
 */
//@Aspect
//@Component
public class AspectConfig {
    private static final Logger log = LoggerFactory.getLogger(AspectConfig.class);

    @Pointcut("execution(* *(..))")
    public void pointcut(){}

    @Before("pointcut()")
    public void before(){
        log.info("AspectConfig before ");
    }

    @Around("pointcut()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        joinPoint.proceed();
        log.info("AspectConfig before ");
    }
}
