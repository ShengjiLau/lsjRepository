package com.lcdt.traffic.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.dao.WaybillPositionSettingMapper;
import com.lcdt.traffic.model.Waybill;
import com.lcdt.traffic.model.WaybillPositionSetting;
import com.lcdt.traffic.service.WaybillPositionSettingService;
import com.lcdt.traffic.web.dto.WaybillPositionSettingDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by lyqishan on 2018/1/15
 */
@Transactional
@Service
public class WaybillPositionSettingServiceImpl implements WaybillPositionSettingService{

    @Autowired
    private WaybillPositionSettingMapper waybillPositionSettingMapper;

    @Override
    public int addWaybillPositionSetting(WaybillPositionSettingDto dto) {
        WaybillPositionSetting waybillPositionSetting=new WaybillPositionSetting();
        BeanUtils.copyProperties(dto,waybillPositionSetting);
        return waybillPositionSettingMapper.insert(waybillPositionSetting);

    }

    @Override
    public int modifyWaybillPositionSetting(WaybillPositionSettingDto dto) {
        WaybillPositionSetting waybillPositionSetting=new WaybillPositionSetting();
        BeanUtils.copyProperties(dto,waybillPositionSetting);
        return waybillPositionSettingMapper.updateByWpsIdAndXCompanyId(waybillPositionSetting);
    }

    @Override
    public WaybillPositionSetting queryWaybillPositionSetting(Map map) {
        return waybillPositionSettingMapper.selectByWpsIdAndXCompanyId(map);
    }

    @Override
    public PageInfo<List<WaybillPositionSetting>> queryWaybillPositionSettingList(Map map) {
        List<WaybillPositionSetting> resultList = null;
        PageInfo page = null;
        int pageNo = 1;
        int pageSize = 0; //0表示所有
        if (map.containsKey("pageNo")) {
            if (map.get("pageNo") != null) {
                pageNo = (Integer) map.get("pageNo");
            }
        }
        if (map.containsKey("pageSize")) {
            if (map.get("pageSize") != null) {
                pageSize = (Integer) map.get("pageSize");
            }
        }
        PageHelper.startPage(pageNo,pageSize);
        resultList=waybillPositionSettingMapper.selectByWaybillIdAndXCompanyId(map);
        page=new PageInfo(resultList);
        return page;
    }
}
