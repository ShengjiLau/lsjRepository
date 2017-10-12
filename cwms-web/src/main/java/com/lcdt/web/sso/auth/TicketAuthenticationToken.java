package com.lcdt.web.sso.auth;

import com.lcdt.clms.permission.model.Permission;
import com.lcdt.login.bean.TicketAuthentication;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

/**
 * Created by ss on 2017/8/21.
 */
public class TicketAuthenticationToken extends AbstractAuthenticationToken {


	private String ticket;

	private TicketAuthentication authentication;

	public void setAuthentication(TicketAuthentication authentication) {
		this.authentication = authentication;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	/**
	 * Creates a token with the supplied array of authorities.
	 *
	 * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal
	 *                    represented by this authentication object.
	 */
	public TicketAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
	}

	List<GrantedAuthority> authorities;

	@Override
	public Collection<GrantedAuthority> getAuthorities() {

		List<Permission> permissions = authentication.getPermissions();

		if (permissions == null || permissions.isEmpty()) {
			return authorities;
		}
		else if (authorities == null){
			for (Permission permission : permissions) {
				PermissionAuthority permissionAuthority = new PermissionAuthority(permission);
				authorities.add(permissionAuthority);
			}
		}
		return authorities;
	}

	@Override
	public Object getCredentials() {
		return ticket;
	}

	@Override
	public Object getDetails() {
		return authentication;
	}

	@Override
	public Object getPrincipal() {
		if (authentication != null) {
			return  authentication.getUser();
		}
		return null;
	}

	@Override
	public String getName() {
		return authentication.getUser().getRealName();
	}

	static class PermissionAuthority implements GrantedAuthority{

		Permission permission;

		public PermissionAuthority(Permission permission) {
			this.permission = permission;
		}

		@Override
		public String getAuthority() {
			return permission.getPermissionCode();
		}
	}

}
