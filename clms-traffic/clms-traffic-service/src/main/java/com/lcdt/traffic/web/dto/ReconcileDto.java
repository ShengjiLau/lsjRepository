package com.lcdt.traffic.web.dto;

import com.lcdt.traffic.model.FeeAccount;
import com.lcdt.traffic.model.FeeExchange;
import com.lcdt.traffic.model.Reconcile;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;



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
	
	@ApiModelProperty("查询起点时间")
	private String beginTime;
	
	@ApiModelProperty("查询终点时间")
	private String endTime;
	
	@ApiModelProperty("每页条目数")
	private int pageSize;
	
	@ApiModelProperty("页码,第几页")
	private int pageNum;
	
	@ApiModelProperty("已收款/付款额")
	private Double sumAmount;
	
	@ApiModelProperty("已收款/付款次数")
	private int sumAmountNum;
	
	private  Long[] accountIds;
	
	@ApiModelProperty("对账当相关收付款记录")
	private List<FeeExchange> feeExchangeList;
	
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


	public Double getSumAmount() {
		return sumAmount;
	}


	public void setSumAmount(Double sumAmount) {
		this.sumAmount = sumAmount;
	}


	public int getSumAmountNum() {
		return sumAmountNum;
	}


	public void setSumAmountNum(int sumAmountNum) {
		this.sumAmountNum = sumAmountNum;
	}


	public Long[] getAccountIds() {
		return accountIds;
	}


	public void setAccountIds(Long[] accountIds) {
		this.accountIds = accountIds;
	}


	public List<FeeExchange> getFeeExchangeList() {
		return feeExchangeList;
	}


	public void setFeeExchangeList(List<FeeExchange> feeExchangeList) {
		this.feeExchangeList = feeExchangeList;
	}


}
