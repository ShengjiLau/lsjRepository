package com.lcdt.aliyunmq;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@ConfigurationProperties(prefix = "aliyunmq",ignoreUnknownFields = true)
public class AliyunConfigProperties extends AliyunMqProperties {


}
