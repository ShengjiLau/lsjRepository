package com.lcdt.traffic;

import com.lcdt.client.ClientAppConfiguration;
import com.lcdt.clms.security.annontion.EnableClmsSecurity;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by yangbinq on 2017/11/20.
 */
@SpringBootApplication
@ImportResource("dubbo-traffic-provider.xml")
@Import({ClientAppConfiguration.class}) //, com.lcdt.swagger.SwaggerConfig.class
@EnableClmsSecurity
public class TrafficServiceApp {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = new SpringApplicationBuilder()
                .sources(TrafficServiceApp.class)
                .run(args);

    }
}
