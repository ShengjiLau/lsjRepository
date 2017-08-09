package com.lcdt.notify.dto;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by ybq on 2017/8/9.
 */
public class SmsDto {

    @Value("${url}")
    private String url;
    @Value("${name}")
    private String name;
    @Value("${pwd}")
    private String pwd;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
