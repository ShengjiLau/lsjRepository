package com.lcdt.customer.service.impl;

import com.lcdt.customer.dao.CustomerInviteLogMapper;
import com.lcdt.customer.model.CustomerInviteLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ss on 2017/11/28.
 */
@Service
public class InviteLogService {

	@Autowired
	public CustomerInviteLogMapper inviteLogDao;

	public CustomerInviteLog selectByInviteId(Long inviteId){
		CustomerInviteLog customerInviteLog = inviteLogDao.selectByPrimaryKey(inviteId);
		return customerInviteLog;
	}

}
