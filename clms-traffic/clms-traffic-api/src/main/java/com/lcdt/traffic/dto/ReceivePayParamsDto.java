package com.lcdt.traffic.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by yangbinq on 2018/4/12.
 *  Description:收入、支出统计项目
 */
public class ReceivePayParamsDto implements java.io.Serializable {

    @ApiModelProperty(value = "司机名称/联系方式")
    private String driverNameOrPhone;

    @ApiModelProperty(value = "业务组ID，全部为0")
    private Long groupId;

    @ApiModelProperty(value = "收付款方名称")
    private String receivAndPayName;

    @ApiModelProperty(value = "收付款方ID，全部为0")
    private Long nameId;

    @ApiModelProperty(value = "日期始,格式：2018-01-02")
    private String createBegin;

    @ApiModelProperty(value = "日期止,格式：2018-01-02")
    private String createEnd;


    @ApiModelProperty(value = "组ID-后台处理")
    private String groupIds;
    @ApiModelProperty(value = "是否删除-后台处理")
    private short isDeleted;
    @ApiModelProperty(value = "0-应收，1-应付-后台处理")
    private short isReceivable;
    @ApiModelProperty(value = "统计列,后台处理")
    private String statCols;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @ApiModelProperty(value = "登录企业ID-后台处理，必须要传")
    private Long companyId;

    @ApiModelProperty(value = "运单编号")
    private String waybillCode;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    public String getDriverNameOrPhone() {
        return driverNameOrPhone;
    }

    public void setDriverNameOrPhone(String driverNameOrPhone) {
        this.driverNameOrPhone = driverNameOrPhone;
    }

    public String getStatCols() {
        return statCols;
    }
    public void setStatCols(String statCols) {
        this.statCols = statCols;
    }
    public short getIsDeleted() {
        return isDeleted;
    }
    public void setIsDeleted(short isDeleted) {
        this.isDeleted = isDeleted;
   }
    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getReceivAndPayName() {
        return receivAndPayName;
    }

    public void setReceivAndPayName(String receivAndPayName) {
        this.receivAndPayName = receivAndPayName;
    }

    public Long getNameId() {
        return nameId;
    }

    public void setNameId(Long nameId) {
        this.nameId = nameId;
    }

    public String getCreateBegin() {
        return createBegin;
    }

    public void setCreateBegin(String createBegin) {
        this.createBegin = createBegin;
    }

    public String getCreateEnd() {
        return createEnd;
    }

    public void setCreateEnd(String createEnd) {
        this.createEnd = createEnd;
    }

    public String getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(String groupIds) {
        this.groupIds = groupIds;
    }

    public short getIsReceivable() {
        return isReceivable;
    }

    public void setIsReceivable(short isReceivable) {
        this.isReceivable = isReceivable;
    }

    public String getWaybillCode() {
        return waybillCode;
    }

    public void setWaybillCode(String waybillCode) {
        this.waybillCode = waybillCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
