package com.lcdt.contract;

import com.lcdt.aliyunmq.AliyunConfigProperties;
import com.lcdt.clms.security.annontion.EnableClmsSecurity;
import com.lcdt.converter.ClmsResponseConvertConfig;
import com.lcdt.wms.config.DubboConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * @author liuh
 * @date 2017-10-16
 */
@SpringBootApplication
@EnableTransactionManagement
@Import({AliyunConfigProperties.class,com.lcdt.swagger.SwaggerConfig.class, DubboConfig.class, ClmsResponseConvertConfig.class})
@EnableClmsSecurity
//@ComponentScan(basePackageClasses = {com.lcdt.userinfo.service.CompanyService.class,com.lcdt.userinfo.service.WarehouseService.class})
public class ContractServiceApp {
    public static void main(String[] args) {

        SpringApplication.run(ContractServiceApp.class, args);

    }
}

