package com.lcdt.traffic.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by yangbinq on 2018/4/16.
 * Desciption:财务-利润统计参数
 */
public class ProfitStatParamsDto implements java.io.Serializable {

    @ApiModelProperty(value = "运单创建日期始,格式：2018-01-02")
    private String createBegin;

    @ApiModelProperty(value = "运单创建日期止,格式：2018-01-02")
    private String createEnd;

    @ApiModelProperty(value = "运单号")
    private String waybillCode;

    @ApiModelProperty(value = "页码",required = true)
    private int pageNo;
    @ApiModelProperty(value = "每页显示条数",required = true)
    private int pageSize;

    @ApiModelProperty(value = "后台处理是否删除")
    private short isDeleted; //0-未删除
    @ApiModelProperty(value = "登录企业ID-后台处理")
    private Long companyId;
    @ApiModelProperty(value = "组ID-后台处理")
    private String groupIds;



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

    public String getWaybillCode() {
        return waybillCode;
    }

    public void setWaybillCode(String waybillCode) {
        this.waybillCode = waybillCode;
    }

    public short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(short isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(String groupIds) {
        this.groupIds = groupIds;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
