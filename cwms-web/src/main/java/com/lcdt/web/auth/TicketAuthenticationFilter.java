package com.lcdt.web.auth;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
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
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		super.doFilter(req, res, chain);
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			if (request.getCookies() == null) {
				chain.doFilter(request, res);
			}
			for (Cookie cookie : request.getCookies()) {
				if (cookie.getName().equals("cwms_ticket")) {
					TicketAuthenticationToken authResult = null;
					try {
						authResult = new TicketAuthenticationToken(null);
						authResult.setTicket(cookie.getValue());
						authResult = (TicketAuthenticationToken) getAuthenticationManager().authenticate(authResult);
						if (authResult == null) {
							return;
						}
					} catch (InternalAuthenticationServiceException failed) {
						unsuccessfulAuthentication(request, response, failed);
					} catch (AuthenticationException failed) {
						unsuccessfulAuthentication(request, response, failed);
						return;
					}
					successfulAuthentication(request, response, chain, authResult);
					return;
				}
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
		return null;
	}
}
