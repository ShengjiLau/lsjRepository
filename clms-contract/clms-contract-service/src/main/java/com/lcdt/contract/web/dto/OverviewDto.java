package com.lcdt.contract.web.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

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
	
	@ApiModelProperty("订单类型：采购==0；销售==1")
	private Short type;
	
	@ApiModelProperty("所属业务组id")
	private Long companyId;
	
	@ApiModelProperty("业务组id数组")
	private String groups;
	
	@ApiModelProperty("业务组id")
	private Long groupId;
	
	@NotBlank(message="查询时间起点不可为空！")
	@ApiModelProperty("查询时间起点")
	private String beginTime;
	
	@NotBlank(message="查询时间终点不可为空！")
	@ApiModelProperty("查询时间终点")
	private String endTime;
	
	@ApiModelProperty("付款状态:0-未付款；1-已付款；2-付款中；3-全部")
	private Integer paymentType;
	
	@ApiModelProperty("每页数量")
	private Integer pageSize;
	
	@ApiModelProperty("页码")
	private Integer pageNum;
	
	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
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

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public String getGroups() {
		return groups;
	}

	public void setGroups(String groups) {
		this.groups = groups;
	}

	public Integer getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}


	
	
	
	
	
	

}
