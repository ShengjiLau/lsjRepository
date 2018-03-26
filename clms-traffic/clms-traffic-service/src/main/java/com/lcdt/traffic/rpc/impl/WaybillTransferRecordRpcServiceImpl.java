package com.lcdt.traffic.rpc.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.dao.WaybillMapper;
import com.lcdt.traffic.dao.WaybillTransferRecordMapper;
import com.lcdt.traffic.dto.WaybillTransferRecordDto;
import com.lcdt.traffic.model.Waybill;
import com.lcdt.traffic.model.WaybillTransferRecord;
import com.lcdt.traffic.notify.ClmsNotifyProducer;
import com.lcdt.traffic.notify.WaybillSenderNotify;
import com.lcdt.traffic.service.WaybillTransferRecordRpcService;
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
public class WaybillTransferRecordRpcServiceImpl implements WaybillTransferRecordRpcService {
    @Autowired
    private WaybillTransferRecordMapper waybillTransferRecordMapper;

    @Autowired
    private ClmsNotifyProducer producer;

    @Reference
    private UserService userService;

    @Autowired
    private WaybillMapper waybillMapper; //运单

    @Autowired
    private WaybillSenderNotify waybillSenderNotify;

    @Override
    public int addWaybillTransferRecord(WaybillTransferRecordDto dto) {
        int result=0;
        WaybillTransferRecord waybillTransferRecord=new WaybillTransferRecord();
        BeanUtils.copyProperties(dto,waybillTransferRecord);
        result+= waybillTransferRecordMapper.insert(waybillTransferRecord);

        //1、我的运单 的 换车记录  2、客户运单的换车记录
        if(dto.getType()==1){
            //我的运单 换车记录发送消息通知
            waybillSenderNotify.ownTranserRecordSendNotify(dto.getWaybillId().toString(),dto.getCompanyId(),dto.getCreateId(),waybillTransferRecord);
        }else if(dto.getType()==2){
            //客户运单 换车记录发送消息知道
            System.out.println("客户换车——————————————————————————————————————————————");
            waybillSenderNotify.customerTranserRecordSendNotify(dto.getWaybillId().toString(),dto.getCompanyId(),dto.getCreateId(),waybillTransferRecord);
        }

        //更新主单里的司机、车辆信息
        Waybill waybill =new Waybill();
        BeanUtils.copyProperties(dto,waybill);
        waybill.setId(dto.getWaybillId());
        result+=waybillMapper.updateWaybillByTransferRecord(waybill);

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
}
