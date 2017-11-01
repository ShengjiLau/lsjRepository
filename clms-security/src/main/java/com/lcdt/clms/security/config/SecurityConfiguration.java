package com.lcdt.clms.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
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
@EnableGlobalMethodSecurity(jsr250Enabled = true,prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
		web.ignoring().antMatchers("/css/**").antMatchers("/js/**").antMatchers("/img/**");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(ticketAuthProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.exceptionHandling().authenticationEntryPoint(entryPoint());
		http.authorizeRequests().anyRequest().authenticated();
		http.addFilterAt(ticketAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests().antMatchers("/auth/**").permitAll()
				.antMatchers("/register/**").permitAll()
				.and().logout().logoutSuccessHandler(ticketLogoutSuccessHandler()).logoutUrl("/signout").logoutSuccessUrl("/auth/loginpage").permitAll()
				.and().exceptionHandling().accessDeniedHandler(deniedHandler())
				.and().csrf().disable();
	}

	@Bean
	public TicketLogoutSuccessHandler ticketLogoutSuccessHandler(){
		return new TicketLogoutSuccessHandler();
	}


	@Bean
	public CasLoginEntryPoint entryPoint() {
		return new CasLoginEntryPoint();
	}

	public TicketAuthenticationFilter ticketAuthenticationFilter() {
		TicketAuthenticationFilter ticketAuthenticationFilter = new TicketAuthenticationFilter(new AntPathRequestMatcher("/**"));
		ticketAuthenticationFilter.setAuthenticationFailureHandler(new TicketAuthFailureHandler());
		try {
			ticketAuthenticationFilter.setAuthenticationManager(authenticationManager());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ticketAuthenticationFilter;
	}

	@Bean
	public TicketAuthProvider ticketAuthProvider() {
		return new TicketAuthProvider();
	}

	@Bean
	public TicketAccessDeniedHandler deniedHandler() {
		return new TicketAccessDeniedHandler();
	}

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
		sessionLocaleResolver.setDefaultLocale(Locale.CHINA);
		return sessionLocaleResolver;
	}

}
