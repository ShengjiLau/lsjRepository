package com.lcdt.contract.model;

import java.io.Serializable;

/**
 * 供应商信息
 * @author Sheng-ji Lau
 * @date 2018年3月12日上午9:32:43
 * @version
 */
public class DeliveryInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4793255L;
	
	private Long companyId;
	private String supplier;
	private String linkMan;
	private String linkTel;
	private String address;
	
	
	public String getLinkMan() {
		return linkMan;
	}
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	public String getLinkTel() {
		return linkTel;
	}
	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
