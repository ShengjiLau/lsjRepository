package com.lcdt.traffic.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.traffic.model.FeeAccount;
import com.lcdt.traffic.service.FeeAccountService;
import com.lcdt.traffic.web.dto.*;
import com.lcdt.userinfo.model.Group;
import com.lcdt.util.ClmsBeanUtil;
import com.lcdt.util.WebProduces;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liz on 2018/3/29.
 */
@RestController
@RequestMapping("/api/receivableWaybillFee")
@Api(value = "应收记账api", description = "应收记账操作")
public class ReceivableFeeAccountApi {
    @Autowired
    private FeeAccountService feeAccountService;

    @ApiOperation("应收记账——列表")
    @RequestMapping(value = "/feeAccountWaybillList", produces = WebProduces.JSON_UTF_8, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('receivable_fee_account_waybill_list')")
    public PageBaseDto feeAccountWaybillList(@Validated WaybillCustListParamsDto dto,
                                             @ApiParam(value = "页码", required = true) @RequestParam Integer pageNo,
                                             @ApiParam(value = "每页显示条数", required = true) @RequestParam Integer pageSize) {
        StringBuffer sb = new StringBuffer();
        if (dto.getGroupId()!=null&&dto.getGroupId()>0) {//传业务组，查这个组帮定的客户
            sb.append(" find_in_set('"+dto.getGroupId()+"',group_id)"); //项目组id
        } else {
            //没传组，查这个用户所有组帮定的客户
            List<Group> groupList = SecurityInfoGetter.groups();
            if(groupList!=null&&groupList.size()>0){
                sb.append("(");
                for(int i=0;i<groupList.size();i++) {
                    Group group = groupList.get(i);
                    sb.append(" find_in_set('"+group.getGroupId()+"',group_id)"); //所有项目组ids
                    if(i!=groupList.size()-1){
                        sb.append(" or ");
                    }
                }
                sb.append(")");
            }
        }
        Long companyId = SecurityInfoGetter.getCompanyId();
        Map map= ClmsBeanUtil.beanToMap(dto);
        map.put("companyId",companyId);
        map.put("pageNo",pageNo);
        map.put("pageSize",pageSize);
        map.put("isDeleted",0);
        map.put("groupIds",sb.toString());
        map.put("isReceivable",(short)0);

        PageInfo<List<FeeAccountWaybillDto>> listPageInfo = feeAccountService.feeAccountWaybillList(map);
        PageBaseDto pageBaseDto = new PageBaseDto(listPageInfo.getList(), listPageInfo.getTotal());
        return pageBaseDto;
    }

    @ApiOperation("应收记账——记账(进入记账页面)")
    @RequestMapping(value = "/feeAccountPage", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('receivable_fee_account_page')")
    public JSONObject feeAccountPage(@ApiParam(value = "运单ID",required = true) @RequestParam Long waybillId) {
        Map m = new HashMap<>();
        m.put("waybillId", waybillId);
        m.put("isReceivable", (short)0);

        Map map = feeAccountService.feeAccountPage(m);

        JSONObject jsonObject=new JSONObject();
        if(map!=null) {
            jsonObject.put("code",0);
            jsonObject.put("message","记账明细");
            jsonObject.put("data",map);
            return jsonObject;
        }else {
            throw new RuntimeException("获取失败");
        }
    }

    @ApiOperation("应收记账——保存记账")
    @RequestMapping(value = "/feeAccountSave", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('receivable_fee_account_save')")
    public JSONObject feeAccountSave(@Validated FeeAccount dto) {
//        Long companyId = SecurityInfoGetter.getCompanyId();
//        Map map = new HashMap();
//        map.put("companyId", companyId);
//        map.put("isDeleted", (short)0);
//
//        if (dto.getType()!=null) {
//            map.put("type",dto.getType());
//        }
//        if (dto.getIsReceivable()!=null) {
//            map.put("isReceivable",dto.getIsReceivable());
//        }
//        if (dto.getName()!=null) {
//            map.put("name",dto.getName());
//        }
//        if(dto.getProId()!=null){
//            map.put("proId",dto.getProId());
//        }
//        PageInfo pageInfo = feePropertyService.feePropertyList(map);
//
//        if(pageInfo.getTotal() > 0){
//            throw new RuntimeException("该所属款项下的费用类型已存在");
//        }else {
//            int result = feePropertyService.modifyFeeProperty(dto);
//            if (result > 0) {
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.put("code", 0);
//                jsonObject.put("message", "修改成功");
//                return jsonObject;
//            } else {
//                throw new RuntimeException("修改失败");
//            }
//        }
        return null;
    }

    @ApiOperation("应收记账——查看流水")
    @RequestMapping(value = "/findFeeFlow", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('receivable_find_fee_flow')")
    public JSONObject findFeeFlow(@ApiParam(value = "费用类型ID",required = true) @RequestParam Long proId) {
//        int result = feePropertyService.modifyFeePropertyIsDelete(proId);
//        if (result > 0) {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("code", 0);
//            jsonObject.put("message", "删除成功");
//            return jsonObject;
//        } else {
//            throw new RuntimeException("删除失败");
//        }
        return null;
    }

    @ApiOperation("应收记账单——列表")
    @RequestMapping(value = "/feeAccountList", produces = WebProduces.JSON_UTF_8, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('receivable_fee_account_list')")
    public PageBaseDto feeAccountList(@Validated FeeAccountDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        dto.setCompanyId(companyId);
        dto.setIsDeleted((short)0);
        dto.setIsReceivable((short)0);
        PageInfo<List<FeeAccountDto>> listPageInfo = feeAccountService.feeAccountList(dto);
        PageBaseDto pageBaseDto = new PageBaseDto(listPageInfo.getList(), listPageInfo.getTotal());
        return pageBaseDto;
    }

    @ApiOperation("应收记账单——列表留言")
    @RequestMapping(value = "/feeAccountAddMsgPage", produces = WebProduces.JSON_UTF_8, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('receivable_fee_account_add_msg')")
    public PageBaseDto feeAccountAddMsgPage(@ApiParam(value = "记账单ID",required = true) @RequestParam Long accountId) {
//        Long companyId = SecurityInfoGetter.getCompanyId();
//        dto.setCompanyId(companyId);
//        dto.setIsDeleted((short)0);
//        dto.setIsReceivable((short)0);
//        PageInfo<List<FeeAccountDto>> listPageInfo = feeAccountService.feeAccountList(dto);
//        PageBaseDto pageBaseDto = new PageBaseDto(listPageInfo.getList(), listPageInfo.getTotal());
//        return pageBaseDto;
        return null;
    }

    @ApiOperation("应收记账单——审核/取消审核")
    @RequestMapping(value = "/feeAccountAudit", produces = WebProduces.JSON_UTF_8, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('receivable_fee_account_audit')")
    public JSONObject feeAccountAudit(@ApiParam(value = "0-取消审核，1-审核",required = true) @RequestParam short auditStatus,
            @ApiParam(value = "记账单IDs",required = true) @RequestParam List<Long> accountIds) {
        Map map = new HashMap();
        map.put("auditStatus", auditStatus);
        if(auditStatus == 1) {
            map.put("auditDate", new Date());
        }else{
            map.put("auditDate", null);
        }
        map.put("accountIds", accountIds);
        int result = feeAccountService.feeAccountAudit(map);
        if (result == accountIds.size()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "删除成功");
            return jsonObject;
        } else {
            throw new RuntimeException("删除失败");
        }
    }

    @ApiOperation("应收记账单——对账")
    @RequestMapping(value = "/feeAccountReconcilePage", produces = WebProduces.JSON_UTF_8, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('receivable_fee_account_reconcile_page')")
    public JSONObject feeAccountReconcilePage(@ApiParam(value = "0-取消审核，1-审核",required = true) @RequestParam short auditStatus,
                                      @ApiParam(value = "记账单IDs",required = true) @RequestParam List<Long> accountIds) {
//        Map map = new HashMap();
//        map.put("accountIds", accountIds);
//        List list = feeAccountService.feeAccountReconcileGroup(map);
//        if (list != null && list.size() > 0) {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("data", list);
//            jsonObject.put("code", 0);
//            jsonObject.put("message", "对账详情");
//            return jsonObject;
//        } else {
            throw new RuntimeException("无数据");
//        }
    }

    @ApiOperation("应收记账单——对账单保存")
    @RequestMapping(value = "/feeAccountReconcileSave", produces = WebProduces.JSON_UTF_8, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('receivable_fee_account_reconcile_save')")
    public JSONObject feeAccountReconcileSave(@ApiParam(value = "0-取消审核，1-审核",required = true) @RequestParam short auditStatus,
                                              @ApiParam(value = "记账单IDs",required = true) @RequestParam List<Long> accountIds) {
//        Map map = new HashMap();
//        map.put("accountIds", accountIds);
//        List<Map<String,Object>> list = feeAccountService.feeAccountReconcileGroup(map);
//        if (list != null && list.size() > 0) {
//            for(Map<String,Object> m : list){
//                m.put("reconcileCode",1);
//                m.put("companyId",SecurityInfoGetter.getCompanyId());
//                m.put("groupId",1);
//                m.put("account_amount",m.get("money"));
//                m.put("operatorId",SecurityInfoGetter.getUser().getUserId());
//                m.put("operatorName",SecurityInfoGetter.getUser().getRealName());
//                m.put("isReceivable",0);
//                m.put("createTime",new Date());
//            }
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("data", list);
//            jsonObject.put("code", 0);
//            jsonObject.put("message", "对账详情");
//            return jsonObject;
//        } else {
            throw new RuntimeException("无数据");
//        }
    }
}
