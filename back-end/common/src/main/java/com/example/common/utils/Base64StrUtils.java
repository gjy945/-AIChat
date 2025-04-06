package com.example.common.utils;

import java.util.ArrayList;
import java.util.List;

public class Base64StrUtils {

    // 默认的分割长度
    private static final int DEFAULT_SPLIT_LENGTH = 1000;

    /**
     * 将Base64字符串拆分成多个较短的字符串
     *
     * @param base64String 需要拆分的Base64字符串
     * @param splitLength  每个部分的最大长度，默认为1000
     * @return 拆分后的字符串列表
     */
    public static List<String> splitBase64String(String base64String, int splitLength) {
        if (splitLength <= 0) {
            throw new IllegalArgumentException("Split length must be positive");
        }

        List<String> parts = new ArrayList<>();
        for (int i = 0; i < base64String.length(); i += splitLength) {
            int end = Math.min(i + splitLength, base64String.length());
            parts.add(base64String.substring(i, end));
        }
        return parts;
    }

    /**
     * 将Base64字符串拆分成多个较短的字符串，使用默认的分割长度
     *
     * @param base64String 需要拆分的Base64字符串
     * @return 拆分后的字符串列表
     */
    public static List<String> splitBase64String(String base64String) {
        return splitBase64String(base64String, DEFAULT_SPLIT_LENGTH);
    }

    /**
     * 将拆分后的Base64字符串部分拼接成一个完整的Base64字符串
     *
     * @param parts 拆分后的字符串列表
     * @return 完整的Base64字符串
     */
    public static String joinBase64Parts(List<String> parts) {
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            sb.append(part);
        }
        return sb.toString();
    }
}