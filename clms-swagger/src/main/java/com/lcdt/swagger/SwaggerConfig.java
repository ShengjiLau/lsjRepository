package com.lcdt.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by ss on 2017/11/1.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Value("${swagger-toggle}")
    private boolean online;

    @Bean
    public Docket customDocket() {
        if(!online) {
            return new Docket(DocumentationType.SWAGGER_2)
                    .select()
                    .paths(PathSelectors.none())//如果是线上环境，添加路径过滤，设置为全部都不符合
                    .build();
        }else {
            return new Docket(DocumentationType.SWAGGER_2);
        }
    }

}
