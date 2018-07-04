package com.lcdt.contract.web.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Sheng-ji Lau
 * @date 2018年7月3日
 * @version
 * @Description: TODO 
 */
public class TrendDiagramDto implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 65156456235615245L;

	private String date;
	
	private Integer orderCount;
	
	private BigDecimal orderMoneyAmount;

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	public BigDecimal getOrderMoneyAmount() {
		return orderMoneyAmount;
	}

	public void setOrderMoneyAmount(BigDecimal orderMoneyAmount) {
		this.orderMoneyAmount = orderMoneyAmount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
	
	

}
