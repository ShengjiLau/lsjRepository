package com.lcdt.warehouse.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by yangbinq on 2018/5/14.
 */
public class InWhPlanAddParamsDto implements Serializable {

    @ApiModelProperty(value = "项目组ID")
    private Integer groupId;
    @ApiModelProperty(value = "项目组名")
    private String groupName;
    @ApiModelProperty(value = "合同编码组名")
    private String contractNo;
    @ApiModelProperty(value = "采购单号")
    private String customerPurchaseNo;

    @ApiModelProperty(value = "客户ID")
    private Long customerId;
    @ApiModelProperty(value = "客户名称")
    private String customerName;
    @ApiModelProperty(value = "联系人")
    private String custContactMan;
    @ApiModelProperty(value = "联系电话")
    private String customerPhone;

   // @ApiModelProperty(value = "入库仓库")

    @ApiModelProperty(value = "入库类型")
    private String storageType;

    @ApiModelProperty(value = "计划时间")
    private String storagePlanTime;

    @ApiModelProperty(value = "备注")
    private String storageRemark;

    @ApiModelProperty(value = "送货单位")
    private String deliverymanName;
    @ApiModelProperty(value = "送货人电话")
    private String deliverymanPhone;
    @ApiModelProperty(value = "送货人")
    private String deliverymanLinkman;
    @ApiModelProperty(value = "送货车辆")
    private String deliverymanCar;

}
