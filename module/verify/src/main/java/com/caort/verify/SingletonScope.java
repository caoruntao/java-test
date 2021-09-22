package com.caort.verify;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @author Reed
 * @date 2021/9/22 下午1:14
 */
public class SingletonScope {
    public static void main(String[] args) {
        /*AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder
                .genericBeanDefinition(Student.class)
                .setScope(ConfigurableBeanFactory.SCOPE_SINGLETON)
                .getBeanDefinition();
        DefaultListableBeanFactory parentBeanFactory = new DefaultListableBeanFactory();
        parentBeanFactory.registerBeanDefinition("student", beanDefinition);
        Student student = parentBeanFactory.getBean(Student.class);
        System.out.println(student);
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.setParentBeanFactory(parentBeanFactory);
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.addPropertyValue("name", "xiaoming");
        beanDefinition.setPropertyValues(propertyValues);
        beanFactory.registerBeanDefinition("student", beanDefinition);
        Student student2 = beanFactory.getBean(Student.class);
        System.out.println(student2);*/

        AnnotationConfigApplicationContext parentApplicationContext = new AnnotationConfigApplicationContext();
        parentApplicationContext.register(Student.class);
        parentApplicationContext.refresh();
        System.out.println(parentApplicationContext.getBean(Student.class));
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.setParent(parentApplicationContext);
//        applicationContext.register(Student.class);
        applicationContext.refresh();
        System.out.println(applicationContext.getBean(Student.class));
        parentApplicationContext.close();
        applicationContext.close();
    }

    static class Student{
        private String name;
        private Integer age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }
}
