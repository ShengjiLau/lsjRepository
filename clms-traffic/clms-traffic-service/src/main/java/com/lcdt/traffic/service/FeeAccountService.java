package com.lcdt.traffic.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.dto.WaybillOwnListParamsDto;
import com.lcdt.traffic.model.FeeAccount;

import java.util.Map;

/**
 * Created by liz on 2018/3/30.
 */
public interface FeeAccountService {
    /**
     * 记账列表
     * @param dto
     * @return
     */
    PageInfo waybillFeeList(WaybillOwnListParamsDto dto);
    /**
     * 费用类型列表查询
     * @param m
     * @return
     */
    PageInfo feePropertyList(Map m);
    /**
     * 新增费用类型
     * @param feeAccount
     * @return
     */
    int addFeeProperty(FeeAccount feeAccount);
    /**
     * 编辑费用类型
     * @param feeAccount
     * @return
     */
    int modifyFeeProperty(FeeAccount feeAccount);
    /**
     * 删除费用类型
     * @param accountId
     * @return
     */
    int modifyFeePropertyIsDelete(Long accountId);
}
