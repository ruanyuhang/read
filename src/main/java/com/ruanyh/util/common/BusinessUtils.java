package com.ruanyh.util.common;

import org.apache.commons.lang.StringUtils;

/**
 * 与业务相关的方法
 */
public class BusinessUtils {
    /**
     * 私有的构造方法,不允许实例化
     */
    private BusinessUtils() {}


    // 解析参数
//    public static Map<String, String> parseParams(HttpServletRequest request) {
//        Map<String, String> params = new HashMap<>();
//
//        // TODO 参数
//
//
//        return params;
//    }


    // 敏感词


    /**
     * 版本号比较
     * 正数：currentVersion大于destVersion
     * 0：版本号相同
     * 负数：currentVersion小于destVersion
     * @param currentVersion 当前版本
     * @param destVersion 目标版本
     * @return
     */
    public static int compareVersion(String currentVersion, String destVersion) {
        if (StringUtils.isBlank(destVersion)) {
            throw new IllegalArgumentException("Illegal argument: destVersion is null");
        }

        if (StringUtils.isBlank(currentVersion)) {
            return -1;
        }

        if (currentVersion.contains("-debug")) {
            currentVersion = currentVersion.substring(0, currentVersion.indexOf("-debug"));
        } else if (currentVersion.contains("-")) {
            currentVersion = currentVersion.substring(0, currentVersion.indexOf("-"));
        }

        String[] versions = currentVersion.split("\\.");
        String[] destVersions = destVersion.split("\\.");
        int minLen = Math.min(versions.length, destVersions.length);
        int result = 0;
        for (int i = 0; i < minLen; i++) {
            // 比较版本号，只适用数字
            Integer v = Integer.parseInt(versions[i]);
            Integer dv = Integer.parseInt(destVersions[i]);
            if (v - dv != 0) {
                return v - dv;
            }
        }
        return result;
    }

}
