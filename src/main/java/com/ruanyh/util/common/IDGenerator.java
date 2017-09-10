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
        return UUID.randomUUID().toString().replace("-", "");
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
    }

}
