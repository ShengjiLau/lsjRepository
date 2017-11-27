package com.lcdt.customer.service.impl;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.StringWriter;

/**
 * Created by ss on 2017/11/24.
 */
@Service
public class InviteCustomerService {


	@Autowired
	private JavaMailSender mailSender;

	VelocityContext velocityContext = new VelocityContext();
	/**
	 * 发送邀请邮件
	 */
	public void sendInviteEmail(String inviteEmailTo) {


		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("mawei@lichendt.com");
		message.setTo("18754988272@163.com");
		message.setSubject("主题：简单邮件");
		message.setText(resolveInviteEmailContent());

		mailSender.send(message);
	}


	public String resolveInviteEmailContent(){
		StringWriter w = new StringWriter();
		velocityContext.put("username", "测试邮件");
		velocityContext.put("inviteUser", "被邀请的人");
		velocityContext.put("inviteCompanyName", "被邀请的公司名");
		velocityContext.put("inviteCompanyType", "被邀请的公司类型");
		velocityContext.put("inviteUrl", "http://www.baidu.com");

		Velocity.mergeTemplate("inviteEmail.vm", "UTF-8", velocityContext, w);
//		Velocity.evaluate()

//		velocityContext.internalPut("")

		return "这是一封测试邮件";
	}



}
