package com.lcdt.traffic.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.dao.FeeFlowLogMapper;
import com.lcdt.traffic.dao.FeeFlowMapper;
import com.lcdt.traffic.dto.*;
import com.lcdt.traffic.service.IFeeFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by yangbinq on 2018/4/12.
 */
@Service
public class FeeFlowServiceImpl implements IFeeFlowService {

    @Autowired
    private FeeFlowMapper feeFlowMapper;


    @Transactional(readOnly = true)
    @Override
    public PageInfo feeFlowList(FeeFlow4SearchParamsDto dto) {
        int pageNo = 1;
        int pageSize = 0; //0表示所有
        if (dto.getPageNo()>0) {
            pageNo = dto.getPageNo();
        }
        if (dto.getPageSize()>0) {
            pageSize = dto.getPageSize();
        }
        PageHelper.startPage(pageNo, pageSize);
        List<FeeFlow4SearchResultDto> list = feeFlowMapper.searchCondition(dto);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Map<String, Object>> receivePayStat(ReceivePayParamsDto dto) {
        return feeFlowMapper.receivePayStat(dto);
    }


    @Transactional(readOnly = true)
    @Override
    public PageInfo profitStat(ProfitStatParamsDto dto) {
        int pageNo = 1;
        int pageSize = 0; //0表示所有
        if (dto.getPageNo()>0) {
            pageNo = dto.getPageNo();
        }
        if (dto.getPageSize()>0) {
            pageSize = dto.getPageSize();
        }
        PageHelper.startPage(pageNo, pageSize);
        List<ProfitStatResultDto> list = feeFlowMapper.profitStat(dto);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }
}
