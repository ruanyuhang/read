package com.ruanyh.util.common;

import java.util.Random;
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
        return uuid(null);
    }

    /**
     * UUID
     * @param separator 分隔符
     * @return
     */
    public static String uuid(String separator) {
        if (separator == null) {
            separator = "";
        }
        String uuid = UUID.randomUUID().toString();
        return "-".equals(separator) ? uuid : uuid.replace("-", separator);
    }

    /**
     * 数字随机码
     * 6位
     * @return
     */
    public static String randomNum() {
        return randomNum(6);
    }

    /**
     * 数字随机码
     * @param n 位数
     * @return
     */
    public static String randomNum(int n) {
        StringBuffer code = new StringBuffer();
        for (int i = 0; i < n; i++) {
            code.append(new Random().nextInt(10));
        }
        return code.toString();
    }


    public static void main(String[] args) {
        System.out.println(uuid());
        System.out.println(randomNum(6));
        System.out.println(System.getProperty("user.home"));
        System.out.println(uuid("@"));
    }

}
