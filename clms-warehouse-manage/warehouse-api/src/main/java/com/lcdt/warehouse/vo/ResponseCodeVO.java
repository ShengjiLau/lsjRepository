package com.lcdt.warehouse.vo;


/**
 * @author Sheng-ji Lau
 * @date 2018年6月2日
 * @version
 * @Description: TODO 
 */
public class ResponseCodeVO {
	
	/**
	 * 访问成功返回的code值
	 */
	public static final Integer successCode = 0;
	
	/**
	 * 访问失败返回的code值
	 */
	public static final Integer failedCode = -1;
	
	/**
	 * 同一接口返回格式
	 */
	public static final String code = "code";
	public static final String message = "message";
	public static final String data = "data";
	
}
