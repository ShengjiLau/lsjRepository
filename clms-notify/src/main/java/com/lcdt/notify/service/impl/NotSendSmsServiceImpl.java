package com.lcdt.notify.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lcdt.notify.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ss on 2017/10/23.
 */
@Service(version = "customer")
public class NotSendSmsServiceImpl implements SmsService{

	private Logger logger = LoggerFactory.getLogger(NotSendSmsServiceImpl.class);

	@Override
	public boolean sendSms(String[] phones, String signature, String message) {
		logger.info("验证码 "+ message);
		return true;
	}

	@Override
	public String findSmsBalance() {
		return null;
	}
}
