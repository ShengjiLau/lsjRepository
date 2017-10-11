package com.lcdt.userinfo.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ss on 2017/8/1.
 */
public class RegisterUtils {


	static final char[] HEXSTRING = "0123456789abcdef".toCharArray();

	public static String md5Encrypt(String srcPwd) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("md5");
			byte[] digest = md5.digest(srcPwd.getBytes());
			return binaryTohexString(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}



	public static String binaryTohexString(byte[] bytes) {
		char[] hexs = new char[bytes.length * 2];
		for (int i = 0, offset = 0; i < bytes.length; i++) {
			byte b = bytes[i];
			hexs[offset++] = HEXSTRING[b >> 4 & 0xf];
			hexs[offset++] = HEXSTRING[b & 0xf];
		}
		return new String(hexs);
	}

}
