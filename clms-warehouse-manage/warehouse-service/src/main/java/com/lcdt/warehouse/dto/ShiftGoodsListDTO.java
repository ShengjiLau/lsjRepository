package com.lcdt.warehouse.dto;

import java.io.Serializable;
import java.util.List;

import com.lcdt.warehouse.entity.Inventory;
import com.lcdt.warehouse.entity.ShiftGoodsDO;

public class ShiftGoodsListDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1585621135211L;
	
	private Long inventoryId;
	
	private String storageLocationCode;       
	
	private String batch;
	
	private String goodsName;
	
	private String goodsSpec;
	
	private String goodsCode;
	
	private String barCode;
	
	
	
	
	
	private List<ShiftGoodsDO> shiftGoodsDOList;

	public List<ShiftGoodsDO> getShiftGoodsDOList() {
		return shiftGoodsDOList;
	}

	public void setShiftGoodsDOList(List<ShiftGoodsDO> shiftGoodsDOList) {
		this.shiftGoodsDOList = shiftGoodsDOList;
	}

	public Long getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(Long inventoryId) {
		this.inventoryId = inventoryId;
	}

	public String getStorageLocationCode() {
		return storageLocationCode;
	}

	public void setStorageLocationCode(String storageLocationCode) {
		this.storageLocationCode = storageLocationCode;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsSpec() {
		return goodsSpec;
	}

	public void setGoodsSpec(String goodsSpec) {
		this.goodsSpec = goodsSpec;
	}

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}


	
	
	
	
	
	
	

}
