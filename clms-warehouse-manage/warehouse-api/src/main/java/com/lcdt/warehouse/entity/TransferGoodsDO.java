package com.lcdt.warehouse.entity;

import java.io.Serializable;

public class TransferGoodsDO implements Serializable {
    private Long goodsId;

    private Long originGoodsId;

    private Long inventoryId;

    private Long transferNum;

    private String remark;

    private Long transferId;

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
            && (this.getTransferId() == null ? other.getTransferId() == null : this.getTransferId().equals(other.getTransferId()));
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
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}