package com.caort.coupon.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author caort
 * @date 2022/8/25 08:52
 */
@SpringBootApplication(scanBasePackages = "com.caort.coupon")
@EnableJpaAuditing
//用于扫描Dao @Repository
@EnableJpaRepositories("com.caort.coupon")
//用于扫描JPA实体类 @Entity，默认扫本包当下路径
@EntityScan("com.caort.coupon")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
