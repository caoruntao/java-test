package com.caort.coupon.calculation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author caort
 * @date 2022/8/18 14:16
 */
@SpringBootApplication(scanBasePackages = "com.caort.coupon.calculation")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
