package com.example.common.utils;

import cn.hutool.core.map.MapUtil;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

// Map转换为LinkedHashMap
public class MapToLinkedHashMapUtil {

    /**
     * 将Map转换为LinkedHashMap（带key）
     * @param dataMap
     * @param keyName
     * @return
     */
    public static LinkedHashMap getMapValueForLinkedHashMap(Map dataMap, String keyName) {
        LinkedHashMap returnMap = new LinkedHashMap();
        if (MapUtil.isEmpty(dataMap)) {
            return returnMap;
        }
        Object valueObj = dataMap.get(keyName);
        if (valueObj instanceof Map) {
            Map objMap = (Map) valueObj;
            Iterator iterator = objMap.keySet().iterator();
            while (iterator.hasNext()) {
                Object objKey = iterator.next();
                Object objValue = objMap.get(objKey);
                if (objValue instanceof Map) {
                    returnMap.put(objKey, getMapValueForLinkedHashMap((Map)objValue));
                } else {
                    returnMap.put(objKey, objValue);
                }
            }
        }
        return returnMap;
    }

    /**
     * 将Map转换为LinkedHashMap（不带key）
     * @param dataMap
     * @return
     */
    public static LinkedHashMap getMapValueForLinkedHashMap(Map dataMap) {
        LinkedHashMap returnMap = new LinkedHashMap();
        if (MapUtil.isEmpty(dataMap)) {
            return returnMap;
        }
        Iterator iterator = dataMap.keySet().iterator();
        while (iterator.hasNext()) {
            Object objKey = iterator.next();
            Object objValue = dataMap.get(objKey);
            if (objValue instanceof Map) {
                returnMap.put(objKey, getMapValueForLinkedHashMap((Map) objValue));
            } else {
                returnMap.put(objKey, objValue);
            }
        }
        return returnMap;
    }

}
