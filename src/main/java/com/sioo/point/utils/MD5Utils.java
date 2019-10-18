package com.sioo.point.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * @Description: MD5Utils 算法的Java Bean
 * md5 类实现了RSA Data Security, Inc.在提交给IETF
 * 的RFC1321中的MD5 message-digest 算法。
 * @Param:
 * @return:
 * @Author: fanghuaiming
 * @Date: 2:32 PM 2019/7/9
 */
public class MD5Utils {
	public static String toMD5(String plainText) {
		byte[] secretBytes = null;
		try {
			secretBytes = MessageDigest.getInstance("md5").digest(
					plainText.getBytes());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("There is no MD5 algorithm！");
		}
		// 16进制数字
		String md5code = new BigInteger(1, secretBytes).toString(16);
		// 如果生成数字未满32位，需要前面补0
		for (int i = 0; i < 32 - md5code.length(); i++) {
			md5code = "0" + md5code;
		}
		return md5code;
	}
}
	



