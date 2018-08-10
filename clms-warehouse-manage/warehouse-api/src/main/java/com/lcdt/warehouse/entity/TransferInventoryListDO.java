package com.lcdt.warehouse.entity;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("库存转换单主表")
@Data
public class TransferInventoryListDO implements Serializable {
	@ApiModelProperty("主键id")
    private Long transfersId;
	@ApiModelProperty("所属业务组id")
    private Long groupId;
	@ApiModelProperty("所属业务组名称")
    private String groupName;
	@ApiModelProperty("客户id")
    private Long customerId;
	@ApiModelProperty("客户名称")
    private String customerName;
	@ApiModelProperty("仓库id")
    private Long warehouseId;
	@ApiModelProperty("仓库名称")
    private String warehouseName;
	@ApiModelProperty("备注")
    private String remark;
	@ApiModelProperty("附件名称")
    private String attachmentName;
	@ApiModelProperty("附件")
    private String attachment;
	@ApiModelProperty("创建时间")
    private Date gmtCreate;
	@ApiModelProperty("修改时间")
    private Date gmtModified;
	@ApiModelProperty("创建人")
    private String createUser;
	@ApiModelProperty("创建人id")
    private Long createUserId;
	@ApiModelProperty("所属公司id")
    private Long companyId;
	@ApiModelProperty("转换单状态：0-新建；1-完成；2-取消")
    private Byte listStatus;
	@ApiModelProperty("流水号")
    private String listSerialNo;
	@ApiModelProperty("完成时间")
	private Date gmtComplete;
    
    private static final long serialVersionUID = 1115611561115156L;

}