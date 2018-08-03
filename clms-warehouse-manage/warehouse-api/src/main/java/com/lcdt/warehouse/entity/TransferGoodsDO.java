package com.lcdt.warehouse.entity;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("转换单商品Model")
public class TransferGoodsDO implements Serializable {
	@ApiModelProperty("转换单商品id")
    private Long goodsId;
	@ApiModelProperty("源商品id")
    private Long originGoodsId;
	@ApiModelProperty("库存id")
    private Long inventoryId;
	@ApiModelProperty("转换数量")
    private Long transferNum;
	@ApiModelProperty("备注")
    private String remark;
	@ApiModelProperty("转换单主表id")
    private Long transferId;
	@ApiModelProperty("库位id")
    private Long whLocId;
	@ApiModelProperty("库位编码")
    private String whLocCode;
	@ApiModelProperty("商品批次")
    private String goodsBatch;
	@ApiModelProperty("0-原料；1-生成")
    private Byte isMaterial;
	@ApiModelProperty("商品名称")
    private String goodsName;
	@ApiModelProperty("商品编码")
    private String goodsCode;
	@ApiModelProperty("商品条形码")
    private String goodsBarcode;
	@ApiModelProperty("商品规格")
    private String goodsSpec;
	@ApiModelProperty("商品单位")
    private String goodsUnit;

    private static final long serialVersionUID = 1L;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getOriginGoodsId() {
        return originGoodsId;
    }

    public void setOriginGoodsId(Long originGoodsId) {
        this.originGoodsId = originGoodsId;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Long getTransferNum() {
        return transferNum;
    }

    public void setTransferNum(Long transferNum) {
        this.transferNum = transferNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Long getTransferId() {
        return transferId;
    }

    public void setTransferId(Long transferId) {
        this.transferId = transferId;
    }

    public Long getWhLocId() {
        return whLocId;
    }

    public void setWhLocId(Long whLocId) {
        this.whLocId = whLocId;
    }

    public String getWhLocCode() {
        return whLocCode;
    }

    public void setWhLocCode(String whLocCode) {
        this.whLocCode = whLocCode == null ? null : whLocCode.trim();
    }

    public String getGoodsBatch() {
        return goodsBatch;
    }

    public void setGoodsBatch(String goodsBatch) {
        this.goodsBatch = goodsBatch == null ? null : goodsBatch.trim();
    }

    public Byte getIsMaterial() {
        return isMaterial;
    }

    public void setIsMaterial(Byte isMaterial) {
        this.isMaterial = isMaterial;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode == null ? null : goodsCode.trim();
    }

    public String getGoodsBarcode() {
        return goodsBarcode;
    }

    public void setGoodsBarcode(String goodsBarcode) {
        this.goodsBarcode = goodsBarcode == null ? null : goodsBarcode.trim();
    }

    public String getGoodsSpec() {
        return goodsSpec;
    }

    public void setGoodsSpec(String goodsSpec) {
        this.goodsSpec = goodsSpec == null ? null : goodsSpec.trim();
    }

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit == null ? null : goodsUnit.trim();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TransferGoodsDO other = (TransferGoodsDO) that;
        return (this.getGoodsId() == null ? other.getGoodsId() == null : this.getGoodsId().equals(other.getGoodsId()))
            && (this.getOriginGoodsId() == null ? other.getOriginGoodsId() == null : this.getOriginGoodsId().equals(other.getOriginGoodsId()))
            && (this.getInventoryId() == null ? other.getInventoryId() == null : this.getInventoryId().equals(other.getInventoryId()))
            && (this.getTransferNum() == null ? other.getTransferNum() == null : this.getTransferNum().equals(other.getTransferNum()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getTransferId() == null ? other.getTransferId() == null : this.getTransferId().equals(other.getTransferId()))
            && (this.getWhLocId() == null ? other.getWhLocId() == null : this.getWhLocId().equals(other.getWhLocId()))
            && (this.getWhLocCode() == null ? other.getWhLocCode() == null : this.getWhLocCode().equals(other.getWhLocCode()))
            && (this.getGoodsBatch() == null ? other.getGoodsBatch() == null : this.getGoodsBatch().equals(other.getGoodsBatch()))
            && (this.getIsMaterial() == null ? other.getIsMaterial() == null : this.getIsMaterial().equals(other.getIsMaterial()))
            && (this.getGoodsName() == null ? other.getGoodsName() == null : this.getGoodsName().equals(other.getGoodsName()))
            && (this.getGoodsCode() == null ? other.getGoodsCode() == null : this.getGoodsCode().equals(other.getGoodsCode()))
            && (this.getGoodsBarcode() == null ? other.getGoodsBarcode() == null : this.getGoodsBarcode().equals(other.getGoodsBarcode()))
            && (this.getGoodsSpec() == null ? other.getGoodsSpec() == null : this.getGoodsSpec().equals(other.getGoodsSpec()))
            && (this.getGoodsUnit() == null ? other.getGoodsUnit() == null : this.getGoodsUnit().equals(other.getGoodsUnit()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getGoodsId() == null) ? 0 : getGoodsId().hashCode());
        result = prime * result + ((getOriginGoodsId() == null) ? 0 : getOriginGoodsId().hashCode());
        result = prime * result + ((getInventoryId() == null) ? 0 : getInventoryId().hashCode());
        result = prime * result + ((getTransferNum() == null) ? 0 : getTransferNum().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getTransferId() == null) ? 0 : getTransferId().hashCode());
        result = prime * result + ((getWhLocId() == null) ? 0 : getWhLocId().hashCode());
        result = prime * result + ((getWhLocCode() == null) ? 0 : getWhLocCode().hashCode());
        result = prime * result + ((getGoodsBatch() == null) ? 0 : getGoodsBatch().hashCode());
        result = prime * result + ((getIsMaterial() == null) ? 0 : getIsMaterial().hashCode());
        result = prime * result + ((getGoodsName() == null) ? 0 : getGoodsName().hashCode());
        result = prime * result + ((getGoodsCode() == null) ? 0 : getGoodsCode().hashCode());
        result = prime * result + ((getGoodsBarcode() == null) ? 0 : getGoodsBarcode().hashCode());
        result = prime * result + ((getGoodsSpec() == null) ? 0 : getGoodsSpec().hashCode());
        result = prime * result + ((getGoodsUnit() == null) ? 0 : getGoodsUnit().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", goodsId=").append(goodsId);
        sb.append(", originGoodsId=").append(originGoodsId);
        sb.append(", inventoryId=").append(inventoryId);
        sb.append(", transferNum=").append(transferNum);
        sb.append(", remark=").append(remark);
        sb.append(", transferId=").append(transferId);
        sb.append(", whLocId=").append(whLocId);
        sb.append(", whLocCode=").append(whLocCode);
        sb.append(", goodsBatch=").append(goodsBatch);
        sb.append(", isMaterial=").append(isMaterial);
        sb.append(", goodsName=").append(goodsName);
        sb.append(", goodsCode=").append(goodsCode);
        sb.append(", goodsBarcode=").append(goodsBarcode);
        sb.append(", goodsSpec=").append(goodsSpec);
        sb.append(", goodsUnit=").append(goodsUnit);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}