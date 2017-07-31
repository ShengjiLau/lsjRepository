package test.com.lcdt.userinfo.dao;

import com.lcdt.userinfo.dao.FrontUserInfoMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

/**
 * Created by ss on 2017/7/31.
 */
public class FrontUserInfoMapperTest extends BaseIntegrationContext {

	@Autowired
	FrontUserInfoMapper mapper;

	@Test
	@Rollback(true)
	public void testQuery(){
		mapper.selectAll();
	}

}
