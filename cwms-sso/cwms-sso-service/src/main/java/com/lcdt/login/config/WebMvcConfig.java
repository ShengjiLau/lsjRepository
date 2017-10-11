package com.lcdt.login.config;

import com.lcdt.login.web.filter.CompanyInterceptor;
import com.lcdt.login.web.filter.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by ss on 2017/9/19.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {


	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/account/**");
		registry.addInterceptor(new CompanyInterceptor()).addPathPatterns("/account/**");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		super.addResourceHandlers(registry);
		registry.addResourceHandler("/assert").addResourceLocations("/static/assert/");
	}
}
