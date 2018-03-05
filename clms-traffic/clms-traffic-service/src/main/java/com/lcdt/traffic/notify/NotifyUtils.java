package com.lcdt.traffic.notify;

import com.lcdt.notify.model.DefaultNotifyReceiver;
import com.lcdt.notify.model.DefaultNotifySender;

/**
 * Created by yangbinq on 2018/3/5.
 * Description:消息发送接收类
 */
public class NotifyUtils {

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
     *  消息接收
     * @param companyId
     * @param userId
     * @param Phone
     * @return
     */
    public static DefaultNotifyReceiver notifyReceiver(Long companyId, Long userId, String Phone) {
        DefaultNotifyReceiver defaultNotifyReceiver = new DefaultNotifyReceiver(); //消息测试
        defaultNotifyReceiver.setCompanyId(companyId);
        defaultNotifyReceiver.setUserId(userId);
        defaultNotifyReceiver.setPhoneNum(Phone);
        return defaultNotifyReceiver;
    }



}
