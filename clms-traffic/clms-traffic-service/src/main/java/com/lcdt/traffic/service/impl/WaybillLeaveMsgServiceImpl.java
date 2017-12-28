package com.lcdt.traffic.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.dao.WaybillLeaveMsgMapper;
import com.lcdt.traffic.model.WaybillLeaveMsg;
import com.lcdt.traffic.service.WaybillLeaveMsgService;
import com.lcdt.traffic.web.dto.WaybillLeaveMsgDto;
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
public class WaybillLeaveMsgServiceImpl implements WaybillLeaveMsgService {
    @Autowired
    private WaybillLeaveMsgMapper waybillLeaveMsgMapper;
    @Override
    public int addWaybillLeaveMsg(WaybillLeaveMsgDto dto) {
        WaybillLeaveMsg waybillLeaveMsg=new WaybillLeaveMsg();
        BeanUtils.copyProperties(dto,waybillLeaveMsg);
        return waybillLeaveMsgMapper.insert(waybillLeaveMsg);
    }

    @Override
    public int deleteWaybillLeaveMsg(Long id, Long companyId) {
        return 0;
    }

    @Override
    public int modifyWaybillLeaveMsg(WaybillLeaveMsgDto dto) {
        WaybillLeaveMsg waybillLeaveMsg =new WaybillLeaveMsg();
        BeanUtils.copyProperties(dto,waybillLeaveMsg);
        return waybillLeaveMsgMapper.updateByIdAndCompanyId(waybillLeaveMsg);
    }

    @Override
    public WaybillLeaveMsg queryWaybillLeaveMsg(Long id, Long companyId) {
        WaybillLeaveMsg waybillLeaveMsg=new WaybillLeaveMsg();
        waybillLeaveMsg.setId(id);
        waybillLeaveMsg.setCompanyId(companyId);
        return waybillLeaveMsgMapper.selectByIdAndCompanyId(waybillLeaveMsg);
    }

    @Override
    public PageInfo queryWaybillLeaveMsgList(WaybillLeaveMsgDto dto, PageInfo pageInfo) {
        List<WaybillLeaveMsg> resultList=null;
        PageInfo page=null;
        WaybillLeaveMsg waybillLeaveMsg=new WaybillLeaveMsg();
        BeanUtils.copyProperties(dto,waybillLeaveMsg);
        PageHelper.startPage(pageInfo.getPageNum(),pageInfo.getPageSize());
        resultList=waybillLeaveMsgMapper.selectByCondition(waybillLeaveMsg);
        page=new PageInfo(resultList);
        return page;
    }
}
