package com.lcdt.warehouse.dto;

import com.lcdt.warehouse.entity.Inventory;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.util.StringUtils;

public class InventoryQueryDto extends PageQueryDto {

    private Long companyId;

    @ApiModelProperty(value = "仓库id")
    private Long wareHouseId;

    @ApiModelProperty(value = "库位")
    private String storageLocationCode;

    @ApiModelProperty(value = "批次")
    private String batch;

    @ApiModelProperty(value = "商品")
    private Long goodsId;

    @ApiModelProperty(value = "客户id")
    private Long customerId;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品条码")
    private String goodsBarCode;

    @ApiModelProperty(value = "商品编码")
    private String goodsCode;

    @ApiModelProperty(value = "商品分类")
    private String goodsCategory;

    private Long originalGoodsId;

    private Long classifyId;

    public boolean goodsQueryExist(){
        if (!StringUtils.isEmpty(getGoodsName()) ||
                !StringUtils.isEmpty(goodsBarCode)
                || !StringUtils.isEmpty(goodsCode)
                || !StringUtils.isEmpty(goodsCategory)
                || getClassifyId() != null) {
            return true;
        }
        return false;
    }


    public Long getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Long classifyId) {
        this.classifyId = classifyId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getOriginalGoodsId() {
        return originalGoodsId;
    }

    public void setOriginalGoodsId(Long originalGoodsId) {
        this.originalGoodsId = originalGoodsId;
    }

    public String getGoodsCategory() {
        return goodsCategory;
    }

    public void setGoodsCategory(String goodsCategory) {
        this.goodsCategory = goodsCategory;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsBarCode() {
        return goodsBarCode;
    }

    public void setGoodsBarCode(String goodsBarCode) {
        this.goodsBarCode = goodsBarCode;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public Long getWareHouseId() {
        return wareHouseId;
    }

    public void setWareHouseId(Long wareHouseId) {
        this.wareHouseId = wareHouseId;
    }

    public String getStorageLocationCode() {
        return storageLocationCode;
    }

    public void setStorageLocationCode(String strogeLocationCode) {
        this.storageLocationCode = strogeLocationCode;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Long getGoodsId() {
        return this.goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public static Inventory dtoToDataBean(InventoryQueryDto dto) {
        Inventory inventory = new Inventory();
        if (dto == null) {
            return inventory;
        }
        inventory.setWareHouseId(dto.getWareHouseId());
        inventory.setStorageLocationCode(dto.getStorageLocationCode());
        inventory.setGoodsId(dto.getGoodsId());
        inventory.setCustomerId(dto.getCustomerId());
        return inventory;
    }

    @Override
    public String toString() {
        return "InventoryQueryDto{" +
                "wareHouseId=" + wareHouseId +
                ", strogeLocationCode=" + storageLocationCode +
                ", batch='" + batch + '\'' +
                ", goodsId='" + goodsId + '\'' +
                '}';
    }
}
