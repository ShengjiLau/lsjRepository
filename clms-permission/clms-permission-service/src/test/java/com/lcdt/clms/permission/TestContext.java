package com.lcdt.clms.permission;

import com.alibaba.druid.pool.DruidDataSource;
import com.lcdt.wms.config.MybatisCommonConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

/**
 * Created by ss on 2017/10/12.
 */
@SpringBootApplication
@ComponentScan("com.lcdt.clms.permission")
@Import(MybatisCommonConfig.class)
public class TestContext {




	@Bean
	public DataSource dataSource(){
		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
		druidDataSource.setUrl("jdbc:mysql://101.37.21.24:3306/cwms_cloud_db");
		druidDataSource.setUsername("root");
		druidDataSource.setPassword("Root123!");
		return druidDataSource;
	}


}
