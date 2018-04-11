package com.lcdt.userinfo.rpc;

import com.lcdt.userinfo.model.FeeProperty;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/11.
 */
public interface FinanceRpcService {
    List<FeeProperty> getFeePropertyList(Map m);
    List<FeeProperty> selectByCondition(Map m);
}
