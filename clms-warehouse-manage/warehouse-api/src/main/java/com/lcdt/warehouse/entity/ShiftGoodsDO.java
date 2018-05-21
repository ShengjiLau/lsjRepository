package com.lcdt.warehouse.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Sheng-ji Lau
 * @date 2018年5月18日
 * @version
 * @Description: TODO 
 */
public class ShiftGoodsDO implements Serializable {
    private Long shiftGoodsId;

    private Long inventoryId;

    private String shiftLocation;

    private BigDecimal shiftPlanNum;

    private BigDecimal shiftNum;

    private String remark;

    private static final long serialVersionUID = 1491515315L;

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

   
}