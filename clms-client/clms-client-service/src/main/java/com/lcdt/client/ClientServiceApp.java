package com.lcdt.client;

import com.lcdt.clms.security.annontion.EnableClmsSecurity;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by yangbinq on 2017/11/22.
 */
@SpringBootApplication
@ImportResource("classpath:dubbo-client-provider.xml")
@Import({com.lcdt.swagger.SwaggerConfig.class})
@EnableClmsSecurity
public class ClientServiceApp {
	public static void main(String[] args) {
		ConfigurableApplicationContext run = new SpringApplicationBuilder().sources(ClientServiceApp.class).run(args);
	}
}
