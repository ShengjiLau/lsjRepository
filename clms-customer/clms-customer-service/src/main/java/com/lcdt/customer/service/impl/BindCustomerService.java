package com.lcdt.customer.service.impl;

import com.lcdt.customer.model.Customer;
import com.lcdt.customer.model.CustomerInviteLog;
import com.lcdt.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ss on 2017/11/28.
 */
public class BindCustomerService {

	@Autowired
	InviteLogService inviteLogService;

	@Autowired
	CustomerService customerService;

	@Transactional(rollbackFor = Exception.class)
	public Customer bindCustomer(Long inviteLogId,Long customerId,Long beInvitedCompanyId,Integer[] bindType){
		CustomerInviteLog customerInviteLog = inviteLogService.selectByInviteId(inviteLogId);
		Long inviteCompanyId = customerInviteLog.getInviteCompanyId();
		//绑定被邀请公司的客户 和 邀请公司
		Customer customer = customerService.selectByCustomerId(customerId,inviteCompanyId);
		customer.setCompanyId(inviteCompanyId);
		customerService.updateCustomer(customer);
		//绑定邀请公司的客户 和 被邀请公司
		Long inviteCustomerId = customerInviteLog.getInviteCustomerId();
		Customer inviteCustomer = customerService.selectByCustomerId(inviteCustomerId, inviteCustomerId);
		inviteCustomer.setCompanyId(beInvitedCompanyId);
		return customer;
	}
}







