package com.lcdt.notify;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ImportResource("classpath:producer.xml")
@Inherited
@ComponentScan("com.lcdt.notify")
public @interface EnableClmsNotifyProducer {
}
