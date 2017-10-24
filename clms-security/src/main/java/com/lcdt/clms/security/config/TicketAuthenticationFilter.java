package com.lcdt.clms.security.config;

import com.sso.client.utils.TicketHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ss on 2017/8/22.
 */
public class TicketAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	public TicketAuthenticationFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
	}

	public TicketAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
		super(requiresAuthenticationRequestMatcher);
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
}
