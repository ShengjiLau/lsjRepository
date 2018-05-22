package com.lcdt.aliyunmq;

import java.util.Properties;

public class PropertiesUtil {

    public static Properties aliyunProperties(AliyunMqConfig mqConfig) {
        Properties properties = new Properties();
        safeput(properties,"AccessKey", mqConfig.getAccesskey());
        safeput(properties,"SecretKey", mqConfig.getSecretkey());
        safeput(properties,"ConsumerId", mqConfig.getConsumerId());
        safeput(properties,"ProducerId", mqConfig.getProducerId());
        safeput(properties,"OnsAddress", mqConfig.getOnsAddress());
        safeput(properties,"Topic", mqConfig.getTopic());
        return properties;
    }

    public static void safeput(Properties properties,String key, Object object) {
        if (object != null) {
            properties.put(key, object);
        }
    }

}
