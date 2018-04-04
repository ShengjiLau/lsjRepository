package com.lcdt.aliyunmq;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aliyunmq",ignoreUnknownFields = true)
public class AliyunConfigProperties {


    private String onsAddress;
    private String accesskey;
    private String secretkey;
    private String consumerId;
    private String producerId;
    private String topic;

    public String getOnsAddress() {
        return onsAddress;
    }

    public void setOnsAddress(String onsAddress) {
        this.onsAddress = onsAddress;
    }

    public String getAccesskey() {
        return accesskey;
    }

    public void setAccesskey(String accesskey) {
        this.accesskey = accesskey;
    }

    public String getSecretkey() {
        return secretkey;
    }

    public void setSecretkey(String secretkey) {
        this.secretkey = secretkey;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public String getProducerId() {
        return producerId;
    }

    public void setProducerId(String producerId) {
        this.producerId = producerId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
