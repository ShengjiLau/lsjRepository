package com.lcdt.warehouse.entity;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author 
 */
public class AllotProduct implements Serializable {
    @ApiModelProperty(value="商品id")
    private Long apId;

    @ApiModelProperty(value="调拨单id")
    private Long allotId;

    @ApiModelProperty(value="商品id")
    private Long goodsId;

    @ApiModelProperty(value="商品原始id")
    private Long originalGoodsId;

    @ApiModelProperty(value="商品分类")
    private String goodsClassify;

    @ApiModelProperty(value="库存id")
    private Long inventoryId;

    @ApiModelProperty(value="商品名称")
    private String name;

    @ApiModelProperty(value="商品编码")
    private String code;

    @ApiModelProperty(value="商品条码")
    private String barCode;

    @ApiModelProperty(value="商品规格")
    private String spec;

    @ApiModelProperty(value="计量单位")
    private String unit;

    @ApiModelProperty(value="批次")
    private String batchNum;

    @ApiModelProperty(value="库位id")
    private Long warehouseLocId;

    @ApiModelProperty(value="库位编码")
    private String warehouseLocCode;

    @ApiModelProperty(value="库存")
    private Double inventory;

    @ApiModelProperty(value="调拨数量")
    private Double allotNum;

    @ApiModelProperty(value="备注")
    private String remark;

    @ApiModelProperty(value="0-未删除，1-已删除")
    private Short isDeleted;

    private static final long serialVersionUID = 1L;

    public Long getApId() {
        return apId;
    }

    public void setApId(Long apId) {
        this.apId = apId;
    }

    public Long getAllotId() {
        return allotId;
    }

    public void setAllotId(Long allotId) {
        this.allotId = allotId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getOriginalGoodsId() {
        return originalGoodsId;
    }

    public void setOriginalGoodsId(Long originalGoodsId) {
        this.originalGoodsId = originalGoodsId;
    }

    public String getGoodsClassify() {
        return goodsClassify;
    }

    public void setGoodsClassify(String goodsClassify) {
        this.goodsClassify = goodsClassify;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(String batchNum) {
        this.batchNum = batchNum;
    }

    public Long getWarehouseLocId() {
        return warehouseLocId;
    }

    public void setWarehouseLocId(Long warehouseLocId) {
        this.warehouseLocId = warehouseLocId;
    }

    public String getWarehouseLocCode() {
        return warehouseLocCode;
    }

    public void setWarehouseLocCode(String warehouseLocCode) {
        this.warehouseLocCode = warehouseLocCode;
    }

    public Double getInventory() {
        return inventory;
    }

    public void setInventory(Double inventory) {
        this.inventory = inventory;
    }

    public Double getAllotNum() {
        return allotNum;
    }

    public void setAllotNum(Double allotNum) {
        this.allotNum = allotNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }
}