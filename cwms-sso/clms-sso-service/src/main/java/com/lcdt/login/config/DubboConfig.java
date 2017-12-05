package com.lcdt.login.config;

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
@DubboComponentScan(basePackages = {"com.lcdt.login.service","com.lcdt.*.service"})
public class DubboConfig {

	@Value("${applicationName}")
	public String applicationName = "clms-sso";

	@Value("${protocalport}")
	public Integer protocolPort = 20883;

	@Value("${zookeeperurl}")
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
		registryConfig.setProtocol("zookeeper");
		return registryConfig;
	}

	@Bean
	public ConsumerConfig consumerConfig(){
		ConsumerConfig consumerConfig = new ConsumerConfig();
		consumerConfig.setTimeout(3000);
		return consumerConfig;
	}

}
