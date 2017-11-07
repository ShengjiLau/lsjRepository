package com.lcdt.userinfo.web;

import com.alibaba.fastjson.JSONObject;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ss on 2017/11/3.
 */
@ControllerAdvice
public class RestExceptionHandler{

	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	@ResponseBody
	public String defaultErrorHandler(HttpServletRequest request,Exception e){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", -1);
		jsonObject.put("message", e.getMessage());
		return jsonObject.toString();
	}

}
