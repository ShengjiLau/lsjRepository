package com.lcdt.wms.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "mq")
@PropertySource("classpath:/mq.properties")
@Component
public class AliMqConfigContants {

    /**
     * 启动测试之前请替换如下 XXX 为您的配置
     */
    public String topic;
    public String producer_id;
    public String consumer_id;
    public String order_topic;
    public String order_producer_id;
    public String order_consumer_id;
    public String access_key;
    public String secret_key;
    public String tag;

    public String onsAddress;

    public String getOnsAddress() {
        return onsAddress;
    }

    public void setOnsAddress(String onsAddress) {
        this.onsAddress = onsAddress;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getProducer_id() {
        return producer_id;
    }

    public void setProducer_id(String producer_id) {
        this.producer_id = producer_id;
    }

    public String getConsumer_id() {
        return consumer_id;
    }

    public void setConsumer_id(String consumer_id) {
        this.consumer_id = consumer_id;
    }

    public String getOrder_topic() {
        return order_topic;
    }

    public void setOrder_topic(String order_topic) {
        this.order_topic = order_topic;
    }

    public String getOrder_producer_id() {
        return order_producer_id;
    }

    public void setOrder_producer_id(String order_producer_id) {
        this.order_producer_id = order_producer_id;
    }

    public String getOrder_consumer_id() {
        return order_consumer_id;
    }

    public void setOrder_consumer_id(String order_consumer_id) {
        this.order_consumer_id = order_consumer_id;
    }

    public String getAccess_key() {
        return access_key;
    }

    public void setAccess_key(String access_key) {
        this.access_key = access_key;
    }

    public String getSecret_key() {
        return secret_key;
    }

    public void setSecret_key(String secret_key) {
        this.secret_key = secret_key;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
