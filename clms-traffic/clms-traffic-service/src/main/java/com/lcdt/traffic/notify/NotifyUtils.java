package com.lcdt.traffic.notify;

import com.lcdt.notify.model.DefaultNotifyReceiver;
import com.lcdt.notify.model.DefaultNotifySender;

/**
 * Created by yangbinq on 2018/3/5.
 * Description:消息发送接收类
 */
public class NotifyUtils {

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
     *  消息接收(承运商)
     * @param companyId
     * @param userId
     * @param Phone
     * @return
     */
    public static DefaultNotifyReceiver notifyCarrierReceiver(Long companyId, Long userId, String Phone) {
        DefaultNotifyReceiver defaultNotifyReceiver = new DefaultNotifyReceiver(); //消息测试
        defaultNotifyReceiver.setCarrierCompanyId(companyId);
        defaultNotifyReceiver.setCarrierUserId(userId);
        defaultNotifyReceiver.setCarrierPhoneNum(Phone);
        return defaultNotifyReceiver;
    }



    /****
     *  消息接收(司机)
     * @param Phone
     * @return
     */
    public static DefaultNotifyReceiver notifyDriverReceiver(String Phone) {
        DefaultNotifyReceiver defaultNotifyReceiver = new DefaultNotifyReceiver(); //消息测试
        defaultNotifyReceiver.setDriverPhoneNum(Phone);
        return defaultNotifyReceiver;
    }


}
