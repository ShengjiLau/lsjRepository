package com.lcdt.clms.security.helper;

import com.lcdt.login.bean.TicketAuthentication;
import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.model.Group;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

/**
 * Created by ss on 2017/10/20.
 */
public class SecurityInfoGetter {

	public static User getUser(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		TicketAuthentication details = (TicketAuthentication) authentication.getDetails();
		return details.getUser();
	}


	public static Long getCompanyId(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		TicketAuthentication details = (TicketAuthentication) authentication.getDetails();
		UserCompRel userCompRel = details.getUserCompRel();
		return userCompRel.getCompId();
	}

	public static List<Group> groups(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		TicketAuthentication details = (TicketAuthentication) authentication.getDetails();
		UserCompRel userCompRel = details.getUserCompRel();
		return userCompRel.getGroups();
	}

}
