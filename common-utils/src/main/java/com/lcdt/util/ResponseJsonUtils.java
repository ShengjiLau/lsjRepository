package com.lcdt.util;

import com.alibaba.fastjson.JSONObject;
import com.lcdt.util.ResponseCodeVO;

/**
 * @author Sheng-ji Lau
 * @date 2018年7月17日
 * @version
 * @Description: TODO 
 */
public class ResponseJsonUtils {
	
	
	public static <T> JSONObject successResponseJsonWithoutData(String message) {
		return  successResponseJson(null, message);
	}
	
	
	public static <T> JSONObject failedResponseJsonWithoutData(String message) {
		return  failedResponseJson(null, message);
	}
	
	/**
	 * 请求成功的返回格式
	 */
	public static <T> JSONObject successResponseJson(T data,String message) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ResponseCodeVO.CODE, ResponseCodeVO.SUCCESS_CODE);
		jsonObject.put(ResponseCodeVO.MESSAGE, message);
		if (null != data) {
			jsonObject.put(ResponseCodeVO.DATA, data);
		}
		return jsonObject;
	}
	
	/**
	 * 请求失败的返回格式
	 */
	public static <T> JSONObject failedResponseJson(T data,String message) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ResponseCodeVO.CODE, ResponseCodeVO.FAILED_CODE);
		jsonObject.put(ResponseCodeVO.MESSAGE, message);
		if (null != data) {
			jsonObject.put(ResponseCodeVO.DATA, data);
		}
		return jsonObject;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
