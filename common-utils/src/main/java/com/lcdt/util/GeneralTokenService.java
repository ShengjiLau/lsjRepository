package com.lcdt.util;

import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * Created by ss on 2017/11/27.
 */
public class GeneralTokenService {

	/**
	 * 通用的token 生成方法
	 * @param values
	 * @param validTime
	 * @return
	 */
	public static String generateToken(Map<Object,Object> values,long validTime){
		return null;
	}


	public void tokenTransform(String token) {
		if (StringUtils.isEmpty(token)) {
			return ;
		}

		return;
	}

	public boolean isTokenValid(){
		return false;
	}


}
