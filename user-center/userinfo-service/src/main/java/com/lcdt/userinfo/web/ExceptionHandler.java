package com.lcdt.userinfo.web;

import com.alibaba.fastjson.JSONObject;
import com.lcdt.util.HttpUtils;
import com.lcdt.util.WebProduces;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by ss on 2017/11/3.
 */
@Component
@ControllerAdvice
public class ExceptionHandler extends SimpleMappingExceptionResolver {

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		boolean ajaxRequest = HttpUtils.isAjaxRequest(request);

		if (ajaxRequest) {
			//返回json格式错误信息
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("code", -1);
			jsonObject.put("message", ex.getMessage());
			try {
				response.setContentType(WebProduces.JSON_UTF_8);
				PrintWriter writer = response.getWriter();
				writer.write(jsonObject.toString());
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}else{
			return super.doResolveException(request, response, handler, ex);
		}
	}

}
