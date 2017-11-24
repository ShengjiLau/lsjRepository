package com.lcdt.client.service.impl;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

/**
 * @AUTHOR liuh
 * @DATE 2017-11-16
 */
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
public class MyClientServiceImplTest {

    @Autowired
    private ClientService myClientService;

    @Test
    public void getMyClientList() throws Exception {
/*        MyClient myClient = new MyClient();
        myClient.setCompanyId(8L);
        myClient.setClientType(new Short("1"));
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(1);
        pageInfo.setPageSize(2);
        List<MyClient> list = myClientService.getMyClientList(myClient,pageInfo);
        //assertTrue(list.size()>0);*/
    }

}