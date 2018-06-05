package com.lcdt.driver.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "bdyy")
@Component
public class BDYYConstants {

    private String ak;

    private String service_id;

    public String getAk() {
        return ak;
    }

    public void setAk(String ak) {
        this.ak = ak;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    @Override
    public String toString() {
        return "BDYYConstants{" +
                "ak='" + ak + '\'' +
                ", service_id='" + service_id + '\'' +
                '}';
    }
}
