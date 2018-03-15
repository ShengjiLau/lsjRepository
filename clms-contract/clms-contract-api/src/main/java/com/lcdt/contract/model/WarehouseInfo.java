package com.lcdt.contract.model;

/**
 * 仓库信息
 * @author Sheng-ji Lau
 * @date 2018年3月12日上午9:32:36
 * @version
 */

public class WarehouseInfo{
	private Integer whId;
	private String whName;
	private Integer whType;
	
	public Integer getWhId() {
		return whId;
	}
	public void setWhId(Integer whId) {
		this.whId = whId;
	}
	public String getWhName() {
		return whName;
	}
	public void setWhName(String whName) {
		this.whName = whName;
	}
	public Integer getWhType() {
		return whType;
	}
	public void setWhType(Integer whType) {
		this.whType = whType;
	}
	
}
