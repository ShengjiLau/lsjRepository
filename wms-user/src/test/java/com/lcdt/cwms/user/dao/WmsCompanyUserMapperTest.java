package com.lcdt.cwms.user.dao;

import com.lcdt.cwms.user.MybatisUtils;
import com.lcdt.cwms.user.model.WmsCompany;
import com.lcdt.cwms.user.model.WmsCompanyUserRelation;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import java.util.List;

/**
 * Created by ss on 2017/8/4.
 */
@Ignore
public class WmsCompanyUserMapperTest {

	private SqlSession sqlSession = MybatisUtils.openSession();

	private void init(){
//		MybatisUtils.openSession()
	}


	@Rollback
	@Test
	public void test(){
		WmsCompanyUserRelationMapper mapper = sqlSession.getMapper(WmsCompanyUserRelationMapper.class);
		List<WmsCompanyUserRelation> wmsCompanyUserRelations = mapper.selectCompanyByUserId(12);
		WmsCompanyUserRelation wmsCompanyUserRelation = wmsCompanyUserRelations.get(0);
		WmsCompany wmsCompany = wmsCompanyUserRelation.getWmsCompany();
		Assert.assertTrue(wmsCompany != null);
		Assert.assertTrue(wmsCompany.getFullName() != null);
	}


}
