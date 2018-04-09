package com.lcdt.traffic.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.model.FeeAccount;
import com.lcdt.traffic.web.dto.FeeAccountDto;
import com.lcdt.traffic.web.dto.WaybillOwnListParamsDto;

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
     * 记账（进入记账页面）
     * @param m
     * @return
     */
    Map feeAccountPage(Map m);

    /**
     * 记账单列表
     * @param dto
     * @return
     */
    PageInfo feeAccountList(FeeAccountDto dto);
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
