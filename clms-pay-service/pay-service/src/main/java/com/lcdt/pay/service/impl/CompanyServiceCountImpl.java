package com.lcdt.pay.service.impl;

import com.lcdt.pay.dao.CompanyServiceCountMapper;
import com.lcdt.pay.dao.ServiceProductMapper;
import com.lcdt.pay.model.CompanyServiceCount;
import com.lcdt.pay.model.ServiceProduct;
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

    @Autowired
    ServiceProductMapper serviceProductMapper;

    private static final Logger log = LoggerFactory.getLogger(CompanyServiceCountImpl.class);


    //后台管理员设置增加
    public CompanyServiceCount addCountNum(Long companyId,String serviceName,Integer num,String operationUserName){

        final List<ServiceProduct> serviceProducts = serviceProductMapper.selectProductByName(serviceName);
        if (CollectionUtils.isEmpty(serviceProducts)) {
            throw new RuntimeException("产品不存在");
        }
        final List<CompanyServiceCount> companyServiceCounts = countMapper.selectByCompanyId(companyId, serviceName);
        CompanyServiceCount count = null;
        if (CollectionUtils.isEmpty(companyServiceCounts)) {
            final CompanyServiceCount companyServiceCount = new CompanyServiceCount();
            companyServiceCount.setProductName(serviceName);
            companyServiceCount.setCompanyId(companyId);
            companyServiceCount.setProductServiceNum(num);
            countMapper.insert(companyServiceCount);
        }else{
            final CompanyServiceCount companyServiceCount = companyServiceCounts.get(0);
            companyServiceCount.setProductServiceNum(companyServiceCount.getProductServiceNum() + num);
            countMapper.updateByPrimaryKey(companyServiceCount);
        }
        productCountService.logAddProductCount(serviceProducts.get(0).getProductName(), "管理员增加余额", num, operationUserName, companyId, count.getProductServiceNum(), ProductCountServiceImpl.CountLogType.ADMIN_TOPUP);
        return count;
    }



    @Override
    public List<CompanyServiceCount> companyServiceCount(Long companyId) {
        List<CompanyServiceCount> companyServiceCounts = countMapper.selectByCompanyId(companyId, null);
        return companyServiceCounts;
    }

    public CompanyServiceCount reduceCompanyProductCount(Long companyId,String serviceName,Integer reduceNum){
        return reduceCompanyProductCount(companyId, serviceName, reduceNum, null, null);
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
            if (username != null) {
                productCountService.reduceProductCount(serviceName,des,reduceNum,username,companyId,companyServiceCount.getProductServiceNum());
            }
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
