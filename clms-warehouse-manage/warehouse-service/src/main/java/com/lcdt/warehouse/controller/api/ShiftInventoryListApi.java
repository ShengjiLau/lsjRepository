package com.lcdt.warehouse.controller.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.util.ResponseJsonUtils;
import com.lcdt.warehouse.controller.exception.ShiftInventoryException;
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
@Api(description="移库单接口")
//@Slf4j
public class ShiftInventoryListApi {
	
	@Autowired
	private ShiftInventoryListService shiftInventoryListService;
	
	private Logger log = LoggerFactory.getLogger(ShiftInventoryListApi.class);
	
	
	@PostMapping("/add")
	@ApiOperation(value = "创建移库单")
	@PreAuthorize(value = "hasRole('ROLE_SYS_ADMIN') or hasAuthority('shift_inventory_add')")
	public JSONObject insertInventoryList(@RequestBody ShiftInventoryListDTO shiftInventoryListDTO) {
		String validatedMessage = validateParamInfo(shiftInventoryListDTO);
		if (null != validatedMessage) {
			return ResponseJsonUtils.failedResponseJsonWithoutData(validatedMessage);
		}
		
		int result = shiftInventoryListService.insertShiftInventoryList(shiftInventoryListDTO);	
		log.debug("创建移库单的数量:"+result);
		
		if (result > 0) {
			String message = "创建成功";
			return ResponseJsonUtils.successResponseJsonWithoutData(message);
		}else {
			String message = "创建移库单时出现异常";
			throw new ShiftInventoryException(message);
		}
	}
	
	
	@PostMapping("/complete")
	@ApiOperation(value = "完成移库单")
	@PreAuthorize(value = "hasRole('ROLE_SYS_ADMIN') or hasAuthority('shift_inventory_complete')")
	public JSONObject completeInventoryList(@RequestBody ShiftInventoryListDTO shiftInventoryListDTO) {
		String validatedMessage = validateComplete(shiftInventoryListDTO);
		if (null != validatedMessage) {
			return ResponseJsonUtils.failedResponseJsonWithoutData(validatedMessage);
		}
		
		int result = shiftInventoryListService.completeShiftInventoryList(shiftInventoryListDTO);
		log.debug("完成移库单的数量:"+result);
		
		if (result > 0) {
			String message = "完成成功";
			return ResponseJsonUtils.successResponseJsonWithoutData(message);
		}else {
			String message = "完成移库单时出现异常";
			throw new ShiftInventoryException(message);
		}
	}
	
	
	@GetMapping("/list")
	@ApiOperation(value = "查询移库单列表")
	@PreAuthorize(value = "hasRole('ROLE_SYS_ADMIN') or hasAuthority('shift_inventory_get')")
	public JSONObject getShiftInventoryList(ShiftInventoryListDTO shiftInventoryListDTO) {
		PageBaseDto<ShiftInventoryListDTO> pageBaseDto = shiftInventoryListService.getShiftInventoryList(shiftInventoryListDTO);
		String message = "移库单列表";
		return ResponseJsonUtils.successResponseJson(pageBaseDto, message);
	}
	
	
	@GetMapping("/details")
	@ApiOperation(value = "查询移库单详情")
	@PreAuthorize(value = "hasRole('ROLE_SYS_ADMIN') or hasAuthority('shift_inventory_get')")
	public JSONObject getOneShiftInventoryDetails(@ApiParam(value="移库单id",required=true) @RequestParam Long shiftId) {
		ShiftInventoryListDTO shiftInventoryListDTO = shiftInventoryListService.getShiftInventoryListDetails(shiftId);
		
		if (null != shiftInventoryListDTO) {
			String message = "移库单详情";
			return ResponseJsonUtils.successResponseJson(shiftInventoryListDTO, message);
		}else {
			String message = "查询失败";
			return ResponseJsonUtils.failedResponseJsonWithoutData(message);
		}
		
		
	}
	
	
	@PostMapping("/remove")
	@ApiOperation(value = "取消移库单")
	@PreAuthorize(value = "hasRole('ROLE_SYS_ADMIN') or hasAuthority('shift_inventory_remove')")
	public JSONObject deleteShiftInventoryList(Long shiftInventoryListId) {
		int result = shiftInventoryListService.deleteShiftInventoryList(shiftInventoryListId);
		log.debug("取消移库单的数量:"+result);
		
		if (result > 0) {
			String message = "取消成功!";
			return ResponseJsonUtils.successResponseJsonWithoutData(message);
		}else {
			String message = "取消失败!";
			return ResponseJsonUtils.failedResponseJsonWithoutData(message);
		}
		
	}
	
	
	@GetMapping("/inventoryList")
	@ApiOperation(value = "查询移库单列表-无权限")
	public JSONObject getShiftInventoryListWithoutAuthorize(ShiftInventoryListDTO shiftInventoryListDTO) {
		PageBaseDto<ShiftInventoryListDTO> pageBaseDto = shiftInventoryListService.getShiftInventoryList(shiftInventoryListDTO);
		String message = "移库单列表";
		return ResponseJsonUtils.successResponseJson(pageBaseDto, message);
	}


	/**
	 * 新建时验证传入的移库单信息
	 * @param shiftInventoryListDTO
	 * @return
	 */
	public String validateParamInfo(ShiftInventoryListDTO shiftInventoryListDTO) {
		if (null == shiftInventoryListDTO.getShiftGoodsListDTOList() || 0 == shiftInventoryListDTO.getShiftGoodsListDTOList().size()) {
			return "至少添加一条移库信息！";	
		}
		if (null == shiftInventoryListDTO.getGroupName() || "".equals(shiftInventoryListDTO.getGroupName())) {
			return "业务组名称不可为空！";
		}
		if (null == shiftInventoryListDTO.getCustomerName() || "".equals(shiftInventoryListDTO.getCustomerName())) {
			return "客户名称不可为空！";
		}
		if (null == shiftInventoryListDTO.getWarehouseName() || "".equals(shiftInventoryListDTO.getWarehouseName())) {
			return  "仓库名称不可为空！";
		}
		if (null == shiftInventoryListDTO.getShiftType()) {
			return "移库类型不可为空！";
		}
		return null;
	}
	
	
	
	/**
	 * 新建时验证传入的移库单信息
	 * @param shiftInventoryListDTO
	 * @return
	 */
	public String validateComplete(ShiftInventoryListDTO shiftInventoryListDTO) {	
		if (null == shiftInventoryListDTO.getShiftGoodsListDTOList() || 0 == shiftInventoryListDTO.getShiftGoodsListDTOList().size()) {
			return "至少添加一条移库信息！";
		}
		if (null == shiftInventoryListDTO.getShiftUser()) {
			return "移库人不可为空！";
		}
		if (null == shiftInventoryListDTO.getShiftTime()) {
			return "移库时间不可为空！";
		}
		return null;
	}
	






}
