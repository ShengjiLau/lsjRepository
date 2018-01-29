package com.lcdt.traffic.util;

/**
 * Created by yangbinq on 2018/1/29.
 */
public class PlanMsgSendBO {

    private static PlanMsgSendBO instance;

    private PlanMsgSendBO() {
    }

    public synchronized static PlanMsgSendBO getInstance() {
        if (instance == null) {
            instance = new PlanMsgSendBO();
        }
        return instance;
    }




}
