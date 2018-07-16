package com.lcdt.contract.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;

/**
 * @author Sheng-ji Lau
 * @date 2018年7月16日
 * @version
 * @Description: TODO 
 */
@ControllerAdvice
@RestController
public class GlobalExceptionController {
	
	
	@ExceptionHandler(Exception.class)
	public JSONObject handlerAllException(Exception e) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", -1);
		jsonObject.put("message", "系统服务异常，请稍后再试！");
		return jsonObject;
	}
	
	

}
