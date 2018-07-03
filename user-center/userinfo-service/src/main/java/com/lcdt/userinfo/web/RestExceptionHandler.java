package com.lcdt.userinfo.web;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.lang.invoke.MethodHandle;

/**
 * Created by ss on 2017/11/3.
 */
@ControllerAdvice
public class RestExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

	ExpressionParser expressionParser = new SpelExpressionParser();


	@org.springframework.web.bind.annotation.ExceptionHandler(AccessDeniedException.class)
	@ResponseBody
	public String AccessDeniedHandler(HttpServletRequest request, Exception e,HandlerMethod handle) {
		if (handle != null) {
			PreAuthorize annotation = AnnotationUtils.getAnnotation(handle.getMethod(), PreAuthorize.class);
			if (annotation != null) {
				logger.info("权限是 "+ annotation.value());
				Expression expression = expressionParser.parseExpression(annotation.value());
				AuthAnnontionValue value = new AuthAnnontionValue();
				try {
					expression.getValue(value);
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("code", -1);
					jsonObject.put("message", "缺少权限 " + value.getAuthority());
					return jsonObject.toString();
				}catch (EvaluationException ex){
					logger.error(ex.getMessage(),ex);
				}
			}
		}

		return defaultErrorHandler(request, e);
	}


	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	@ResponseBody
	public String defaultErrorHandler(HttpServletRequest request, Exception e) {
		logger.error(e.getMessage(), e);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", -1);
		jsonObject.put("message", e.getMessage());
		return jsonObject.toString();
	}

	static class AuthAnnontionValue {

		private String role;
		private String authority;

		public boolean hasAuthority(String authority) {
			this.authority = authority;
			return true;
		}
		public boolean hasAnyAuthority(String authority) {
			this.authority = authority;
			return true;
		}
		public boolean hasRole(String role) {
			this.role = role;
			return false;
		}

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

		public String getAuthority() {
			return authority;
		}

		public void setAuthority(String authority) {
			this.authority = authority;
		}
	}
}
