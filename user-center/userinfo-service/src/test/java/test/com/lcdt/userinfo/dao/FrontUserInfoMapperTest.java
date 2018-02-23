package test.com.lcdt.userinfo.dao;

import com.lcdt.userinfo.dto.RegisterDto;
import com.lcdt.userinfo.exception.PhoneHasRegisterException;
import com.lcdt.userinfo.service.UserService;
import com.lcdt.userinfo.service.impl.EmployeeServiceImpl;
import com.lcdt.userinfo.service.impl.UserServiceImpl;
import com.lcdt.userinfo.web.dto.UpdateEmployeeAccountDto;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;

/**
 * Created by ss on 2017/7/31.
 */
public class FrontUserInfoMapperTest extends BaseIntegrationContext {

	@Autowired
	UserService userService;


	@Autowired
	EmployeeServiceImpl employeeService;

	@Test
	@Rollback
	public void testRegister() {
		RegisterDto registerDto = new RegisterDto();
		registerDto.setPassword("mawer111");
		registerDto.setUserPhoneNum("15666836323");
		try {
			userService.registerUser(registerDto);
		} catch (PhoneHasRegisterException e) {
			e.printStackTrace();
			assertTrue(true);
		}
	}

	@Test
	@Rollback
	public void testInsertGroups(){

		UpdateEmployeeAccountDto updateEmployeeAccountDto = new UpdateEmployeeAccountDto();
		updateEmployeeAccountDto.setUserPhoneNum("18666667789");
		ArrayList<Long> longs = new ArrayList<>();
		longs.add(11L);
		longs.add(1L);
		updateEmployeeAccountDto.setGroups(longs);


		ArrayList<Long> roles = new ArrayList<>();
		longs.add(10L);
		longs.add(68L);
		updateEmployeeAccountDto.setRoles(roles);
		updateEmployeeAccountDto.setUserCompRelId(94L);
		updateEmployeeAccountDto.setName("胖子");

		employeeService.updateEmployee(updateEmployeeAccountDto);
	}


}
