package com.lcdt.cwms.user;

import com.lcdt.cwms.user.dao.WmsCompanyUserRelationMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by ss on 2017/8/3.
 */
public class MybatisUtils {

	private static String mybatisTestConf = "mybatis-test-conf.xml";

	public static SqlSession openSession() {
		Reader reader = null;
		try {
			reader = Resources.getResourceAsReader(mybatisTestConf);
		} catch (IOException e) {
			e.printStackTrace();
		}
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		SqlSessionFactory sessionFactory = builder.build(reader);
		SqlSession session = sessionFactory.openSession();
		return session;
	}


}
