package com.lcdt.notify.config;

import com.lcdt.aliyunmq.AliyunConfigProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "timeline-mq",ignoreUnknownFields = true)
@Component
public class TimeLineMqConfig  {

    private String timelineMQName;

    public String getTimelineMQName() {
        return timelineMQName;
    }

    public void setTimelineMQName(String timelineMQName) {
        this.timelineMQName = timelineMQName;
    }
}
