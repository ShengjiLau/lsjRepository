

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
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.tl.commons.util.StringUtility;

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
    @Autowired
    private FeeExchangeMapper feeExchangeMapper;

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
        List<FeeAccountDto> feeAccountDtoList = feeAccountMapper.selectWaybillFeeAccountDetail(m);
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
            map.put("groupId",(Integer)m.get("isOwn")==1?waybill.getGroupId():null);
            map.put("groupName",(Integer)m.get("isOwn")==1?waybill.getGroupName():null);
        }else{
            map.put("waybillId", null);
            map.put("waybillCode", null);
            map.put("groupId", null);
            map.put("groupName", null);
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
    public Map feeAccountDetail(Long accountId){
        Map<String,Object> map = new HashedMap();
        FeeAccountDto dto = feeAccountMapper.selectFeeAccountDetail(accountId);
        map.put("feeAccount", dto);
        map.put("waybillItems", waybillItemsMapper.selectByWaybillId(dto.getWaybillId()));
        return map;
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
    public List<Map<String,Object>> feeAccountReconcilePage(Map map) {
        List<Map<String,Object>> list = feeAccountMapper.feeAccountGroupByReceivPayName(map);
        return list;
    }
    @Override
    public boolean feeAccountReconcileSave(List<Map<String,Object>> list, short payeeType) {
        try{
            if (list != null && list.size() > 0) {
                ReconcileDto dto = new ReconcileDto();
                Date createTime = new Date();
                List<Reconcile> reconcileList = new ArrayList<>();
                for (Map<String, Object> m : list) {
                    Reconcile reconcile = new Reconcile();
                    reconcile.setCompanyId(SecurityInfoGetter.getCompanyId());
                    reconcile.setTransportationExpenses(Double.parseDouble(m.get("freightTotal").toString()));//运费
                    reconcile.setOtherExpenses(Double.parseDouble(m.get("otherFeeTotal").toString()));//其他费用
                    reconcile.setOperatorId(SecurityInfoGetter.getUser().getUserId());
                    reconcile.setOperatorName(SecurityInfoGetter.getUser().getRealName());
                    reconcile.setCreateTime(createTime);
                    reconcile.setCancelOk((short) 0);
                    reconcile.setWaybillId(m.get("waybillIds").toString());
                    reconcile.setAccountId(m.get("accountIds").toString());
                    reconcile.setPayeeType(payeeType);
                    reconcile.setPayerId(Long.parseLong(m.get("nameId").toString()));
                    reconcile.setPayerName(m.get("name").toString());
                    reconcile.setGroupId(Long.parseLong(m.get("groupId").toString()));
                    reconcileList.add(reconcile);
                }
                int result = reconcileMapper.insertByBatch(reconcileList);
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
                        feeAccountMapper.updateReconcileCodeAndId(dto);
                    }
                }
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public List<FeeAccountDto> feeAccountReconcileDetail(Long reconcileId) {
        List<FeeAccountDto> list = feeAccountMapper.selectByReconcileId(reconcileId);
        return list;
    }

    public boolean feeAccountReconcileCancel(Long[] accountIds) {
        try{
            Map map = new HashMap();
            map.put("accountIds", accountIds);
            //按对账id对记账单进行分组
            List<Map<String,Object>> list = feeAccountMapper.feeAccountGroupByReconcile(map);

            if(list != null && list.size() > 0){
                for(Map<String, Object> m : list){
                    Long reconcileId = Long.parseLong(m.get("reconcileId").toString());
                    Reconcile reconcile = reconcileMapper.selectByPrimaryKey(reconcileId);
                    if(reconcile != null){
                        //记账单运费总金额
                        Double freightTotal = Double.parseDouble(m.get("freightTotal").toString());
                        //记账单其他费用总金额
                        Double otherFeeTotal = Double.parseDouble(m.get("otherFeeTotal").toString());

                        //对账单总运费金额
                        Double transportationExpenses = reconcile.getTransportationExpenses()==null?0.0:reconcile.getTransportationExpenses();
                        //对账单其他总费用金额
                        Double otherExpenses = reconcile.getOtherExpenses()==null?0.0:reconcile.getOtherExpenses();

                        //对账单收付款记录条数
                        int exchangeCount = feeExchangeMapper.selectCountFeeExchangeByReconcileId(reconcileId);

                        //记账单对应记账单总金额 - 对账单对账金额 = 0（在没有收付款记录的情况下，对账单可直接取消/删除）
                        if((transportationExpenses + otherExpenses) - (freightTotal + otherFeeTotal) == 0){
                            if(exchangeCount <= 0){
                                Long[] reconcileIdArr = {reconcileId};
                                reconcileMapper.cancelByBatch(reconcileIdArr);
                            }
                        }else{
                            if(exchangeCount <= 0) {
                                //否则需要从对账单中减去记账单金额
                                reconcile.setTransportationExpenses(transportationExpenses - freightTotal);
                                reconcile.setOtherExpenses(otherExpenses - otherFeeTotal);

                                StringBuffer newAccountIds = new StringBuffer();//对账单新的accountIds

                                List<String> oldAccountIdsArr_r = Arrays.asList(reconcile.getAccountId().split(","));//对账单原始accountIds

                                List<String> accountIdsArr_a = Arrays.asList(m.get("accountIds").toString().split(","));//多个记账单id

                                //取出不同的accountId
                                for (String accountId : oldAccountIdsArr_r) {
                                    if (!accountIdsArr_a.contains(accountId)) {
                                        newAccountIds.append(accountId + ",");
                                    }
                                }

                                reconcile.setAccountId(newAccountIds.length() > 0 ? newAccountIds.substring(0, newAccountIds.length() - 1) : null);
                                //根据accountIds找出对应waybillIds
                                if (StringUtility.isNotEmpty(reconcile.getAccountId())) {
                                    reconcile.setWaybillId(feeAccountMapper.getWaybillIdsByAccountIds(reconcile.getAccountId()));
                                } else {
                                    reconcile.setWaybillId(null);
                                }

                                reconcileMapper.updateByPrimaryKey(reconcile);
                            }
                        }
                    }
                }
                //设置记账单记账信息为空
                ReconcileDto reconcileDto = new ReconcileDto();
                reconcileDto.setReconcileCode(null);
                reconcileDto.setReconcileId(null);
                reconcileDto.setAccountIds(accountIds);
                feeAccountMapper.updateReconcileCodeAndId(reconcileDto);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}

