package com.ruanyh.util.common;

import java.util.UUID;

/**
 * ID生成器
 * Created by ruanyh on 17/9/9.
 */
public class IDGenerator {

    /**
     * 私有构造器,不允许实例化
     */
    private IDGenerator() {}

    /**
     * UUID
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String randomCode() {
        return null;
    }

}
