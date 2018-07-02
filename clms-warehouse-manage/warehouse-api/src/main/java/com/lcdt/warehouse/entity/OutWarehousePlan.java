package com.lcdt.warehouse.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.lcdt.warehouse.dto.OutWhOrderDto;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author code generate
 * @since 2018-05-07
 */
public class OutWarehousePlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "outplan_id", type = IdType.AUTO)
    private Long outplanId;

    private String planNo;

    private Long groupId;

    private String groupName;

    private String contractNo;

    private String customerPurchaseNo;

    private Long customerId;

    private String customerName;

    private String customerContactName;

    private String customerContactPhone;

    private Long wareHouseId;

    private String warehouseName;

    private String storageType;

    private Date storagePlanTime;

    private String storageRemark;

    private String pickupUnit;

    private String pickupLinkman;

    private String pickupIdentiycard;

    private String pickupPhone;

    private String pickupCar;

    private Integer planStatus;

    private String attachment;

    private Long createUserId;

    private String createUserName;

    private Date createTime;

    private Long updateId;

    private String updateName;

    private Date updateDate;

    private Long companyId;


    //扩充属性
    @TableField(exist=false)
    private List<OutplanGoods> goodsList; //商品详细列表

    @TableField(exist = false)
    private List<OutWhOrderDto> outWhOrderDtoList;//出库单列表



    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getOutplanId() {
        return outplanId;
    }

    public void setOutplanId(Long outplanId) {
        this.outplanId = outplanId;
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
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

    public Date getStoragePlanTime() {
        return storagePlanTime;
    }

    public void setStoragePlanTime(Date storagePlanTime) {
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

    public String getPickupLinkman() {
        return pickupLinkman;
    }

    public void setPickupLinkman(String pickupLinkman) {
        this.pickupLinkman = pickupLinkman;
    }

    public String getPickupIdentiycard() {
        return pickupIdentiycard;
    }

    public void setPickupIdentiycard(String pickupIdentiycard) {
        this.pickupIdentiycard = pickupIdentiycard;
    }

    public String getPickupPhone() {
        return pickupPhone;
    }

    public void setPickupPhone(String pickupPhone) {
        this.pickupPhone = pickupPhone;
    }

    public String getPickupCar() {
        return pickupCar;
    }

    public void setPickupCar(String pickupCar) {
        this.pickupCar = pickupCar;
    }

    public Integer getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(Integer planStatus) {
        this.planStatus = planStatus;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public List<OutplanGoods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<OutplanGoods> goodsList) {
        this.goodsList = goodsList;
    }

    public List<OutWhOrderDto> getOutWhOrderDtoList() {
        return outWhOrderDtoList;
    }

    public void setOutWhOrderDtoList(List<OutWhOrderDto> outWhOrderDtoList) {
        this.outWhOrderDtoList = outWhOrderDtoList;
    }
}
