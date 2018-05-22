package com.lcdt.warehouse.dto;

import java.io.Serializable;
import java.util.List;
import com.lcdt.warehouse.entity.ShiftGoodsDO;

public class ShiftGoodsListDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1585621135211L;
	
	//库存id
	private Long inventoryId;
	
	//库位名称
	private String storageLocationCode;       
	
	//商品批次
	private String goodsBatch;
	
	//商品名称
	private String goodsName;
	
	//商品规格
	private String goodsSpec;
	
	//商品编码
	private String goodsCode;
	
	//商品编码
	private String barCode;
	
	//可用库存
	private Float usableInventory;
	
	//商品计量单位
	private String baseUnit;
	
	//移入的库存商品信息列表
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

	public Float getUsableInventory() {
		return usableInventory;
	}

	public void setUsableInventory(Float usableInventory) {
		this.usableInventory = usableInventory;
	}

	public String getGoodsBatch() {
		return goodsBatch;
	}

	public void setGoodsBatch(String goodsBatch) {
		this.goodsBatch = goodsBatch;
	}

	public String getBaseUnit() {
		return baseUnit;
	}

	public void setBaseUnit(String baseUnit) {
		this.baseUnit = baseUnit;
	}


	
	
	
	
	
	
	

}
