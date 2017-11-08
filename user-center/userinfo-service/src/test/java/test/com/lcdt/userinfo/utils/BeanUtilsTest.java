package test.com.lcdt.userinfo.utils;

import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.web.dto.ModifyInvoiceDto;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

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

}
