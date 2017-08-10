package com.lcdt.web.http;

//import com.alibaba.dubbo.common.json.JSONObject;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
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

	public void setResponseJsonHeader(HttpServletResponse response){
		response.setHeader("content-type","application/json;charset=UTF-8");
	}


	public void responseError(HttpServletResponse response,Exception exception){
		setResponseJsonHeader(response);
		JSONObject errorObj = new JSONObject();
		errorObj.put("code", -1);
		errorObj.put("message", exception.getMessage());
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(errorObj.toString());
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void responseOk(HttpServletResponse response){
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
