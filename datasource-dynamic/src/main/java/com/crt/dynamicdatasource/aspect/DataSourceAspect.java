package com.crt.dynamicdatasource.aspect;

import com.crt.dynamicdatasource.dynamic.DynamicDataSourceHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @Author reed
 * @Date 2020/7/17 9:25 上午
 */
@Component
@Aspect
public class DataSourceAspect {
    private static final String[] METHOD_PATTERNS = new String[] {"query", "select", "find", "get"};

    @Pointcut("execution(public * com.crt.dynamicdatasource.controller..*.*(..))")
    private void datasourcePoint(){}

    @Before("datasourcePoint()")
    private void before(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        if(isSlave(methodName)){
            DynamicDataSourceHolder.markSlave01();
        }else{
            DynamicDataSourceHolder.markMaster();
        }
    }

    private boolean isSlave(String methodName){
       for(String methodPattern : METHOD_PATTERNS){
           if(methodName.startsWith(methodPattern)){
               return true;
           }
       }
        return false;
    }
}
