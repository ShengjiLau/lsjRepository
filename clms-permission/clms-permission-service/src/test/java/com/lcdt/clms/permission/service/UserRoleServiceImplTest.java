package com.lcdt.clms.permission.service;

import com.lcdt.clms.permission.SpringBootIntegrationTest;
import com.lcdt.clms.permission.TestContext;
import com.lcdt.clms.permission.model.Permission;
import com.lcdt.clms.permission.model.Role;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

/**
 * Created by ss on 2017/10/12.
 */
@Ignore
public class UserRoleServiceImplTest extends SpringBootIntegrationTest{

		@Autowired
		UserRoleService userRoleService;

		@Test
		public void testGetRoles(){
			List<Role> userRole = userRoleService.getUserRole(1L, 1L);
			Assert.assertNotNull(userRole);
			Role role = userRole.get(0);
			Assert.assertEquals(userRole.get(0).getRoleName(),"测试员");
		}

}
