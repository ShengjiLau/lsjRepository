package com.lcdt.contract.web.dto;

import java.io.Serializable;
import java.util.List;



import com.lcdt.contract.model.Order;
import com.lcdt.contract.model.OrderApproval;
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
	
	@ApiModelProperty(value="查询创建时间起点")
	private String beginTime;
	
	@ApiModelProperty(value="查询创建时间终点")
	private String endTime;

	private Short paymentStatus;

	private String paymentNum;

	private String billingRecordNum;
	
	@ApiModelProperty(value="页码")
	private int pageNum;
	@ApiModelProperty(value="每页条目数")
	private int pageSize;
	
	private List<OrderProduct> orderProductList;

	private List<OrderApproval> orderApprovalList;
	
	

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

	public Short getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Short paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getPaymentNum() {
		return paymentNum;
	}

	public void setPaymentNum(String paymentNum) {
		this.paymentNum = paymentNum;
	}

	public String getBillingRecordNum() {
		return billingRecordNum;
	}

	public void setBillingRecordNum(String billingRecordNum) {
		this.billingRecordNum = billingRecordNum;
	}

	public List<OrderApproval> getOrderApprovalList() {
		return orderApprovalList;
	}

	public void setOrderApprovalList(List<OrderApproval> orderApprovalList) {
		this.orderApprovalList = orderApprovalList;
	}
}
