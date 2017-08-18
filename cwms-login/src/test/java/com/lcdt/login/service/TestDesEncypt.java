package com.lcdt.login.service;

import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.UnsupportedEncodingException;

/**
 * Created by ss on 2017/8/17.
 */
public class TestDesEncypt {

	@Test
	public void testEncypt() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("userId", "123");
		jsonObject.put("ip", "198.168.1.0");

		String src = jsonObject.toString();
		DesEncypt desEncypt = new DesEncypt("91BE73DFEFCD0708");
		try {
			String encode = desEncypt.encode(src);
			System.out.println(encode);
			String decode = desEncypt.decode(encode);
			Assert.assertEquals(src,decode);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		}

	}

}
