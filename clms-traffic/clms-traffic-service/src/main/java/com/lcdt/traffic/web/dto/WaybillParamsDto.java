package com.lcdt.traffic.web.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by yangbinq on 2017/12/13.
 */
public class WaybillParamsDto {

    @ApiModelProperty(value = "计划编码")
    private Long planCode;

    @ApiModelProperty(value = "所属项目组ID")
    private Long groupId;


    @ApiModelProperty(value = "所属项目组名称")
    private Long groupName;


    @ApiModelProperty(value = "派单方式 0-其它\n" +
            "1-直派\n" +
            "2-竞价\n")
    private Short sendOrderType;


    @ApiModelProperty(value = "承运人类型0-其它\n" +
            "1-承运商\n" +
            "2-司机")
    private Short carrierType;


    @ApiModelProperty(value = "竞价开始")
    private String bidingStart;

    @ApiModelProperty(value = "竞价结束")
    private String bidingEnd;

    @ApiModelProperty(value = "客户-ID")
    private Long customerId;

    @ApiModelProperty(value = "客户-名称")
    private String customerName;

    @ApiModelProperty(value = "客户-销售单号")
    private String salesOrder;

    @ApiModelProperty(value = "客户-电话")
    private String customerPhone;

    @ApiModelProperty(value = "客户-联系人-默认客户")
    private String custContactMan;

    @ApiModelProperty(value = "发货-仓库-id")
    private Long sendWhId;

    @ApiModelProperty(value = "发货-仓库-名称")
    private String sendWhName;

    @ApiModelProperty(value = "发货-联系人")
    private String sendMan;

    @ApiModelProperty(value = "发货-联系人-电话")
    private String sendPhone;

    @ApiModelProperty(value = "发货-省")
    private String sendProvince;
    @ApiModelProperty(value = "发货-市")
    private String sendCity;
    @ApiModelProperty(value = "发货-县")
    private String sendCounty;
    @ApiModelProperty(value = "发货-详细")
    private String sendAddress;

    @ApiModelProperty(value = "收货-联系人")
    private String receiveMan;
    @ApiModelProperty(value = "发货-电话")
    private String receivePhone;
    @ApiModelProperty(value = "发货-市")
    private String receiveProvince;
    @ApiModelProperty(value = "发货-市")
    private String receiveCity;
    @ApiModelProperty(value = "发货-市")
    private String receiveCounty;
    @ApiModelProperty(value = "发货-详细")
    private String receiveAddress;

    @ApiModelProperty(value = "运输-运输方式-1-陆运\n" +
            "2-海运\n" +
            "3-空运\n" +
            "5-铁运\n" +
            "6-多式联运\n")
    private Short transportWay;

    @ApiModelProperty(value = "运输-货物类型")
    private String goodsType;
    @ApiModelProperty(value = "运输-起运时间")
    private String startDate;
    @ApiModelProperty(value = "运输-到达时间")
    private String arriveDate;

    @ApiModelProperty(value = "运输-其它要求-是否上传回传单 0-否\n" +
            "1-是")
    private Short isReceipt;

    @ApiModelProperty(value = "运输-其它要求-是否加急 0-否\n" +
            "1-是")
    private Short isUrgent;

    @ApiModelProperty(value = "运输-其它要求-配送方式 0-上门装货\n" +
            "1-送货到站\n" +
            "3-送货上门\n" +
            "4-到站自体")
    private String distributionWay;

    @ApiModelProperty(value = "运输-计划备注")
    private String planRemark;


    @ApiModelProperty(value = "附件信息-附件1地址")
    private String attachment1;
    @ApiModelProperty(value = "附件信息-附件1名称")
    private String attachment1Name;
    @ApiModelProperty(value = "附件信息-附件2地址")
    private String attachment2;
    @ApiModelProperty(value = "附件信息-附件2名称")
    private String attachment2Name;
    @ApiModelProperty(value = "附件信息-附件3地址")
    private String attachment3;
    @ApiModelProperty(value = "附件信息-附件3名称")
    private String attachment3Name;
    @ApiModelProperty(value = "附件信息-附件4地址")
    private String attachment4;
    @ApiModelProperty(value = "附件信息-附件4名称")
    private String attachment4Name;
    @ApiModelProperty(value = "附件信息-附件5地址")
    private String attachment5;
    @ApiModelProperty(value = "附件信息-附件5名称")
    private String attachment5Name;

    @ApiModelProperty(value = "计划审批-0-不需要审批\n" +
            "1-需要审批")
    private Short isApproval;

}
