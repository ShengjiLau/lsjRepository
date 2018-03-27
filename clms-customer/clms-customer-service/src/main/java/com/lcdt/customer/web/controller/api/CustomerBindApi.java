package com.lcdt.customer.web.controller.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.customer.dao.CustomerInviteLogMapper;
import com.lcdt.customer.dao.CustomerMapper;
import com.lcdt.customer.exception.CustomerNotBindException;
import com.lcdt.customer.model.Customer;
import com.lcdt.customer.model.CustomerInviteLog;
import com.lcdt.customer.service.CustomerService;
import com.lcdt.customer.service.impl.InviteCustomerService;
import com.lcdt.customer.service.impl.InviteLogService;
import com.lcdt.customer.web.dto.InviteDto;
import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.userinfo.service.CompanyService;
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
	@PreAuthorize("hasAnyAuthority('customer_bind') or hasRole('ROLE_SYS_ADMIN')")
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

	@Reference
	CompanyService companyService;

	//customerid 被绑定的客户选择的客户
	@ApiOperation("绑定客户")
	@RequestMapping(value = "/bind")
	@ResponseBody
	public ModelAndView bind(Long inviteId,Long customerId){
		Long companyId = SecurityInfoGetter.getCompanyId();

		CustomerInviteLog customerInviteLog = inviteLogService.selectByInviteId(inviteId);
		customerInviteLog.setIsValid(0);
		Long inviteCompanyId = customerInviteLog.getInviteCompanyId();

		HashMap<String, Long> stringLongHashMap = new HashMap<>();
		stringLongHashMap.put("companyId", companyId);
		stringLongHashMap.put("bindCompId", inviteCompanyId);
		List<Customer> customers = mapper.selectByCondition(stringLongHashMap);

		if (customers != null && !customers.isEmpty()) {
			ModelAndView error = new ModelAndView("error");
			Customer customer = customers.get(0);
			error.addObject("error", "客户管理里 " + customer.getCustomerName() + "已绑定" + customer.getBindCompany());
			return error;
		}

		User user = SecurityInfoGetter.getUser();

		if (companyId.equals(inviteCompanyId)) {
			ModelAndView errorView = new ModelAndView("/error");
			errorView.addObject("username", user.getRealName());
			errorView.addObject("headimg", user.getPictureUrl());
			String successTipStr = "失败原因：同一企业内不能相互邀请绑定";
			errorView.addObject("errortip", successTipStr);
			return errorView;
		}
		Company company = companyService.selectById(inviteCompanyId);
		Customer customer;
		if (customerId == null) {
			customer = new Customer();
			customer.setCustomerName(company.getFullName());
			customer.setShortName(company.getShortName());
			customer.setDetailAddress(company.getDetailAddress());
			customer.setRegistrationAddress(company.getRegistrationAddress());
			customer.setCompanyId(companyId);
			mapper.insert(customer);
		}else{
			customer = mapper.selectByPrimaryKey(customerId, companyId);
		}


		//绑定被邀请的客户id

		if (customer.getBindCpid() != null) {
			ModelAndView errorView = new ModelAndView("/error");
			errorView.addObject("username", user.getRealName());
			errorView.addObject("headimg", user.getPictureUrl());
			String successTipStr = "失败原因：客户已绑定";
			errorView.addObject("errortip", successTipStr);
			return errorView;
		}
		//帮邀请的绑定公司id是 邀请人的公司id
		customer.setBindCpid(inviteCompanyId);
		customer.setBindCompany(company.getFullName());
		customerService.customerUpdate(customer);
		//绑定邀请人的公司id
		Customer customer1 = mapper.selectByPrimaryKey(customerInviteLog.getInviteCustomerId(), customerInviteLog.getInviteCompanyId());
		Company company1 = companyService.selectById(companyId);
		customer1.setBindCpid(companyId);
		customer1.setBindCompany(company1.getFullName());
		customerService.updateCustomerBindCompId(customer1);
		inviteLogMapper.updateByPrimaryKey(customerInviteLog);
		ModelAndView successView = new ModelAndView("invite_success");
		successView.addObject("username", user.getRealName());
		successView.addObject("headimg", user.getPictureUrl());
		String s = inviteCustomerService.clientTypeToString(customer.getClientTypes());
		String successTipStr = "贵公司已成为【"+company.getFullName()+"】的合作伙伴";
		successView.addObject("successtip", successTipStr);
		successView.addObject("host", "http://39.107.12.215:88");
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
	@PreAuthorize("hasAnyAuthority('customer_bind') or hasRole('ROLE_SYS_ADMIN')")
	public Customer unBindCustomer(Long customerId){
		Long companyId = SecurityInfoGetter.getCompanyId();
		Customer customer = null;
		try {
			customer = customerService.unBindCustomer(customerId, companyId);
		} catch (CustomerNotBindException e) {
			e.printStackTrace();
		}
		return customer;
	}

	public boolean isTokenValid(String token){
		return true;
	}

}
