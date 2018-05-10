package com.lcdt.warehouse.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.IdType;

import java.beans.Transient;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.lcdt.warehouse.dto.InPlanGoodsInfoResultDto;

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
public class InWarehousePlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "plan_id", type = IdType.AUTO)
    private Long planId;
    /**
     * 计划编号
     */
    private String planNo;
    private Date createTime;
    private String createUserName;
    private Long createUserId;
    /**
     * 所属项目组 id
     */
    private Integer groupId;
    /**
     * 所属项目组 名称
     */
    private String groupName;
    private Long companyId;
    private String customerName;
    private Long customerId;
    /**
     * 10-待发布
20- 配舱中
30-已配舱
40-完成
50-取消

     */
    private Integer planStatus;
    private String customerContactName;
    private String customerContactPhone;
    /**
     * 采购单号
     */
    private String customerPurchaseNo;
    /**
     * 入库类型
{id:"01",value:"原料入库"},
  {id:"02",value:"成品入库"},
              {id:"03",value:"退换货入库"},
              {id:"04",value:"采购入库"},
              {id:"05",value:"其它"},
     */
    private String storageType;
    /**
     * 计划入库时间
     */
    private Date storagePlanTime;
    /***
     * 备注
     */
    private String storageRemark;
    /**
     * 送货单位
     */
    private String deliverymanName;
    /**
     * 送货人电话
     */
    private String deliverymanPhone;
    /**
     * 送货人
     */
    private String deliverymanLinkman;
    /**
     * 送货车辆
     */
    private String deliverymanCar;
    /**
     * 入库仓库id
     */
    private Long wareHouseId;
    /**
     * 入库仓库名
     */
    private String warehouseName;
    /**
     * 合同编号
     */
    private String contractNo;
    /***
     * 附件
     */
    private String attachment;

    /**
     * 更新人id
     */
    private Long updateId;
    /**
     * 更新人
     */
    private String updateName;
    /**
     * 更新时间
     */
    private Date updateDate;

    //扩充属性
    @TableField(exist=false)
    private List<InPlanGoodsInfoResultDto> goodsList; //商品详细列表

    public List<InPlanGoodsInfoResultDto> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<InPlanGoodsInfoResultDto> goodsList) {
        this.goodsList = goodsList;
    }




    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Integer getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(Integer planStatus) {
        this.planStatus = planStatus;
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

    public String getCustomerPurchaseNo() {
        return customerPurchaseNo;
    }

    public void setCustomerPurchaseNo(String customerPurchaseNo) {
        this.customerPurchaseNo = customerPurchaseNo;
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

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }


    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
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
}
