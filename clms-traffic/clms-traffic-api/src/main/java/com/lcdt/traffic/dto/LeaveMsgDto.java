package com.lcdt.traffic.dto;

import com.lcdt.converter.ResponseData;
import com.lcdt.traffic.model.PlanLeaveMsg;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangbinq on 2018/6/27.
 */
public class LeaveMsgDto implements Serializable,ResponseData {

    @ApiModelProperty(value = "计划ID")
    private Long waybillPlanId;

    @ApiModelProperty(value = "对应记录条数")
    private Integer total;

    @ApiModelProperty(value = "创建企业ID")
    private Long createCompanyId;

    public Long getWaybillPlanId() {
        return waybillPlanId;
    }

    private List<PlanLeaveMsg> list;


    public void setWaybillPlanId(Long waybillPlanId) {
        this.waybillPlanId = waybillPlanId;
    }

    public List<PlanLeaveMsg> getList() {
        return list;
    }

    public void setList(List<PlanLeaveMsg> list) {
        this.list = list;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Long getCreateCompanyId() {
        return createCompanyId;
    }

    public void setCreateCompanyId(Long createCompanyId) {
        this.createCompanyId = createCompanyId;
    }
}
