package com.lcdt.login;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Created by ss on 2017/8/17.
 */
@SpringBootApplication
public class AppStarter {

	public static void main(String[] args) {
		new SpringApplicationBuilder(AppStarter.class)
				.run(args);
	}

}
