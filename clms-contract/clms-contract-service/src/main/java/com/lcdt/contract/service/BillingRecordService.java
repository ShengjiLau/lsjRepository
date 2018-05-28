package com.lcdt.contract.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.contract.model.BillingRecord;
import com.lcdt.contract.web.dto.BillingRecordDto;

import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2018-05-07
 */
public interface BillingRecordService {


    /**
     * 获取开票记录列表
     *
     * @param billingRecordDto
     * @return
     */
    PageInfo<List<BillingRecord>> billingRecordList(BillingRecordDto billingRecordDto, PageInfo pageInfo);

    /**
     * 新增开票记录
     *
     * @param billingRecord
     * @return
     */
    int addBillingRecord(BillingRecord billingRecord);


    /**
     * 根据主键获取开票记录详情
     * @param brId
     * @return
     */
    BillingRecord billingRecordDetail(Long brId);


}
