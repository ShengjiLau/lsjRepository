package com.lcdt.userinfo.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;

/**
 * Created by ss on 2017/11/3.
 */
public class ApiError {

	public Integer code;

	//前台可显示的错误提示
	public String errorTips;

	public String detailMessage;


	public ApiError(HttpStatus badRequest, String error, Exception ex) {
		detailMessage = ex.getMessage();
		code = -1;
	}

	public HttpStatus getStatus(){
		return HttpStatus.OK;
	}
}
