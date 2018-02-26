package test.com.lcdt.userinfo.dao;

import com.lcdt.userinfo.service.impl.EmployeeServiceImpl;
import com.lcdt.userinfo.web.dto.CreateEmployeeAccountDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

public class EmployeeServiceTest extends BaseIntegrationContext{

    @Autowired
    EmployeeServiceImpl employeeService;

    @Test
    @Rollback
    public void testAddEmployee(){


    }

    @Test
    @Rollback
    public void testRemoveEmployee(){
        employeeService.removeUserCompRel(181L);
    }

}
