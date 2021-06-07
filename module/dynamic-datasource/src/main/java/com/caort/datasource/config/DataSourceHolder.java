package com.caort.datasource.config;

/**
 * @author Reed
 * @date 2021/5/10 下午2:53
 */
public class DataSourceHolder {
    public static final String MASTER = "master";
    public static final String SLAVE = "slave";

    private static ThreadLocal<String> key = ThreadLocal.withInitial(() -> SLAVE);

    public static String get(){
        return key.get();
    }

    public static void markMaster(){
        key.set(MASTER);
    }

    public static void markSlave(){
        key.set(SLAVE);
    }

    public static void clearThreadLocal(){
        key.remove();
    }
}
