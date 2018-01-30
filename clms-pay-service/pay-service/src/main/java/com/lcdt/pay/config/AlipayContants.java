package com.lcdt.pay.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@ConfigurationProperties
@PropertySource("classpath:/alipay-${spring.profiles.active}.properties")
@Component
public class AlipayContants {

    private static String APP_PRIVATE_KEY;

    private static String FORMAT = "json";

    private static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtNS7AWkByJcQ7iuHoZH3Xj2RyoTl0HPqEl2Djn1T0WsPcOPgBvUzj2Z1PjA191SwsIwU8oZeUGiEye2iV80J9eusHaOpFo7xouEuydkSruhy5wEhvMVUpNxWHWw4W297h/b6PaoZ5jRe+HzmZquNWrGYxvG2y8tQkiBi2EqZVF7RWPZ3e0wlTsoepc36lr7b0123HMT1utNKC4HkmYQBd70LVGi/RWWuune7Y+HNFqq//Z4N9LKrIqQEcr/VmLmtXgQ/bWAPXgFidKNUmm6oVuaCtoFVYqwyMkpFLRtzCKyYc8TTJfNhGDHqbimoFtMsjiI7l9FhMNGQvI/rS/j6LwIDAQAB";

    private static String SIGN_TYPE = "RSA2";

    private static String returnUrl = "http://domain.com/CallBack/return_url.jsp";

    private static  String notifyUrl = "/alipay/notify";

    private static String APP_ID = "";

    private static String API_GATAWAY = "";


    public static String getAppPrivateKey() {
        return APP_PRIVATE_KEY;
    }

    public static void setAppPrivateKey(String appPrivateKey) {
        APP_PRIVATE_KEY = appPrivateKey;
    }

    public static String getFORMAT() {
        return FORMAT;
    }

    public static void setFORMAT(String FORMAT) {
        AlipayContants.FORMAT = FORMAT;
    }

    public static String getAlipayPublicKey() {
        return ALIPAY_PUBLIC_KEY;
    }

    public static void setAlipayPublicKey(String alipayPublicKey) {
        ALIPAY_PUBLIC_KEY = alipayPublicKey;
    }

    public static String getSignType() {
        return SIGN_TYPE;
    }

    public static void setSignType(String signType) {
        SIGN_TYPE = signType;
    }

    public static String getReturnUrl() {
        return returnUrl;
    }

    public static void setReturnUrl(String returnUrl) {
        AlipayContants.returnUrl = returnUrl;
    }

    public static String getNotifyUrl() {
        return notifyUrl;
    }

    public static void setNotifyUrl(String notifyUrl) {
        AlipayContants.notifyUrl = notifyUrl;
    }

    public static String getAppId() {
        return APP_ID;
    }

    public static void setAppId(String appId) {
        APP_ID = appId;
    }

    public static String getApiGataway() {
        return API_GATAWAY;
    }

    public static void setApiGataway(String apiGataway) {
        API_GATAWAY = apiGataway;
    }
}
