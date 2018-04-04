package com.lcdt.aliyunmq;

import java.util.Properties;

public class PropertiesUtil {

    public static Properties aliyunProperties(AliyunConfigProperties configProperties) {
        Properties properties = new Properties();
        safeput(properties,"AccessKey", configProperties.getAccesskey());
        safeput(properties,"SecretKey", configProperties.getSecretkey());
        safeput(properties,"ConsumerId", configProperties.getConsumerId());
        safeput(properties,"ProducerId", configProperties.getProducerId());
        safeput(properties,"OnsAddress", configProperties.getOnsAddress());
        safeput(properties,"Topic", configProperties.getTopic());
        return properties;
    }

    public static void safeput(Properties properties,String key, Object object) {
        if (object != null) {
            properties.put(key, object);
        }
    }

}
