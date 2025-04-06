package com.example.common.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * bcrypt加密工具类
 */
public final class PasswordUtils {

    /**
     * BCrypt密码编码器实例。
     * 默认使用10的strength进行编码。 工作因子
     */
    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder(4);

    /**
     * 私有构造函数，防止实例化工具类.
     */
    private PasswordUtils() {
        throw new AssertionError("工具类不能被实例化");
    }

    /**
     * 使用BCrypt算法对密码进行编码。
     *
     * @param rawPassword 要编码的原始密码
     * @return 编码后的密码
     */
    public static String encodePassword(String rawPassword) {
        return ENCODER.encode(rawPassword);
    }

    /**
     * 验证给定的原始密码是否与存储的编码密码匹配。
     *
     * @param rawPassword 原始密码
     * @param encodedPassword 存储的编码密码
     * @return 如果密码匹配返回true，否则返回false
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword) {
        return ENCODER.matches(rawPassword, encodedPassword);
    }
}