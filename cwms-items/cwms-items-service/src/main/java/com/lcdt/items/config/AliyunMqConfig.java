package com.lcdt.items.config;

import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.bean.ConsumerBean;
import com.aliyun.openservices.ons.api.bean.ProducerBean;
import com.aliyun.openservices.ons.api.bean.Subscription;
import com.lcdt.aliyunmq.AliyunConfigProperties;
import com.lcdt.aliyunmq.PropertiesUtil;
import com.lcdt.items.notify.mqlistener.ItemsInfoInitializationListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class AliyunMqConfig {

    @Autowired
    AliyunConfigProperties aliyunMqConfig;


    @Bean(initMethod = "start",destroyMethod = "shutdown")
    public ConsumerBean consumerBean(){

        Properties properties = PropertiesUtil.aliyunProperties(aliyunMqConfig);

        ConsumerBean consumerBean = new ConsumerBean();
        consumerBean.setProperties(properties);

        Map<Subscription, MessageListener> map = new HashMap<Subscription, MessageListener>();

        Subscription subscription = new Subscription();
        subscription.setTopic(aliyunMqConfig.getTopic());
        subscription.setExpression("*");
        map.put(subscription, notifyServiceListener());

        consumerBean.setSubscriptionTable(map);
        return consumerBean;
    }

    @Autowired
    public ItemsInfoInitializationListener notifyServiceListener(){
        ItemsInfoInitializationListener notifyServiceListener = new ItemsInfoInitializationListener();
        return notifyServiceListener;
    }

}
