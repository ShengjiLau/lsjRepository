package com.lcdt.pay.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;

@Configuration
@Order(LOWEST_PRECEDENCE)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.antMatcher("/alipay/notify").authorizeRequests().anyRequest().permitAll();
        http.antMatcher("/wechatpaynotify").authorizeRequests().anyRequest().permitAll();
    }
}
