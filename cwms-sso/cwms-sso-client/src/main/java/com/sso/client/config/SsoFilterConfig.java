package com.sso.client.config;

import com.sso.client.filter.ProxyFilterChain;
import com.sso.client.filter.SsoFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.Filter;
import java.util.ArrayList;

/**
 * Created by ss on 2017/9/15.
 */
@Configuration
public class SsoFilterConfig extends WebMvcConfigurerAdapter {

	@Bean
	public FilterRegistrationBean ssoFilterRegisterBean(){
		ArrayList<Filter> filters = new ArrayList<>();
		SsoFilter ssoFilter = new SsoFilter();
		filters.add(ssoFilter);
		ProxyFilterChain proxyFilterChain = new ProxyFilterChain(filters);
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(proxyFilterChain);
		return filterRegistrationBean;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
	}
}
