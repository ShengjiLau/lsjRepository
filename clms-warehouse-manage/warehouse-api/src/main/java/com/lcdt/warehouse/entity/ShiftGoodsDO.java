package com.lcdt.warehouse.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Sheng-ji Lau
 * @date 2018年5月18日
 * @version
 * @Description: TODO 
 */
@ApiModel("移库商品信息")
public class ShiftGoodsDO implements Serializable {
	
	@ApiModelProperty("移库商品信息")
    private Long shiftGoodsId;
	
	@NotNull(message="库存id不可为空")
	@ApiModelProperty("库存id")
    private Long inventoryId;
	
	@NotBlank
	@ApiModelProperty("目标库编码")
    private String shiftLocation;
	
	@NotNull
	@ApiModelProperty("计划移库量")
    private BigDecimal shiftPlanNum;

	@NotNull
	@ApiModelProperty("实际移库量")
    private BigDecimal shiftNum;

	@ApiModelProperty("备注")
    private String remark;
 
	@ApiModelProperty("所对应的移库单id")
    private Long shiftInventoryId;

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

	public Long getShiftInventoryId() {
		return shiftInventoryId;
	}

	public void setShiftInventoryId(Long shiftInventoryId) {
		this.shiftInventoryId = shiftInventoryId;
	}

   
}