package com.lcdt.items.notify.mqlistener;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.lcdt.items.service.ItemsInfoInitializationService;
import com.lcdt.notify.model.JsonParserPropertyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.aliyun.openservices.ons.api.Action.CommitMessage;

/**
 * Created by lyqishan on 2018/2/6
 */
@Component
public class ItemsInfoInitializationListener implements MessageListener {

    Executor executor = Executors.newFixedThreadPool(4);
    private Logger logger = LoggerFactory.getLogger(ItemsInfoInitializationListener.class);
    @Autowired
    private ItemsInfoInitializationService itemsInfoInitializationService;
    /**
     * 需要保证多线程安全
     * @param message
     * @param context
     * @return
     */
    @Override
    public Action consume(Message message, ConsumeContext context) {
        String msgID = message.getMsgID();

        byte[] body = message.getBody();

        //反序列化 转化成bean
        JsonParserPropertyEvent event = JSONObject.parseObject(body, JsonParserPropertyEvent.class, Feature.SupportNonPublicField);

        logger.info("consume message : {} {} {}",event.getClass().getSimpleName(),event.getEventName(),event.getSender().sendCompanyId());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                itemsInfoInitializationService.itemInfoInitialization(event.getSender().getCompanyId(),event.getEventName(),event.getSender().getUserId());
            }
        });

        return CommitMessage;
    }

}
