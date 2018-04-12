package com.lcdt.traffic.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.model.FeeAccount;
import com.lcdt.traffic.web.dto.FeeAccountDto;
import com.lcdt.traffic.web.dto.FeeAccountSaveParamsDto;

import java.util.List;
import java.util.Map;

/**
 * Created by liz on 2018/3/30.
 */
public interface FeeAccountService {
    /**
     * 记账运单列表
     * @param map
     * @return
     */
    PageInfo feeAccountWaybillList(Map map);
    /**
     * 记账（进入记账页面）
     * @param m
     * @return
     */
    Map feeAccountPage(Map m);
    /**
     * 保存/修改记账单及流水
     * @param dto
     * @return
     */
    boolean feeAccountSave(FeeAccountSaveParamsDto dto);
    /**
     * 查询单个运单费用明细
     * @return
     */
    List<FeeAccountDto> selectFlowByWaybillId(Map m);
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

    /**
     * 记账单审核
     * @param map
     * @return
     */
    int feeAccountAudit(Map map);

    /**
     * 记账单对账
     * @param map
     * @return
     */
//    List feeAccountWaybillList(Map map);
}
