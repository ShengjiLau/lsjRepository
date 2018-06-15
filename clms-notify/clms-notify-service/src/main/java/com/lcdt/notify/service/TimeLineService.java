package com.lcdt.notify.service;

import com.lcdt.notify.dao.TimelineMapper;
import com.lcdt.notify.model.Timeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TimeLineService {

    @Autowired
    private TimelineMapper mapper;

    @Transactional(rollbackFor = Exception.class)
    public Timeline save(Timeline timeline) {
        mapper.insert(timeline);
        return timeline;
    }

    public List<Timeline> list(String tag,Long companyId,Long dataId){
        return mapper.selectByTag(companyId, tag,dataId);
    }



}
