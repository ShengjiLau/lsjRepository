package com.lcdt.traffic.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.dao.WaybillTransferRecordMapper;
import com.lcdt.traffic.model.WaybillTransferRecord;
import com.lcdt.traffic.service.WaybillTransferRecordService;
import com.lcdt.traffic.web.dto.WaybillTransferRecordDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lyqishan on 2017/12/28
 */
@Transactional
@Service
public class WaybillTransferRecordServiceImpl implements WaybillTransferRecordService {
    @Autowired
    private WaybillTransferRecordMapper waybillTransferRecordMapper;
    @Override
    public int addWaybillTransferRecord(WaybillTransferRecordDto dto) {
        WaybillTransferRecord waybillTransferRecord=new WaybillTransferRecord();
        BeanUtils.copyProperties(dto,waybillTransferRecord);
        return waybillTransferRecordMapper.insert(waybillTransferRecord);
    }

    @Override
    public int deleteWaybillTransferRecord(Long id, Long companyId) {
        return 0;
    }

    @Override
    public int modifyWaybillTransferRecord(WaybillTransferRecordDto dto) {
        WaybillTransferRecord waybillTransferRecord=new WaybillTransferRecord();
        BeanUtils.copyProperties(dto,waybillTransferRecord);
        return waybillTransferRecordMapper.updateTranserRecordByIdAndXCompanyId(waybillTransferRecord);
    }

    @Override
    public WaybillTransferRecord queryWaybillTransferRecord(WaybillTransferRecordDto dto) {
        WaybillTransferRecord waybillTransferRecord =new WaybillTransferRecord();
        BeanUtils.copyProperties(dto,waybillTransferRecord);
        return waybillTransferRecordMapper.selectTranserRecordByIdAndXCompanyId(waybillTransferRecord);
    }

    @Override
    public PageInfo queryWaybillTransferRecordList(WaybillTransferRecordDto dto, PageInfo pageInfo) {
        List<WaybillTransferRecord> resultList=null;
        PageInfo page=null;
        WaybillTransferRecord waybillTransferRecord=new WaybillTransferRecord();
        BeanUtils.copyProperties(dto,waybillTransferRecord);
        PageHelper.startPage(pageInfo.getPageNum(),pageInfo.getPageSize());
        resultList=waybillTransferRecordMapper.selectTranserRecordByCondition(waybillTransferRecord);
        page=new PageInfo(resultList);
        return page;
    }
}
