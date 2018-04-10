package com.lcdt.pay.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "wechatpay",ignoreUnknownFields = true)
@Component
public class WechatPayConfig {

    private String wechatPayUrl;

    private String alipayReturnUrl;

    private String alipayNotifyUrl;


    public String getAlipayReturnUrl() {
        return alipayReturnUrl;
    }

    public void setAlipayReturnUrl(String alipayReturnUrl) {
        this.alipayReturnUrl = alipayReturnUrl;
    }

    public String getAlipayNotifyUrl() {
        return alipayNotifyUrl;
    }

    public void setAlipayNotifyUrl(String alipayNotifyUrl) {
        this.alipayNotifyUrl = alipayNotifyUrl;
    }

    public String getWechatPayUrl() {
        return wechatPayUrl;
    }

    public void setWechatPayUrl(String wechatPayUrl) {
        this.wechatPayUrl = wechatPayUrl;
    }
}
