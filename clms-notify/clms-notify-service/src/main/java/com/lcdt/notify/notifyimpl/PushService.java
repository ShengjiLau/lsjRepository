package com.lcdt.notify.notifyimpl;

import com.lcdt.notify.model.Notify;
import com.lcdt.notify.model.NotifyReceiver;

public interface PushService {

    void push(Notify notify, NotifyReceiver receiver, Object eventAttach);

    void push(NotifyReceiver receiver,String pushContent,Object attach);

}
