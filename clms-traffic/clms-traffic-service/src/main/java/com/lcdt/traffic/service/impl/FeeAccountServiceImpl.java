

package com.lcdt.traffic.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.traffic.dao.*;
import com.lcdt.traffic.model.*;
import com.lcdt.traffic.service.FeeAccountService;
import com.lcdt.traffic.web.dto.*;
import com.lcdt.userinfo.model.FeeProperty;
import com.lcdt.userinfo.rpc.FinanceRpcService;
import com.lcdt.util.ClmsBeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

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
    @Autowired
    private FeeFlowMapper feeFlowMapper;
    @Autowired
    private FeeFlowLogMapper feeFlowLogMapper;
    @Reference
    FinanceRpcService financeRpcService;
    @Autowired
    private CustomerCompanyIds customerCompanyIds;
    @Autowired
    private ReconcileMapper reconcileMapper;

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
    public FeeAccountWaybillDto feeAccountWaybillFeeTotal(Map map){
        FeeAccountWaybillDto dto;
        if(Integer.parseInt(map.get("waybillType").toString()) == 0){
            dto = waybillMapper.selectFeeAccountMyWaybillFeeTotalByCondition(map);
        }else {
            Map cMapIds = customerCompanyIds.getCustomerCompanyIds(map);
            map.put("companyIds", cMapIds.get("companyIds") != null ?
                                    cMapIds.get("companyIds").toString().replaceAll("company_id", "w.company_id") :
                                    cMapIds.get("companyIds"));
            map.put("carrierCompanyId", map.get("companyId"));
            map.remove("companyId");
            map.remove("customerName");
            dto = waybillMapper.selectFeeAccountCustomerWaybillFeeTotalByCondition(map);
        }
        return dto;
    }
    @Override
    public PageInfo<List<FeeAccountWaybillDto>> feeAccountCustomerWaybillList(Map map) {
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
        Map cMapIds = customerCompanyIds.getCustomerCompanyIds(map);
        map.put("companyIds", cMapIds.get("companyIds"));
        map.put("carrierCompanyId", map.get("companyId"));
        map.remove("companyId");
        map.remove("customerName");
        PageHelper.startPage(pageNo, pageSize);
        resultList = waybillMapper.selectFeeAccountCustomerWaybillByCondition(map);
        page = new PageInfo(resultList);
        return page;
    }
    @Override
    public Map feeAccountPage(Map m){
        Map map = new HashMap();
        List<FeeAccountDto> feeAccountDtoList = feeAccountMapper.selectFeeAccountDetail(m);
        List<WaybillItems> waybillItemsList = waybillItemsMapper.selectByWaybillId(Long.parseLong(m.get("waybillId").toString()));
        m.put("isShow", (short)0);
        List<FeeProperty> showPropertyList = financeRpcService.selectByCondition(m);
        m.put("isShow", (short)1);
        List<FeeProperty> hidePropertyList = financeRpcService.selectByCondition(m);
        map.put("waybillItemsList", waybillItemsList);
        map.put("feeAccountDtoList", feeAccountDtoList);
        map.put("showPropertyList", showPropertyList);
        map.put("hidePropertyList", hidePropertyList);
        Waybill waybill = waybillMapper.selectByPrimaryKey(Long.parseLong(m.get("waybillId").toString()));
        if(waybill != null){
            map.put("waybillId", waybill.getId());
            map.put("waybillCode", waybill.getWaybillCode());
        }else{
            map.put("waybillId", null);
            map.put("waybillCode", null);
        }
        return map;
    }
    @Override
    public boolean feeAccountSave(FeeAccountSaveParamsDto saveParamsDto){
        try{
            Waybill waybill = waybillMapper.selectByPrimaryKey(saveParamsDto.getWaybillId());
            List<FeeAccountDto> dtoList = saveParamsDto.getFeeAccountDtoList();//所有记账单（包含流水)
            //删除已有的对账单、流水及流水修改日志信息（is_delete=1）
            feeAccountMapper.updateIsDeleteByCondition(saveParamsDto);
            feeFlowMapper.updateIsDeleteByCondition(saveParamsDto);
            feeFlowLogMapper.updateIsDeleteByCondition(saveParamsDto);

            if(dtoList != null && dtoList.size() > 0){
                for(FeeAccountDto dto : dtoList){
                    FeeAccount account = new FeeAccount();
                    BeanUtils.copyProperties(dto, account); //记账单复制对象属性
                    account.setCompanyId(saveParamsDto.getCompanyId());
                    account.setWaybillId(saveParamsDto.getWaybillId());
                    account.setWaybillCode(saveParamsDto.getWaybillCode());
                    account.setGroupId(saveParamsDto.getGroupId());
                    account.setGroupName(saveParamsDto.getGroupName());
                    account.setOperatorId(saveParamsDto.getUserId());
                    account.setOperatorName(saveParamsDto.getRealName());
                    account.setIsReceivable(saveParamsDto.getIsReceivable());
                    account.setAuditStatus((short)0);
                    account.setCreateDate(new Date());
                    account.setIsDeleted((short)0);
                    feeAccountMapper.insert(account);

                    if(dto.getFeeFlowList() != null && dto.getFeeFlowList().size() > 0){
                        List<FeeFlow> insertFeeFlowList = new ArrayList<>();//新增的流水
                        for(FeeFlow flow : dto.getFeeFlowList()) {
                            if(flow.getFlowId() == null) {
                                flow.setAccountId(account.getAccountId());
                                flow.setWaybillId(saveParamsDto.getWaybillId());
                                flow.setWaybillCode(saveParamsDto.getWaybillCode());
                                flow.setWaybillDate(waybill.getCreateDate());
                                flow.setOriginalMoney(flow.getMoney());
                                flow.setGroupId(saveParamsDto.getGroupId());
                                flow.setCreateId(saveParamsDto.getUserId());
                                flow.setCreateName(saveParamsDto.getRealName());
                                flow.setCreateDate(new Date());
                                flow.setIsReceivable(saveParamsDto.getIsReceivable());
                                flow.setCompanyId(saveParamsDto.getCompanyId());
                                flow.setIsDeleted((short)0);
                                insertFeeFlowList.add(flow);
                            }
                        }
                        if(insertFeeFlowList.size() > 0){
                            feeFlowMapper.insertBatch(insertFeeFlowList);
                        }
                    }
                }
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public List<FeeAccountDto> selectFlowByWaybillId(Map m){
        List<FeeAccountDto> list = feeAccountMapper.selectFlowByWaybillId(m);
        return list;
    }
    @Override
    public PageInfo feeAccountList(FeeAccountListParamsDto dto){
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
        resultList = feeAccountMapper.selectByCondition(map);
        page = new PageInfo(resultList);
        return page;
    }
    @Override
    public FeeAccountDto feeAccountFeeTotal(FeeAccountListParamsDto dto){
        Map map= ClmsBeanUtil.beanToMap(dto);
        FeeAccountDto resultDto = feeAccountMapper.selectByConditionFeeTotal(map);
        return resultDto;
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

    @Override
    public List feeAccountReconcilePage(Map map) {
        List list = feeAccountMapper.feeAccountGroupByReceivPayName(map);
        return list;
    }
    @Override
    public int feeAccountReconcileSave(List<Map<String,Object>> list, short payeeType) {
        int result = 0;
        if (list != null && list.size() > 0) {
            ReconcileDto dto = new ReconcileDto();
//            Date createTime = new Date();
            List<Reconcile> reconcileList = new ArrayList<>();
            for (Map<String, Object> m : list) {
                Reconcile reconcile = new Reconcile();
                reconcile.setCompanyId(SecurityInfoGetter.getCompanyId());
                reconcile.setTransportationExpenses(Double.parseDouble(m.get("freightTotal").toString()));//运费
                reconcile.setOtherExpenses(Double.parseDouble(m.get("otherFeeTaotal").toString()));//其他费用
                reconcile.setOperatorId(SecurityInfoGetter.getUser().getUserId());
                reconcile.setOperatorName(SecurityInfoGetter.getUser().getRealName());
//                reconcile.setCreateTime(createTime);
                reconcile.setCancelOk((short) 0);
                reconcile.setWaybillId(m.get("waybillIds").toString());
                reconcile.setAccountId(m.get("accountIds").toString());
                reconcile.setPayeeType(payeeType);
                reconcile.setPayerId(Long.parseLong(m.get("nameId").toString()));
                reconcile.setPayerName(m.get("name").toString());
                reconcile.setGroupId(Long.parseLong(m.get("groupId").toString()));
                reconcileList.add(reconcile);
            }
            result = reconcileMapper.insertByBatch(reconcileList);
            if(result == reconcileList.size()){
                for(Reconcile reconcile : reconcileList){
//                    reconcile.setReconcileCode("DZ" + DateUtility.date2String(new Date(),
//                            "yyyyMMdd") + reconcile.getReconcileId());
//                    //修改对账单号
//                    result += reconcileMapper.updateByPrimaryKeySelective(reconcile);
                    //修改每个对账单对应的记账单对账信息
                    dto.setReconcileId(reconcile.getReconcileId());
                    dto.setReconcileCode(reconcileMapper.selectByPrimaryKey(reconcile.getReconcileId()).getReconcileCode());
                    String[] accountIdStrArr = reconcile.getAccountId().split(",");
                    Long[] accountIds = new Long[accountIdStrArr.length];
                    for(int i=0; i<accountIdStrArr.length; i++){
                        accountIds[i] = Long.parseLong(accountIdStrArr[i]);
                    }
                    dto.setAccountIds(accountIds);
                    result += feeAccountMapper.updateReconcileCodeAndId(dto);
                }
            }
        }
        return result;
    }

    @Override
    public List<FeeAccountDto> feeAccountReconcileDetail(Long reconcileId) {
        List<FeeAccountDto> list = feeAccountMapper.selectByReconcileId(reconcileId);
        return list;
    }
}

