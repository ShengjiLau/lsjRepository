package com.lcdt.clms.security;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import com.lcdt.clms.security.config.SecurityConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by ss on 2017/10/13.
 */
@Configuration
@ComponentScan("com.lcdt.clms.security.config")
@Import(SecurityConfiguration.class)
@DubboComponentScan(basePackages = "com.lcdt.clms.security.config")
public class SecurityConfig {

}
