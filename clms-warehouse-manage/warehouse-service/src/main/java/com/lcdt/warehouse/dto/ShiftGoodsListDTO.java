package com.lcdt.warehouse.dto;

import java.io.Serializable;
import java.util.List;

import com.lcdt.warehouse.entity.Inventory;
import com.lcdt.warehouse.entity.ShiftGoodsDO;

public class ShiftGoodsListDTO extends Inventory implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1585621135211L;
	
	//商品id
	private Long goodsId;
	
	//商品名称
	private String goodsName;
	
	//商品规格
	private String goodsSpec;
	
	//商品编码
	private String goodsCode;
		
	//商品编码
	private String barCode;
	
	private Integer goodsPrice;
	
	//商品批次
	private String goodsBatch;
	
	private Long subItemId;
	
	private Long originalGoodsId;
	
	//商品分类
	private String goodsCategory;
	
	private String goodsClassify;
	
	private String minUnit;
	
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

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public Integer getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(Integer goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public Long getSubItemId() {
		return subItemId;
	}

	public void setSubItemId(Long subItemId) {
		this.subItemId = subItemId;
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

	public String getGoodsClassify() {
		return goodsClassify;
	}

	public void setGoodsClassify(String goodsClassify) {
		this.goodsClassify = goodsClassify;
	}

	public String getMinUnit() {
		return minUnit;
	}

	public void setMinUnit(String minUnit) {
		this.minUnit = minUnit;
	}

	

	
	

}
