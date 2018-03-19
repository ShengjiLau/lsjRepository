package com.lcdt.clms.security.annontion;

import com.lcdt.clms.security.token.config.TokenBaseSecurityConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(TokenBaseSecurityConfig.class)
public @interface EnableTokenBaseSecurity {
}
