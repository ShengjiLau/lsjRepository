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
import com.lcdt.traffic.web.dto.FeeAccountWaybillDto;
import com.lcdt.userinfo.model.FeeProperty;
import com.lcdt.userinfo.rpc.FinanceRpcService;
import com.lcdt.util.ClmsBeanUtil;
import jdk.nashorn.internal.ir.annotations.Reference;
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
    @Reference
    private FinanceRpcService financeRpcService;

    @Override
    public PageInfo feeAccountWaybillList(Map map){
        List<FeeAccountWaybillDto> resultList = null;

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
        PageHelper.startPage(pageNo, pageSize);
        resultList = waybillMapper.selectFeeAccountWaybillByCondition(map);
        page = new PageInfo(resultList);

        return page;
    }
    @Override
    public Map feeAccountPage(Map m){
        List<WaybillItems> waybillItemsList = waybillItemsMapper.selectByWaybillId(Long.parseLong(m.get("waybillId").toString()));
        List<FeeAccountDto> feeAccountDtoList = feeAccountMapper.selectFeeAccountDetail(m);
        m.put("isShow", (short)0);
        List<FeeProperty> showPropertyList = financeRpcService.selectByCondition(m);
        m.put("isShow", (short)1);
        List<FeeProperty> hidePropertyList = financeRpcService.selectByCondition(m);
        Map map = new HashMap();
        map.put("waybillItemsList", waybillItemsList);
        map.put("feeAccountDtoList", feeAccountDtoList);
        map.put("showPropertyList", showPropertyList);
        map.put("hidePropertyList", hidePropertyList);
        return m;
    }
    @Override
    public int feeAccountSave(Map map){

        return 0;
    }
    @Override
    public List<FeeAccountDto> selectFlowByWaybillId(Map m){
        List<FeeAccountDto> list = feeAccountMapper.selectFlowByWaybillId(m);
        return list;
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

    @Override
    public int feeAccountAudit(Map map) {
        int result = feeAccountMapper.auditByAccountIds(map);
        return result;
    }

//    @Override
//    public List feeAccountReconcileGroup(Map map) {
//        List list = feeAccountMapper.reconcileByAccountIds(map);
//        return list;
//    }
}
