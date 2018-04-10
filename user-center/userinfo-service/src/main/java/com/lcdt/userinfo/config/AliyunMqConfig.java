package com.lcdt.userinfo.config;

import com.aliyun.openservices.ons.api.bean.ProducerBean;
import com.lcdt.aliyunmq.AliyunConfigProperties;
import com.lcdt.aliyunmq.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class AliyunMqConfig {

    @Autowired
    AliyunConfigProperties aliyunMqConfig;

    @Bean(initMethod = "start",destroyMethod = "shutdown")
    public ProducerBean producerBean(){
        Properties properties = PropertiesUtil.aliyunProperties(aliyunMqConfig);
        ProducerBean producerBean = new ProducerBean();
        producerBean.setProperties(properties);
        return producerBean;
    }

}
