package com.lcdt.customer.web.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by yangbinq on 2017/11/24.
 */
public class CustomerContactParamsDto {

    @ApiModelProperty(value = "联系人ID-编辑必须传入")
    private Long contactId;

    @ApiModelProperty(required = true, value = "客户ID")
    private Long customerId;

    @ApiModelProperty(value = "职位")
    private String duty;

    @ApiModelProperty(value = "手机")
    private String mobile;

    @ApiModelProperty(value = "邮箱")
    private String mail;

    @ApiModelProperty(value = "生日")
    private Date birthday;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "所属地区-省")
    private String province;

    @ApiModelProperty(value = "所属地区-市")
    private String city;

    @ApiModelProperty(value = "所属地区-县")
    private String county;

    @ApiModelProperty(value = "详细地址")
    private String detailAddress;

    @ApiModelProperty(value = "邮编")
    private String postCode;

    @ApiModelProperty(value = "电话")
    private String telNo;

    @ApiModelProperty(value = "传真")
    private String fax;

    @ApiModelProperty(value = "备注")
    private String remark;

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }
}
