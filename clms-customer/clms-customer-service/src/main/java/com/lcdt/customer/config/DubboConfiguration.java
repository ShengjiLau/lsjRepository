package com.lcdt.customer.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.AnnotationBean;
import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * Created by yangbinq on 2017/11/23.
 */
//@Configuration
@DubboComponentScan("com.lcdt.customer.rpcservice")
public class DubboConfiguration {
    @Value("${applicationName:customer-service}")
    private String APPLICATION_NAME;
    @Value("${registryAddress:zookeeper://127.0.0.1:2181}")
    private String REGISTRY_ADDRESS;
    @Value("${clientName:curator}")
    private String CLIENT_NAME;
    public String ANNOTATION_PACKAGE = "com.lcdt.customer.rpcservice";


    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(APPLICATION_NAME);
        return applicationConfig;
    }

    @Bean
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(REGISTRY_ADDRESS);
        registryConfig.setClient("curator");
        return registryConfig;
    }


    @Bean
    public AnnotationBean annotationBean() {
        AnnotationBean annotationBean = new AnnotationBean();
        annotationBean.setPackage(ANNOTATION_PACKAGE);
        return annotationBean;
    }
}
