package com.ruanyh.util;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

public class OkHttpUtils {
    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient();

    static {
        HTTP_CLIENT.setConnectTimeout(30, TimeUnit.SECONDS);
    }

    private OkHttpUtils() {}


}
