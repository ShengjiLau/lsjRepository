package com.lcdt.wms.config;

import com.alibaba.dubbo.config.RegistryConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ss on 2017/12/1.
 */
@Configuration
public class DubboCommonConfig {

	@Value("ZKHOST")
	static String ZKHOST;


	@Bean
	public RegistryConfig registryConfig(){
		RegistryConfig registryConfig = new RegistryConfig();
		registryConfig.setAddress(ZKHOST);
		registryConfig.setProtocol("zookeeper");
		return registryConfig;
	}

}
