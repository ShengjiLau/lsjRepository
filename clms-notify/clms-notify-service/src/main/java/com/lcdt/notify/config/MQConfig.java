package com.lcdt.notify.config;

import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.bean.ConsumerBean;
import com.aliyun.openservices.ons.api.bean.Subscription;
import com.lcdt.aliyunmq.AliyunConfigProperties;
import com.lcdt.aliyunmq.PropertiesUtil;
import com.lcdt.notify.mqlistener.NotifyServiceListener;
import com.lcdt.notify.mqlistener.TimeLineMqListener;
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
    TimeLineMqConfig timeLineMqConfig;

    @Autowired
    NotifyServiceListener notifyServiceListener;

    @Autowired
    TimeLineMqListener timeLineMqListener;

    @Bean(initMethod = "start",destroyMethod = "shutdown")
    public ConsumerBean consumerBean(){
        return configConsumer(aliyunConfigProperties.getTopic(), notifyServiceListener);
    }

    @Bean(initMethod = "start",destroyMethod = "shutdown",name = "timelinelistener")
    public ConsumerBean timeLineconsumerBean(){
        return configConsumer(timeLineMqConfig.getTimelineMQName(), timeLineMqListener);
    }

    private ConsumerBean configConsumer(String topic,MessageListener messageListener) {
        ConsumerBean consumerBean = baseconfigConsumerBean();
        Subscription subscription = new Subscription();
        subscription.setExpression("*");
        subscription.setTopic(topic);
        return addSubcription(consumerBean,subscription,messageListener);
    }


    private ConsumerBean addSubcription(ConsumerBean consumerBean,Subscription subscription,MessageListener messageListener){

        Map<Subscription, MessageListener> subscriptionTable = consumerBean.getSubscriptionTable();
        if (subscriptionTable == null) {
            subscriptionTable = new HashMap<Subscription, MessageListener>();
        }
        subscriptionTable.put(subscription,messageListener);
        consumerBean.setSubscriptionTable(subscriptionTable);
        return consumerBean;
    }

    private ConsumerBean baseconfigConsumerBean(){
        Properties properties = PropertiesUtil.aliyunProperties(aliyunConfigProperties);
        ConsumerBean consumerBean = new ConsumerBean();
        consumerBean.setProperties(properties);
        return consumerBean;
    }

}
