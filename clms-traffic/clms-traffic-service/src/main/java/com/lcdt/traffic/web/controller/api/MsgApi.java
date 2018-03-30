package com.lcdt.traffic.web.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.lcdt.traffic.model.Msg;
import com.lcdt.traffic.service.MsgService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Sheng-ji Lau
 * @date 2018年3月30日下午3:06:16
 * @version
 */
@RestController
@RequestMapping("/account/msg")
@Api(value="MsgApi",description="留言接口")
public class MsgApi {
	
	@Autowired
	private MsgService msgService;
	
	@ApiOperation(value="添加留言")
	@PostMapping("/add")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('account_msg_add')")
	public JSONObject addMsg(@Validated @RequestBody Msg msg,BindingResult bindingResult) {
		JSONObject jsonObject = new JSONObject();
		
		
		
		
		
		int result =msgService.addMsg(msg);
		
		
		
		
		return jsonObject;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
