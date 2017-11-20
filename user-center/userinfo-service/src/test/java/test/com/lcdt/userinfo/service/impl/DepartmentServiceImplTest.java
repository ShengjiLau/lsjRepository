package test.com.lcdt.userinfo.service.impl;

import com.lcdt.userinfo.dao.DepartmentMapper;
import com.lcdt.userinfo.model.Department;
import com.lcdt.userinfo.service.impl.DepartmentServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * Created by ss on 2017/11/20.
 */
public class DepartmentServiceImplTest {

	public DepartmentServiceImpl service = new DepartmentServiceImpl();


	@Test
	public void testGetIdsNames() {
		DepartmentMapper mapper = Mockito.mock(DepartmentMapper.class);
		Mockito.when(mapper.selectByPrimaryKey(Mockito.anyLong())).thenAnswer(new Answer<Department>() {
			@Override
			public Department answer(InvocationOnMock invocation) throws Throwable {
				Object o = invocation.getArguments()[0];
				Department department = new Department();
				String name = "name" + Long.valueOf((Long) o);
				department.setDeptName(name);
				return department;
			}
		});

		Whitebox.setInternalState(service,"departmentMapper",mapper);

		String names = "1,2,3";
		String idsNames = service.getIdsNames(names);

		Assert.assertEquals("name1,name2,name3",idsNames);
	}

}
