package com.lcdt.pay.rpc;

import java.util.Date;
import java.util.List;

public interface ProductCountService {

    void reduceProductCount(String productName, String des, Integer countNum, String userName);

    void logAddProductCount(String productName, String des, Integer countNum, String userName);

    List<ProductCountLog> countLogs(Long companyId, String productName, Date startTime, Date endTime);

}