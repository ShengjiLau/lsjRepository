package com.lcdt.warehouse.dto;

import java.io.Serializable;
import java.util.List;

import com.lcdt.warehouse.entity.ShiftGoodsDO;

public class ShiftGoodsListDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1585621135211L;
	
	private Long InventoryId;
	
	private List<ShiftGoodsDO> shiftGoodsDOList;

	public List<ShiftGoodsDO> getShiftGoodsDOList() {
		return shiftGoodsDOList;
	}

	public void setShiftGoodsDOList(List<ShiftGoodsDO> shiftGoodsDOList) {
		this.shiftGoodsDOList = shiftGoodsDOList;
	}

	public Long getInventoryId() {
		return InventoryId;
	}

	public void setInventoryId(Long inventoryId) {
		InventoryId = inventoryId;
	}
	
	
	
	
	
	

}
