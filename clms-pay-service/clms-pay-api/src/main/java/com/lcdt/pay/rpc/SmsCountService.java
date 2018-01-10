package com.lcdt.pay.rpc;

public interface SmsCountService {

    public static String smsServiceProductName = "sms_service";

    public static String gmsLocationService = "gms_service";

    void reduceSmsCount(Long companyId,String productName);

}
