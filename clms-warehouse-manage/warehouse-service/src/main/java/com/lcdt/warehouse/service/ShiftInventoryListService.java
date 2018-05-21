package com.lcdt.warehouse.service;

import com.lcdt.warehouse.dto.ShiftInventoryListDTO;

/**
 * @author Sheng-ji Lau
 * @date 2018年5月10日
 * @version
 * @Description: TODO 
 */
public interface ShiftInventoryListService {
	
	
	/**
	 * 创建移库单
	 * @return
	 */
	int insertShiftInventoryList(ShiftInventoryListDTO shiftInventoryListDTO);
	
	
	/**
	 * 完成移库单
	 */
	int completeShiftInventoryList(ShiftInventoryListDTO shiftInventoryListDTO);
	
	
	
	
	

}
