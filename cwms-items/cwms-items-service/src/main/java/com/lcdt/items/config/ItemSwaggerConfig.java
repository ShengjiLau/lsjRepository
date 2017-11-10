package com.lcdt.items.config;

import com.lcdt.swagger.SwaggerConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @AUTHOR liuh
 * @DATE 2017-11-09
 */
@Configuration
@Import(SwaggerConfig.class)
public class ItemSwaggerConfig {
}
