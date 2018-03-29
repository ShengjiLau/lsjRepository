package com.lcdt.traffic.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class FeeFlowLog implements Serializable {
    private Long logId;

    private Long flowId;

    private Float money;

    private Float oldMoney;

    private Long operatorId;

    private String operatorName;

    private Date createDate;

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