package com.lcdt.contract.web.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.TreeMap;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Sheng-ji Lau
 * @date 2018年6月28日
 * @version
 * @Description: TODO 
 */
public class OrderOverviewDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -15858933333L;

	@ApiModelProperty("采购/销售单")
	private Integer numOfOrders;
	
	@ApiModelProperty("采购/销售金额")
	private BigDecimal totalOrderAmount;
	
	@ApiModelProperty("采购/销售客单价")
	private BigDecimal customerUnitPrice;
	
	@ApiModelProperty("采购/销售商品数量")
	private Integer numOfProduct;
	
	@ApiModelProperty("采购/销售商品均价")
	private BigDecimal averagePriceOfProduce;
	
	@ApiModelProperty("合同 待发布")
	private Integer waitingReleasedContract;
	
	@ApiModelProperty("合同 待生效")
	private Integer waitingEffectiveContract; 
	
	@ApiModelProperty("合同 生效中")
	private Integer inEffectContract;
	
	@ApiModelProperty("合同 已失效")
	private Integer defunctContract;
	
	@ApiModelProperty("订单 待发布")
	private Integer waitingReleasedOrder;
	
	@ApiModelProperty("订单 待收款")
	private Integer waitingMoneyGatheringOrder;
	
	@ApiModelProperty("订单 收款中")
	private Integer moneyCollectingOrder;
	
	@ApiModelProperty("订单 已收款")
	private Integer moneyReceivedOrder;
	
	@ApiModelProperty("采购/销售 走势图")
	private List<TrendDiagramDto> trendDiagramList;

	public Integer getNumOfOrders() {
		return numOfOrders;
	}

	public void setNumOfOrders(Integer numOfOrders) {
		this.numOfOrders = numOfOrders;
	}

	public BigDecimal getTotalOrderAmount() {
		return totalOrderAmount;
	}

	public void setTotalOrderAmount(BigDecimal totalOrderAmount) {
		this.totalOrderAmount = totalOrderAmount;
	}

	public BigDecimal getCustomerUnitPrice() {
		return customerUnitPrice;
	}

	public void setCustomerUnitPrice(BigDecimal customerUnitPrice) {
		this.customerUnitPrice = customerUnitPrice;
	}

	public Integer getNumOfProduct() {
		return numOfProduct;
	}

	public void setNumOfProduct(Integer numOfProduct) {
		this.numOfProduct = numOfProduct;
	}

	public BigDecimal getAveragePriceOfProduce() {
		return averagePriceOfProduce;
	}

	public void setAveragePriceOfProduce(BigDecimal averagePriceOfProduce) {
		this.averagePriceOfProduce = averagePriceOfProduce;
	}

	public Integer getWaitingReleasedContract() {
		return waitingReleasedContract;
	}

	public void setWaitingReleasedContract(Integer waitingReleasedContract) {
		this.waitingReleasedContract = waitingReleasedContract;
	}

	public Integer getWaitingEffectiveContract() {
		return waitingEffectiveContract;
	}

	public void setWaitingEffectiveContract(Integer waitingEffectiveContract) {
		this.waitingEffectiveContract = waitingEffectiveContract;
	}

	public Integer getInEffectContract() {
		return inEffectContract;
	}

	public void setInEffectContract(Integer inEffectContract) {
		this.inEffectContract = inEffectContract;
	}

	public Integer getDefunctContract() {
		return defunctContract;
	}

	public void setDefunctContract(Integer defunctContract) {
		this.defunctContract = defunctContract;
	}

	public Integer getWaitingReleasedOrder() {
		return waitingReleasedOrder;
	}

	public void setWaitingReleasedOrder(Integer waitingReleasedOrder) {
		this.waitingReleasedOrder = waitingReleasedOrder;
	}

	public Integer getWaitingMoneyGatheringOrder() {
		return waitingMoneyGatheringOrder;
	}

	public void setWaitingMoneyGatheringOrder(Integer waitingMoneyGatheringOrder) {
		this.waitingMoneyGatheringOrder = waitingMoneyGatheringOrder;
	}

	public Integer getMoneyCollectingOrder() {
		return moneyCollectingOrder;
	}

	public void setMoneyCollectingOrder(Integer moneyCollectingOrder) {
		this.moneyCollectingOrder = moneyCollectingOrder;
	}

	public Integer getMoneyReceivedOrder() {
		return moneyReceivedOrder;
	}

	public void setMoneyReceivedOrder(Integer moneyReceivedOrder) {
		this.moneyReceivedOrder = moneyReceivedOrder;
	}

	public List<TrendDiagramDto> getTrendDiagramList() {
		return trendDiagramList;
	}

	public void setTrendDiagramList(List<TrendDiagramDto> trendDiagramList) {
		this.trendDiagramList = trendDiagramList;
	}

	

	

	
	
	
	
	
	

}
