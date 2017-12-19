package com.lcdt.traffic.web.dto;

import com.lcdt.converter.ResponseData;
import com.lcdt.traffic.model.PlanLeaveMsg;

import java.util.List;

/**
 * Created by yangbinq on 2017/12/16.
 */
public class PlanLeaveMsgResultDto implements ResponseData {

    private long total;

    private List<PlanLeaveMsg> list;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<PlanLeaveMsg> getList() {
        return list;
    }

    public void setList(List<PlanLeaveMsg> list) {
        this.list = list;
    }
}

