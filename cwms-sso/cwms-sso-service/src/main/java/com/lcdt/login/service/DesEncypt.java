package com.lcdt.login.service;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Created by ss on 2017/8/17.
 */
public class DesEncypt {

	public static final String KEY_ALGORITHM = "DES";

	private Cipher encodeCipher;
	private Cipher decodeCipher;

	private String encodeKey;


	public DesEncypt(String key){
		encodeKey = key;
		try {
			encodeCipher = Cipher.getInstance(KEY_ALGORITHM);
			decodeCipher = Cipher.getInstance(KEY_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}

		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * 生成密钥key对象
	 *
	 */
	private static SecretKey keyGenerator(String keyStr) throws Exception {
		byte input[] = HexString2Bytes(keyStr);
		DESKeySpec desKey = new DESKeySpec(input);
		// 创建一个密匙工厂，然后用它把DESKeySpec转换成
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey securekey = keyFactory.generateSecret(desKey);
		return securekey;
	}


	// 从十六进制字符串到字节数组转换
	public static byte[] HexString2Bytes(String hexstr) {
		byte[] b = new byte[hexstr.length() / 2];
		int j = 0;
		for (int i = 0; i < b.length; i++) {
			char c0 = hexstr.charAt(j++);
			char c1 = hexstr.charAt(j++);
			b[i] = (byte) ((parse(c0) << 4) | parse(c1));
		}
		return b;
	}

	private static int parse(char c) {
		if (c >= 'a')
			return (c - 'a' + 10) & 0x0f;
		if (c >= 'A')
			return (c - 'A' + 10) & 0x0f;
		return (c - '0') & 0x0f;
	}


	public void init() throws Exception {
		encodeCipher.init(Cipher.ENCRYPT_MODE,keyGenerator(encodeKey));
		decodeCipher.init(Cipher.DECRYPT_MODE,keyGenerator(encodeKey));
	}

	public String encode(String data) throws UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
		byte[] bytes = encodeCipher.doFinal(data.getBytes("UTF-8"));
		return Base64.getEncoder().encodeToString(bytes);
	}

	public String decode(String data) throws UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
		byte[] decode = Base64.getDecoder().decode(data.getBytes("UTF-8"));
		return new String(decodeCipher.doFinal(decode));
	}

}
