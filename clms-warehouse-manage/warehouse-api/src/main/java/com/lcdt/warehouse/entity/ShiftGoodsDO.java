package com.lcdt.warehouse.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Sheng-ji Lau
 * @date 2018年6月01日
 * @version
 * @Description: TODO 
 */
@ApiModel("移库商品信息")
public class ShiftGoodsDO implements Serializable {

	@ApiModelProperty("移库商品信息id")
    private Long shiftGoodsId;

	@ApiModelProperty("移库单id")
    private Long inventoryId;

	@ApiModelProperty("移入库位编码")
    private String shiftLocation;

	@ApiModelProperty("计划移入数量")
    private BigDecimal shiftPlanNum;

	@ApiModelProperty("实际移入数量")
    private BigDecimal shiftNum;

	@ApiModelProperty("备注")
    private String remark;

	@ApiModelProperty("商品相关库存的id")
    private Long shiftInventoryId;

	@ApiModelProperty("库位id")
    private Long storageLocationId;

	@ApiModelProperty("商品id,goodsInfo表主键")
    private Long goodsId;

	@ApiModelProperty("商品批次")
    private String batch;

	@ApiModelProperty("源商品id")
    private Long originalGoodsId;

	@ApiModelProperty("商品单位")
    private String baseUnit;

	@ApiModelProperty("")
    private Float inventoryPrice;

    private static final long serialVersionUID = 1L;

    public Long getShiftGoodsId() {
        return shiftGoodsId;
    }

    public void setShiftGoodsId(Long shiftGoodsId) {
        this.shiftGoodsId = shiftGoodsId;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getShiftLocation() {
        return shiftLocation;
    }

    public void setShiftLocation(String shiftLocation) {
        this.shiftLocation = shiftLocation == null ? null : shiftLocation.trim();
    }

    public BigDecimal getShiftPlanNum() {
        return shiftPlanNum;
    }

    public void setShiftPlanNum(BigDecimal shiftPlanNum) {
        this.shiftPlanNum = shiftPlanNum;
    }

    public BigDecimal getShiftNum() {
        return shiftNum;
    }

    public void setShiftNum(BigDecimal shiftNum) {
        this.shiftNum = shiftNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Long getShiftInventoryId() {
        return shiftInventoryId;
    }

    public void setShiftInventoryId(Long shiftInventoryId) {
        this.shiftInventoryId = shiftInventoryId;
    }

    public Long getStorageLocationId() {
        return storageLocationId;
    }

    public void setStorageLocationId(Long storageLocationId) {
        this.storageLocationId = storageLocationId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch == null ? null : batch.trim();
    }

    public Long getOriginalGoodsId() {
        return originalGoodsId;
    }

    public void setOriginalGoodsId(Long originalGoodsId) {
        this.originalGoodsId = originalGoodsId;
    }

    public String getBaseUnit() {
        return baseUnit;
    }

    public void setBaseUnit(String baseUnit) {
        this.baseUnit = baseUnit == null ? null : baseUnit.trim();
    }

    public Float getInventoryPrice() {
        return inventoryPrice;
    }

    public void setInventoryPrice(Float inventoryPrice) {
        this.inventoryPrice = inventoryPrice;
    }

}