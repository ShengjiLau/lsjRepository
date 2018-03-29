package com.lcdt.traffic.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class FeeReconcile implements Serializable {
    private Long reconcileId;

    private String reconcileCode;

    private Long companyId;

    private Long groupId;

    private String groupName;

    private Long nameId;

    private String name;

    private Float moneySum;

    private Long operatorId;

    private String operatorName;

    private Long isReceivable;

    private Date createDate;

    private Short isDeleted;

    private static final long serialVersionUID = 1L;

    public Long getReconcileId() {
        return reconcileId;
    }

    public void setReconcileId(Long reconcileId) {
        this.reconcileId = reconcileId;
    }

    public String getReconcileCode() {
        return reconcileCode;
    }

    public void setReconcileCode(String reconcileCode) {
        this.reconcileCode = reconcileCode;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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

    public Long getNameId() {
        return nameId;
    }

    public void setNameId(Long nameId) {
        this.nameId = nameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getMoneySum() {
        return moneySum;
    }

    public void setMoneySum(Float moneySum) {
        this.moneySum = moneySum;
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

    public Long getIsReceivable() {
        return isReceivable;
    }

    public void setIsReceivable(Long isReceivable) {
        this.isReceivable = isReceivable;
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