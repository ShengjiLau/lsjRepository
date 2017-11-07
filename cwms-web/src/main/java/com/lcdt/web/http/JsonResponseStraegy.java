package com.lcdt.web.http;


import com.alibaba.fastjson.JSONObject;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by ss on 2017/8/10.
 */
@Component
public class JsonResponseStraegy {

	private static JSONObject okJsonObj = new JSONObject();

	static {
		okJsonObj.put("code", 0);
		okJsonObj.put("message", "success");
	}

	public void setResponseJsonHeader(HttpServletResponse response) {
		response.setHeader("content-type", "application/json;charset=UTF-8");
	}


	public void responseLoginError(HttpServletResponse response, Exception e) {
		setResponseJsonHeader(response);
		JSONObject errorObj = new JSONObject();
		errorObj.put("code", -1);
		if (e instanceof InternalAuthenticationServiceException) {
			errorObj.put("message", "服务器错误");
		} else if (e instanceof AuthenticationException) {
			errorObj.put("message", e.getMessage());
			if (e instanceof BadCredentialsException) {
				errorObj.put("message", "密码错误");
			} else if (e instanceof UsernameNotFoundException) {
				errorObj.put("message", "用户不存在");
			}
		} else {
			errorObj.put("meeage", "未知错误");
		}
		writeJsonToResponse(response, errorObj);
	}

	public void responseError(HttpServletResponse response, Exception exception) {
		setResponseJsonHeader(response);
		JSONObject errorObj = new JSONObject();
		exception.getLocalizedMessage();
		errorObj.put("code", -1);
		errorObj.put("message", exception.getLocalizedMessage());
		writeJsonToResponse(response, errorObj);
	}


	public String responseSuccessString(Object obj){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", 0);
		jsonObject.put("message", "success");
		jsonObject.put("data", obj);
		return jsonObject.toString();
	}

	private void writeJsonToResponse(HttpServletResponse response, JSONObject errorObj) {
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(errorObj.toString());
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void responseOk(HttpServletResponse response) {
		setResponseJsonHeader(response);
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		writer.write(okJsonObj.toString());
		writer.flush();
	}


}
