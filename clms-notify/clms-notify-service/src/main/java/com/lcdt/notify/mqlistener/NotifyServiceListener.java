package com.lcdt.notify.mqlistener;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.lcdt.notify.dao.MqMessageLogMapper;
import com.lcdt.notify.model.JsonParserPropertyEvent;
import com.lcdt.notify.model.MqMessageLog;
import com.lcdt.notify.model.TrafficStatusChangeEvent;
import com.lcdt.notify.service.SendNotifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.*;

import static com.aliyun.openservices.ons.api.Action.CommitMessage;

@Component
public class NotifyServiceListener implements MessageListener{

    Executor executor = Executors.newFixedThreadPool(4);

    @Autowired
    private SendNotifyService sendNotifyService;

    private Logger logger = LoggerFactory.getLogger(NotifyServiceListener.class);

    /**
     * 需要保证多线程安全
     * @param message
     * @param context
     * @return
     */
    @Override
    public Action consume(Message message, ConsumeContext context) {
        String msgID = message.getMsgID();

        if (isMessageBeRead(msgID)) {
            return CommitMessage;
        }

        byte[] body = message.getBody();

        //反序列化 转化成bean
        JsonParserPropertyEvent event = JSONObject.parseObject(body, JsonParserPropertyEvent.class, Feature.SupportNonPublicField);

        logger.info("consume message : {} {} {}",event.getClass().getSimpleName(),event.getEventName(),event.getSender().sendCompanyId());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                sendNotifyService.handleEvent(event);
            }
        });


        return CommitMessage;
    }


    @Autowired
    MqMessageLogMapper mapper;

    @Transactional(rollbackFor = Exception.class)
    private void saveMessageLog(String msgId){
        MqMessageLog mqMessageLog = new MqMessageLog();
        mqMessageLog.setMessageId(msgId);
        mqMessageLog.setBeHandle(true);
        mapper.insert(mqMessageLog);
    }


    @Transactional(rollbackFor = Exception.class)
    private boolean isMessageBeRead(String msgId){
        MqMessageLog mqMessageLog = mapper.selectByPrimaryKey(msgId);
        if (mqMessageLog == null) {
            saveMessageLog(msgId);
        }
        return mqMessageLog == null ? false : mqMessageLog.getBeHandle();
    }

}
