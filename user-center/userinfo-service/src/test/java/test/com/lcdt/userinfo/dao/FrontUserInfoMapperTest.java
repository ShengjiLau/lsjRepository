package test.com.lcdt.userinfo.dao;

import com.lcdt.userinfo.dto.RegisterDto;
import com.lcdt.userinfo.exception.PhoneHasRegisterException;
import com.lcdt.userinfo.service.UserService;
import com.lcdt.userinfo.service.impl.UserServiceImpl;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import static org.junit.Assert.assertTrue;

/**
 * Created by ss on 2017/7/31.
 */
@Ignore
public class FrontUserInfoMapperTest extends BaseIntegrationContext {

	@Autowired
	UserService userService;

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

}
