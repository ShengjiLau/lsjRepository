package com.lcdt.notify.service;

/**
 * Created by ybq on 2017/8/9.
 */
public interface SmsService {

    /***
     * 短息发送
     * @param phones
     * @param signature
     * @param message
     */
    boolean sendSms(String[] phones, String signature, String message);

    /***
     * 短信余额查询
     * @return
     */
    String findSmsBalance();


}
