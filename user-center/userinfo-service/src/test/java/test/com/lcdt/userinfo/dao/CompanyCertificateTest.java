package test.com.lcdt.userinfo.dao;

import com.lcdt.userinfo.dao.CompanyCertificateMapper;
import com.lcdt.userinfo.model.CompanyCertificate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

public class CompanyCertificateTest extends BaseIntegrationContext{

    @Autowired
    CompanyCertificateMapper mapper;

    @Test
    @Rollback
    public void testMapper(){
        mapper.selectByCompanyId(1L);
        CompanyCertificate companyCertificate = new CompanyCertificate();
        companyCertificate.setCompId(1L);
//        companyCertificate.setCertiId();
        companyCertificate.setCreateId(1L);
        mapper.insert(companyCertificate);
        companyCertificate.setIdentityBack("123");
        mapper.updateByPrimaryKey(companyCertificate);

    }


}
