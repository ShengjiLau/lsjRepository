package com.lcdt.traffic.web.dto;

import com.lcdt.converter.ResponseData;
import com.lcdt.traffic.model.WaybillPlan;

import java.util.List;

/**
 * Created by yangbinq on 2017/12/18.
 */
public class WaybillPlanResultDto implements ResponseData {

    private long total;

    private List<WaybillPlan> list;


    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<WaybillPlan> getList() {
        return list;
    }

    public void setList(List<WaybillPlan> list) {
        this.list = list;
    }
}
