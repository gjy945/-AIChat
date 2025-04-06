package com.example.common.utils;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;

import java.nio.charset.StandardCharsets;

public class EncryptionUtil {
    // 加密密钥，需妥善保管，长度必须为 16、24 或 32 字节
    private static final byte[] KEY = "Tbvtc123!!321123".getBytes(StandardCharsets.UTF_8);

    public static String encrypt(String plaintext) {
        AES aes = SecureUtil.aes(KEY);
        return aes.encryptHex(plaintext);
    }

    public static String decrypt(String ciphertext) {
        AES aes = SecureUtil.aes(KEY);
        return aes.decryptStr(ciphertext);
    }
}