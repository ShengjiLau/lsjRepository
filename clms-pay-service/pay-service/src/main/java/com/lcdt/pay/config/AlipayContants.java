package com.lcdt.pay.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:alipay-${spring.profiles.active}.properties")
public class AlipayContants {

    private static String APP_PRIVATE_KEY;

    private static String FORMAT = "json";

    private static String ALIPAY_PUBLIC_KEY;

    private static String SIGN_TYPE;

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
