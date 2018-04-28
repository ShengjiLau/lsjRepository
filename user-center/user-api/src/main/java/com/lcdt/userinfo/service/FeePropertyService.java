package com.lcdt.userinfo.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.userinfo.model.FeeProperty;
import com.lcdt.userinfo.model.User;

import java.util.List;
import java.util.Map;

/**
 * Created by liz on 2018/3/29.
 */
public interface FeePropertyService {
    /**
     * 费用类型列表查询
     * @param m
     * @return
     */
    PageInfo feePropertyList(Map m);
    /**
     * 新增费用类型
     * @param feeProperty
     * @return
     */
    int addFeeProperty(FeeProperty feeProperty);
    /**
     * 编辑费用类型
     * @param feeProperty
     * @return
     */
    int modifyFeeProperty(FeeProperty feeProperty);
    /**
     * 删除费用类型
     * @param proId
     * @return
     */
    int modifyFeePropertyIsDelete(Long proId);
    /**
     * 费用类型list
     * @param m
     * @return
     */
    List<FeeProperty> getFeePropertyList(Map m);
    /**
     * 初始化运费费用类型
     */
    int initFeeProperty(User user, Long companyId);
}
