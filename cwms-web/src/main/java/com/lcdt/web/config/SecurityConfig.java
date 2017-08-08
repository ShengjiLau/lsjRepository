package com.lcdt.web.config;

import com.lcdt.web.auth.SecurityUserDeatilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by ss on 2017/8/8.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	SecurityUserDeatilService userDeatilService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDeatilService)
				.passwordEncoder(new PasswordEncoder() {
					@Override
					public String encode(CharSequence rawPassword) {
						return RegisterUtils.md5Encrypt(rawPassword.toString());
					}

					@Override
					public boolean matches(CharSequence rawPassword, String encodedPassword) {
						return encode(rawPassword).toUpperCase().equals(encodedPassword.toUpperCase());
					}
				});
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		super.configure(http);
		http.authorizeRequests().anyRequest().authenticated()
				.and().formLogin().and().httpBasic();
	}

}
