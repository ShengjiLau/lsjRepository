package com.lcdt.traffic.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.notify.model.DefaultNotifyReceiver;
import com.lcdt.notify.model.DefaultNotifySender;
import com.lcdt.notify.model.TrafficStatusChangeEvent;
import com.lcdt.traffic.dao.WaybillMapper;
import com.lcdt.traffic.dao.WaybillTransferRecordMapper;
import com.lcdt.traffic.model.WaybillTransferRecord;
import com.lcdt.traffic.notify.ClmsNotifyProducer;
import com.lcdt.traffic.notify.CommonAttachment;
import com.lcdt.traffic.service.WaybillTransferRecordService;
import com.lcdt.traffic.web.dto.WaybillTransferRecordDto;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.service.CompanyService;
import com.lcdt.userinfo.service.UserService;
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

    @Autowired
    private ClmsNotifyProducer producer;

    @Reference
    private UserService userService;

    @Autowired
    private WaybillMapper waybillMapper; //运单

    @Override
    public int addWaybillTransferRecord(WaybillTransferRecordDto dto) {
        int result=0;
        WaybillTransferRecord waybillTransferRecord=new WaybillTransferRecord();
        BeanUtils.copyProperties(dto,waybillTransferRecord);
        result+= waybillTransferRecordMapper.insert(waybillTransferRecord);




        return result;
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

    private void sendNotify(WaybillTransferRecordDto dto){

    }
}
