package com.caort.datasource.aop;

import com.caort.datasource.config.DataSourceHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Reed
 * @date 2021/5/10 下午3:17
 */
@Component
@Aspect
public class DynamicDataSourceAop {
    private final String[] prefixes = new String[]{"find", "get", "select"};
    private static final Logger log = LoggerFactory.getLogger(DynamicDataSourceAop.class);

    @Pointcut("execution(* com.caort.datasource.repository.*.*(..))")
    void repository(){}

    @Before("repository()")
    void determineDataSource(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        for (String prefix : prefixes) {
            if(methodName.startsWith(prefix)){
                DataSourceHolder.markSlave();
                log.info("determineDataSource mark datasource[slave] from method[{}]", methodName);
                return;
            }
        }
        DataSourceHolder.markMaster();
        log.info("determineDataSource mark datasource[master] from method[{}]", methodName);
    }

    @After("repository()")
    void clearThreadLocal(){
        DataSourceHolder.clearThreadLocal();
        log.info("clear ThreadLocal is finished");
    }


}
