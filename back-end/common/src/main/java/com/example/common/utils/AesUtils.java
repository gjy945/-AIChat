package com.example.common.utils;

import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

import java.nio.charset.StandardCharsets;

/**
 * AES加密方式算法工具类
 */
public class AesUtils {
	/**
	 * KEY 随机的后续可更改
	 */
	private static final byte[] key ="A8F4eq2ioO9IiA7X".getBytes(StandardCharsets.UTF_8);
	/**
	 * 初始化加密(默认的AES加密方式)
	 */
	private static final SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);
 
 
	/**
	 * 加密
	 * @param str 加密之前的字符串
	 * @return
	 */
	public static String encryptHex(String str){
		return aes.encryptHex(str);
	}
 
	/**
	 * 解密
	 * @param str 加密后的字符串
	 * @return
	 */
	public static String decryptStr(String str){
		return aes.decryptStr(str);
	}
 
 
}