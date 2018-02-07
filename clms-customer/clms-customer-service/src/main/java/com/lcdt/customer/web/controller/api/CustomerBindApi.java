package com.lcdt.customer.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.customer.dao.CustomerInviteLogMapper;
import com.lcdt.customer.dao.CustomerMapper;
import com.lcdt.customer.model.Customer;
import com.lcdt.customer.model.CustomerInviteLog;
import com.lcdt.customer.service.CustomerService;
import com.lcdt.customer.service.impl.InviteCustomerService;
import com.lcdt.customer.service.impl.InviteLogService;
import com.lcdt.customer.web.dto.InviteDto;
import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
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

	@ApiOperation("获取邀请邮件内容")
	@RequestMapping(value = "/invitecustomer",method = RequestMethod.POST)
	@ResponseBody
	public InviteDto inviteCustomer(Long customerId) {
		Long companyId = SecurityInfoGetter.getCompanyId();
		User user = SecurityInfoGetter.getUser();
		Company company = SecurityInfoGetter.geUserCompRel().getCompany();
		return inviteCustomerService.buildInviteEmailContent(customerId,companyId,user,company);
	}



	@ApiOperation("发送邀请邮件")
	@RequestMapping(value = "/sendemail",method = RequestMethod.POST)
	public String inviteCustomer(String bindEmail,Long inviteId) {
		Long companyId = SecurityInfoGetter.getCompanyId();
		User user = SecurityInfoGetter.getUser();
		Company company = SecurityInfoGetter.geUserCompRel().getCompany();
		CustomerInviteLog customerInviteLog = inviteLogMapper.selectByPrimaryKey(inviteId);
		Customer customer = mapper.selectByPrimaryKey(customerInviteLog.getInviteCustomerId(), companyId);
		try {
			inviteCustomerService.sendInviteEmail(bindEmail,customerInviteLog,customer,companyId,user,company);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", 0);
		jsonObject.put("message", "发送成功");
		return jsonObject.toString();
	}



	//customerid 被绑定的客户选择的客户
	@ApiOperation("绑定客户")
	@RequestMapping(value = "/bind")
	@ResponseBody
	public ModelAndView bind(Long inviteId,Long customerId){
		Long companyId = SecurityInfoGetter.getCompanyId();

		CustomerInviteLog customerInviteLog = inviteLogService.selectByInviteId(inviteId);
		customerInviteLog.setIsValid(0);

		inviteLogMapper.updateByPrimaryKey(customerInviteLog);

		Long inviteCompanyId = customerInviteLog.getInviteCompanyId();

		//绑定被邀请的客户id
		Customer customer = mapper.selectByPrimaryKey(customerId, companyId);
		if (customer.getBindCpid() != null) {
			throw new RuntimeException("客户已绑定");
		}
		//帮邀请的绑定公司id是 邀请人的公司id
		customer.setBindCpid(inviteCompanyId);
		customerService.customerUpdate(customer);

		//绑定邀请人的公司id
		Customer customer1 = mapper.selectByPrimaryKey(customerInviteLog.getInviteCustomerId(), customerInviteLog.getInviteCompanyId());
		customer1.setBindCpid(companyId);
		customerService.updateCustomerBindCompId(customer1);

		User user = SecurityInfoGetter.getUser();

		ModelAndView successView = new ModelAndView("invite_success");
		successView.addObject("username", user.getRealName());
		return successView;
	}


	@ApiOperation("绑定客户页面")
	@RequestMapping(value = "/customerlist",method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('bindCustomer') or hasRole('ROLE_SYS_ADMIN')")
	public ModelAndView customer(@RequestParam(name = "a") Long inviteLogId,@RequestParam(name = "b") String token){
		//TODO 检查链接上的token 有效性
		CustomerInviteLog customerInviteLog = inviteLogService.selectByInviteId(inviteLogId);

		if (customerInviteLog.getIsValid() != null) {
			if (customerInviteLog.getIsValid() == 0 || !customerInviteLog.getInviteToken().equals(token)) {
				ModelAndView modelAndView = new ModelAndView();
				modelAndView.setViewName("invite_not_valid");
				return modelAndView;
			}
		}

		HashMap<Object, Object> map = new HashMap<>();
		Long companyId = SecurityInfoGetter.getCompanyId();
		UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
		map.put("companyId", companyId);
		map.put("notbind", true);
		PageInfo<Customer> pageInfo = customerService.customerList(map);
		List<Customer> list = pageInfo.getList();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("invite_customer");
		modelAndView.addObject("list", list);
		modelAndView.addObject("log", customerInviteLog);
		modelAndView.addObject("currentCompanyName", userCompRel.getCompany().getFullName());
		modelAndView.addObject("username", userCompRel.getUser().getRealName());
		return modelAndView;
	}

	@RequestMapping("/unbindCustomer")
	@ApiOperation("解绑客户")
	public Customer unBindCustomer(Long customerId){
		Long companyId = SecurityInfoGetter.getCompanyId();
		Customer customer = customerService.unBindCustomer(customerId, companyId);
		return customer;
	}

	public boolean isTokenValid(String token){
		return true;
	}

}
