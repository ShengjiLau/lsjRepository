package com.lcdt.customer.web.controller.api;

import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.customer.dao.CustomerMapper;
import com.lcdt.customer.model.Customer;
import com.lcdt.customer.model.CustomerInviteLog;
import com.lcdt.customer.service.CustomerService;
import com.lcdt.customer.service.impl.InviteLogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@Autowired
	InviteLogService inviteLogService;

	@ApiOperation("绑定客户")
	@RequestMapping("/bind")
	public Customer bind(Long inviteId,Long customerId){
		Long companyId = SecurityInfoGetter.getCompanyId();
		Customer customer = mapper.selectByPrimaryKey(customerId, companyId);
		customer.setCompanyId(companyId);
		customerService.customerUpdate(customer);
		return customer;
	}

	@ApiOperation("绑定客户页面")
	@RequestMapping("/customerlist")
	@PreAuthorize("hasAuthority('bindCustomer') or hasRole('sys_admin_role')")
	public ModelAndView customer(@RequestParam(name = "a") Long inviteLogId,@RequestParam(name = "b") String token){
		//TODO 检查链接上的token 有效性
		CustomerInviteLog customerInviteLog = inviteLogService.selectByInviteId(inviteLogId);
		if (customerInviteLog.getIsValid() == 0) {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("invite_not_valid");
			return modelAndView;
		}

		HashMap<Object, Object> map = new HashMap<>();
		PageInfo<Customer> pageInfo = customerService.customerList(map);
		List<Customer> list = pageInfo.getList();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("invite.html");
		modelAndView.addObject("list", list);
		modelAndView.addObject("log", customerInviteLog);
		return modelAndView;
	}

	public boolean isTokenValid(String token){
		return true;
	}

}
