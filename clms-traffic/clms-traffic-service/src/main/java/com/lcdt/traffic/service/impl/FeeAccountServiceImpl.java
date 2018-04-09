package com.lcdt.traffic.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.dao.FeeAccountMapper;
import com.lcdt.traffic.dao.WaybillItemsMapper;
import com.lcdt.traffic.dao.WaybillMapper;
import com.lcdt.traffic.model.FeeAccount;
import com.lcdt.traffic.model.WaybillItems;
import com.lcdt.traffic.service.FeeAccountService;
import com.lcdt.traffic.web.dto.FeeAccountDto;
import com.lcdt.traffic.web.dto.WaybillFeeDto;
import com.lcdt.traffic.web.dto.WaybillOwnListParamsDto;
import com.lcdt.util.ClmsBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.lcdt.traffic.dto.WaybillOwnListParamsDto;

/**
 * Created by liz on 2018/3/30.
 */
@Service
public class FeeAccountServiceImpl implements FeeAccountService{
    @Autowired
    private WaybillMapper waybillMapper;
    @Autowired
    private WaybillItemsMapper waybillItemsMapper;
    @Autowired
    private FeeAccountMapper feeAccountMapper;

    @Override
    public PageInfo waybillFeeList(WaybillOwnListParamsDto dto){
        List<WaybillFeeDto> resultList = null;

        PageInfo page = null;
        int pageNo = 1;
        int pageSize = 0; //0表示所有
//        if (dto.getPageNo() != null) {
//            pageNo = dto.getPageNo();
//        }
//        if (dto.getPageSize() != null) {
//            pageSize = dto.getPageSize();
//        }
        PageHelper.startPage(pageNo, pageSize);
        Map map= ClmsBeanUtil.beanToMap(dto);
        resultList = waybillMapper.selectWaybillFeeByCondition(map);
        page = new PageInfo(resultList);

        return page;
    }
    @Override
    public Map feeAccountPage(Map m){
        List<FeeAccountDto> feeAccountDtoList = feeAccountMapper.selectFeeAccountDetail(m);
        List<WaybillItems> waybillItemsList = waybillItemsMapper.selectByWaybillId(Long.parseLong(m.get("waybillId").toString()));
        Map map = new HashMap();
        map.put("feeAccountDtoList", feeAccountDtoList);
        map.put("waybillItemsList", waybillItemsList);
        return m;
    }
    @Override
    public PageInfo feeAccountList(FeeAccountDto dto){
        List<FeeAccountDto> resultList = null;
        PageInfo page = null;
        int pageNo = 1;
        int pageSize = 0; //0表示所有
        if (dto.getPageNum() != null) {
            pageNo = dto.getPageNum();
        }
        if (dto.getPageSize() != null) {
            pageSize = dto.getPageSize();
        }
        PageHelper.startPage(pageNo, pageSize);
        Map map= ClmsBeanUtil.beanToMap(dto);
        resultList = feeAccountMapper.selectOwnByCondition(map);
        page = new PageInfo(resultList);
        return page;
    }
    @Override
    public PageInfo feePropertyList(Map m) {
        return null;
    }

    @Override
    public int addFeeProperty(FeeAccount feeAccount) {
        return 0;
    }

    @Override
    public int modifyFeeProperty(FeeAccount feeAccount) {
        return 0;
    }

    @Override
    public int modifyFeePropertyIsDelete(Long accountId) {
        return 0;
    }
}
