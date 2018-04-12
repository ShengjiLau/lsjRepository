package com.lcdt.traffic.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.dao.FeeFlowLogMapper;
import com.lcdt.traffic.dao.FeeFlowMapper;
import com.lcdt.traffic.dto.FeeFlow4SearchParamsDto;
import com.lcdt.traffic.dto.FeeFlow4SearchResultDto;
import com.lcdt.traffic.service.IFeeFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by yangbinq on 2018/4/12.
 */
@Service
public class FeeFlowServiceImpl implements IFeeFlowService {


    @Autowired
    private FeeFlowLogMapper feeFlowLogMapper;

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
}
