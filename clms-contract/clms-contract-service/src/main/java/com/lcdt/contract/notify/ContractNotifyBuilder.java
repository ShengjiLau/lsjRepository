package com.lcdt.contract.notify;

import com.lcdt.notify.model.DefaultNotifyReceiver;
import com.lcdt.notify.model.DefaultNotifySender;

/**
 * Created by liuhu on 2018年6月28日.
 * Description:消息发送接收类
 */
public class ContractNotifyBuilder {

    public static final String OWN_WAYBILL_WEB_NOTIFY_URL="/transport.html#/myWaybill/";
    public static final String CUSTOMER_WAYBILL_WEB_NOTIFY_URL="/transport.html#/customerWaybill/";

    /***
     * 消息发送
     * @param companyId
     * @param userId
     * @return
     */
    public static DefaultNotifySender notifySender(Long companyId, Long userId) {
        DefaultNotifySender defaultNotifySender = new DefaultNotifySender(companyId, userId);
        return  defaultNotifySender;
    }


    /****
     *  消息接收者（带手机号，可短信通知）
     * @param companyId
     * @param userId
     * @param Phone
     * @return
     */
    public static DefaultNotifyReceiver notifyCarrierReceiver(Long companyId, Long userId, String Phone) {
        //消息测试
        DefaultNotifyReceiver defaultNotifyReceiver = new DefaultNotifyReceiver();
        defaultNotifyReceiver.setCarrierCompanyId(companyId);
        defaultNotifyReceiver.setCarrierUserId(userId);
        defaultNotifyReceiver.setCarrierPhoneNum(Phone);
        return defaultNotifyReceiver;
    }

    /**
     * 消息接收者（五手机号，一般用于无需手机通知的场景）
     * @param companyId
     * @param userId
     * @return
     */
    public static DefaultNotifyReceiver notifyCarrierReceiver(Long companyId, Long userId) {
        //消息测试
        DefaultNotifyReceiver defaultNotifyReceiver = new DefaultNotifyReceiver();
        defaultNotifyReceiver.setCarrierCompanyId(companyId);
        defaultNotifyReceiver.setCarrierUserId(userId);
        return defaultNotifyReceiver;
    }



}
