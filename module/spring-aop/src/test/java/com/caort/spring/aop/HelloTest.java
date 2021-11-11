package com.caort.spring.aop;

import com.caort.spring.aop.service.HelloService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Reed
 * @date 2021/10/14 上午10:50
 */
@SpringBootTest
public class HelloTest {
    @Autowired
    private HelloService helloService;

    @Test
    public void testNestedCall() {
        System.out.println(helloService.hello());
    }
}
