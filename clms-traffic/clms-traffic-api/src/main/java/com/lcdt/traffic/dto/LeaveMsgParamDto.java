package com.lcdt.traffic.dto;

import com.lcdt.converter.ResponseData;
import com.lcdt.traffic.model.PlanLeaveMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangbinq on 2018/6/27.
 */
public class LeaveMsgParamDto implements Serializable {


    @ApiModelProperty(value = "参数对象")
    private List<LeaveMsgDto> leaveMsgDtoList;

    @ApiModelProperty(value = "登录企业ID")
    private Long loginCmpId;



    public List<LeaveMsgDto> getLeaveMsgDtoList() {
        return leaveMsgDtoList;
    }

    public void setLeaveMsgDtoList(List<LeaveMsgDto> leaveMsgDtoList) {
        this.leaveMsgDtoList = leaveMsgDtoList;
    }


    public Long getLoginCmpId() {
        return loginCmpId;
    }

    public void setLoginCmpId(Long loginCmpId) {
        this.loginCmpId = loginCmpId;
    }
}
