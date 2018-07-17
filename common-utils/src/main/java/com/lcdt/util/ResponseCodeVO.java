package com.lcdt.util;


/**
 * @author Sheng-ji Lau
 * @date 2018年6月2日
 * @version
 * @Description: TODO 
 */
public final class ResponseCodeVO {
	
	/**
	 * 访问成功返回的code值
	 */
	public static final Integer SUCCESS_CODE = 0;
	
	/**
	 * 访问失败返回的code值
	 */
	public static final Integer FAILED_CODE = -1;
	
	/**
	 * 统一接口返回格式
	 */
	public static final String CODE = "code";
	public static final String MESSAGE = "message";
	public static final String DATA = "data";
	
}
