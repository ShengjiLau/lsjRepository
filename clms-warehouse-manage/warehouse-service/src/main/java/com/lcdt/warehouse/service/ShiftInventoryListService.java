package com.lcdt.warehouse.service;

import com.github.pagehelper.PageInfo;
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
	
	
	/**
	 * 依据条件查询移库单列表
	 * @param shiftInventoryListDTO
	 * @return
	 */
	PageInfo<ShiftInventoryListDTO> getShiftInventoryList(ShiftInventoryListDTO shiftInventoryListDTO);
	
	
	/**
	 * 查询单个移库单详情
	 */
	ShiftInventoryListDTO getShiftInventoryListDetails(Long shiftInventoryListId);
	
	

}
