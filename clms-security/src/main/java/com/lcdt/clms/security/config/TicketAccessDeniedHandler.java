package com.lcdt.clms.security.config;

import com.sso.client.utils.RedirectHelper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ss on 2017/8/21.
 * 拒绝访问时的异常处理
 */
public class TicketAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
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
