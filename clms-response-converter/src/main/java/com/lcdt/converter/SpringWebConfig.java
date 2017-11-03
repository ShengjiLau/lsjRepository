package com.lcdt.converter;

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


	private List<MediaType> supportMediaTypes = new ArrayList<>(10);

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		super.extendMessageConverters(converters);
		ClmsMessageConvert clmsMessageConvert = new ClmsMessageConvert();
		supportMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		converters.add(clmsMessageConvert);
	}
}
