package com.lcdt.manage.config;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import com.lcdt.clms.security.config.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * Created by ss on 2017/8/8.
 */
@Configuration
@ComponentScan("com.lcdt.clms.security.config")
@DubboComponentScan(basePackages = "com.lcdt.clms.security.config")
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true,prePostEnabled = true)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
		web.ignoring().antMatchers("/css/**","/js/**","/img/**","/wechatpaynotify","/alipay/notify","/alipay/returnurl","/front/**");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(ticketAuthProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry());
		http.exceptionHandling().authenticationEntryPoint(entryPoint());
		http.addFilterAt(ticketAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests().antMatchers("/auth/**").permitAll()
				.antMatchers("/register/**","/wechatpaynotify","/alipay/notify","/alipay/returnurl").permitAll()
				.and().logout().logoutSuccessHandler(ticketLogoutSuccessHandler()).logoutUrl("/signout").logoutSuccessUrl("/auth/loginpage").permitAll()
				.and().exceptionHandling().accessDeniedHandler(deniedHandler())
				.and().csrf().disable();
		http.authorizeRequests().antMatchers("/admin/**").authenticated();
	}

	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
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
