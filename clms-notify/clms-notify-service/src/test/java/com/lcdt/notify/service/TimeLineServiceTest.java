package com.lcdt.notify.service;

import com.lcdt.notify.NotifyServiceApp;
import com.lcdt.notify.model.Timeline;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NotifyServiceApp.class)
public class TimeLineServiceTest {

    @Autowired
    TimeLineService timeLineService;

    @Test
    public void saveTimeLineTest(){
        Timeline timeline = new Timeline();
        timeline.setActionDes("test");
        timeline.setCompanyId(1L);
        timeline.setDataid(11L);
        timeline.setActionTime(new Date());
        timeline.setActionTitle("testTitle");
        timeline.setSearchkey("sea");
        timeline.setMqMessageId("12");
        timeLineService.save(timeline);
    }

}
