package com.lcdt.userinfo.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class FeeProperty implements Serializable {
    @ApiModelProperty(value="主键id")
    private Long proId;

    @ApiModelProperty(value="0-运输，1-仓储")
    private Short type;

    @ApiModelProperty(value="费用类型名称")
    private String name;

    @ApiModelProperty(value="0-应收，1-应付")
    private Short isReceivable;

    @ApiModelProperty(value="0-默认显示，1-默认隐藏")
    private Short isShow;

    @ApiModelProperty(value="企业id")
    private Long companyId;

    @ApiModelProperty(value="操作人id")
    private Long operatorId;

    @ApiModelProperty(value="操作人姓名")
    private String operatorName;

    @ApiModelProperty(value="创建时间")
    private Date createDate;

    @ApiModelProperty(value="0-未删除，1-已删除")
    private Short isDeleted;

    private static final long serialVersionUID = 1L;

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