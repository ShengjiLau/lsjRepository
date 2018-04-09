package test.com.lcdt.userinfo.service.impl;

import com.lcdt.clms.permission.model.SysRole;
import com.lcdt.clms.permission.service.SysRoleService;
import com.lcdt.userinfo.dto.CompanyDto;
import com.lcdt.userinfo.exception.CompanyExistException;
import com.lcdt.userinfo.exception.DeptmentExistException;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.model.Group;
import com.lcdt.userinfo.service.CompanyService;
import com.lcdt.userinfo.service.DepartmentService;
import com.lcdt.userinfo.service.GroupManageService;
import com.lcdt.userinfo.service.UserGroupService;
import com.lcdt.userinfo.service.impl.CreateCompanyServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

import static org.mockito.Mockito.*;
/**
 * Created by ss on 2017/10/27.
 */
public class CreateCompanyTest {

	public final String test_admin = "test_admin";
	private CreateCompanyServiceImpl createCompanyService;

	private CompanyService companyService;

	private SysRole sysRole;

	private SysRoleService sysRoleService;

	private Company company;

	private GroupManageService groupManageService;

	private UserGroupService userGroupService;
	public CompanyDto companyDto = new CompanyDto();
	public Group createrDefaultGroup;
	private DepartmentService departmentService;


	@Before
	public void setUp(){
		departmentService = mock(DepartmentService.class);
		userGroupService = Mockito.mock(UserGroupService.class);
		groupManageService = Mockito.mock(GroupManageService.class);
		sysRoleService = Mockito.mock(SysRoleService.class);
		createCompanyService = new CreateCompanyServiceImpl();
		companyService = Mockito.mock(CompanyService.class);
		sysRole = new SysRole();
		sysRole.setSysRoleCode(test_admin);

		companyDto.setUserId(2L);
		companyDto.setCreateId(2L);
		createrDefaultGroup = new Group();
		createrDefaultGroup.setCompanyId(companyDto.getCompanyId());
		when(groupManageService.createGroup(any())).thenReturn(createrDefaultGroup);

		Whitebox.setInternalState(createCompanyService,"sysAdminRole",sysRole);
		Whitebox.setInternalState(createCompanyService,"companyService",companyService);
		Whitebox.setInternalState(createCompanyService,"sysRoleService",sysRoleService);
		Whitebox.setInternalState(createCompanyService,"groupManageService",groupManageService);
		Whitebox.setInternalState(createCompanyService,"userGroupService",userGroupService);
		Whitebox.setInternalState(createCompanyService,"departmentService",departmentService);
		try {
			company = new Company();
			company.setCompId(3L);
			company.setCreateId(companyDto.getCreateId());
			Mockito.when(companyService.createCompany(Mockito.any())).thenReturn(company);
		} catch (CompanyExistException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCreateCompany() throws UserNotExistException {

		try {
			createCompanyService.createCompany(companyDto);
			//创建公司
			Mockito.verify(companyService).createCompany(companyDto);
			//设置创建者添加系统权限
			Mockito.verify(sysRoleService).addUserSysRole(sysRole, 2L, company.getCompId());
			//设置默认用户组
			Mockito.verify(userGroupService).addUserToGroup(anyLong(), anyLong(), any());

			Mockito.verify(departmentService).createDepartment(any());
		} catch (CompanyExistException e) {
			e.printStackTrace();
		} catch (DeptmentExistException e) {
			e.printStackTrace();
		}
	}
}
