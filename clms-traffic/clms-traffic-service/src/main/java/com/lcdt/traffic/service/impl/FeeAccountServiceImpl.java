package com.lcdt.traffic.service.impl;

import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.dto.WaybillOwnListParamsDto;
import com.lcdt.traffic.model.FeeAccount;
import com.lcdt.traffic.service.FeeAccountService;

import java.util.Map;

/**
 * Created by liz on 2018/3/30.
 */
public class FeeAccountServiceImpl implements FeeAccountService{
    @Override
    public PageInfo waybillFeeList(WaybillOwnListParamsDto dto){
//        List<WaybillDao> resultList = null;
//
//        PageInfo page = null;
//        int pageNo = 1;
//        int pageSize = 0; //0表示所有
//        if (dto.getPageNo() != null) {
//            pageNo = dto.getPageNo();
//        }
//        if (dto.getPageSize() != null) {
//            pageSize = dto.getPageSize();
//        }
//        PageHelper.startPage(pageNo, pageSize);
//        Map map= ClmsBeanUtil.beanToMap(dto);
//        resultList = waybillMapper.selectOwnByCondition(map);
//        page = new PageInfo(resultList);
//
//        return page;
        return null;
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
