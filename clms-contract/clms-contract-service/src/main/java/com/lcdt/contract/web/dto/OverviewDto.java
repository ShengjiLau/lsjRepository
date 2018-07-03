package com.lcdt.contract.web.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Sheng-ji Lau
 * @date 2018年6月29日
 * @version
 * @Description: TODO 
 */
public class OverviewDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 48535656566L;

	@ApiModelProperty("所属业务组id")
	private Long companyId;
	
	@ApiModelProperty("业务组id数组")
	private Long[] groups;
	
	@ApiModelProperty("业务组id")
	private Long groupId;
	
	@ApiModelProperty("查询时间起点")
	private String beginTime;
	
	@ApiModelProperty("查询时间终点")
	private String endTime;
	

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long[] getGroups() {
		return groups;
	}

	public void setGroups(Long[] groups) {
		this.groups = groups;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	
	
	
	
	
	

}
