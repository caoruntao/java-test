package com.crt.dynamicdatasource.dynamic;

/**
 * @Author reed
 * @Date 2020/7/17 9:01 上午
 */
public class DynamicDataSourceHolder {
    private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

    public static final String MASTER = "master";
    public static final String SLAVE01 = "slave01";

    private static void setContext(String context){
        CONTEXT.set(context);
    }
    public static String getContext(){
        return CONTEXT.get();
    }

    public static void markMaster(){
        setContext(MASTER);
    }

    public static void markSlave01(){
        setContext(SLAVE01);
    }
}
