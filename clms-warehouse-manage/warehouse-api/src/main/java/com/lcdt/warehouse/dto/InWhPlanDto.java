package com.lcdt.warehouse.dto;

import com.lcdt.converter.ResponseData;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangbinq on 2018/5/14.
 */
public class InWhPlanDto implements Serializable,ResponseData {


    /**
	 * 
	 */
	private static final long serialVersionUID = 16963314L;
	@ApiModelProperty(value = "计划ID")
    private Long planId;
    @ApiModelProperty(value = "项目组ID")
    private Long groupId;
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
    private String customerContactName;
    @ApiModelProperty(value = "联系电话")
    private String customerContactPhone;
    @ApiModelProperty(value = "入库仓库ID")
    private Long wareHouseId;
    @ApiModelProperty(value = "入库仓库名")
    private String warehouseName;
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
    @ApiModelProperty(value = "附件信息")
    private String attachment;


    private Long companyId;
    private String createUserName;
    private Long createUserId;
    private String planNo;

    private List<InWhPlanGoodsDto> inWhPlanGoodsDtoList;
    private List<InWarehouseOrderDto> inWarehouseOrderDtoList; //入库计划详细


    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getCustomerPurchaseNo() {
        return customerPurchaseNo;
    }

    public void setCustomerPurchaseNo(String customerPurchaseNo) {
        this.customerPurchaseNo = customerPurchaseNo;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerContactName() {
        return customerContactName;
    }

    public void setCustomerContactName(String customerContactName) {
        this.customerContactName = customerContactName;
    }

    public String getCustomerContactPhone() {
        return customerContactPhone;
    }

    public void setCustomerContactPhone(String customerContactPhone) {
        this.customerContactPhone = customerContactPhone;
    }

    public Long getWareHouseId() {
        return wareHouseId;
    }

    public void setWareHouseId(Long wareHouseId) {
        this.wareHouseId = wareHouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public String getStoragePlanTime() {
        return storagePlanTime;
    }

    public void setStoragePlanTime(String storagePlanTime) {
        this.storagePlanTime = storagePlanTime;
    }

    public String getStorageRemark() {
        return storageRemark;
    }

    public void setStorageRemark(String storageRemark) {
        this.storageRemark = storageRemark;
    }

    public String getDeliverymanName() {
        return deliverymanName;
    }

    public void setDeliverymanName(String deliverymanName) {
        this.deliverymanName = deliverymanName;
    }

    public String getDeliverymanPhone() {
        return deliverymanPhone;
    }

    public void setDeliverymanPhone(String deliverymanPhone) {
        this.deliverymanPhone = deliverymanPhone;
    }

    public String getDeliverymanLinkman() {
        return deliverymanLinkman;
    }

    public void setDeliverymanLinkman(String deliverymanLinkman) {
        this.deliverymanLinkman = deliverymanLinkman;
    }

    public String getDeliverymanCar() {
        return deliverymanCar;
    }

    public void setDeliverymanCar(String deliverymanCar) {
        this.deliverymanCar = deliverymanCar;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public List<InWhPlanGoodsDto> getInWhPlanGoodsDtoList() {
        return inWhPlanGoodsDtoList;
    }

    public void setInWhPlanGoodsDtoList(List<InWhPlanGoodsDto> inWhPlanGoodsDtoList) {
        this.inWhPlanGoodsDtoList = inWhPlanGoodsDtoList;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public List<InWarehouseOrderDto> getInWarehouseOrderDtoList() {
        return inWarehouseOrderDtoList;
    }

    public void setInWarehouseOrderDtoList(List<InWarehouseOrderDto> inWarehouseOrderDtoList) {
        this.inWarehouseOrderDtoList = inWarehouseOrderDtoList;
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }
}
