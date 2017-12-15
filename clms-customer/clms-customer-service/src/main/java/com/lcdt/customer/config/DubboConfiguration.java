package com.lcdt.customer.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by yangbinq on 2017/12/13.
 */
@Configuration
public class DubboConfiguration {

    @Value("${applicationName:provider-customer}")
    public String applicationName;

    @Value("${zookeeperurl:zookeeper://127.0.0.1:2181}")
    public String zookeeperurl;

    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(applicationName);
        return applicationConfig;
    }

    @Bean
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(zookeeperurl);
        registryConfig.setClient("curator");
        return registryConfig;
    }
}