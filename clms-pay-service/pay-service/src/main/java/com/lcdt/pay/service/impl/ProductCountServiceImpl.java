package com.lcdt.pay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.lcdt.pay.dao.CompanyServiceCountMapper;
import com.lcdt.pay.dao.ProductCountLogMapper;
import com.lcdt.pay.model.CompanyServiceCount;
import com.lcdt.pay.rpc.ProductCountLog;
import com.lcdt.pay.rpc.ProductCountService;
import com.lcdt.pay.utils.OrderNoGenerator;
import com.lcdt.pay.model.PageResultDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ProductCountServiceImpl implements ProductCountService {

    @Autowired
    ProductCountLogMapper countLogMapper;

    @Autowired
    CompanyServiceCountMapper companyServiceCountMapper;

    @Override
    public ProductCountLog reduceProductCount(String productName, String des, Integer countNum, String userName,Long companyId,Integer remainCounts) {
        ProductCountLog productCountLog = new ProductCountLog();
        productCountLog.setServiceName(productName);
        productCountLog.setConsumeNum(countNum);
        productCountLog.setLogDes(des);
        productCountLog.setLogNo(OrderNoGenerator.generateDateNo(2));
        productCountLog.setLogType(CountLogType.COUNSUMETYPE);
        productCountLog.setUserName(userName);
        productCountLog.setCompanyId(companyId);
        productCountLog.setRemainNum(remainCounts);
        countLogMapper.insert(productCountLog);
        return productCountLog;
    }

    @Override
    public ProductCountLog logAddProductCount(String productName,String des,Integer countNum,String userName,Long companyId,Integer remainCounts){
        return this.logAddProductCount(productName, des, countNum, userName, companyId, remainCounts, CountLogType.TOPUPCOUNTTYPE);
    }



    public ProductCountLog logAddProductCount(String productName,String des,Integer countNum,String userName,Long companyId,Integer remainCounts,Integer logType){
        ProductCountLog productCountLog = new ProductCountLog();
        productCountLog.setServiceName(productName);
        productCountLog.setConsumeNum(countNum);
        productCountLog.setLogDes(des);
        productCountLog.setLogNo(OrderNoGenerator.generateDateNo(2));
        productCountLog.setLogType(logType);
        productCountLog.setCompanyId(companyId);
        productCountLog.setUserName(userName);
        productCountLog.setRemainNum(remainCounts);
        productCountLog.setLogTime(new Date());
        countLogMapper.insert(productCountLog);
        return productCountLog;
    }


    public PageResultDto<ProductCountLog> countLogs(Long companyId, String productName, Date startTime, Date endTime,Integer logType,Integer pageSize,Integer pageNo){
        PageHelper.startPage(pageNo, pageSize);
        List<ProductCountLog> productCountLogs = countLogMapper.selectByProductNameCompanyId(companyId, productName, startTime, endTime, logType);
        return new PageResultDto<ProductCountLog>(productCountLogs);
    }

    public List<ProductCountLog> countLogs(Long companyId, String productName, Date startTime, Date endTime,Integer logType){
        return countLogMapper.selectByProductNameCompanyId(companyId, productName, startTime, endTime,logType);
    }


    public ProductCountLog logAddProductCountAndCompanyCount(String productName,String des,Integer countNum,String userName,Long companyId,Integer remainCounts){
        ProductCountLog productCountLog = new ProductCountLog();
        productCountLog.setServiceName(productName);
        productCountLog.setConsumeNum(countNum);
        productCountLog.setLogDes(des);
        productCountLog.setLogNo(OrderNoGenerator.generateDateNo(2));
        productCountLog.setLogType(CountLogType.TOPUPCOUNTTYPE);

        productCountLog.setCompanyId(companyId);
        productCountLog.setUserName(userName);
        productCountLog.setRemainNum(remainCounts);
        productCountLog.setLogTime(new Date());
        countLogMapper.insert(productCountLog);

        CompanyServiceCount companyServiceCount1 = new CompanyServiceCount();
        companyServiceCount1.setCompanyId(companyId);
        companyServiceCount1.setProductName(productName);
        companyServiceCount1.setProductServiceNum(remainCounts);
        companyServiceCountMapper.insert(companyServiceCount1);

        return productCountLog;
    }


    public static final class CountLogType {
        public static final Integer COUNSUMETYPE = 1;

        public static final Integer TOPUPCOUNTTYPE = 2;
        public static final Integer ADMIN_TOPUP = 3;
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
