package com.lcdt.notify.web.api;

import com.github.pagehelper.PageHelper;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.notify.model.Timeline;
import com.lcdt.notify.service.TimeLineService;
import com.lcdt.notify.web.PageResultDto;
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
    public PageResultDto<Timeline> list(String tag, Integer pageSize, Integer pageNo){
        PageHelper.startPage(pageNo, pageSize);
        List<Timeline> list = timeLineService.list(tag, SecurityInfoGetter.getCompanyId());
        return new PageResultDto<Timeline>(list);
    }


}
