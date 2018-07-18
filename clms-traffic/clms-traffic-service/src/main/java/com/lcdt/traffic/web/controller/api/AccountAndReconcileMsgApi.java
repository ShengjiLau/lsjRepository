package com.lcdt.traffic.web.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.lcdt.traffic.model.Msg;
import com.lcdt.traffic.service.MsgService;
import com.lcdt.traffic.web.dto.MsgDto;
import com.lcdt.traffic.web.dto.PageBaseDto;
import com.lcdt.util.ResponseJsonUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author Sheng-ji Lau
 * @date 2018年3月30日下午3:06:16
 * @version
 */

@RestController
@RequestMapping("/account/msg")
@Api(value = "AccountAndReconcileMsgApi", description = "记账对账留言接口")
public class AccountAndReconcileMsgApi {
	
	@Autowired
	private MsgService msgService;
	
	@ApiOperation(value = "添加留言")
	@PostMapping("/add")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('account_msg_add')")
	public JSONObject addMsg(@Validated @RequestBody Msg msg, BindingResult bindingResult) {
		Map<String,String> map = validRequestBody(bindingResult);
		if (!map.isEmpty()) {
			String message = "验证信息未能通过！";
			return  ResponseJsonUtils.failedResponseJson(map, message);
		}
		
		int result = msgService.addMsg(msg);
		if (result > 0) {
			String message = "添加成功";
			return ResponseJsonUtils.successResponseJsonWithoutData(message);
		}else {
			String message = "添加留言失败";
			throw new RuntimeException(message);
		}
	}
	
	@ApiOperation(value = "修改留言")
	@PostMapping("/modify")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('account_msg_modify')")
	public JSONObject modifyMsg(@Validated @RequestBody Msg msg, BindingResult bindingResult) {
		Map<String,String> map = validRequestBody(bindingResult);
		if (!map.isEmpty()) {
			String message = "验证信息未能通过！";
			return  ResponseJsonUtils.failedResponseJson(map, message);
		}
	
		int result = msgService.updateMsg(msg);
		if (result > 0) {
			String message = "修改成功";
			return ResponseJsonUtils.successResponseJsonWithoutData(message);
		}else {
			String message = "修改留言失败";
			throw new RuntimeException(message);
		}
	}
	
	@ApiOperation(value = "设置删除")
	@PostMapping("/delete")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('account_msg_delete')")
	public JSONObject setIsDelete(@RequestParam @ApiParam(value = "留言id") Long msgId) {
		int result = msgService.cancelMsg(msgId);
		if (result > 0) {
			String message = "删除成功";
			return ResponseJsonUtils.successResponseJsonWithoutData(message);
		}else {
			String message = "删除留言失败";
			throw new RuntimeException(message);
		}
	}
	
	@ApiOperation(value = "依据条件查询留言")
	@GetMapping("/select")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('account_msg_get')")
	public JSONObject getMsg(MsgDto msgDto) {
		List<Msg> msgList = msgService.selectSomeMsg(msgDto);
		PageBaseDto<Msg> pageBaseDto = new PageBaseDto<Msg>(msgList, msgList.size());
		String message = "留言列表";
		return ResponseJsonUtils.successResponseJson(pageBaseDto, message);
	}
	
	
	/**
	 * 验证传入信息
	 * @param bindingResult
	 * @return
	 */
	public Map<String,String> validRequestBody(BindingResult bindingResult) {
		Map<String,String> map = new HashMap<String, String>();
		if (bindingResult.hasErrors()) {
			for (FieldError fieldError: bindingResult.getFieldErrors()) {
				map.put(fieldError.getField(),fieldError.getDefaultMessage());
			}
		}
		return map;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
