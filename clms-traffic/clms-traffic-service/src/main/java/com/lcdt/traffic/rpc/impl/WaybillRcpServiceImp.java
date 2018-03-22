package com.lcdt.traffic.rpc.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.dao.WaybillMapper;
import com.lcdt.traffic.dto.DriverWaybillListParsmsDto;
import com.lcdt.traffic.dto.DriverWaybillParamsDto;
import com.lcdt.traffic.model.Waybill;
import com.lcdt.traffic.model.WaybillDao;
import com.lcdt.traffic.service.WaybillRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lyqishan on 2018/3/19
 */
@Service
public class WaybillRcpServiceImp implements WaybillRpcService {

    @Autowired
    private WaybillMapper waybillMapper;

    @Override
    public PageInfo queryDriverWaybillList(DriverWaybillListParsmsDto dto) {
        List<WaybillDao> resultList = null;
        PageInfo page = null;
        int pageNo = 1;
        int pageSize = 0; //0表示所有
        if (dto.getPageNo()>0) {
            pageNo=dto.getPageNo();
        }
        if (dto.getPageSize()>0) {
            pageSize=dto.getPageSize();
        }
        Map map= new HashMap();

        map.put("driverId",dto.getDriverId());
        map.put("waybillStatus",dto.getWaybillStatus());
        PageHelper.startPage(pageNo, pageSize);
        resultList = waybillMapper.selectDriverByCondition(map);
        page = new PageInfo(resultList);
        return page;
    }



    @Override
    public Waybill modifyWaybillStatusByDriver(DriverWaybillParamsDto dto) {
        Waybill waybill=null;
        Map map=new HashMap();
        map.put("driverId",dto.getDriverId());
        map.put("updateName",dto.getUpdateName());
        map.put("updateId",dto.getUpdateId());
        map.put("waybillStatus",dto.getWaybillStatus());
        map.put("waybillIds",dto.getWaybillIds());
        int result=waybillMapper.updateWaybillByDriver(map);
        if(result>0){
            List<WaybillDao> resultList=waybillMapper.selectWaybillByWaybillIds(dto.getWaybillIds().toString());
            waybill=resultList.get(0);
        }else {
            throw new RuntimeException("修改失败");
        }
        return waybill;

    }

    @Override
    public Waybill modifyWaybillReceiptByDriver(DriverWaybillParamsDto dto) {
        Waybill waybill=null;
        Map map=new HashMap();
        map.put("driverId",dto.getDriverId());
        map.put("updateName",dto.getUpdateName());
        map.put("updateId",dto.getUpdateId());
        map.put("waybillIds",dto.getWaybillIds());
        map.put("electronicalReceipt",dto.getElectronicalReceipt());
        int result=waybillMapper.updateWaybillByDriver(map);
        if(result>0){
            List<WaybillDao> resultList=waybillMapper.selectWaybillByWaybillIds(dto.getWaybillIds().toString());
            waybill=resultList.get(0);
        }else {
            throw new RuntimeException("上传失败");
        }
        return waybill;
    }
}
