package com.lcdt.userinfo.web.controller.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.userinfo.service.UserService;
import com.lcdt.userinfo.service.impl.EmployeeServiceImpl;
import com.lcdt.userinfo.web.dto.CreateEmployeeAccountDto;
import com.lcdt.userinfo.web.dto.PageResultDto;
import com.lcdt.userinfo.web.dto.SearchEmployeeDto;
import com.lcdt.userinfo.web.dto.UpdateEmployeeAccountDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ss on 2017/11/14.
 */
@RestController
@Api(value = "员工接口", description = "员工相关接口")
@RequestMapping("/api/employee")
public class EmployeeApi {

	@Autowired
	UserService userService;


	public static JSONObject successMessage ;
	public static JSONObject failMessage;

	static {
		successMessage = new JSONObject();
		successMessage.put("code", 0);
		successMessage.put("message", "请求成功");

		failMessage = new JSONObject();
		failMessage.put("code", -1);
		failMessage.put("message", "请求异常");
	}

	@Autowired
	EmployeeServiceImpl employeeService;

	@ApiOperation("添加员工接口")
	@RequestMapping(value = "/addemployee", method = RequestMethod.POST)
	public String addEmployeeAccount(@Validated CreateEmployeeAccountDto dto, HttpServletRequest request) {
		String groups = request.getParameter("jsonGroups");
		String roles = request.getParameter("jsonRoles");
		List<Long> jsonGroups = JSONArray.parseArray(groups, Long.class);
		List<Long> jsonRoles = JSONArray.parseArray(roles, Long.class);

		dto.setGroups(jsonGroups);
		dto.setRoles(jsonRoles);

		boolean b = employeeService.addEmployee(dto);
		if (b) {
			return successMessage.toString();
		}else {
			return failMessage.toString();
		}
	}

	@ApiOperation("所有员工列表")
	@RequestMapping(value = "/employeelist", method = RequestMethod.POST)
	public PageResultDto employeeList(@ApiParam(required = true) Integer pageNo, @ApiParam(required = true) Integer pageSize,SearchEmployeeDto dto) {
		Long companyId = SecurityInfoGetter.getCompanyId();
		dto.setCompanyId(companyId);
		List<UserCompRel> userCompRels = employeeService.queryAllEmployee(dto);
		PageResultDto<UserCompRel> userCompRelPageResultDto = new PageResultDto<>(userCompRels);
		return userCompRelPageResultDto;
	}


	@ApiOperation("更新员工接口")
	@RequestMapping(value = "/updateemployee",method = RequestMethod.POST)
	public UserCompRel updateEmployee(UpdateEmployeeAccountDto dto) {
		UserCompRel userCompRel = employeeService.updateEmployee(dto);
		return userCompRel;
	}

	@ApiOperation("获取手机号的用户信息")
	@RequestMapping(value = "/queryphone",method = RequestMethod.POST)
	public User queryUserPhone(String phone){
		User user = userService.selectUserByPhone(phone);
		return user;
	}



}
