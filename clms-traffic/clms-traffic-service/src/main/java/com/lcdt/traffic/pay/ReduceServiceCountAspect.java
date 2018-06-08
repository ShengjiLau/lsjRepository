package com.lcdt.traffic.pay;

import com.lcdt.pay.rpc.CompanyServiceCountService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class ReduceServiceCountAspect {


    @Around("@annotation(reduceServiceCount)")
    public void checkServiceNumEnough(ProceedingJoinPoint joinPoint, ReduceServiceCount reduceServiceCount) {
    }

}
