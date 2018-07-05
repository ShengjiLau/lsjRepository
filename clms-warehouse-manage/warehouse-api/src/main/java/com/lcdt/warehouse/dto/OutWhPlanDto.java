package com.lcdt.warehouse.dto;

import com.baomidou.mybatisplus.annotations.TableField;
import com.lcdt.converter.ResponseData;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangbinq on 2018/5/14.
 */
public class OutWhPlanDto implements Serializable,ResponseData {


    @ApiModelProperty(value = "计划ID")
    private Long outplanId;
    @ApiModelProperty(value = "项目组ID")
    private Long groupId;
    @ApiModelProperty(value = "项目组名")
    private String groupName;
    @ApiModelProperty(value = "合同")
    private String contractNo;
    @ApiModelProperty(value = "销售单号")
    private String customerPurchaseNo;
    @ApiModelProperty(value = "客户ID")
    private Long customerId;
    @ApiModelProperty(value = "客户名称")
    private String customerName;
    @ApiModelProperty(value = "联系人")
    private String customerContactName;
    @ApiModelProperty(value = "联系电话")
    private String customerContactPhone;
    @ApiModelProperty(value = "出库仓库ID")
    private Long wareHouseId;
    @ApiModelProperty(value = "出库仓库名")
    private String warehouseName;
    @ApiModelProperty(value = "出库类型")
    private String storageType;
    @ApiModelProperty(value = "计划时间")
    private String storagePlanTime;
    @ApiModelProperty(value = "备注")
    private String storageRemark;
    @ApiModelProperty(value = "提货单位")
    private String pickupUnit;
    @ApiModelProperty(value = "提货人电话")
    private String pickupPhone;
    @ApiModelProperty(value = "提货人")
    private String pickupLinkman;
    @ApiModelProperty(value = "提货车辆")
    private String pickupCar;
    @ApiModelProperty(value = "提货人身证")
    private String pickupIdentiycard;

    @ApiModelProperty(value = "附件信息")
    private String attachment;



    private Integer planStatus;

    @ApiModelProperty(value = "登录企业ID")
    private Long companyId;
    @ApiModelProperty(value = "登录用户ID")
    private Long userId;
    @ApiModelProperty(value = "登录用户名")
    private String userName;

    private String createUserName;
    private Long createUserId;
    private String planNo;

    private List<OutWhPlanGoodsDto> outWhPlanGoodsDtoList; //出库计划货物

    private List<OutWhOrderDto> outWhOrderDtoList;//出库单列表




    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getOutplanId() {
        return outplanId;
    }

    public void setOutplanId(Long outplanId) {
        this.outplanId = outplanId;
    }

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

    public String getPickupUnit() {
        return pickupUnit;
    }

    public void setPickupUnit(String pickupUnit) {
        this.pickupUnit = pickupUnit;
    }

    public String getPickupPhone() {
        return pickupPhone;
    }

    public void setPickupPhone(String pickupPhone) {
        this.pickupPhone = pickupPhone;
    }

    public String getPickupLinkman() {
        return pickupLinkman;
    }

    public void setPickupLinkman(String pickupLinkman) {
        this.pickupLinkman = pickupLinkman;
    }

    public String getPickupCar() {
        return pickupCar;
    }

    public void setPickupCar(String pickupCar) {
        this.pickupCar = pickupCar;
    }

    public String getPickupIdentiycard() {
        return pickupIdentiycard;
    }

    public void setPickupIdentiycard(String pickupIdentiycard) {
        this.pickupIdentiycard = pickupIdentiycard;
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

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    public List<OutWhPlanGoodsDto> getOutWhPlanGoodsDtoList() {
        return outWhPlanGoodsDtoList;
    }

    public void setOutWhPlanGoodsDtoList(List<OutWhPlanGoodsDto> outWhPlanGoodsDtoList) {
        this.outWhPlanGoodsDtoList = outWhPlanGoodsDtoList;
    }

    public List<OutWhOrderDto> getOutWhOrderDtoList() {
        return outWhOrderDtoList;
    }

    public void setOutWhOrderDtoList(List<OutWhOrderDto> outWhOrderDtoList) {
        this.outWhOrderDtoList = outWhOrderDtoList;
    }

    public Integer getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(Integer planStatus) {
        this.planStatus = planStatus;
    }
}
