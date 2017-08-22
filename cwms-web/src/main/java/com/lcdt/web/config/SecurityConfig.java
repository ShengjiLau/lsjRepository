package com.lcdt.web.config;

import com.lcdt.web.auth.*;
import com.lcdt.web.sso.auth.CasLoginEntryPoint;
import com.lcdt.web.sso.auth.TicketAuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

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
		auth.authenticationProvider(ticketAuthProvider());

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

		http.exceptionHandling().authenticationEntryPoint(entryPoint());

		http.addFilterAt(ticketAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)

				.authorizeRequests().antMatchers("/auth/**").permitAll()
				.antMatchers("/register/**").permitAll()

				.anyRequest().authenticated()
				.and().logout().logoutUrl("/signout").logoutSuccessUrl("/auth/loginpage").permitAll()
				.and().exceptionHandling().accessDeniedHandler(deniedHandler())
				.and().csrf().disable();

		;
	}

	@Bean
	public CasLoginEntryPoint entryPoint(){
		return new CasLoginEntryPoint();
	}

//	@Bean
//	public TicketAuthFilter ticketAuthFilter(){
//		return new TicketAuthFilter();
//	}

	public TicketAuthenticationFilter ticketAuthenticationFilter(){
//		super(new AntPathRequestMatcher("/login", "POST"));
		TicketAuthenticationFilter ticketAuthenticationFilter = new TicketAuthenticationFilter(new AntPathRequestMatcher("/**"));
		try {
			ticketAuthenticationFilter.setAuthenticationManager(authenticationManager());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ticketAuthenticationFilter;
	}

	@Bean
	public TicketAuthProvider ticketAuthProvider(){
		return new TicketAuthProvider();
	}


	@Bean
	public WmsUserNamePwdAuthFilter wmsUserNamePwdAuthFilter() throws Exception {
		WmsUserNamePwdAuthFilter filter = new WmsUserNamePwdAuthFilter();
		filter.setAuthenticationManager(authenticationManagerBean());
		filter.setAuthenticationSuccessHandler(new AjaxLoginSuccessHandler());
		filter.setAuthenticationFailureHandler(loginFailureHandler());
		return filter;
	}

	@Bean
	public TicketAccessDeniedHandler deniedHandler(){
		return new TicketAccessDeniedHandler();
	}


	@Bean
	public AjaxLoginFailureHandler loginFailureHandler() {
		return new AjaxLoginFailureHandler();
	}

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
		sessionLocaleResolver.setDefaultLocale(Locale.CHINA);
		return sessionLocaleResolver;
	}

}
