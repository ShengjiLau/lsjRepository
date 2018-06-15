package com.lcdt.warehouse.utils;

import java.io.Serializable;


import lombok.Getter;
import lombok.Setter;

/**
 * @author Sheng-ji Lau
 * @date 2018年6月4日
 * @version
 * @Description: TODO 
 */

@Setter
@Getter
public class ShiftGoodsBO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1515164155L;

	//商品批次
	private String goodsBatch;
	
	//商品编码
	private String goodsCode;
	
	//公司id
	private Long companyId;
	
	//客户id
	private Long customerId;
	
	//仓库id
	private Long warehouseId;
	
	//库位编码
	private String storageLocationCode;
	
	
}
