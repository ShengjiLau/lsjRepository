package com.lcdt.wms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by ss on 2017/12/1.
 */
@Configuration
@PropertySource("classpath:redis.yml")
public class RedisConfiguration {

}
