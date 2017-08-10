package com.lcdt.web.config;

import com.lcdt.web.auth.LoginSuccessHandler;
import com.lcdt.web.auth.SecurityUserDeatilService;
import com.lcdt.web.auth.WmsUserNamePwdAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by ss on 2017/8/8.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	SecurityUserDeatilService userDeatilService;

	@Autowired
	WmsWebLoginFailureHandler failureHandler;

	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
		web.ignoring().antMatchers("/css/**").antMatchers("/js/**").antMatchers("/img/**");
	}

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
		http.addFilterAt(wmsUserNamePwdAuthFilter(), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests().antMatchers("/auth/**").permitAll()
				.anyRequest().authenticated()
				.antMatchers("/register/**").permitAll()
				.and().formLogin().loginPage("/auth/loginpage").loginProcessingUrl("/login").successHandler(new LoginSuccessHandler())
				.and().logout().logoutUrl("/signout").logoutSuccessUrl("/auth/loginpage").permitAll()

				.and().csrf().disable();
	}

	@Bean
	public WmsUserNamePwdAuthFilter wmsUserNamePwdAuthFilter() throws Exception {
		WmsUserNamePwdAuthFilter filter = new WmsUserNamePwdAuthFilter();
		filter.setAuthenticationManager(authenticationManagerBean());
		filter.setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler());
		return filter;
	}

}
