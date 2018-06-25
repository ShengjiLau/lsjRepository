package com.lcdt.userinfo;

import com.lcdt.aliyunmq.AliyunConfigProperties;
import com.lcdt.clms.permission.PermissionAppConfiguration;
import com.lcdt.clms.security.annontion.EnableClmsSecurity;
import com.lcdt.converter.ClmsResponseConvertConfig;
import com.lcdt.wms.config.DubboConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import java.util.concurrent.CountDownLatch;

/**
 * Created by ss on 2017/7/31.
 */
@SpringBootApplication
@Import({AliyunConfigProperties.class,PermissionAppConfiguration.class, ClmsResponseConvertConfig.class, DubboConfig.class})
@EnableClmsSecurity
@ComponentScan({"com.lcdt.swagger","com.lcdt.userinfo"})
public class UserServiceApp {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = new SpringApplicationBuilder()
				.sources(UserServiceApp.class)
				.run(args);
	}
}
