package com.lcdt.notify.mqlistener;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.lcdt.notify.model.Timeline;
import com.lcdt.notify.service.TimeLineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TimeLineMqListener implements MessageListener{

    private Logger logger = LoggerFactory.getLogger(TimeLineMqListener.class);

    @Autowired
    TimeLineService timeLineService;

    @Override
    public Action consume(Message message, ConsumeContext context) {

        Timeline timelineMessage = JSONObject.parseObject(message.getBody(), Timeline.class, Feature.SupportNonPublicField);
        timelineMessage.setMqMessageId(message.getMsgID());
        logger.info("接收到 timeline message {}",timelineMessage);
        timeLineService.save(timelineMessage);
        return Action.CommitMessage;
    }
}
