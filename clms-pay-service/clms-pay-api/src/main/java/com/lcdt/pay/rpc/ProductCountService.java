package com.lcdt.pay.rpc;

import java.util.Date;
import java.util.List;

public interface ProductCountService {

    ProductCountLog reduceProductCount(String productName, String des, Integer countNum, String userName,Long companyId,Integer remainCounts);

    ProductCountLog logAddProductCount(String productName, String des, Integer countNum, String userName,Long companyId,Integer remainCounts);

    ProductCountLog logAddProductCountAndCompanyCount(String productName,String des,Integer countNum,String userName,Long companyId,Integer remainCounts);

    List<ProductCountLog> countLogs(Long companyId, String productName, Date startTime, Date endTime,Integer logType);

}