package com.lcdt.clms.permission;

import com.lcdt.wms.config.MybatisCommonConfig;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by ss on 2017/10/12.
 */
@Configuration
@ComponentScan("com.lcdt.clms.permission")
//@Import(MybatisCommonConfig.class)
public class PermissionAppConfiguration {

}
