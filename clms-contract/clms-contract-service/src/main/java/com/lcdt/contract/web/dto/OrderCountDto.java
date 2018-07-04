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
	
	private TreeMap<String, Integer> purchaseOrderCountByDate;
	
	private TreeMap<String, Integer> salesOrderCountByDate;

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

	public TreeMap<String, Integer> getPurchaseOrderCountByDate() {
		return purchaseOrderCountByDate;
	}

	public void setPurchaseOrderCountByDate(TreeMap<String, Integer> purchaseOrderCountByDate) {
		this.purchaseOrderCountByDate = purchaseOrderCountByDate;
	}

	public TreeMap<String, Integer> getSalesOrderCountByDate() {
		return salesOrderCountByDate;
	}

	public void setSalesOrderCountByDate(TreeMap<String, Integer> salesOrderCountByDate) {
		this.salesOrderCountByDate = salesOrderCountByDate;
	}



	
	

}
