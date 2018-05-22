package com.lcdt.notify.config;

import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.bean.ConsumerBean;
import com.aliyun.openservices.ons.api.bean.Subscription;
import com.lcdt.aliyunmq.AliyunConfigProperties;
import com.lcdt.aliyunmq.PropertiesUtil;
import com.lcdt.notify.mqlistener.NotifyServiceListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class MQConfig {

    @Autowired
    AliyunConfigProperties aliyunConfigProperties;

    @Autowired
    NotifyServiceListener notifyServiceListener;

    @Bean(initMethod = "start",destroyMethod = "shutdown")
    public ConsumerBean consumerBean(){

        Properties properties = PropertiesUtil.aliyunProperties(aliyunConfigProperties);

        ConsumerBean consumerBean = new ConsumerBean();
        consumerBean.setProperties(properties);

        Map<Subscription, MessageListener> map = new HashMap<Subscription, MessageListener>();

        Subscription subscription = new Subscription();
        subscription.setTopic(aliyunConfigProperties.getTopic());
        subscription.setExpression("*");
        map.put(subscription, notifyServiceListener);

        consumerBean.setSubscriptionTable(map);
        return consumerBean;
    }
    @Bean(initMethod = "start",destroyMethod = "shutdown",name = "timelinelistener")
    public ConsumerBean timeLineconsumerBean(){

        Properties properties = PropertiesUtil.aliyunProperties(aliyunConfigProperties);

        ConsumerBean consumerBean = new ConsumerBean();
        consumerBean.setProperties(properties);

        Map<Subscription, MessageListener> map = new HashMap<Subscription, MessageListener>();

        Subscription subscription = new Subscription();
        subscription.setTopic("topic");
        subscription.setExpression("*");
        map.put(subscription, notifyServiceListener);

        consumerBean.setSubscriptionTable(map);
        return consumerBean;
    }


}
