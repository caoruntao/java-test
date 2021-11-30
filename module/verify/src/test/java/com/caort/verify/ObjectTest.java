package com.caort.verify;

import org.junit.Test;

/**
 * @author Caort.
 * @date 2021/9/17 上午8:51
 */
public class ObjectTest {
    @Test
    public void test1(){
        Object o = new Object();
        System.out.println(o);
        Object obj = o;
        System.out.println(obj);
        o = null;
        System.out.println(o);
        System.out.println(obj);
    }
}
