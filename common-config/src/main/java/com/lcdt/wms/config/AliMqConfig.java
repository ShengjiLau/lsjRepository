package com.lcdt.wms.config;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.Properties;

@Configuration
@Import(AliMqConfigContants.class)
@AutoConfigureAfter(AliMqConfigContants.class)
public class AliMqConfig {

    @Autowired
    AliMqConfigContants configContants;

    @Bean
    public Producer producer() {
        Properties producerProperties = new Properties();
        producerProperties.setProperty(PropertyKeyConst.ProducerId, configContants.getProducer_id());
        producerProperties.setProperty(PropertyKeyConst.AccessKey, configContants.getAccess_key());
        producerProperties.setProperty(PropertyKeyConst.SecretKey, configContants.getSecret_key());
        producerProperties.setProperty(PropertyKeyConst.ONSAddr, configContants.getOnsAddress());
        Producer producer = ONSFactory.createProducer(producerProperties);
        producer.start();
        return producer;
    }

    @Bean
    public Consumer consumer(){
        Properties producerProperties = new Properties();
        producerProperties.setProperty(PropertyKeyConst.ProducerId, configContants.getProducer_id());
        producerProperties.setProperty(PropertyKeyConst.AccessKey, configContants.getAccess_key());
        producerProperties.setProperty(PropertyKeyConst.SecretKey, configContants.getSecret_key());
        producerProperties.setProperty(PropertyKeyConst.ONSAddr, configContants.getOnsAddress());
        producerProperties.setProperty(PropertyKeyConst.ConsumerId, configContants.getConsumer_id());
        Consumer consumer = ONSFactory.createConsumer(producerProperties);
        consumer.start();
        return consumer;
    }

}
