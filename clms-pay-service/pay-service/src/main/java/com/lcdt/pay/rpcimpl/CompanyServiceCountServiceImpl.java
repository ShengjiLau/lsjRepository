package com.lcdt.pay.rpcimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lcdt.pay.dao.CompanyServiceCountMapper;
import com.lcdt.pay.model.CompanyServiceCount;
import com.lcdt.pay.rpc.CompanyServiceCountService;
import com.lcdt.pay.service.impl.CompanyServiceCountImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by liz on 2018/6/11.
 */
public class CompanyServiceCountServiceImpl implements CompanyServiceCountService {

    @Autowired
    CompanyServiceCountMapper countMapper;

    private static final Logger log = LoggerFactory.getLogger(CompanyServiceCountImpl.class);

    @Override
    public List<CompanyServiceCount> companyServiceCount(Long companyId) {
        return null;
    }

    @Override
    public CompanyServiceCount reduceCompanyProductCount(Long companyId, String serviceName, Integer reduceNum) {
        return null;
    }

    @Override
    public boolean checkCompanyProductCount(Long companyId, String serviceName, Integer reduceNum) {
        return false;
    }

    @Override
    public int companyProductCount(Long companyId, String serviceName) {
        List<CompanyServiceCount> companyServiceCounts = countMapper.selectByCompanyId(companyId, serviceName);
        if (CollectionUtils.isEmpty(companyServiceCounts)) {
            log.error("额外服务超限");
//            throw new RuntimeException(serviceName + "数量不足");
            return 0;
        }else{
            return companyServiceCounts.get(0).getProductServiceNum();
        }
    }
}
