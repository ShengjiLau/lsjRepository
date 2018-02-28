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

        String comment = "";
        String nickname = "nickname";


        CreateEmployeeAccountDto createEmployeeAccountDto = new CreateEmployeeAccountDto();
        createEmployeeAccountDto.setUserPhoneNum("15666836323");
        createEmployeeAccountDto.setComment(comment);
        createEmployeeAccountDto.setNickName(nickname);
        String duty = "duty";
        createEmployeeAccountDto.setDuty(duty);


        boolean b = employeeService.addEmployee(createEmployeeAccountDto, 1L);



    }

    @Test
    @Rollback
    public void testRemoveEmployee(){
        employeeService.removeUserCompRel(181L);
    }

}
