package com.lcdt.clms.security;

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
@ImportResource("dubbo-security-consumer.xml")
public class SecurityConfig {
}
