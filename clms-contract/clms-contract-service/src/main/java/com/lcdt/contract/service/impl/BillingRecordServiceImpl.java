package com.lcdt.contract.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.contract.dao.BillingRecordMapper;
import com.lcdt.contract.model.BillingRecord;
import com.lcdt.contract.service.BillingRecordService;
import com.lcdt.contract.web.dto.BillingRecordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2018-05-07
 */
@Service
@Transactional
public class BillingRecordServiceImpl implements BillingRecordService {

    @Autowired
    private BillingRecordMapper billingRecordMapper;

    @Override
    public PageInfo<List<BillingRecord>> billingRecordList(BillingRecordDto billingRecordDto, PageInfo pageInfo) {
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        List<BillingRecord> billingRecordList = billingRecordMapper.selectByCondition(billingRecordDto);
        PageInfo page = new PageInfo(billingRecordList);
        return page;
    }

    @Override
    public int addBillingRecord(BillingRecord billingRecord) {
        int row = billingRecordMapper.insertSelective(billingRecord);
        return row;
    }
}
