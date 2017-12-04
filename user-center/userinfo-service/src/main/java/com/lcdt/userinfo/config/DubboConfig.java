package com.lcdt.userinfo.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ss on 2017/12/4.
 */
@Configuration
@DubboComponentScan(basePackages = {"com.lcdt.userinfo.service.impl","com.lcdt.clms.permission.service.impl"})
public class DubboConfig {

	@Value("")
	private static String ZKHOST = "";


	@Bean
	public ApplicationConfig applicationConfig(){
		ApplicationConfig applicationConfig = new ApplicationConfig();
		applicationConfig.setName("user-service");
		return applicationConfig;
	}

	@Bean
	public RegistryConfig registryConfig(){
		RegistryConfig registryConfig = new RegistryConfig();
		registryConfig.setAddress("127.0.0.1:2181");
		registryConfig.setProtocol("zookeeper");
		return registryConfig;
	}

	@Bean
	public ConsumerConfig consumerConfig(){
		ConsumerConfig consumerConfig = new ConsumerConfig();
		consumerConfig.setTimeout(3000);
		return consumerConfig;
	}


//	public ProviderConfig providerConfig(){
//		ProviderConfig providerConfig = new ProviderConfig();
//		providerConfig.set
//	}

}
