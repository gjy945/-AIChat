package com.example.common.constant;

/**
 *
 * 密码常量
 */
public class PasswordConstant {

    // 默认密码 身份证号后六位
    public static byte[] getDEFAULT_PASSWORD(String idNumber) {
        return idNumber.substring(idNumber.length() - 6).getBytes();
    }
}
