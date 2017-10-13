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
 */
public class TicketAccessDeniedHandler implements AccessDeniedHandler {


	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.isAuthenticated()) {
			if (!response.isCommitted()) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN,accessDeniedException.getMessage());
			}
		}
		else {
			RedirectHelper.redirectToLoginUrlWithAuthBack(request,response);
		}
	}
}
