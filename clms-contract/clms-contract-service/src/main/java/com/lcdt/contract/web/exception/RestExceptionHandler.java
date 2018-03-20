package com.lcdt.contract.web.exception;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @AUTHOR liuh
 * @DATE 2017-03-05
 */
@ControllerAdvice
public class RestExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);


	@ExceptionHandler(Exception.class)
	@ResponseBody
	public JSONObject defaultErrorHandler(HttpServletRequest request,Exception e){
		logger.error(e.getMessage(),e);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", -1);
		jsonObject.put("message", e.getMessage()==null?"空指针异常":e.getMessage());
		return jsonObject;
	}

}