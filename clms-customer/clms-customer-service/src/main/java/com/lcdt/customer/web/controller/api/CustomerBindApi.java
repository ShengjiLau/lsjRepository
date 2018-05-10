package com.lcdt.customer.web.controller.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.customer.Utils.CommonUtil;
import com.lcdt.customer.config.ConfigConstant;
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
import com.lcdt.userinfo.model.Group;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.userinfo.service.CompanyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import java.util.Date;
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

	@Autowired
	ConfigConstant configConstant;



	@RequestMapping("/testurl")
	public ModelAndView testLogoutUrl(){
		return new ModelAndView("error");
	}

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
	@Transactional
	public ModelAndView bind(Long inviteId,@RequestParam(required = false) Long customerId){
		Long companyId = SecurityInfoGetter.getCompanyId();

		CustomerInviteLog customerInviteLog = inviteLogService.selectByInviteId(inviteId);
		customerInviteLog.setIsValid(0);
		Long inviteCompanyId = customerInviteLog.getInviteCompanyId();
		Customer inviteCustomer = mapper.selectByPrimaryKey(customerInviteLog.getInviteCustomerId(), customerInviteLog.getInviteCompanyId());

		HashMap<String, Long> stringLongHashMap = new HashMap<>();
		stringLongHashMap.put("companyId", companyId);
		stringLongHashMap.put("bindCompId", inviteCompanyId);
		List<Customer> customers = mapper.selectByCondition(stringLongHashMap);
		User user = SecurityInfoGetter.getUser();
		if (customers != null && !customers.isEmpty()) {
			ModelAndView errorView = new ModelAndView("error");
			Customer customer = customers.get(0);
			errorView.addObject("username", user.getRealName());
			errorView.addObject("headimg", user.getPictureUrl());
			errorView.addObject("errortip", "客户管理里【" + customer.getCustomerName() + "】已绑定企业【" + customer.getBindCompany() + "】。");
			return errorView;
		}

		if (companyId.equals(inviteCompanyId)) {
			ModelAndView errorView = new ModelAndView("error");
			errorView.addObject("username", user.getRealName());
			errorView.addObject("headimg", user.getPictureUrl());
			String errorTipStr = "失败原因：同一企业内不能相互邀请绑定";
			errorView.addObject("errortip", errorTipStr);
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
			customer.setCreateDate(new Date());
			customer.setCreateId(user.getUserId());
			customer.setCreateName(user.getRealName());
			customer.setLinkDuty(company.getLinkDuty());
			customer.setLinkEmail(company.getLinkEmail());
			customer.setLinkMan(company.getLinkMan());
			customer.setLinkTel(company.getLinkTel());
			customer.setProvince(company.getProvince());
			customer.setCity(company.getCity());
			customer.setCounty(company.getCounty());

			customer.setFax(company.getFax());
			customer.setPostCode(company.getPostCode());
			customer.setTelNo(company.getTelNo());
			customer.setTelNo1(company.getTelNo1());
			customer.setBankNo(company.getBankNo());

			customer.setInvoiceTitle(company.getInvoiceTitle());
			customer.setRegistrationNo(company.getRegistrationNo());
			customer.setBankName(company.getBankName());

			String clientTypes = inviteCustomer.getClientTypes();

			customer.setClientTypes(CommonUtil.reverseCustomerTypesStr(clientTypes));

			List<Group> groups = SecurityInfoGetter.geUserCompRel().getGroups();
			if (groups != null && !groups.isEmpty()) {
				Group group = groups.get(0);
				customer.setGroupIds(String.valueOf(group.getGroupId()));
				customer.setGroupNames(group.getGroupName());
			}
			customerService.customerAdd(customer);
		}else{
			customer = mapper.selectByPrimaryKey(customerId, companyId);
		}

		//绑定被邀请的客户id

		if (customer.getBindCpid() != null) {
			ModelAndView errorView = new ModelAndView("error");
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

		Company company1 = companyService.selectById(companyId);
		inviteCustomer.setBindCpid(companyId);
		inviteCustomer.setBindCompany(company1.getFullName());
		customerService.updateCustomerBindCompId(inviteCustomer);
		inviteLogMapper.updateByPrimaryKey(customerInviteLog);
		ModelAndView successView = new ModelAndView("invite_success");
		successView.addObject("username", user.getRealName());
		successView.addObject("headimg", user.getPictureUrl());
		String successTipStr = "贵公司已成为【"+company.getFullName()+"】的合作伙伴。";
		successView.addObject("successtip", successTipStr);
		successView.addObject("host", configConstant.bindurlHost);
		return successView;
	}


	@ApiOperation("绑定客户页面")
	@RequestMapping(value = "/customerlist",method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('bindCustomer') or hasRole('ROLE_SYS_ADMIN')")
	public ModelAndView customer(@RequestParam(name = "a") Long inviteLogId,@RequestParam(name = "b") String token){
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
		List<Group> groups = SecurityInfoGetter.geUserCompRel().getGroups();

		StringBuilder sb = new StringBuilder();
		sb.append("(");
		for (int i = 0; i < groups.size() ;i++) {
			Group group = groups.get(i);
				//组ID
			sb.append(" find_in_set('" + group.getGroupId() + "',group_ids)");
			if(i != groups.size() - 1){
					sb.append(" or ");
				}
		}
		sb.append(")");
		map.put("groupIds", sb.toString());
		PageInfo<Customer> pageInfo = customerService.customerList(map);
		List<Customer> list = pageInfo.getList();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("invite_customer");
		modelAndView.addObject("list", list);
		modelAndView.addObject("log", customerInviteLog);
		modelAndView.addObject("currentCompanyName", userCompRel.getCompany().getFullName());
		modelAndView.addObject("username", userCompRel.getUser().getRealName());
		modelAndView.addObject("headimg", userCompRel.getUser().getPictureUrl());
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




}
