package com.lcdt.items.service.impl;

import com.github.pagehelper.PageInfo;
import com.lcdt.items.model.ItemsInfo;
import com.lcdt.items.model.ItemsInfoDao;
import com.lcdt.items.service.ItemsInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @AUTHOR liuh
 * @DATE 2017-11-21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemsInfoServiceImplTest {

    Logger logger = LoggerFactory.getLogger(ItemsInfoServiceImplTest.class);

    @Autowired
    private ItemsInfoService itemsInfoService;
    @Test
    public void queryItemsByCondition() throws Exception {
        ItemsInfoDao itemsInfoDao = new ItemsInfoDao();
        itemsInfoDao.setCompanyId(8L);
        itemsInfoDao.setClassifyId(50L);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(1);
        pageInfo.setPageSize(2);
        List<ItemsInfo> itemsInfoList = itemsInfoService.queryItemsByCondition(itemsInfoDao,pageInfo);
        logger.info("itemsInfoList--size:",itemsInfoList.size());
    }

}