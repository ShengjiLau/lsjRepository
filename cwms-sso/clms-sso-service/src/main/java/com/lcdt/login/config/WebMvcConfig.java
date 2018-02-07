package com.lcdt.login.config;

import com.lcdt.login.web.filter.CompanyInterceptorAbstract;
import com.lcdt.login.web.filter.LoginInterceptorAbstract;
import org.springframework.context.annotation.Bean;
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
		registry.addInterceptor(loginInterceptorAbstract()).addPathPatterns("/account/**");
		registry.addInterceptor(new CompanyInterceptorAbstract()).addPathPatterns("/account/**");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		super.addResourceHandlers(registry);
		registry.addResourceHandler("/assert").addResourceLocations("/static/assert/");
	}

	@Bean
	public LoginInterceptorAbstract loginInterceptorAbstract(){
		return new LoginInterceptorAbstract();
	}

}
