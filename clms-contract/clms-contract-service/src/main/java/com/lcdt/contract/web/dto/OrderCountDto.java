package com.lcdt.contract.web.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.TreeMap;

public class OrderCountDto implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9963626126L;

	private Integer purchaseOrderCount;
	
	private Integer salesOrderCount;
	
	private TreeMap<Date, Integer> orderCountByDate;

	public Integer getPurchaseOrderCount() {
		return purchaseOrderCount;
	}

	public void setPurchaseOrderCount(Integer purchaseOrderCount) {
		this.purchaseOrderCount = purchaseOrderCount;
	}

	public Integer getSalesOrderCount() {
		return salesOrderCount;
	}

	public void setSalesOrderCount(Integer salesOrderCount) {
		this.salesOrderCount = salesOrderCount;
	}

	public TreeMap<Date, Integer> getOrderCountByDate() {
		return orderCountByDate;
	}

	public void setOrderCountByDate(TreeMap<Date, Integer> orderCountByDate) {
		this.orderCountByDate = orderCountByDate;
	}
	
	

}
