package com.example.common.utils;

import java.util.*;
import java.util.stream.Collectors;

// HashMap 工具类
public class HashMapUtil {

    /**
     * 将 HashMap 中的条目随机打乱
     *
     * @param map HashMap
     * @return 打乱后的 HashMap
     */
    public static <K, V> Map<K, V> shuffleMap(Map<K, V> map) {
        // 将 Map 的条目转换为列表
        List<Map.Entry<K, V>> entryList = new ArrayList<>(map.entrySet());

        // 打乱列表
        Collections.shuffle(entryList);

        // 创建新的 Map
        Map<K, V> shuffledMap = new HashMap<>();
        for (Map.Entry<K, V> entry : entryList) {
            shuffledMap.put(entry.getKey(), entry.getValue());
        }

        return shuffledMap;
    }

    /**
     * 获取 Map 的前 n 个条目
     *
     * @param map    要处理的 Map
     * @param number 要获取的条目数量
     * @param <K>    键的类型
     * @param <V>    值的类型
     * @return 前 n 个条目的列表
     */
    public static <K, V> List<Map.Entry<K, V>> getFirstNEntries(Map<K, V> map, int number) {
        if (map == null || number <= 0) {
            return Collections.emptyList(); // 返回空列表
        }

        return map.entrySet().stream()
                .limit(number) // 限制条目数量
                .collect(Collectors.toList());
    }


    /**
     * 获取 Map 的前 n 个条目，并返回一个新的 Map
     *
     * @param map    要处理的 Map
     * @param number 要获取的条目数量
     * @param <K>    键的类型
     * @param <V>    值的类型
     * @return 包含前 n 个条目的新 Map
     */
    public static <K, V> Map<K, V> getFirstNEntriesAsMap(Map<K, V> map, int number) {
        if (map == null || number <= 0) {
            return Collections.emptyMap(); // 返回空 Map
        }

        return map.entrySet().stream()
                .limit(number) // 限制条目数量
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
