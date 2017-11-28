package com.lcdt.customer.service.impl;

import com.lcdt.customer.dao.CustomerInviteLogMapper;
import com.lcdt.customer.model.CustomerInviteLog;

/**
 * Created by ss on 2017/11/28.
 */
public class InviteLogService {

	public CustomerInviteLogMapper inviteLogDao;

	public CustomerInviteLog selectByInviteId(Long inviteId){
		CustomerInviteLog customerInviteLog = inviteLogDao.selectByPrimaryKey(inviteId);
		return customerInviteLog;
	}

}
