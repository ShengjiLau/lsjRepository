package com.lcdt.notify.web.api;

import com.github.pagehelper.PageHelper;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.notify.model.Timeline;
import com.lcdt.notify.service.TimeLineService;
import com.lcdt.notify.web.PageResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/timeline")
public class TimeLineApi {

    @Autowired
    TimeLineService timeLineService;

    @GetMapping("/list")
    public PageResultDto<Timeline> list(String tag,Long dataId, @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) Integer pageNo){
        if (pageNo != null && pageSize != null) {
            PageHelper.startPage(pageNo, pageSize);
        }
        List<Timeline> list = timeLineService.list(tag, SecurityInfoGetter.getCompanyId(),dataId);
        return new PageResultDto<Timeline>(list);
    }


}
