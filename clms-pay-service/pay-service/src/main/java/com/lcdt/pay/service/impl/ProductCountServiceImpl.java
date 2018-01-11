package com.lcdt.pay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lcdt.pay.dao.ProductCountLogMapper;
import com.lcdt.pay.rpc.ProductCountLog;
import com.lcdt.pay.rpc.ProductCountService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ProductCountServiceImpl implements ProductCountService {

    @Autowired
    ProductCountLogMapper countLogMapper;

    @Override
    public void reduceProductCount(String productName, String des, Integer countNum, String userName,Long companyId) {
        ProductCountLog productCountLog = new ProductCountLog();
        productCountLog.setServiceName(productName);
        productCountLog.setConsumeNum(countNum);
        productCountLog.setLogDes(des);
        productCountLog.setLogNo(uuidno());
        productCountLog.setLogType(CountLogType.COUNSUMETYPE);
        countLogMapper.insert(productCountLog);
    }

    @Override
    public void logAddProductCount(String productName,String des,Integer countNum,String userName,Long companyId){
        ProductCountLog productCountLog = new ProductCountLog();
        productCountLog.setServiceName(productName);
        productCountLog.setConsumeNum(countNum);
        productCountLog.setLogDes(des);
        productCountLog.setLogNo(uuidno());
        productCountLog.setLogType(CountLogType.TOPUPCOUNTTYPE);
        productCountLog.setCompanyId(companyId);
        countLogMapper.insert(productCountLog);
    }

    public List<ProductCountLog> countLogs(Long companyId, String productName, Date startTime, Date endTime,Integer logType){
        return countLogMapper.selectByProductNameCompanyId(companyId, productName, startTime, endTime,logType);
    }


    static final class CountLogType {
        public static final Integer COUNSUMETYPE = 1;

        public static final Integer TOPUPCOUNTTYPE = 2;
    }


    private String uuidno() {
        String s = UUID.randomUUID().toString();
        return s.replace("-", "");
    }

}
