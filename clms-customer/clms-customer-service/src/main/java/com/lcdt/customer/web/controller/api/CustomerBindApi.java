package com.lcdt.customer.web.controller.api;

import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.customer.dao.CustomerInviteLogMapper;
import com.lcdt.customer.dao.CustomerMapper;
import com.lcdt.customer.model.Customer;
import com.lcdt.customer.model.CustomerInviteLog;
import com.lcdt.customer.service.CustomerService;
import com.lcdt.customer.service.impl.InviteCustomerService;
import com.lcdt.customer.service.impl.InviteLogService;
import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.model.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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

	@Autowired
	InviteCustomerService inviteCustomerService;

	@Autowired
	CustomerInviteLogMapper inviteLogMapper;

	@ApiOperation("邀请客户绑定")
	@RequestMapping(value = "/invitecustomer",method = RequestMethod.GET)
	public String inviteCustomer(Long customerId, String bindEmail) {
		Long companyId = SecurityInfoGetter.getCompanyId();
		User user = SecurityInfoGetter.getUser();
		Company company = SecurityInfoGetter.geUserCompRel().getCompany();
		return inviteCustomerService.buildInviteEmailContent(customerId,companyId,user,company);
	}


	@ApiOperation("绑定客户")
	@RequestMapping("/bind")
	@ResponseBody
	public ModelAndView bind(Long inviteId,Long customerId){
		Long companyId = SecurityInfoGetter.getCompanyId();

		CustomerInviteLog customerInviteLog = inviteLogService.selectByInviteId(inviteId);
		customerInviteLog.setIsValid(0);
		inviteLogMapper.updateByPrimaryKey(customerInviteLog);

		Customer customer = mapper.selectByPrimaryKey(customerId, companyId);
		customer.setCompanyId(companyId);
		customerService.customerUpdate(customer);
		ModelAndView successView = new ModelAndView("invite_success");
		return successView;
	}

	@ApiOperation("绑定客户页面")
	@RequestMapping("/customerlist")
	@PreAuthorize("hasAuthority('bindCustomer') or hasRole('ROLE_SYS_ADMIN')")
	public ModelAndView customer(@RequestParam(name = "a") Long inviteLogId,@RequestParam(name = "b") String token){
		//TODO 检查链接上的token 有效性
		CustomerInviteLog customerInviteLog = inviteLogService.selectByInviteId(inviteLogId);

		if (customerInviteLog.getIsValid() == 0 || !customerInviteLog.getInviteToken().equals(token)) {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("invite_not_valid");
			return modelAndView;
		}

		HashMap<Object, Object> map = new HashMap<>();
		Long companyId = SecurityInfoGetter.getCompanyId();
		map.put("companyId", companyId);
		PageInfo<Customer> pageInfo = customerService.customerList(map);
		List<Customer> list = pageInfo.getList();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("invite_customer");
		modelAndView.addObject("list", list);
		modelAndView.addObject("log", customerInviteLog);
		return modelAndView;
	}

	public boolean isTokenValid(String token){
		return true;
	}

}
