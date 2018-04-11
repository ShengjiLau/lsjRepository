package com.lcdt.traffic.web.dto;

import java.io.Serializable;

import com.lcdt.traffic.model.Reconcile;



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
	
	private String beginTime;
	
	private String endTime;
	
	private int pageSize;
	
	private int pageNum;

	private Long group;
	
	
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


	public Long getGroup() {
		return group;
	}


	public void setGroup(Long group) {
		this.group = group;
	}


	

	
	
	
	
	
	
	
	
	
	
	
	
	

}
