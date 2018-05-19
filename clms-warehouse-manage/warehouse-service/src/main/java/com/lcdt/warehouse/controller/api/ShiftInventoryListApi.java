package com.lcdt.warehouse.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.lcdt.warehouse.dto.ShiftInventoryListDTO;
import com.lcdt.warehouse.service.ShiftInventoryListService;

import io.swagger.annotations.Api;

/**
 * @author Sheng-ji Lau
 * @date 2018年5月10日
 * @version
 * @Description: TODO 
 */

@RestController
@RequestMapping("/api/shiftInventory")
@Api
public class ShiftInventoryListApi {
	
	@Autowired
	private ShiftInventoryListService shiftInventoryListService;
	
	
	
	
	public JSONObject insertInventoryList(ShiftInventoryListDTO shiftInventoryListDTO) {
		JSONObject jsonObject = new JSONObject();
		
		int result = shiftInventoryListService.insertShiftInventoryList(shiftInventoryListDTO);
		
		return jsonObject;
		
	}
	
	
	
	
	
	
	
	

}
