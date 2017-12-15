package com.lcdt.wms.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ss on 2017/12/1.
 */
@Configuration
@DubboComponentScan(basePackages = {"com.lcdt.login.service","com.lcdt.*.service.impl","com.lcdt.clms.*.service.impl"})
public class DubboConfig {

	@Value("${applicationName:dubbo-app}")
	public String applicationName = "clms-sso";

	@Value("${protocalport:20880}")
	public Integer protocolPort = 20883;

	@Value("${zookeeperurl:127.0.0.1:2181}")
	private String zookeeperUrl = "127.0.0.1:2181";

	@Bean
	public ApplicationConfig applicationConfig(){
		ApplicationConfig applicationConfig = new ApplicationConfig();
		applicationConfig.setName(applicationName);
		return applicationConfig;
	}


	@Bean
	public ProtocolConfig protocolConfig(){
		ProtocolConfig protocolConfig = new ProtocolConfig();
		protocolConfig.setPort(protocolPort);
		return protocolConfig;
	}

	@Bean
	public RegistryConfig registryConfig(){
		RegistryConfig registryConfig = new RegistryConfig();
		registryConfig.setAddress(zookeeperUrl);
		registryConfig.setClient("zkclient");
		return registryConfig;
	}

	@Bean
	public ConsumerConfig consumerConfig(){
		ConsumerConfig consumerConfig = new ConsumerConfig();
		consumerConfig.setTimeout(30000);
		return consumerConfig;
	}

}
