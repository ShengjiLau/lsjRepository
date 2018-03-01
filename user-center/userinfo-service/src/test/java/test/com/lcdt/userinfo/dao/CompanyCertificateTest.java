package test.com.lcdt.userinfo.dao;

import com.lcdt.userinfo.dao.CompanyCertificateMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CompanyCertificateTest extends BaseIntegrationContext{

    @Autowired
    CompanyCertificateMapper mapper;

    @Test
    public void testMapper(){
        mapper.selectByCompanyId(1L);
    }


}
