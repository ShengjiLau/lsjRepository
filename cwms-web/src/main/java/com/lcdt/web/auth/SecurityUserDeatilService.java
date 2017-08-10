package com.lcdt.web.auth;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.lcdt.userinfo.model.FrontUserInfo;
import com.lcdt.userinfo.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by ss on 2017/8/8.
 */
@Service
public class SecurityUserDeatilService implements UserDetailsService {

	@Reference
	UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			FrontUserInfo frontUserInfo = userService.queryByPhone(username);
			return new WmsUserDetails(frontUserInfo);
		} catch (UserNotExistException e) {
			e.printStackTrace();
			throw new UsernameNotFoundException(username+"不存在");
		}
	}
}
