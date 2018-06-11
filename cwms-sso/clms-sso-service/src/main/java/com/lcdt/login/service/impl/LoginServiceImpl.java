package com.lcdt.login.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import com.lcdt.clms.permission.model.Permission;
import com.lcdt.clms.permission.model.Role;
import com.lcdt.clms.permission.model.SysRole;
import com.lcdt.clms.permission.service.SysRoleService;
import com.lcdt.clms.permission.service.UserPermissionService;
import com.lcdt.clms.permission.service.UserRoleService;
import com.lcdt.login.bean.TicketAuthentication;
import com.lcdt.login.exception.InvalidTicketException;
import com.lcdt.login.service.AuthTicketService;
import com.lcdt.login.service.LoginService;
import com.lcdt.login.ticket.TicketBean;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.lcdt.userinfo.model.Group;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.userinfo.model.UserGroupRelation;
import com.lcdt.userinfo.service.CompanyService;
import com.lcdt.userinfo.service.UserGroupService;
import com.lcdt.userinfo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * Created by ss on 2017/8/18.
 */
@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	AuthTicketService ticketService;

	@Reference(check = false)
	UserService userService;

	@Reference(check = false)
	CompanyService companyService;

	@Reference(check = false)
	UserPermissionService permissionService;

	@Reference(check = false)
	UserGroupService userGroupService;

	@Value("${login.host}")
	private String host;



	@Override
	public TicketAuthentication queryTicket(String ticket) throws InvalidTicketException, UserNotExistException {
		TicketBean ticketValid = ticketService.isTicketValid(ticket);
		if (ticketValid == null) {
			throw new InvalidTicketException("ticket 验证失败");
		}
		User user = userService.queryByUserId(ticketValid.getUserId());
		TicketAuthentication authentication = new TicketAuthentication();
		authentication.setTicket(ticket);
		authentication.setUser(user);
		if (userService.isUserAdmin(user.getUserId())) {
			//后台管理员用户
			List<Permission> permissions = permissionService.adminPermission(user.getUserId());
			authentication.setPermissions(permissions);
		}


		if (ticketValid.getCompanyId() == null) {
			authentication.setChooseCompany(false);
		}else{
			UserCompRel companyMember = companyService.queryByUserIdCompanyId(ticketValid.getUserId(), ticketValid.getCompanyId().longValue());
			authentication.setUserCompRel(companyMember);
			if (companyMember != null) {
				List<Permission> permissions = permissionService.userPermissions(companyMember.getUserId(), companyMember.getCompId());
				authentication.setPermissions(permissions);
				List<SysRole> sysRoles = permissionService.userSysRoles(user.getUserId(), companyMember.getCompId());
				authentication.setSysRoles(sysRoles);
				List<Group> userGroupRelations = userGroupService.userGroups(user.getUserId(), companyMember.getCompId());
				authentication.setGroups(userGroupRelations);
			}

		}


		return authentication;
	}


	@Override
	public String loginUrl(String srcUrl){
		if (!host.startsWith("http://")) {
			host = "http://" + host;
		}
		StringBuilder sb = new StringBuilder(host);
		sb.append("?").append("auth_callback=").append(srcUrl);
		return sb.toString();
	}

	@Override
	public boolean needUpdateAuth(String ticket) {
		//根据ticket 拿到user 判断user 信息的更新时间
		return false;
	}

}
