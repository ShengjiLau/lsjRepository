package com.lcdt.aliyunmq;

public class AliyunMqProperties implements AliyunMqConfig {

    private String onsAddress;
    private String accesskey;
    private String secretkey;
    private String consumerId;
    private String producerId;
    private String topic;

    @Override
    public String getOnsAddress() {
        return onsAddress;
    }

    @Override
    public void setOnsAddress(String onsAddress) {
        this.onsAddress = onsAddress;
    }

    @Override
    public String getAccesskey() {
        return accesskey;
    }

    @Override
    public void setAccesskey(String accesskey) {
        this.accesskey = accesskey;
    }

    @Override
    public String getSecretkey() {
        return secretkey;
    }

    @Override
    public void setSecretkey(String secretkey) {
        this.secretkey = secretkey;
    }

    @Override
    public String getConsumerId() {
        return consumerId;
    }

    @Override
    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    @Override
    public String getProducerId() {
        return producerId;
    }

    @Override
    public void setProducerId(String producerId) {
        this.producerId = producerId;
    }

    @Override
    public String getTopic() {
        return topic;
    }

    @Override
    public void setTopic(String topic) {
        this.topic = topic;
    }
}
