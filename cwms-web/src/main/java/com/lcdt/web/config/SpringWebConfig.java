package com.lcdt.web.config;

import com.lcdt.web.http.ClmsMessageConvert;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ss on 2017/8/8.
 */
@Configuration
public class SpringWebConfig extends WebMvcConfigurerAdapter{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		super.addResourceHandlers(registry);
		registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
		registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
		registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/");
	}


	private List<MediaType> supportMediaTypes = new ArrayList<>(10);

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		ClmsMessageConvert clmsMessageConvert = new ClmsMessageConvert();
		supportMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		clmsMessageConvert.setSupportedMediaTypes(supportMediaTypes);
		converters.add(clmsMessageConvert);
//		super.configureMessageConverters(converters);
	}
}
