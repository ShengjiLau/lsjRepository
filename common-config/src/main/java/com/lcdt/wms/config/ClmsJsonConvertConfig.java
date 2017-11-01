package com.lcdt.wms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ss on 2017/10/30.
 */
@Configuration
public class ClmsJsonConvertConfig extends WebMvcConfigurerAdapter {
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
