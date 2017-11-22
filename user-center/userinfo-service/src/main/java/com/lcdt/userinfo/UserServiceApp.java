package com.lcdt.userinfo;

import com.lcdt.clms.permission.PermissionAppConfiguration;
import com.lcdt.clms.security.annontion.EnableClmsSecurity;
import com.lcdt.converter.ClmsResponseConvertConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import java.util.concurrent.CountDownLatch;

/**
 * Created by ss on 2017/7/31.
 */
@SpringBootApplication
@ImportResource("dubbo-user-provider.xml")
@Import({PermissionAppConfiguration.class, ClmsResponseConvertConfig.class})
@EnableClmsSecurity
public class UserServiceApp {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = new SpringApplicationBuilder()
				.sources(UserServiceApp.class)
				.run(args);

	}
}
