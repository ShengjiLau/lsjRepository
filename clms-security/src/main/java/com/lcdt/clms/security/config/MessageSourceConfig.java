package com.lcdt.clms.security.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * Created by ss on 2017/8/11.
 */
@Configuration
public class MessageSourceConfig {

	@Bean
	public MessageSource messageSource(){
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setAlwaysUseMessageFormat(true);
		messageSource.setBasename("classpath:/org/springframework/security/messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

}
