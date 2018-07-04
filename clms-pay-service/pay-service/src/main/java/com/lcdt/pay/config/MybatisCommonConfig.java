package com.lcdt.pay.config;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by ss on 2017/8/4.
 */
@Configuration
@EnableTransactionManagement
public class MybatisCommonConfig implements TransactionManagementConfigurer {

	@Autowired
	DataSource dataSource;

	/**
	 * mapper scanner 扫描mapper接口,并自动生成代理bean注入到容器中
	 *
	 * @return
	 */
	@Bean
	public static MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setBasePackage("com.lcdt.*.*.dao,com.lcdt.*.dao");
		return mapperScannerConfigurer;
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory() {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		ClassPathResource classPathResource = new ClassPathResource("mybatis-config.xml");
		if (classPathResource.exists()) {
			sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
		}
		PageInterceptor pageInterceptor = new PageInterceptor();
//		pageInterceptor.setProperties();
		//分页插件
		PageHelper pageHelper = new PageHelper();
		Properties properties = new Properties();
		properties.setProperty("reasonable", "true");
		properties.setProperty("supportMethodsArguments", "true");
		properties.setProperty("returnPageInfo", "check");
		properties.setProperty("params", "count=countSql");
		pageInterceptor.setProperties(properties);

		//添加插件
		sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageInterceptor});

		try {
			SqlSessionFactory object = sqlSessionFactoryBean.getObject();
//			object.getConfiguration().addInterceptor();
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}


	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}


}
