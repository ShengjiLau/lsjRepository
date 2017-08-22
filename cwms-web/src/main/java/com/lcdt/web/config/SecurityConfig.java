package com.lcdt.web.config;

import com.lcdt.web.auth.TicketAccessDeniedHandler;
import com.lcdt.web.auth.TicketAuthenticationFilter;
import com.lcdt.web.sso.auth.CasLoginEntryPoint;
import com.lcdt.web.sso.auth.TicketAuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
public class SecurityConfig extends WebSecurityConfigurerAdapter {

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
	public CasLoginEntryPoint entryPoint() {
		return new CasLoginEntryPoint();
	}

	public TicketAuthenticationFilter ticketAuthenticationFilter() {
		TicketAuthenticationFilter ticketAuthenticationFilter = new TicketAuthenticationFilter(new AntPathRequestMatcher("/**"));
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
