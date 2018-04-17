package com.lcdt.contract;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lcdt.contract.dao.ContractMapper;
import com.lcdt.contract.service.ContractService;
import com.lcdt.contract.service.OrderService;
import com.lcdt.contract.web.controller.api.SalesContractApi;

/**
 * @author Sheng-ji Lau
 * @date 2018年4月16日下午4:18:52
 * @version
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=ContractServiceApp.class)
public class Test002 {
	 Logger logger = LoggerFactory.getLogger(SalesContractApi.class);

	    @Autowired
	    private ContractService contractService;
	    
	    @Autowired
	    private OrderService orderService;
	    
	    @Autowired
	    private ContractMapper cm;
	
	@Test
	public void testIsDraft() {
		
		int i=orderService.updateOrderIsDraft((long)330,(short)1);
		
		logger.debug("ok:"+i);
		
		
		
	}
	
	
	
	

}
