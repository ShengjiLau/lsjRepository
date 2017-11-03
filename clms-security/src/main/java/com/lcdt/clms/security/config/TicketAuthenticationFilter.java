package com.lcdt.clms.security.config;

import com.sso.client.utils.TicketHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ss on 2017/8/22.
 */
public class TicketAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private AuthenticationSuccessHandler successHandler = new ForwardRequestHandler();

	public TicketAuthenticationFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
	}

	public TicketAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
		super(requiresAuthenticationRequestMatcher);
		this.setAuthenticationSuccessHandler(successHandler);
	}

	@Override
	protected boolean requiresAuthentication(HttpServletRequest request,
											 HttpServletResponse response) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null) {
			return true;
		}
		if (authentication.isAuthenticated() && authentication.getCredentials() != null) {
			TicketAuthenticationToken token = (TicketAuthenticationToken) authentication;
			Object credentials = authentication.getCredentials();
			String ticket = (String) credentials;
			String ticketInCookie = TicketHelper.findTicketInCookie(request);
			if (ticketInCookie == null || !ticket.equals(ticketInCookie)) {
				return true;
			}
			return false;
		}
		return true;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
		String ticketInCookie = TicketHelper.findTicketInCookie(request);
		if (StringUtils.isEmpty(ticketInCookie)){
			throw new TicketAuthException("请先登陆");
		}
		TicketAuthenticationToken token = new TicketAuthenticationToken(ticketInCookie);
		Authentication authenticate = getAuthenticationManager().authenticate(token);
		return authenticate;
	}

	public static class ForwardRequestHandler implements AuthenticationSuccessHandler{

		private Logger logger = LoggerFactory.getLogger(ForwardRequestHandler.class);

		@Override
		public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
			//尝试把请求再次转发到之前的地址
			logger.info("forward request {}",request.getServletPath());
			request.getRequestDispatcher(request.getServletPath()).forward(request,response);
		}
	}











}
