package com.lcdt.warehouse.dao;

import com.lcdt.warehouse.WarehouseServiceApp;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = WarehouseServiceApp.class)
public abstract class BaseIntegrationContext {
}