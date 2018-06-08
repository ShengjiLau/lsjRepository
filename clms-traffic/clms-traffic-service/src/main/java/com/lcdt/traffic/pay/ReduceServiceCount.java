package com.lcdt.traffic.pay;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ReduceServiceCount {

    String serviceName();

    int reduceCount();

    long companyId();
}
