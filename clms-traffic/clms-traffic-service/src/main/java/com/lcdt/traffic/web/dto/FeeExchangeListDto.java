package com.lcdt.traffic.web.dto;

import java.io.Serializable;
import java.util.List;

import com.lcdt.traffic.model.FeeExchange;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Sheng-ji Lau
 * @date 2018年4月20日
 * @version
 * @Description: TODO
 */
public class FeeExchangeListDto implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -1156151561561L;
	
	@ApiModelProperty("对账单id")
	private Long reconcileId;
	
	@ApiModelProperty("收付款类型")
	private short type;
	
	@ApiModelProperty("收付款记录集合")
	private List<FeeExchange> feeExchangeList;

	public List<FeeExchange> getFeeExchangeList() {
		return feeExchangeList;
	}

	public void setFeeExchangeList(List<FeeExchange> feeExchangeList) {
		this.feeExchangeList = feeExchangeList;
	}

	public Long getReconcileId() {
		return reconcileId;
	}

	public void setReconcileId(Long reconcileId) {
		this.reconcileId = reconcileId;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}
	
	
	
	

}
