package com.lcdt.contract.web.dto;

import java.io.Serializable;
import java.util.Date;

import com.lcdt.contract.model.Order;

/**
 * @author Sheng-ji Lau
 * @date 2018年3月2日下午6:46:12
 * @version
 */
public class OrderDto extends Order  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1032515L;
	
	private Date beginTime;
	private Date endTime;
	private int pageNum;
	private int pageSize;
	
	
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
