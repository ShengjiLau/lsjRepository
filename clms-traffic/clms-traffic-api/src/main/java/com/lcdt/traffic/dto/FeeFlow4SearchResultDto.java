package com.lcdt.traffic.dto;

/**
 * Created by yangbinq on 2018/4/12.
 */

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by yangbinq on 2018/4/12.
 * Description:应收、应付列表查返回结果集合
 */
public class FeeFlow4SearchResultDto implements java.io.Serializable {

    @ApiModelProperty("业务流水号")
    private String flowCode;

    @ApiModelProperty("记录时间")
    private Date createDate;

    @ApiModelProperty("业务组")
    private String groupName;

    @ApiModelProperty("运单号")
    private String waybillCode;

    @ApiModelProperty("对帐单号")
    private String reconcileCode;

    @ApiModelProperty("收付款方")
    private String name;

    @ApiModelProperty("费用类型")
    private String feeProperty;

    @ApiModelProperty("最新费用")
    private Float money;

    @ApiModelProperty("原始费用")
    private Float originalMoney;

    public String getFlowCode() {
        return flowCode;
    }

    public void setFlowCode(String flowCode) {
        this.flowCode = flowCode;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getWaybillCode() {
        return waybillCode;
    }

    public void setWaybillCode(String waybillCode) {
        this.waybillCode = waybillCode;
    }

    public String getReconcileCode() {
        return reconcileCode;
    }

    public void setReconcileCode(String reconcileCode) {
        this.reconcileCode = reconcileCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFeeProperty() {
        return feeProperty;
    }

    public void setFeeProperty(String feeProperty) {
        this.feeProperty = feeProperty;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public Float getOriginalMoney() {
        return originalMoney;
    }

    public void setOriginalMoney(Float originalMoney) {
        this.originalMoney = originalMoney;
    }
}
