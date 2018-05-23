package com.lcdt.warehouse.controller.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.warehouse.dto.PageBaseDto;
import com.lcdt.warehouse.dto.ShiftInventoryListDTO;
import com.lcdt.warehouse.service.ShiftInventoryListService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author Sheng-ji Lau
 * @date 2018年5月10日
 * @version
 * @Description: TODO 
 */

@RestController
@RequestMapping("/api/shiftInventory")
@Api("移库单接口")
public class ShiftInventoryListApi {
	
	@Autowired
	private ShiftInventoryListService shiftInventoryListService;
	
	private Logger logger = LoggerFactory.getLogger(ShiftInventoryListApi.class);
	
	
	@PostMapping("/add")
	@ApiOperation(value = "创建移库单")
	@PreAuthorize(value = "hasRole('ROLE_SYS_ADMIN') or hasAuthority('shift_inventory_add')")
	public JSONObject insertInventoryList(@Validated ShiftInventoryListDTO shiftInventoryListDTO) {
		JSONObject jsonObject = new JSONObject();
		
		int result = shiftInventoryListService.insertShiftInventoryList(shiftInventoryListDTO);	
		logger.debug("创建移库单的数量:"+result);
		
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
		logger.debug("完成移库单的数量:"+result);
		
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
	public JSONObject getShiftInventoryList(ShiftInventoryListDTO shiftInventoryListDTO) {
		JSONObject jsonObject = new JSONObject();
		PageBaseDto<ShiftInventoryListDTO> pageBaseDto = new PageBaseDto<ShiftInventoryListDTO>();
		
		PageInfo<ShiftInventoryListDTO> pageInfo = shiftInventoryListService.getShiftInventoryList(shiftInventoryListDTO);
		pageBaseDto.setList(pageInfo.getList());
		pageBaseDto.setTotal(pageInfo.getTotal());
		
		jsonObject.put("code", 0);
		jsonObject.put("message", "移库单列表");
		jsonObject.put("data", pageBaseDto);
		return jsonObject;
	}
	
	@PostMapping("/details")
	@ApiOperation(value = "查询移库单详情")
	@PreAuthorize(value = "hasRole('ROLE_SYS_ADMIN') or hasAuthority('shift_inventory_details')")
	public JSONObject getOneShiftInventoryDetails(@ApiParam(value="移库单id",required=true) Long shiftId) {
		JSONObject jsonObject = new JSONObject();
		ShiftInventoryListDTO shiftInventoryListDTO = shiftInventoryListService.getShiftInventoryListDetails(shiftId);
		
		if (null != shiftInventoryListDTO) {
			jsonObject.put("code", 0);
			jsonObject.put("message", "移库单详情");
			jsonObject.put("data", shiftInventoryListDTO);
		}else {
			jsonObject.put("code", -1);
			jsonObject.put("message", "未查询到相关移库单");
		}
		
		return jsonObject;
	}
	
	@PostMapping("/remove")
	@ApiOperation(value = "取消移库单")
	@PreAuthorize(value = "hasRole('ROLE_SYS_ADMIN') or hasAuthority('shift_inventory_remove')")
	public JSONObject deleteShiftInventoryList(Long shiftInventoryListId) {
		JSONObject jsonObject = new JSONObject();
		int result = shiftInventoryListService.deleteShiftInventoryList(shiftInventoryListId);
		logger.debug("取消移库单的数量:"+result);
		
		if (result > 0) {
			jsonObject.put("code", 0);
			jsonObject.put("message", "取消成功");
		}else {
			jsonObject.put("code", -1);
			jsonObject.put("message", "取消出现异常");
		}
		
		return jsonObject;
	}
	
	
	
	
	

}
