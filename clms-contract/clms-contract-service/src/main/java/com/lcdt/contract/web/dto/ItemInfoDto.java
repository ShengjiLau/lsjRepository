package com.lcdt.contract.web.dto;

import java.io.Serializable;

import com.lcdt.contract.model.ItemInfo;

/**
 * @author Sheng-ji Lau
 * @date 2018年3月7日下午2:00:20
 * @version
 */
public class ItemInfoDto extends ItemInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 96362255L;
	private String roughName;
	private Long companyId;
	private Long createId;
	
	public String getRoughName() {
		return roughName;
	}
	public void setRoughName(String roughName) {
		this.roughName = roughName;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Long getCreateId() {
		return createId;
	}
	public void setCreateId(Long createId) {
		this.createId = createId;
	}
	
	
	

}
