package com.lcdt.traffic.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class FeeFlowLog implements Serializable {
    @ApiModelProperty(value="主键id")
    private Long logId;

    @ApiModelProperty(value="流水id")
    private Long flowId;

    @ApiModelProperty(value="本次修改金额")
    private Float money;

    @ApiModelProperty(value="修改之前金额")
    private Float oldMoney;

    @ApiModelProperty(value="修改人id")
    private Long operatorId;

    @ApiModelProperty(value="修改人姓名")
    private String operatorName;

    @ApiModelProperty(value="修改时间")
    private Date createDate;

    @ApiModelProperty(value="0-未删除，1-已删除")
    private Short isDeleted;

    private static final long serialVersionUID = 1L;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Long getFlowId() {
        return flowId;
    }

    public void setFlowId(Long flowId) {
        this.flowId = flowId;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public Float getOldMoney() {
        return oldMoney;
    }

    public void setOldMoney(Float oldMoney) {
        this.oldMoney = oldMoney;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }
}