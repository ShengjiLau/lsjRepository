package com.lcdt.contract;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lcdt.contract.model.OrderProduct;
import com.lcdt.contract.service.ContractService;
import com.lcdt.contract.service.OrderService;
import com.lcdt.contract.web.controller.api.SalesContractApi;
import com.lcdt.contract.web.dto.ContractDto;
import com.lcdt.contract.web.dto.OrderDto;

/**
 * @author Sheng-ji Lau
 * @date 2018年4月4日下午4:57:20
 * @version
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=ContractServiceApp.class)
public class test001 {
	
	 Logger logger = LoggerFactory.getLogger(SalesContractApi.class);

	    @Autowired
	    private ContractService contractService;
	    
	    @Autowired
	    private OrderService orderService;
	
	@Test
	public void testContract() {
		ContractDto cd = new ContractDto();
		cd.setCompanyId((long) 26);
	//	cd.setContractCode("test001");
		cd.setCreateName("lxt");
		cd.setCreateTime(new Date());
		cd.setTitle("testdte");
		cd.setType((short) 1);
		cd.setContractStatus((short) 1);
		contractService.addContract(cd);	
	}
	
	@Test
	public void testOrder(){
		OrderProduct po = new OrderProduct();
		po.setNum(new BigDecimal(23));
		po.setPrice(new BigDecimal(23));
		List pl =new ArrayList();
		pl.add(po);
		OrderDto o= new OrderDto();
		o.setCompanyId((long) 213);
		o.setOrderType((short) 0);
		o.setOrderProductList(pl);
		orderService.addOrder(o);
	}
	
	
	
	
	
	

}
