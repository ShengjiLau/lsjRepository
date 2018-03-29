package test.com.lcdt.userinfo.utils;

import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.userinfo.web.dto.CreateEmployeeAccountDto;
import com.lcdt.userinfo.web.dto.ModifyInvoiceDto;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ss on 2017/11/6.
 */
public class BeanUtilsTest {

	private String title = "测试抬头";

	@Test
	public void testCopyProperties(){
		Company company = new Company();
		ModifyInvoiceDto modifyInvoiceDto = new ModifyInvoiceDto();
		modifyInvoiceDto.setInvoiceTitle(title);
		modifyInvoiceDto.setBankName("银行");
		modifyInvoiceDto.setBankNo("bankNo");
		modifyInvoiceDto.setTelNo1("telno1");
		modifyInvoiceDto.setInvoiceRemark("remark");
		modifyInvoiceDto.setRegistrationAddress("address");
		modifyInvoiceDto.setRegistrationNo("ahsdiah");
		BeanUtils.copyProperties(modifyInvoiceDto,company);
		Assert.assertEquals(company.getInvoiceTitle(),title);
		Assert.assertEquals(company.getBankName(),"银行");
		Assert.assertEquals(company.getTelNo1(),"telno1");
		Assert.assertEquals(company.getInvoiceRemark(),"remark");
//		Assert.assertEquals(company.getInvoiceTitle(),title);
	}

	@Test
	public void printSameProperties(){
		CreateEmployeeAccountDto createEmployeeAccountDto = new CreateEmployeeAccountDto();
		UserCompRel userCompRel = new UserCompRel();
		showProperties(createEmployeeAccountDto,userCompRel,null,null);
	}


	private static void showProperties(Object source, Object target, Class<?> editable, String... ignoreProperties)
			throws BeansException {

		org.springframework.util.Assert.notNull(source, "Source must not be null");
		org.springframework.util.Assert.notNull(target, "Target must not be null");

		Class<?> actualEditable = target.getClass();
		if (editable != null) {
			if (!editable.isInstance(target)) {
				throw new IllegalArgumentException("Target class [" + target.getClass().getName() +
						"] not assignable to Editable class [" + editable.getName() + "]");
			}
			actualEditable = editable;
		}
		PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(actualEditable);
		List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);

		for (PropertyDescriptor targetPd : targetPds) {
			Method writeMethod = targetPd.getWriteMethod();
			if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
				PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
				if (sourcePd != null) {

					System.out.println(sourcePd.getName());

//					Method readMethod = sourcePd.getReadMethod();
//					if (readMethod != null &&
//							ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
//						try {
//							if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
//								readMethod.setAccessible(true);
//							}
//							Object value = readMethod.invoke(source);
//							if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
//								writeMethod.setAccessible(true);
//							}
//							writeMethod.invoke(target, value);
//						}
//						catch (Throwable ex) {
//							throw new FatalBeanException(
//									"Could not copy property '" + targetPd.getName() + "' from source to target", ex);
//						}
//					}
				}
			}
		}
	}
}
