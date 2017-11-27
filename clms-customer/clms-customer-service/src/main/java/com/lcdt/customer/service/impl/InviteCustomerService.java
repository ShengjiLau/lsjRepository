package com.lcdt.customer.service.impl;

import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.model.User;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.Properties;

/**
 * Created by ss on 2017/11/24.
 */
@Service
public class InviteCustomerService {

	@Autowired
	private JavaMailSender mailSender;

	VelocityContext velocityContext = new VelocityContext();

	public String templateName = "/inviteEmail.vm";

	public String inviteEmailSubject = "主题：邀请您使用大驼队协同物流运输系统";

	/**
	 * 发送邀请邮件
	 * @param inviteEmailTo
	 */
	public void sendInviteEmail(String inviteEmailTo, String whoInvitedUserName, User whoBeInvited, Company companyBeInvited,String beInvitedCompanyTypeName) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("mawei@lichendt.com");
		message.setTo(whoBeInvited.getEmail());
		message.setSubject(inviteEmailSubject);
		message.setText(resolveInviteEmailContent(whoInvitedUserName, whoBeInvited.getRealName(),
				companyBeInvited.getFullName(),beInvitedCompanyTypeName,beInvitedUrl()));
		mailSender.send(message);
	}


	public String resolveInviteEmailContent(String customerName,String inviteUserName,String inviteCompanyName,String inviteCompanyTypeName,String inviteUrl){
		StringWriter w = new StringWriter();
		Properties properties=new Properties();
		//设置velocity资源加载方式为class
		properties.setProperty("resource.loader", "class");
		properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		Velocity.init(properties);
		velocityContext.put("username", customerName);
		velocityContext.put("inviteUser",inviteUserName);
		velocityContext.put("inviteCompanyName", inviteCompanyName);
		velocityContext.put("inviteCompanyType", inviteCompanyTypeName);
		velocityContext.put("inviteUrl", inviteUrl);

		Velocity.mergeTemplate(templateName, "UTF-8", velocityContext, w);
		StringWriter stringWriter = new StringWriter();
		Velocity.evaluate(velocityContext, stringWriter, "", w.toString());
		return stringWriter.toString();
	}

	//TODO 被邀请人的邀请链接 需要时间该邀请链接的过期时间和用户检验
	public String beInvitedUrl() {
		return "";
	}

}
