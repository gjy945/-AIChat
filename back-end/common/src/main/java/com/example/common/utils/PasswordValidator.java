package com.example.common.utils;

import java.util.regex.Pattern;

public class PasswordValidator {

    /**
     * 验证密码是否有效。
     *
     * @param password 待验证的密码
     * @return 如果密码有效返回 true，否则返回 false
     */
    public static boolean isValidPassword(String password) {
        // 检查密码是否为空或仅由空格组成
        if (password == null || password.trim().isEmpty()) {
            return false;
        }

        // 检查密码长度是否符合要求
        if (password.length() < 8) { // 最小长度为8
            return false;
        }

        // 使用正则表达式检查密码是否包含至少一个大写字母、一个小写字母、一个数字
        Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$");
        return pattern.matcher(password).matches();
    }
}