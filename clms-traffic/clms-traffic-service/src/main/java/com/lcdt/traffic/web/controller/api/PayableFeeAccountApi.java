package com.lcdt.traffic.web.controller.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.traffic.dto.ReceivePayParamsDto;
import com.lcdt.traffic.model.FeeFlow;
import com.lcdt.traffic.model.Msg;
import com.lcdt.traffic.service.FeeAccountService;
import com.lcdt.traffic.service.MsgService;
import com.lcdt.traffic.util.FinanceUtil;
import com.lcdt.traffic.util.GroupIdsUtil;
import com.lcdt.traffic.web.dto.*;
import com.lcdt.userinfo.model.FeeProperty;
import com.lcdt.userinfo.rpc.FinanceRpcService;
import com.lcdt.util.ClmsBeanUtil;
import com.lcdt.util.WebProduces;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by liz on 2018/3/29.
 */
@RestController
@RequestMapping("/api/payableFeeAccount")
@Api(value = "应付记账api", description = "应付记账操作")
public class PayableFeeAccountApi {
    @Autowired
    private FeeAccountService feeAccountService;
    @Reference
    FinanceRpcService financeRpcService;
    @Autowired
    private MsgService msgService;

//    @ApiOperation("应付记账——列表")
//    @RequestMapping(value = "/feeAccountWaybillList", produces = WebProduces.JSON_UTF_8, method = RequestMethod.GET)
//    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('payable_fee_account_waybill_list')")
//    public PageBaseDto feeAccountWaybillList(@Validated WaybillCustListParamsDto dto) {
//        StringBuffer sb = new StringBuffer();
//        if (dto.getGroupId()!=null&&dto.getGroupId()>0) {//传业务组，查这个组帮定的客户
//            sb.append(" find_in_set('"+dto.getGroupId()+"',group_id)"); //项目组id
//        } else {
//            //没传组，查这个用户所有组帮定的客户
//            List<Group> groupList = SecurityInfoGetter.groups();
//            if(groupList!=null&&groupList.size()>0){
//                sb.append("(");
//                for(int i=0;i<groupList.size();i++) {
//                    Group group = groupList.get(i);
//                    sb.append(" find_in_set('"+group.getGroupId()+"',group_id)"); //所有项目组ids
//                    if(i!=groupList.size()-1){
//                        sb.append(" or ");
//                    }
//                }
//                sb.append(")");
//            }
//        }
//        Long companyId = SecurityInfoGetter.getCompanyId();
//        Map map= ClmsBeanUtil.beanToMap(dto);
//        map.put("companyId",companyId);
//        map.put("isDeleted",0);
//        map.put("groupIds",sb.toString());
//        map.put("isReceivable",(short)1);
//
//        PageInfo<List<FeeAccountWaybillDto>> listPageInfo = feeAccountService.feeAccountWaybillList(map);
//        PageBaseDto pageBaseDto = new PageBaseDto(listPageInfo.getList(), listPageInfo.getTotal());
//        return pageBaseDto;
//    }
    @ApiOperation("应付记账——列表（我的运单）")
    @RequestMapping(value = "/feeAccountMyWaybillList", produces = WebProduces.JSON_UTF_8, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('payable_fee_account_my_waybill_list')")
    public JSONObject feeAccountMyWaybillList(@Validated ReceivePayParamsDto dto,
                                               @ApiParam(value = "页码", required = true) @RequestParam Integer pageNo,
                                               @ApiParam(value = "每页显示条数", required = true) @RequestParam Integer pageSize) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Map map= ClmsBeanUtil.beanToMap(dto);
        map.put("companyId",companyId);
        map.put("pageNo",pageNo);
        map.put("pageSize",pageSize);
        map.put("isDeleted",0);
        if(dto.getGroupId()!=null&&dto.getGroupId()>0)
        {
            map.put("groupIds", GroupIdsUtil.getOwnGroupIds(dto.getGroupId()));
        }
        map.put("isReceivable",(short)1);
        PageInfo<List<FeeAccountWaybillDto>> listPageInfo = feeAccountService.feeAccountWaybillList(map);
        PageBaseDto pageBaseDto = new PageBaseDto(listPageInfo.getList(), listPageInfo.getTotal());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",0);
        jsonObject.put("message","请求成功");

        Map data = new HashMap();
        data.put("list", pageBaseDto.getList());
        data.put("total", pageBaseDto.getTotal());

        map.put("waybillType", 0);//我的运单
        if(dto.getGroupId()!=null&&dto.getGroupId()>0) {
            map.put("groupIds",GroupIdsUtil.getOwnGroupIds(dto.getGroupId()).replaceAll("group_id","w.group_id"));
        }
        FeeAccountWaybillDto feeTotalDto = feeAccountService.feeAccountWaybillFeeTotal(map);
        data.put("feeTotal", FinanceUtil.getFeeTotalDto(feeTotalDto));

        jsonObject.put("data",data);

        return jsonObject;
//        return pageBaseDto;
    }

