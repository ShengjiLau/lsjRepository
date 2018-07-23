package com.lcdt.contract.web.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.lcdt.util.ResponseJsonUtils;

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
		return ResponseJsonUtils.failedResponseJson(null, "系统服务异常，请稍后再试！");
	}
	
}
