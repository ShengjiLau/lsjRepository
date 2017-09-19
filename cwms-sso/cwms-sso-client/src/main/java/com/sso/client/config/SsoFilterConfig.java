package com.sso.client.config;

import com.sso.client.filter.SsoFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by ss on 2017/9/15.
 */
@Configuration
public class SsoFilterConfig extends WebMvcConfigurerAdapter {

	@Bean
	public FilterRegistrationBean ssoFilterRegisterBean(){
		FilterRegistrationBean ssoFilter = new FilterRegistrationBean(new SsoFilter());
		ssoFilter.addUrlPatterns("/");
		return ssoFilter;
	}

}
