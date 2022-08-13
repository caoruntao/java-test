package com.caort.editor;

import com.caort.editor.pojo.User;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Caort
 * @date 2022/8/13 8:39
 */
public class Application {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("META-INF\\property-editors-context.xml");

        User user = applicationContext.getBean("user", User.class);
        System.out.println(user);

        applicationContext.close();
    }
}
