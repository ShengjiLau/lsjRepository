package com.lcdt.warehouse.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.lcdt.warehouse.dto.ShiftInventoryListDTO;
import com.lcdt.warehouse.service.ShiftInventoryListService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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
	
	
	@PostMapping("/add")
	@ApiOperation(value = "创建移库单")
	@PreAuthorize(value = "hasRole('ROLE_SYS_ADMIN') or hasAuthority('shift_inventory_add')")
	public JSONObject insertInventoryList(ShiftInventoryListDTO shiftInventoryListDTO) {
		JSONObject jsonObject = new JSONObject();
		
		int result = shiftInventoryListService.insertShiftInventoryList(shiftInventoryListDTO);
		if (result > 0) {
			jsonObject.put("code", 0);
			jsonObject.put("message", "创建成功");
		}else {
			throw new RuntimeException("创建移库单时出现异常");
		}
		return jsonObject;
	}
	
	
	@PostMapping("/complete")
	@ApiOperation(value = "完成移库单")
	@PreAuthorize(value = "hasRole('ROLE_SYS_ADMIN') or hasAuthority('shift_inventory_complete')")
	public JSONObject completeInventoryList(ShiftInventoryListDTO shiftInventoryListDTO) {
		JSONObject jsonObject = new JSONObject();
		
		int result = shiftInventoryListService.insertShiftInventoryList(shiftInventoryListDTO);
		if (result > 0) {
			jsonObject.put("code", 0);
			jsonObject.put("message", "完成成功");
		}else {
			throw new RuntimeException("完成移库单时出现异常");
		}
		return jsonObject;
	}
	
	@PostMapping("/list")
	@ApiOperation(value = "查询移库单列表")
	@PreAuthorize(value = "hasRole('ROLE_SYS_ADMIN') or hasAuthority('shift_inventory_list')")
	public JSONObject getShiftInventoryList() {
		JSONObject jsonObject = new JSONObject();
		
		
		
		
		
		return jsonObject;
	}
	
	@PostMapping("/details")
	@ApiOperation(value = "查询移库单详情")
	@PreAuthorize(value = "hasRole('ROLE_SYS_ADMIN') or hasAuthority('shift_inventory_details')")
	public JSONObject getOneShiftInventoryDetails(Long shiftId) {
		JSONObject jsonObject = new JSONObject();
		
		
		
		
		
		return jsonObject;
	}
	
	
	
	
	
	
	

}
