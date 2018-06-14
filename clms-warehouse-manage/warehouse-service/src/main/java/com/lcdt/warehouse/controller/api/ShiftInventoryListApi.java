package com.lcdt.warehouse.controller.api;

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
import com.lcdt.warehouse.dto.PageBaseDto;
import com.lcdt.warehouse.dto.ShiftInventoryListDTO;
import com.lcdt.warehouse.service.ShiftInventoryListService;
import com.lcdt.warehouse.vo.ResponseCodeVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Sheng-ji Lau
 * @date 2018年5月10日
 * @version
 * @Description: TODO 
 */

@RestController
@RequestMapping("/api/shiftInventory")
@Api(description="移库单接口")
@Slf4j
public class ShiftInventoryListApi {
	
	@Autowired
	private ShiftInventoryListService shiftInventoryListService;
	
	//private Logger logger = LoggerFactory.getLogger(ShiftInventoryListApi.class);
	
	
	@PostMapping("/add")
	@ApiOperation(value = "创建移库单")
	@PreAuthorize(value = "hasRole('ROLE_SYS_ADMIN') or hasAuthority('shift_inventory_add')")
	public JSONObject insertInventoryList(@RequestBody ShiftInventoryListDTO shiftInventoryListDTO) {
		String message = validateParamInfo(shiftInventoryListDTO);
		if (null != message) {
			return failedResponseJson(message);
		}
		
		int result = shiftInventoryListService.insertShiftInventoryList(shiftInventoryListDTO);	
		log.debug("创建移库单的数量:"+result);
		
		if (result > 0) {
			return successResponseJson(null, "创建成功");
		}else {
			throw new RuntimeException("创建移库单时出现异常");
		}
	}
	
	
	@PostMapping("/complete")
	@ApiOperation(value = "完成移库单")
	@PreAuthorize(value = "hasRole('ROLE_SYS_ADMIN') or hasAuthority('shift_inventory_complete')")
	public JSONObject completeInventoryList(@RequestBody ShiftInventoryListDTO shiftInventoryListDTO) {
		String message = validateComplete(shiftInventoryListDTO);
		if (null != message) {
			return failedResponseJson(message);
		}
		
		int result = shiftInventoryListService.completeShiftInventoryList(shiftInventoryListDTO);
		log.debug("完成移库单的数量:"+result);
		
		if (result > 0) {
			return successResponseJson(null, "完成成功");
		}else {
			throw new RuntimeException("完成移库单时出现异常");
		}
	}
	
	
	@GetMapping("/list")
	@ApiOperation(value = "查询移库单列表")
	@PreAuthorize(value = "hasRole('ROLE_SYS_ADMIN') or hasAuthority('shift_inventory_get')")
	public JSONObject getShiftInventoryList(ShiftInventoryListDTO shiftInventoryListDTO) {
		
		PageBaseDto<ShiftInventoryListDTO> pageBaseDto = new PageBaseDto<ShiftInventoryListDTO>();
		
		PageInfo<ShiftInventoryListDTO> pageInfo = shiftInventoryListService.getShiftInventoryList(shiftInventoryListDTO);
		pageBaseDto.setList(pageInfo.getList());
		pageBaseDto.setTotal(pageInfo.getTotal());
		
		return successResponseJson(pageBaseDto, "移库单列表");
		
	}
	
	
	@GetMapping("/details")
	@ApiOperation(value = "查询移库单详情")
	@PreAuthorize(value = "hasRole('ROLE_SYS_ADMIN') or hasAuthority('shift_inventory_get')")
	public JSONObject getOneShiftInventoryDetails(@ApiParam(value="移库单id",required=true) @RequestParam Long shiftId) {
		
		ShiftInventoryListDTO shiftInventoryListDTO = shiftInventoryListService.getShiftInventoryListDetails(shiftId);
		
		if (null != shiftInventoryListDTO) {
			return successResponseJson(shiftInventoryListDTO, "移库单详情");
		}else {
		    return failedResponseJson("查询失败");
		}
		
		
	}
	
	
	@PostMapping("/remove")
	@ApiOperation(value = "取消移库单")
	@PreAuthorize(value = "hasRole('ROLE_SYS_ADMIN') or hasAuthority('shift_inventory_remove')")
	public JSONObject deleteShiftInventoryList(Long shiftInventoryListId) {
		int result = shiftInventoryListService.deleteShiftInventoryList(shiftInventoryListId);
		log.debug("取消移库单的数量:"+result);
		
		if (result > 0) {
			return successResponseJson(null, "取消成功!");
		}else {
			return failedResponseJson("取消失败！");
		}
		
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
	
	
	/**
	 * 访问成功返回值格式
	 * @param object
	 * @param message
	 * @return
	 */
	private <T> JSONObject successResponseJson(T object,String message) {	
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ResponseCodeVO.CODE, ResponseCodeVO.SUCCESS_CODE);
		jsonObject.put(ResponseCodeVO.MESSAGE, message);
		if (null != object) {
			jsonObject.put(ResponseCodeVO.DATA, object);
		}	
		return jsonObject;
	}
	
	
	/**
	 * 失败访问返回值格式
	 * @param object
	 * @param message
	 * @return
	 */
	private <T> JSONObject failedResponseJson(String message) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ResponseCodeVO.CODE, ResponseCodeVO.FAILED_CODE);
		jsonObject.put(ResponseCodeVO.MESSAGE, message);
		
		return jsonObject;
	}
	
	
	
	
	

}
