package com.lcdt.traffic.web.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

import com.lcdt.traffic.model.Reconcile;

import io.swagger.annotations.ApiModelProperty;



/**
 * @author Sheng-ji Lau
 * @date 2018年4月9日下午4:02:18
 * @version
 */
public class ReconcileDto extends Reconcile implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4584651511515L;
	
	@ApiModelProperty("查询起点时间")
	private String beginTime;
	
	@ApiModelProperty("查询终点时间")
	private String endTime;
	
	@ApiModelProperty("每页条目数")
	private int pageSize;
	
	@ApiModelProperty("页码,第几页")
	private int pageNum;
	
	@ApiModelProperty("记账单id数组")
	@NotEmpty
	private Long[] accountIds;
	
	@NotEmpty
	@ApiModelProperty("运单id数组")
	private Long[] waybillIds;
	
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


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public int getPageNum() {
		return pageNum;
	}


	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}


	public Long[] getAccountIds() {
		return accountIds;
	}


	public void setAccountIds(Long[] accountIds) {
		this.accountIds = accountIds;
	}


	public Long[] getWaybillIds() {
		return waybillIds;
	}


	public void setWaybillIds(Long[] waybillIds) {
		this.waybillIds = waybillIds;
	}


	

	
	
	
	
	
	
	
	
	
	
	
	
	

}
