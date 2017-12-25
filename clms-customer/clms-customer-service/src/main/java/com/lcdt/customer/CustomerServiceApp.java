package com.lcdt.customer;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import com.lcdt.clms.security.annontion.EnableClmsSecurity;
import com.lcdt.converter.ClmsResponseConvertConfig;
import com.lcdt.wms.config.DubboConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by yangbinq on 2017/11/22.
 */
@SpringBootApplication
@EnableTransactionManagement
@Import({com.lcdt.swagger.SwaggerConfig.class, DubboConfig.class, ClmsResponseConvertConfig.class})
@DubboComponentScan(basePackages = "com.lcdt.customer.rpc")
@EnableClmsSecurity
public class CustomerServiceApp {
	public static void main(String[] args) {
		ConfigurableApplicationContext run = new SpringApplicationBuilder().sources(CustomerServiceApp.class).run(args);
	}
}
