package com.lcdt.notify.web.api;

import com.lcdt.notify.model.Timeline;
import com.lcdt.notify.service.TimeLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class TimeLineApi {

    @Autowired
    TimeLineService timeLineService;

    @GetMapping("/api/timeline/list")
    public List<Timeline> list(){
        return Collections.emptyList();
    }


}
