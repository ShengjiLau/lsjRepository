package com.lcdt.traffic.web.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by liz on 2018/4/12.
 */
public class FeeAccountSaveParamsDto {
    @ApiModelProperty(value="企业id", hidden = true)
    private Long companyId;

    @ApiModelProperty(value="登录人id", hidden = true)
    private Long userId;

    @ApiModelProperty(value="登录人真实姓名", hidden = true)
    private String realName;

    @ApiModelProperty(value="0-应收，1-应付", hidden = true)
    private short isReceivable;

    @ApiModelProperty(value="运单id", required = true)
    private Long waybillId;

    @ApiModelProperty(value="运单编号", required = true)
    private String waybillCode;

    @ApiModelProperty(value="业务组id", required = true)
    private Long groupId;

    @ApiModelProperty(value="业务组名称", required = true)
    private String groupName;

    @ApiModelProperty(value="记账单list（包含流水list）", required = true)
    private List<FeeAccountDto> dtoList;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public short getIsReceivable() {
        return isReceivable;
    }

    public void setIsReceivable(short isReceivable) {
        this.isReceivable = isReceivable;
    }

    public Long getWaybillId() {
        return waybillId;
    }

    public void setWaybillId(Long waybillId) {
        this.waybillId = waybillId;
    }

    public String getWaybillCode() {
        return waybillCode;
    }

    public void setWaybillCode(String waybillCode) {
        this.waybillCode = waybillCode;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<FeeAccountDto> getDtoList() {
        return dtoList;
    }

    public void setDtoList(List<FeeAccountDto> dtoList) {
        this.dtoList = dtoList;
    }
}
