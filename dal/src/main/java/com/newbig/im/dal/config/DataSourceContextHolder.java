package com.newbig.im.dal.config;

import io.netty.util.concurrent.FastThreadLocal;

public class DataSourceContextHolder {
    private static final FastThreadLocal<String> contextHolder = new FastThreadLocal<>();

    public static void setDB(String dbType) {
        contextHolder.set(dbType);
    }

    public static String getDB() {
        return (contextHolder.get());
    }

    public static void clearDB() {
        contextHolder.remove();
    }
}
