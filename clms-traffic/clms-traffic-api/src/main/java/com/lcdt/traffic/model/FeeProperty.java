package com.lcdt.traffic.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class FeeProperty implements Serializable {
    private Long proId;

    private Short type;

    private String name;

    private Short isReceivable;

    private Short isShow;

    private Long companyId;

    private Long operatorId;

    private String operatorName;

    private Date createDate;

    private Short isDeleted;

    public Long getProId() {
        return proId;
    }

    public void setProId(Long proId) {
        this.proId = proId;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getIsReceivable() {
        return isReceivable;
    }

    public void setIsReceivable(Short isReceivable) {
        this.isReceivable = isReceivable;
    }

    public Short getIsShow() {
        return isShow;
    }

    public void setIsShow(Short isShow) {
        this.isShow = isShow;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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