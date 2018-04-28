package com.lcdt.customer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class ConfigConstant {

    @Value("${bindurlhost}")
    public String bindurlHost;

    @Value("${logouthost}")
    public String logoutHost;

    @Value("${mainindex}")
    public String mainindex;
}
