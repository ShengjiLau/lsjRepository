package com.lcdt.customer.web.controller.api;

import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.customer.dao.CustomerMapper;
import com.lcdt.customer.model.Customer;
import com.lcdt.customer.service.CustomerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ss on 2017/11/27.
 */
@RestController
@RequestMapping("/api/customer")
public class CustomerBindApi {

	@Autowired
	CustomerService customerService;

	@Autowired
	CustomerMapper mapper;

	@ApiOperation("绑定客户")
	@RequestMapping("/bind")
	public Customer bind(Long inviteId,Long customerId){
		Long companyId = SecurityInfoGetter.getCompanyId();
		Customer customer = mapper.selectByPrimaryKey(customerId);
		customer.setCompanyId(companyId);
		customerService.updateCustomer(customer);
		return customer;
	}

	@ApiOperation("绑定客户页面")
	@RequestMapping("/customerlist")
	public ModelAndView customer(String token){
		HashMap<Object, Object> map = new HashMap<>();
		PageInfo<Customer> pageInfo = customerService.customerList(map);
		List<Customer> list = pageInfo.getList();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("invite.html");
		modelAndView.addObject("list", list);
		return modelAndView;
	}

	public boolean isTokenValid(String token){
		return true;
	}




}
