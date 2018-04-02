package com.lcdt.pay.rpc;

public interface SmsCountService {

    public static String smsServiceProductName = "sms_service";

    public static String gmsLocationService = "gms_service";

    void reduceSmsCount(Long countLogId,String productName,Integer reduceNum);

    boolean checkSmsCount(Long companyId,Integer num);

    boolean checkProductCount(Long companyId,Integer num,String serviceName);

    int getProductCount(Long companyId,String serviceName);

    /**
     * 扣费
     * @param companyId
     * @param serviceName
     * @param num
     */
    void deduction(Long companyId, String serviceName, Integer num);

}
