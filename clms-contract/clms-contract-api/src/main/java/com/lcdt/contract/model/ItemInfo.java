package com.lcdt.contract.model;

import java.io.Serializable;

/**
 * @author Sheng-ji Lau
 * @date 2018年3月7日上午11:31:18
 * @version
 */
public class ItemInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 33636L;
	private Integer ItemId;
	private String subject;
	//商品编码
	private String code;
	//商品规格
	private String subItemProperty;
	private String unitName;
	private Float purchasePrice;
	public Integer getItemId() {
		return ItemId;
	}
	public void setItemId(Integer itemId) {
		ItemId = itemId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSubItemProperty() {
		return subItemProperty;
	}
	public void setSubItemProperty(String subItemProperty) {
		this.subItemProperty = subItemProperty;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public Float getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(Float purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	
	
	
	
	
	
	
	

}
