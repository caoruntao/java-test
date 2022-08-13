package com.caort.verify;

/**
 * @author Caort
 * @date 2022/6/25 11:23
 */
public class InnerClass {
    public static class ChildClass{

    }

    public static void main(String[] args) {
        ChildClass childClass = new ChildClass();
        System.out.println(childClass);
    }
}
