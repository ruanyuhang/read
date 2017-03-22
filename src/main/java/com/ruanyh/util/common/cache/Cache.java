package com.ruanyh.util.common.cache;


public interface Cache {
    /**
     * 增加
     * 如果key存在,返回false,否则返回true
     * @param key
     * @param value
     * @return
     */
    boolean add(String key, Object value);
    /**
     * 增加
     * 如果key存在,返回false, 否则返回true
     * @param key
     * @param value
     * @param expire
     * @return
     */
    boolean add(String key, Object value, Integer expire);


    /**
     * 替换
     * 如果key存在,替换值并返回true, 否则返回false
     * @param key
     * @param value
     * @return
     */
    boolean replace(String key, Object value);
    /**
     * 替换
     * 如果key存在,替换值并返回true, 否则返回false
     * @param key
     * @param value
     * @param expire
     * @return
     */
    boolean replace(String key, Object value, Integer expire);


    /**
     * 更新
     * 更新已存在key的值
     * 如果key存在,替换值并返回true, 否则返回false
     * @param key
     * @param value
     * @return
     */
    boolean set(String key, Object value);
    /**
     * 更新
     * 更新已存在key的值
     * 如果key存在,替换值并返回true, 否则返回false
     * @param key
     * @param value
     * @param expire
     * @return
     */
    boolean set(String key, Object value, Integer expire);

    /**
     * 获取
     * @param key
     * @return
     */
    Object get(String key);

    /**
     * 移除
     * @param key
     * @return
     */
    boolean remove(String key);

}
