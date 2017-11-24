package com.lcdt.customer.web.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by yangbinq on 2017/11/24.
 */
public class CustomerAddParamsDto {

    @ApiModelProperty(required = true, value = "客户名称")
    private String customerName;

    @ApiModelProperty(required = true, value = "客户简称")
    private String shortName;

    @ApiModelProperty(value = "客户编码")
    private String customerCode;

    @ApiModelProperty(required = true, value = "客户类型(逗号隔开ID:1,2,3,4,5,6,7)")
    private String clientTypes;

    @ApiModelProperty(required = true, value = "所属地区-省")
    private String province;

    @ApiModelProperty(required = true, value = "所属地区-市")
    private String city;

    @ApiModelProperty(required = true, value = "所属地区-县")
    private String county;

    @ApiModelProperty(required = true, value = "详细地址")
    private String detailAddress;

    @ApiModelProperty(required = true, value = "业务项目组名(逗号隔开:1,2,3,4,5,6,7)")
    private String groupIds;

    @ApiModelProperty(required = true, value = "项目项目组(逗号隔开:组1,组2,组3)")
    private String groupNames;

    @ApiModelProperty(value = "邮编")
    private String postCode;

    @ApiModelProperty(value = "电话")
    private String telNo;

    @ApiModelProperty(value = "传真")
    private String fax;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "附件1")
    private String attachment1;

    @ApiModelProperty(value = "附件2")
    private String attachment2;


    @ApiModelProperty(value = "开票抬头")
    private String invoiceTitle;

    @ApiModelProperty(value = "税号")
    private String registrationNo;

    @ApiModelProperty(value = "开户银行")
    private String bankName;

    @ApiModelProperty(value = "银行账号")
    private String bankNo;

    @ApiModelProperty(value = "财务电话")
    private String telNo1;

    @ApiModelProperty(value = "注册地址")
    private String registrationAddress;

    @ApiModelProperty(value = "开票备注")
    private String invoiceRemark;


}
