package com.lcdt.login;

import com.lcdt.clms.permission.PermissionAppConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by ss on 2017/8/17.
 */
@SpringBootApplication
@ImportResource("dubbo-web-consumer.xml")
@Import(PermissionAppConfiguration.class)
public class CwmsLoginApp {

	public static void main(String[] args) {
		new SpringApplicationBuilder(CwmsLoginApp.class)
				.run(args);
	}

}
