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
	
	//时间
	private String date;
	
	//订单数量
	private Integer orderCount;
	
	//订单金额
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
