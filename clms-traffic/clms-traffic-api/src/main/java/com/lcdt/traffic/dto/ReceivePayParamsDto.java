package com.lcdt.traffic.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by yangbinq on 2018/4/12.
 *  Description:收入、支出统计项目
 */
public class ReceivePayParamsDto implements java.io.Serializable {

    @ApiModelProperty(value = "业务组ID，全部为0",required = true)
    private Long groupId;

    @ApiModelProperty(value = "收付款方ID，全部为0",required = true)
    private Long nameId;

    @ApiModelProperty(value = "日期始")
    private String createBegin;

    @ApiModelProperty(value = "日期止")
    private String createEnd;


    @ApiModelProperty(value = "组ID-后台处理，必须要传")
    private String groupIds;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
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
}
