package com.lcdt.traffic.pay;

import com.lcdt.pay.rpc.CompanyServiceCountService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ReduceServiceCountAspect {

    @Autowired
    CompanyServiceCountService countService;

    @Around("@annotation(reduceServiceCount)")
    public void checkServiceNumEnough(ProceedingJoinPoint joinPoint, ReduceServiceCount reduceServiceCount) {
        boolean b = countService.checkCompanyProductCount(reduceServiceCount.companyId(), reduceServiceCount.serviceName(), reduceServiceCount.reduceCount());
        if (b) {
            try {
                joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }else {
            throw new RuntimeException("可用订单服务次数不足");
        }
    }

}
