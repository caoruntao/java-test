package com.caort.spring.cloud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Caort.
 * @date 2021/7/30 下午2:45
 */
@SpringBootApplication
@EnableEurekaServer
public class Application {
    @Value("${user.city:shang}")
    private String str;

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(Application.class, args);
        Application bean = applicationContext.getBean(Application.class);
        System.out.println(bean.getStr());
        System.out.println(bean.getStr());
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
