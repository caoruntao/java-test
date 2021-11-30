package com.caort.verify;

import org.junit.Test;

import java.util.List;

/**
 * @author Caort.
 * @date 2021/8/12 上午9:31
 */
public class OtherTest {
    @Test
    public void listTest() {
        List<String> stringList = List.of("1", "2", "3", "4");
        stringList.add("5");
    }

    @Test
    public void finallyTest() {
        try {
            Thread.sleep(1000L);
            System.exit(1);
        } catch (InterruptedException e) {
            // handle exception
            e.printStackTrace();
        } finally {
            System.out.println("finally");
        }
    }

    @Test
    public void valueTest() {
        System.out.println(value());
    }

    private Bean value() {
        Bean bean = new Bean(1);
        try {
            bean.setValue(2);
            return bean;
        } finally {
            bean.setValue(3);
        }
    }

    @Test
    public void test1() {
        int i = 4;
        System.out.println(i - 2 >> 1);
    }
}
