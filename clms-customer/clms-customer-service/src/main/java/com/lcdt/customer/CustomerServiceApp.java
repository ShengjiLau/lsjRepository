package com.lcdt.customer;

import com.lcdt.clms.security.annontion.EnableClmsSecurity;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by yangbinq on 2017/11/22.
 */
@SpringBootApplication
@EnableTransactionManagement
@ImportResource("classpath:dubbo-customer-provider.xml")
@Import({com.lcdt.swagger.SwaggerConfig.class})
@EnableClmsSecurity
public class CustomerServiceApp {
	public static void main(String[] args) {
		ConfigurableApplicationContext run = new SpringApplicationBuilder().sources(CustomerServiceApp.class).run(args);
	}
}