    @ApiOperation("应付记账——列表（客户运单）")
    @RequestMapping(value = "/feeAccountCoustmerWaybillList", produces = WebProduces.JSON_UTF_8, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('payable_fee_account_customer_waybill_list')")
    public JSONObject feeAccountCustomerWaybillList(@Validated ReceivePayParamsDto dto,
                                                     @ApiParam(value = "页码", required = true) @RequestParam Integer pageNo,
                                                     @ApiParam(value = "每页显示条数", required = true) @RequestParam Integer pageSize) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Map map= ClmsBeanUtil.beanToMap(dto);
        map.put("companyId",companyId);
        map.put("pageNo",pageNo);
        map.put("pageSize",pageSize);
        map.put("isDeleted",0);
        if(dto.getGroupId()!=null&&dto.getGroupId()>0)
        {
            map.put("groupIds",GroupIdsUtil.getCustomerGroupIds(dto.getGroupId()));
        }
        map.put("isReceivable",(short)1);
        PageInfo<List<FeeAccountWaybillDto>> listPageInfo = feeAccountService.feeAccountCustomerWaybillList(map);
        PageBaseDto pageBaseDto = new PageBaseDto(listPageInfo.getList(), listPageInfo.getTotal());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",0);
        jsonObject.put("message","请求成功");

        Map data = new HashMap();
        data.put("list", pageBaseDto.getList());
        data.put("total", pageBaseDto.getTotal());

        map.put("waybillType", 1);//客户运单
        if(dto.getGroupId()!=null&&dto.getGroupId()>0) {
            map.put("groupIds",GroupIdsUtil.getOwnGroupIds(dto.getGroupId()).replaceAll("group_id","w.group_id"));
        }
        FeeAccountWaybillDto feeTotalDto = feeAccountService.feeAccountWaybillFeeTotal(map);
        data.put("feeTotal", FinanceUtil.getFeeTotalDto(feeTotalDto));

        jsonObject.put("data",data);

        return jsonObject;
//        return pageBaseDto;
    }
    @ApiOperation("应付记账——记账(进入记账页面)")
    @RequestMapping(value = "/feeAccountPage", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('payable_fee_account_page')")
    public JSONObject feeAccountPage(@ApiParam(value = "运单ID",required = true) @RequestParam Long waybillId,
                                     @ApiParam(value = "1-我的运单，0-客户运单",required = true) @RequestParam Integer isOwn) {
        Map m = new HashMap<>();
        m.put("companyId", SecurityInfoGetter.getCompanyId());
        //费用类型
        m.put("isDeleted", (short)0);
        m.put("type", (short)0);
        //记账单/运单货物明细
        m.put("waybillId", waybillId);
        m.put("isReceivable", (short)1);
        m.put("isOwn", isOwn);

        int reconcileCount = feeAccountService.getWaybillReconcileCount(m);
        if(reconcileCount > 0){
            throw new RuntimeException("此运单已对账，不能记账");
        }else {
            Map map = feeAccountService.feeAccountPage(m);
            if (map != null) {
                List<FeeAccountDto> feeAccountDtoList = (List<FeeAccountDto>) map.get("feeAccountDtoList");
                if (feeAccountDtoList != null && feeAccountDtoList.size() > 0) {
                    for (FeeAccountDto dto : feeAccountDtoList) {
                        List<FeeFlow> feeFlowList = dto.getFeeFlowList();
                        List<FeeProperty> showPropertyList = new ArrayList<>();
//                        List<FeeProperty> hidePropertyList = new ArrayList<>();
                        if (feeFlowList != null && feeFlowList.size() > 0) {
                            List<Long> proIds = new ArrayList<>();
                            for (FeeFlow f : feeFlowList) {
                                proIds.add(f.getProId());
                            }
                            m.put("proIds", proIds);
                        }
//                        m.put("isShow", (short) 0);
                        showPropertyList = financeRpcService.selectByCondition(m);
//                        m.put("isShow", (short) 1);
//                        hidePropertyList = financeRpcService.selectByCondition(m);
                        dto.setShowPropertyList(showPropertyList);
//                        dto.setHidePropertyList(hidePropertyList);
                    }
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code", 0);
                jsonObject.put("message", "记账明细");
                jsonObject.put("data", map);
                return jsonObject;
            } else {
                throw new RuntimeException("获取失败");
            }
        }
    }

    @ApiOperation("应付记账——保存记账")
    @RequestMapping(value = "/feeAccountSave", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('payable_fee_account_save')")
    public JSONObject feeAccountSave(@Validated @RequestBody FeeAccountSaveParamsDto dto) {
        dto.setCompanyId(SecurityInfoGetter.getCompanyId());
        dto.setUserId(SecurityInfoGetter.getUser().getUserId());
        dto.setRealName(SecurityInfoGetter.getUser().getRealName());
        dto.setIsReceivable((short)1);
        boolean result = feeAccountService.feeAccountSave(dto);
        if (result) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "记账成功");
            return jsonObject;
        } else {
            throw new RuntimeException("记账失败");
        }
    }

    @ApiOperation("应付记账——查看流水")
    @RequestMapping(value = "/findFeeFlow", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('payable_find_fee_flow')")
    public JSONObject findFeeFlow(@ApiParam(value = "运单id",required = true) @RequestParam Long waybillId) {
        Map map = new HashMap();
        map.put("waybillId", waybillId);
        map.put("isReceivable", (short)1);
        List<FeeAccountDto> list = feeAccountService.selectFlowByWaybillId(map);
        if (list != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "流水详情");
            jsonObject.put("data", list);
            return jsonObject;
        } else {
            throw new RuntimeException("获取失败");
        }
    }

    @ApiOperation("应付记账单——列表")
    @RequestMapping(value = "/feeAccountList", produces = WebProduces.JSON_UTF_8, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('payable_fee_account_list')")
    public JSONObject feeAccountList(@Validated FeeAccountListParamsDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        dto.setCompanyId(companyId);
        dto.setIsDeleted((short)0);
        dto.setIsReceivable((short)1);
        PageInfo<List<FeeAccountDto>> listPageInfo = feeAccountService.feeAccountList(dto);
        PageBaseDto pageBaseDto = new PageBaseDto(listPageInfo.getList(), listPageInfo.getTotal());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",0);
        jsonObject.put("message","请求成功");

        Map data = new HashMap();
        data.put("list", pageBaseDto.getList());
        data.put("total", pageBaseDto.getTotal());

        FeeAccountDto feeTotalDto = feeAccountService.feeAccountFeeTotal(dto);
        data.put("feeTotal", FinanceUtil.getFeeAccountFeeTotalDto(feeTotalDto));

        jsonObject.put("data",data);

        return jsonObject;
//        return pageBaseDto;
    }

    @ApiOperation("记账单——记账单详情")
    @RequestMapping(value = "/feeAccountDetail", produces = WebProduces.JSON_UTF_8, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('payable_fee_account_detail')")
    public JSONObject feeAccountDetail(@ApiParam(value = "记账单id",required = true) @RequestParam Long accountId) {
        Map resultMap = feeAccountService.feeAccountDetail(accountId);
        if (resultMap != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data", resultMap);
            jsonObject.put("code", 0);
            jsonObject.put("message", "记账单详情");
            return jsonObject;
        } else {
            throw new RuntimeException("获取失败");
        }
    }
    @ApiOperation("应付记账单——列表留言")
    @RequestMapping(value = "/feeAccountAddMsgPage", produces = WebProduces.JSON_UTF_8, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('payable_fee_account_msg_page')")
    public JSONObject feeAccountAddMsgPage(@ApiParam(value = "记账单ID",required = true) @RequestParam Long accountId) {
        MsgDto dto = new MsgDto();
        dto.setType((short)0);
        dto.setOperatorId(SecurityInfoGetter.getUser().getUserId());
        dto.setIsDeleted((short)0);
        dto.setAccountId(accountId);
        List<Msg> list = msgService.selectSomeMsg(dto);
        if (list != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "留言列表");
            jsonObject.put("data", list);
            return jsonObject;
        } else {
            throw new RuntimeException("获取失败");
        }
    }

    @ApiOperation("应付记账单——审核/取消审核")
    @RequestMapping(value = "/feeAccountAudit", produces = WebProduces.JSON_UTF_8, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('payable_fee_account_audit')")
    public JSONObject feeAccountAudit(@ApiParam(value = "0-取消审核，1-审核",required = true) @RequestParam short auditStatus,
                                      @ApiParam(value = "记账单IDs（例:1,2,3）",required = true) @RequestParam String accountIds) {
        Map map = new HashMap();
        map.put("auditStatus", auditStatus);
        if(auditStatus == 1) {
            map.put("auditDate", new Date());
        }else{
            map.put("auditDate", null);
        }
        map.put("accountIds", accountIds.split(","));
        int result = feeAccountService.feeAccountAudit(map);
        if (result == accountIds.split(",").length) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", auditStatus==0?"取消审核成功":"审核成功");
            return jsonObject;
        } else {
            throw new RuntimeException( auditStatus==0?"取消审核失败":"审核失败");
        }
    }

    @ApiOperation("应付记账单——对账（进入对账页面）")
    @RequestMapping(value = "/feeAccountReconcilePage", produces = WebProduces.JSON_UTF_8, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('payable_fee_account_reconcile_page')")
    public JSONObject feeAccountReconcilePage(@ApiParam(value = "记账单IDs（例:1,2,3）",required = true) @RequestParam String accountIds) {
        Map map = new HashMap();
        map.put("accountIds", accountIds.split(","));
        List list = feeAccountService.feeAccountReconcilePage(map);
        if (list != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data", list);
            jsonObject.put("code", 0);
            jsonObject.put("message", "对账详情");
            return jsonObject;
        } else {
            throw new RuntimeException("获取失败");
        }
    }

    @ApiOperation("应付记账单——对账单保存")
    @RequestMapping(value = "/feeAccountReconcileSave", produces = WebProduces.JSON_UTF_8, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('payable_fee_account_reconcile_save')")
    public JSONObject feeAccountReconcileSave(@ApiParam(value = "记账单IDs（例:1,2,3）",required = true) @RequestParam String accountIds) {
        Map map = new HashMap();
        map.put("accountIds", accountIds.split(","));
        List<Map<String,Object>> list = feeAccountService.feeAccountReconcilePage(map);
        boolean result = feeAccountService.feeAccountReconcileSave(list, (short)1);
        if (result) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "对账成功");
            return jsonObject;
        } else {
            throw new RuntimeException("对账失败");
        }
    }

    @ApiOperation("对账单——取消对账")
    @RequestMapping(value = "/feeAccountReconcileCancel", produces = WebProduces.JSON_UTF_8, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('payable_fee_account_reconcile_cancel')")
    public JSONObject feeAccountReconcileCancel(@ApiParam(value = "记账单id（例:1,2,3）",required = true) @RequestParam String accountIds) {
        String[] accountIdStrArr = accountIds.split(",");
        Long[] accountIdArr = new Long[accountIdStrArr.length];
        for(int i=0; i<accountIdStrArr.length; i++){
            accountIdArr[i] = Long.parseLong(accountIdStrArr[i]);
        }
        boolean result = feeAccountService.feeAccountReconcileCancel(accountIdArr);
        if (result) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "取消成功");
            return jsonObject;
        } else {
            throw new RuntimeException("取消失败");
        }
    }
}
