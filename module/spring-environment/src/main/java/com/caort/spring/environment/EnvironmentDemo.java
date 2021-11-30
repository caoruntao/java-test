package com.caort.spring.environment;

import com.caort.spring.environment.conf.MyPropertySource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Caort.
 * @date 2021/5/21 上午9:53
 */
@Configuration
@PropertySource({"classpath:/META-INF/default.properties"})
public class EnvironmentDemo {
    @Value("${user.id}")
    Long id;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(EnvironmentDemo.class, MyPropertySource.class);
        applicationContext.refresh();
        EnvironmentDemo bean = applicationContext.getBean(EnvironmentDemo.class);
        System.out.println(bean.id);
        applicationContext.close();
    }

}
