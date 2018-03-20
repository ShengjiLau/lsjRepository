package com.lcdt.contract.web.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;



import com.lcdt.contract.model.Order;
import com.lcdt.contract.model.OrderProduct;

import io.swagger.annotations.ApiModelProperty;


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
	
	@ApiModelProperty(value="合同起始时间")
	private Date beginTime;
	
	@ApiModelProperty(value="合同终止时间")
	private Date endTime;
	
	@ApiModelProperty(value="页码")
	private int pageNum;
	@ApiModelProperty(value="每页条目数")
	private int pageSize;
	
	private List<OrderProduct> orderProductList;
	
	
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
	public List<OrderProduct> getOrderProductList() {
		return orderProductList;
	}
	public void setOrderProductList(List<OrderProduct> orderProductList) {
		this.orderProductList = orderProductList;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
