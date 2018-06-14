package com.lcdt.pay.service.impl;

import com.lcdt.pay.dao.CompanyServiceCountMapper;
import com.lcdt.pay.model.CompanyServiceCount;
import com.lcdt.pay.rpc.CompanyServiceCountService;
import com.lcdt.pay.rpc.ProductCountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@com.alibaba.dubbo.config.annotation.Service
@Transactional(rollbackFor = Exception.class)
public class CompanyServiceCountImpl implements CompanyServiceCountService {

    @Autowired
    CompanyServiceCountMapper countMapper;

    @Autowired
    ProductCountService productCountService;

    private static final Logger log = LoggerFactory.getLogger(CompanyServiceCountImpl.class);


    @Override
    public List<CompanyServiceCount> companyServiceCount(Long companyId) {
        List<CompanyServiceCount> companyServiceCounts = countMapper.selectByCompanyId(companyId, null);
        return companyServiceCounts;
    }


    public CompanyServiceCount reduceCompanyProductCount(Long companyId,String serviceName,Integer reduceNum,String username,String des){
        List<CompanyServiceCount> companyServiceCounts = countMapper.selectByCompanyId(companyId, serviceName);
        if (CollectionUtils.isEmpty(companyServiceCounts)) {
            log.error("剩余服务次数不足 公司Id{} serviceName:{} reduceName{} ",companyId,serviceName,reduceNum);
            throw new RuntimeException(serviceName + "数量不足");
        }else{
            CompanyServiceCount companyServiceCount = companyServiceCounts.get(0);
            if (companyServiceCount.getProductServiceNum() < reduceNum) {
                log.error("剩余服务次数不足 公司Id{} serviceName:{} reduceName{} ",companyId,serviceName,reduceNum);
                throw new RuntimeException(serviceName + "数量不足");
            }
            companyServiceCount.setProductServiceNum(companyServiceCount.getProductServiceNum() - reduceNum);
            countMapper.updateByPrimaryKey(companyServiceCount);
            productCountService.reduceProductCount(serviceName,des,reduceNum,username,companyId,companyServiceCount.getProductServiceNum());
            return companyServiceCount;
        }
    }

    public boolean checkCompanyProductCount(Long companyId,String serviceName,Integer reduceNum){
        List<CompanyServiceCount> companyServiceCounts = countMapper.selectByCompanyId(companyId, serviceName);
        if (CollectionUtils.isEmpty(companyServiceCounts)) {
            return false;
        }else{
            CompanyServiceCount companyServiceCount = companyServiceCounts.get(0);
            if (companyServiceCount.getProductServiceNum() < reduceNum) {
                return false;
            }
            return true;
        }
    }

    public int companyProductCount(Long companyId,String serviceName){
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
