package com.ruanyh.util.common;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

public class OkHttpUtils {
    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient();

    static {
        HTTP_CLIENT.setConnectTimeout(30, TimeUnit.SECONDS);
    }

    /**
     * 私有的构造方法,不允许实例化
     */
    private OkHttpUtils() {}


}
