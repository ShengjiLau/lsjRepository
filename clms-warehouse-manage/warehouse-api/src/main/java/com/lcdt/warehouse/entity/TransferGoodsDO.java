package com.lcdt.warehouse.entity;

import java.io.Serializable;

public class TransferGoodsDO implements Serializable {
    private Long goodsId;

    private Long originGoodsId;

    private Long inventoryId;

    private Long transferNum;

    private String remark;

    private Long transferId;

    private Long whLocId;

    private String whLocCode;

    private String goodsBatch;
    
    private byte isMaterial;
    
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
            && (this.getGoodsBatch() == null ? other.getGoodsBatch() == null : this.getGoodsBatch().equals(other.getGoodsBatch()));
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
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

	public byte getIsMaterial() {
		return isMaterial;
	}

	public void setIsMaterial(byte isMaterial) {
		this.isMaterial = isMaterial;
	}
}