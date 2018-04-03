package com.lcdt.items.notify.mqlistener;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.lcdt.items.service.ItemsInfoInitializationService;
import com.lcdt.notify.model.JsonParserPropertyEvent;
import com.lcdt.userinfo.model.UserCompRel;
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

        logger.info("接收mq消息 msgId:{}",msgID);

        byte[] body = message.getBody();

        //反序列化 转化成bean
        UserCompRel event = JSONObject.parseObject(body, UserCompRel.class, Feature.SupportNonPublicField);
        itemsInfoInitializationService.itemInfoInitialization(event.getCompId(),event.getUser().getPhone(),event.getUserId());

        return CommitMessage;
    }

}
