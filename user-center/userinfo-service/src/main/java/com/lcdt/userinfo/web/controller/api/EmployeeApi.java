package com.lcdt.userinfo.web.controller.api;

import com.lcdt.userinfo.service.impl.EmployeeServiceImpl;
import com.lcdt.userinfo.web.dto.CreateEmployeeAccountDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ss on 2017/11/14.
 */
@RestController
@Api(value = "员工接口", description = "员工相关接口")
@RequestMapping("/api/employee")
public class EmployeeApi {

	@Autowired
	EmployeeServiceImpl employeeService;


	@ApiOperation("添加员工接口")
	@RequestMapping(value = "/addemployee", method = RequestMethod.POST)
	public void addEmployeeAccount(@Validated CreateEmployeeAccountDto dto) {
		employeeService.addEmployee(dto);
	}


}
