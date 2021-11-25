package com.caort.verify;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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

    @Test
    public void listStringEcho(){
       String[] str = new String[]{"1","2"};
        System.out.println(str.toString());

    }

    @Test
    public void test(){
        AtomicInteger atomicInteger = new AtomicInteger(1);
        int andIncrement = atomicInteger.getAndIncrement();
        System.out.println(andIncrement);

        System.out.println(1 << 3);
    }

    /**
     * 计算n个台阶有几种走法
     * @param n
     * @return
     */
    private int strategy(int n){
        if(n == 1 || n == 2){
            return n;
        }
        return strategy(n - 1) + strategy(n - 2);
    }


}
