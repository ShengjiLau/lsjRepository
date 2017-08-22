package com.lcdt.userinfo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;

import java.util.concurrent.CountDownLatch;

/**
 * Created by ss on 2017/7/31.
 */
@SpringBootApplication
@ImportResource("dubbo-user-provider.xml")
public class UserServiceApp {

	public static void main(String[] args) {

		ConfigurableApplicationContext run = new SpringApplicationBuilder()
				.sources(UserServiceApp.class)
				.web(false)
				.run(args);

		//block main thread
		CountDownLatch countDownLatch = new CountDownLatch(1);
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
