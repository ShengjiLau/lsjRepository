package com.lcdt.web.auth;

import com.lcdt.userinfo.model.FrontUserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Created by ss on 2017/8/8.
 */
public class WmsUserDetails implements UserDetails {

	FrontUserInfo userInfo;

	public WmsUserDetails(FrontUserInfo userInfo) {
		this.userInfo = userInfo;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return userInfo.getUserPass();
	}

	@Override
	public String getUsername() {
		return userInfo.getUserName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		if (userInfo.getUserValid() == null) {
			return true;
		}
		return userInfo.getUserValid() == 1 ? true:false;
	}
}
