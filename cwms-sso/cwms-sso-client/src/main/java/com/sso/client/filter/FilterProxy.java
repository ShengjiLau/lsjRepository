package com.sso.client.filter;

import javax.management.relation.Role;
import javax.servlet.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ss on 2017/9/28.
 */
public abstract class FilterProxy implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		invokeFilter(request,response,chain);
	}

	@Override
	public void destroy() {

	}

	public abstract void invokeFilter(ServletRequest request,ServletResponse response,FilterChain chain) throws IOException, ServletException;

}
