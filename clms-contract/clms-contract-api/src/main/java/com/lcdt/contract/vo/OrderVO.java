package com.lcdt.contract.vo;

import java.math.BigDecimal;

/**
 * @author Sheng-ji Lau
 * @date 2018年7月18日
 * @version
 * @Description: TODO 
 */
public class OrderVO {
	
	/**
	 * 零值
	 */
	public static final BigDecimal ZERO_VALUE = new  BigDecimal(0);
	
	/**
	 * 是否删除
	 */
	public static final short IS_DELETED = 0;
	
	/**
	 * 订单类型：销售订单
	 */
	public static final short SALES_ORDER = 1;
	
	/**
	 * 订单类型：采购订单
	 */
	public static final short PURCHASE_ORDER = 0;
	
	
	/**
	 * 订单状态：已取消
	 */
	public static final short CANCEL_STATUS = 2;


	public static final short WATTING_PUBLISHI = -1;


	/**
	 * 订单状态：未发布
	 */
	public static final short NO_PUBLISHI = 0;
	
	/**
	 * 订单状态：已发布
	 */
	public static final short ALREADY_PUBLISHI = 1;
	
	
	public static final Integer ZERO_INTEGER = 0;
	
	
	
	

}
