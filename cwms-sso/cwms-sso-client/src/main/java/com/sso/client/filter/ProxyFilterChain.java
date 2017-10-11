package com.sso.client.filter;

import javax.servlet.*;
import java.io.IOException;
import java.util.List;

/**
 * Created by ss on 2017/10/9.
 */
public class ProxyFilterChain extends FilterProxy {


	private List<Filter> filters;
	private int pos = 0;

	public ProxyFilterChain(List<Filter> filters) {
		this.filters = filters;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);
	}

	@Override
	public void invokeFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		if (filters == null || filters.isEmpty()) {
			chain.doFilter(request, response);
			return;
		}

		ChainProxy chainProxy = new ChainProxy(filters, chain);
		chainProxy.doFilter(request, response);
	}


	/**
	 * 代理FilterChain
	 */
	static class ChainProxy implements FilterChain {

		private List<Filter> filters;
		private FilterChain originalChain;
		private int pos = 0;
		public ChainProxy(List<Filter> filters, FilterChain chain) {
			this.originalChain = chain;
			this.filters = filters;
		}

		@Override
		public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {

			int size = filters.size();
			if (pos >= size) {
				originalChain.doFilter(request, response);
			}
			else {
//				System.out.println("do filter pos: " + pos);
				Filter filter = filters.get(pos);
				pos++;
				filter.doFilter(request, response, this);
			}
		}
	}


}
