package com.lcdt.pay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lcdt.pay.dao.ProductCountLogMapper;
import com.lcdt.pay.rpc.ProductCountLog;
import com.lcdt.pay.rpc.ProductCountService;
import com.lcdt.pay.utils.OrderNoGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ProductCountServiceImpl implements ProductCountService {

    @Autowired
    ProductCountLogMapper countLogMapper;

    @Override
    public ProductCountLog reduceProductCount(String productName, String des, Integer countNum, String userName,Long companyId,Integer remainCounts) {
        ProductCountLog productCountLog = new ProductCountLog();
        productCountLog.setServiceName(productName);
        productCountLog.setConsumeNum(countNum);
        productCountLog.setLogDes(des);
        productCountLog.setLogNo(OrderNoGenerator.generateDateNo());
        productCountLog.setLogType(CountLogType.COUNSUMETYPE);
        productCountLog.setUserName(userName);
        countLogMapper.insert(productCountLog);
        return productCountLog;
    }

    @Override
    public ProductCountLog logAddProductCount(String productName,String des,Integer countNum,String userName,Long companyId,Integer remainCounts){
        ProductCountLog productCountLog = new ProductCountLog();
        productCountLog.setServiceName(productName);
        productCountLog.setConsumeNum(countNum);
        productCountLog.setLogDes(des);
        productCountLog.setLogNo(OrderNoGenerator.generateDateNo());
        productCountLog.setLogType(CountLogType.TOPUPCOUNTTYPE);

        productCountLog.setCompanyId(companyId);
        productCountLog.setUserName(userName);
        productCountLog.setRemainNum(remainCounts);
        productCountLog.setLogTime(new Date());
        countLogMapper.insert(productCountLog);
        return productCountLog;
    }

    public List<ProductCountLog> countLogs(Long companyId, String productName, Date startTime, Date endTime,Integer logType){
        return countLogMapper.selectByProductNameCompanyId(companyId, productName, startTime, endTime,logType);
    }




    static final class CountLogType {
        public static final Integer COUNSUMETYPE = 1;

        public static final Integer TOPUPCOUNTTYPE = 2;
    }

    //流水号不使用uuID
    private String uuidno() {

        String s = UUID.randomUUID().toString();
        return s.replace("-", "");
    }
    public static final class PayType {
        public static final Integer ALIPAY = 1;
        public static final Integer BALANCEPAY = 0;
        public static final Integer WECHATPAY = 2;
        public static final Integer OFFLINEPAY = 3;
    }
}
