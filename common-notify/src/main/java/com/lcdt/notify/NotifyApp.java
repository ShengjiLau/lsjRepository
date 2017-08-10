package com.lcdt.notify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class}) //启动排除数据库注入
@PropertySource(value={"classpath:sms.properties"},ignoreResourceNotFound=true) //找不到不报错
@ImportResource("dubbo-notify-provider.xml")
public class NotifyApp {
    public static void main(String[] args) {
        SpringApplication.run(NotifyApp.class, args);
    }

}
