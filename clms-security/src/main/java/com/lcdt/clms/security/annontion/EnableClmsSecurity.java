package com.lcdt.clms.security.annontion;

import com.lcdt.clms.security.SecurityConfig;
import com.lcdt.clms.security.config.RestExceptionHandler;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created by ss on 2017/10/13.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({SecurityConfig.class})
@Inherited
public @interface EnableClmsSecurity {

}
