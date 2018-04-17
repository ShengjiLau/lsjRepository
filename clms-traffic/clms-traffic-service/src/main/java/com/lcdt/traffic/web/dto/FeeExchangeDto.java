package com.lcdt.traffic.web.dto;

import java.io.Serializable;

import com.lcdt.traffic.model.FeeExchange;

/**
 * @author Sheng-ji Lau
 * @date 2018年4月17日
 * @version
 * @Description: TODO 
 */
public class FeeExchangeDto extends FeeExchange implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -15151560655482125L;
	
	private String beginTime;
	
	private String endTime;
	
	private int pageNo;
	
	private int pageSize;
	
	

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

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	
	
	
	

}
