package com.caort.verify;

import org.junit.Test;

/**
 * @author Reed
 * @date 2021/8/13 上午9:15
 */
public class StringTest {
    @Test
    public void test1(){
        String str1 = new String("11") + new String("111");
        String str2 = "11111";
        System.out.println(str1 == str2);
    }

    @Test
    public void test2(){
        String str1 = new String("11") + new String("111");
        str1.intern();
        String str2 = "11111";
        System.out.println(str1 == str2);
    }

    @Test
    public void test3(){
        String str1 = new String("11") + new String("111");
        String str2 = "11111";
        str1.intern();
        System.out.println(str1 == str2);
    }

    @Test
    public void test4(){
        String str1 = new String("11") + new String("111");
        String str2 = "11111";
        str1 = str1.intern();
        System.out.println(str1 == str2);
    }

    @Test
    public void test5(){
        String str2 = "11";
        String a = new String("11");
        a = a.intern();
        String str3 = "11";
        System.out.println(a == str2);
        System.out.println(str2 == str3);
    }

    @Test
    public void test6(){
        String str1 = new String("11");
        System.out.println(str1 == str1.intern());
        String str2 = "11";
        System.out.println(str1.intern() == str2);
    }
}
