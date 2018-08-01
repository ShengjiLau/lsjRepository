package com.lcdt.warehouse.dto;

import java.util.List;

import com.lcdt.warehouse.entity.TransferGoodsDO;
import com.lcdt.warehouse.entity.TransferInventoryListDO;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Sheng-ji Lau
 * @date 2018年8月1日
 * @version
 * @Description: TODO 
 */
@Setter
@Getter
public class TransferInventoryListDTO extends TransferInventoryListDO{
	
	private List<TransferGoodsDO> transferGoodsDOList;
	
	private String customName;
	
	private String materialProduct;
	
	private String finishedProduct;
	
	private Integer pageSize;
	
	private Integer pageNo;


}
