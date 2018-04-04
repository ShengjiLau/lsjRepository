package com.lcdt.pay.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "wechatpay",ignoreUnknownFields = true)
@Component
public class WechatPayConfig {

    private String wechatPayUrl;

    public String getWechatPayUrl() {
        return wechatPayUrl;
    }

    public void setWechatPayUrl(String wechatPayUrl) {
        this.wechatPayUrl = wechatPayUrl;
    }
}
