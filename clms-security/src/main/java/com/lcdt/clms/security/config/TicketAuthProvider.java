package com.lcdt.clms.security.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.clms.permission.model.Permission;
import com.lcdt.clms.permission.model.SysRole;
import com.lcdt.login.bean.TicketAuthentication;
import com.lcdt.login.exception.InvalidTicketException;
import com.lcdt.login.service.LoginService;
import com.lcdt.userinfo.exception.UserNotExistException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by ss on 2017/8/22.
 */
public class TicketAuthProvider implements AuthenticationProvider {

	@Reference(timeout = 10000,check = false)
	private LoginService loginService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		TicketAuthenticationToken token = (TicketAuthenticationToken) authentication;
		String ticket = (String) authentication.getCredentials();
		try {

			TicketAuthentication ticketAuthentication = loginService.queryTicket(ticket);
			return createLoginSuccessAuthentication(ticket, ticketAuthentication);
		} catch (InvalidTicketException e) {
			throw new TicketAuthException("请先登陆");
		} catch (UserNotExistException e) {
			throw new TicketAuthException("请先登陆");
		}
	}


	protected TicketAuthenticationToken createLoginSuccessAuthentication(String ticket, TicketAuthentication authentication) {
		//将permission的permissionCode 和 sysRole中的Role转化成SpringSecurity中的authority
		List<Permission> permissions = authentication.getPermissions();
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		if (permissions != null && !permissions.isEmpty()) {
			for (Permission permission : permissions) {
				SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permission.getPermissionCode().toLowerCase());
				authorities.add(authority);
			}
		}

		List<SysRole> sysRoles = authentication.getSysRoles();
		if (null != sysRoles && !sysRoles.isEmpty()) {
			for (SysRole role : sysRoles) {
				SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getSysRoleCode().toUpperCase());
				authorities.add(authority);
			}
		}

		TicketAuthenticationToken ticketAuthenticationToken = new TicketAuthenticationToken(ticket, authentication, authorities);
		return ticketAuthenticationToken;
	}


	@Override
	public boolean supports(Class<?> authentication) {
		return TicketAuthenticationToken.class.equals(authentication);
	}
}
