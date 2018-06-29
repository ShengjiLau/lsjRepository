package com.lcdt.contract.web.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.TreeMap;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Sheng-ji Lau
 * @date 2018年6月28日
 * @version
 * @Description: TODO 
 */
public class OrderOverviewDto {
	
	@ApiModelProperty("所属业务组id")
	private Long companyId;
	
	@ApiModelProperty("业务组id数组")
	private Long[] groups;
	
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
	private TreeMap<Date,Hashtable> trendDiagram;
	
	
	
	
	

}
