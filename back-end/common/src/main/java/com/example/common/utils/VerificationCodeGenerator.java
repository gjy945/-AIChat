package com.example.common.utils;

import java.util.Random;

/**
 *  验证码生成器4位数字
 */
public class VerificationCodeGenerator {

    /**
     * 生成四位数字的短信验证码
     *
     * @return 四位数字的验证码
     */
    public static String generateVerificationCode() {
        Random random = new Random();
        int code = 1000 + random.nextInt(9000); // 生成1000到9999之间的随机数
        return String.valueOf(code);
    }
}