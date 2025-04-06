package com.example.common.utils;


import java.util.ArrayList;
import java.util.List;

public class StringNumberExtractor {


    /**
     * 提取字符串中的数字
     */
    public static int extractNumber(String str) {
        String numberStr = str.replaceAll("[^0-9]", "");
        if (numberStr.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(numberStr);
    }

    /**
     * 提取字符串中的单位
     */
    public static String extractUnit(String text) {
        if (text == null || text.isEmpty()) {
            return null;
        }
        List<String> commonUnits = new ArrayList<>();
        commonUnits.add("克");
        commonUnits.add("千克");
        commonUnits.add("吨");
        commonUnits.add("毫克");
        commonUnits.add("升");
        commonUnits.add("毫升");
        commonUnits.add("立方米");
        commonUnits.add("立方分米");
        commonUnits.add("立方厘米");
        commonUnits.add("平方米");
        commonUnits.add("平方分米");
        commonUnits.add("平方厘米");
        commonUnits.add("米");
        commonUnits.add("分米");
        commonUnits.add("厘米");
        commonUnits.add("毫米");
        commonUnits.add("个");
        commonUnits.add("只");
        commonUnits.add("双");
        commonUnits.add("套");
        commonUnits.add("箱");
        commonUnits.add("盒");
        commonUnits.add("袋");
        commonUnits.add("瓶");
        commonUnits.add("g");
        commonUnits.add("kg");
        commonUnits.add("t");
        commonUnits.add("mg");
        commonUnits.add("L");
        commonUnits.add("mL");
        commonUnits.add("m³");
        commonUnits.add("dm³");
        commonUnits.add("cm³");
        commonUnits.add("m²");
        commonUnits.add("dm²");
        commonUnits.add("cm²");
        commonUnits.add("m");
        commonUnits.add("dm");
        commonUnits.add("cm");
        commonUnits.add("mm");
        // 添加更多常见单位

        for (String unit : commonUnits) {
            if (text.contains(unit)) {
                return unit;
            }
        }
        return null;
    }
}