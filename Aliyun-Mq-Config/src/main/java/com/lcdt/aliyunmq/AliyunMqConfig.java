package com.lcdt.aliyunmq;

public interface AliyunMqConfig {
    String getOnsAddress();

    void setOnsAddress(String onsAddress);

    String getAccesskey();

    void setAccesskey(String accesskey);

    String getSecretkey();

    void setSecretkey(String secretkey);

    String getConsumerId();

    void setConsumerId(String consumerId);

    String getProducerId();

    void setProducerId(String producerId);

    String getTopic();

    void setTopic(String topic);
}
