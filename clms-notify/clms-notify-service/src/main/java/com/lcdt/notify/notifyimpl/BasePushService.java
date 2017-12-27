package com.lcdt.notify.notifyimpl;

import com.lcdt.notify.model.Notify;
import com.lcdt.notify.model.NotifyReceiver;

public class BasePushService implements PushService{

    @Override
    public void push(Notify notify, NotifyReceiver receiver, Object eventAttach) {

    }

    @Override
    public void push(NotifyReceiver receiver, String pushContent, Object attach) {

    }
}
