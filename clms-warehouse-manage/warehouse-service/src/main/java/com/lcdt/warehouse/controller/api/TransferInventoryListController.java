package com.lcdt.warehouse.controller.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.lcdt.util.ResponseJsonUtils;
import com.lcdt.warehouse.controller.exception.ShiftInventoryException;
import com.lcdt.warehouse.dto.PageBaseDto;
import com.lcdt.warehouse.dto.TransferInventoryListDTO;
import com.lcdt.warehouse.dto.TransferListDTO;
import com.lcdt.warehouse.service.TransferInventoryListService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Sheng-ji Lau
 * @date 2018年8月1日
 * @version
 * @Description: TODO 
 */
@RestController
@RequestMapping("/api/transfer")
@Api(description = "库存转换单api")
@Slf4j
public class TransferInventoryListController {
	
	@Autowired
	private TransferInventoryListService transferInventoryListService;
	
	@PostMapping("/add")
	@ApiOperation(value = "创建库存转换单")
	@PreAuthorize(value = "hasRole('ROLE_SYS_ADMIN') or hasAuthority('transfer_inventory_add')")
	public JSONObject insertTransferInventoryList(TransferInventoryListDTO transferInventoryListDTO) {
		int result = transferInventoryListService.insertTransferInventoryList(transferInventoryListDTO);
		String message = null;
		if (result > 0) {
			message = "创建成功";
			return ResponseJsonUtils.successResponseJsonWithoutData(message);
		}else {
			message = "创建库存转换单时出现异常！";
			throw new ShiftInventoryException(message);
		}
	}
	
	
	@PostMapping("/complete")
	@ApiOperation(value = "完成库存转换单")
	@PreAuthorize(value = "hasRole('ROLE_SYS_ADMIN') or hasAuthority('transfer_inventory_complete')")
	public JSONObject complementTransferInventoryList(TransferInventoryListDTO transferInventoryListDTO) {
		int result = transferInventoryListService.completeTransferInventoryList(transferInventoryListDTO);
		String message = null;
		if (result > 0) {
			message = "操作成功";
			return ResponseJsonUtils.successResponseJsonWithoutData(message);
		}else {
			message = "完成库存转换单时出现异常！";
			throw new ShiftInventoryException(message);
		}
	}
	
	
	@PostMapping("/list")
	@ApiOperation(value = "查询库存转换单列表")
	@PreAuthorize(value = "hasRole('ROLE_SYS_ADMIN') or hasAuthority('transfer_inventory_get')")
	public JSONObject getTransferInventoryLists(TransferListDTO transferListDTO) {
		PageBaseDto<TransferInventoryListDTO> pageBaseDto = transferInventoryListService.getTransferInventoryListDTOList(transferListDTO);
		String message = "库存转换单列表";
		return ResponseJsonUtils.successResponseJson(pageBaseDto, message);
	}
	
	
	@PostMapping("/detail")
	@ApiOperation(value = "查询库存转换单详情")
	@PreAuthorize(value = "hasRole('ROLE_SYS_ADMIN') or hasAuthority('transfer_inventory_get')")
	public JSONObject getTransferInventoryDetail(Long transferId) {
		TransferInventoryListDTO transferInventoryListDTO = transferInventoryListService.getTransferInventoryListDTODetail(transferId);
		String message = null;
		if (null != transferInventoryListDTO) {
			message = "库存转换单详情";
			return ResponseJsonUtils.successResponseJson(transferInventoryListDTO, message);
		}else {
			message = "查询详情失败！";
			throw new RuntimeException(message);
		}
	}
	
	
	@PostMapping("/remove")
	@ApiOperation(value = "取消库存转换单")
	@PreAuthorize(value = "hasRole('ROLE_SYS_ADMIN') or hasAuthority('transfer_inventory_remove')")
	public JSONObject cancelTransferInventoryList(Long transferId) {
		int result = transferInventoryListService.updateTransferStatus(transferId);
		String message = null;
		if (result > 0) {
			message = "操作成功";
			return ResponseJsonUtils.successResponseJsonWithoutData(message);
		}else {
			message = "取消库存转换单时出现异常！";
			throw new ShiftInventoryException(message);
		}
	}
	
	

}
