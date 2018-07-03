package com.lcdt.clms.security.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lcdt.util.HttpUtils;
import com.sso.client.utils.RedirectHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by ss on 2017/8/21.
 * 拒绝访问时的异常处理
 */
public class TicketAccessDeniedHandler implements AccessDeniedHandler {

	private Logger logger = LoggerFactory.getLogger(TicketAccessDeniedHandler.class);

	private static final String JSON_MEDIA_TYPE = "application/json; charset=UTF-8";

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			logger.info("request access denied {} user:{}",request.getRequestURI(),authentication.getName());
			if (response.isCommitted()) {
				logger.warn("response is commited but no show access denied message path:{}",request.getRequestURI());
				return;
			}

			//AJAX 返回JSON 格式化错误信息
			if (HttpUtils.isAjaxRequest(request)){
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("message", accessDeniedException.getMessage());
				jsonObject.put("code", -1);
				response.setContentType(JSON_MEDIA_TYPE);
				PrintWriter writer = response.getWriter();
				writer.write(jsonObject.toString());
				writer.flush();
				return;
			}

			//已登录 没有权限访问 显示错误信息
			if (!response.isCommitted()) {
				response.setCharacterEncoding("UTF-8");
				response.sendError(HttpServletResponse.SC_FORBIDDEN,accessDeniedException.getMessage());
			}
		}
		else {
			//没有进行认证 需要进行登陆
			RedirectHelper.redirectToLoginUrlWithAuthBack(request,response);
		}
	}
}
