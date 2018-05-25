package com.lcdt.traffic.web.controller.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.traffic.dto.ProfitStatParamsDto;
import com.lcdt.traffic.dto.ProfitStatResult1Dto;
import com.lcdt.traffic.dto.ProfitStatResultDto;
import com.lcdt.traffic.dto.ReceivePayParamsDto;
import com.lcdt.traffic.service.IFeeFlowService;
import com.lcdt.traffic.web.dto.PageBaseDto;
import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.model.FeeProperty;
import com.lcdt.userinfo.model.Group;
import com.lcdt.userinfo.rpc.FinanceRpcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by yangbinq on 2018/4/12.
 */
@RestController
@RequestMapping("/finace")
@Api(value = "财务统计",description = "财务统计")
public class FinanceApi {

    @Reference
    private FinanceRpcService financeRpcService;


    @Autowired
    private IFeeFlowService iFeeFlowService;


    @ApiOperation("应收统计")
    @RequestMapping(value = "/receive/stat",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN')")
    public JSONObject receiveStat(ReceivePayParamsDto dto) {
        JSONObject jsonObject = new JSONObject();
        try {
            List<Map<String, Object>> mapList = statData(dto, (short)0);
            jsonObject.put("code", 0);
            jsonObject.put("message", "");
            jsonObject.put("data", JSONArray.toJSON(mapList==null?new Object():mapList));
        } catch (Exception e) {
            jsonObject.put("code", -1);
            jsonObject.put("message",e.getMessage());
        }
        return jsonObject;
    }



    @ApiOperation("应付统计 ")
    @RequestMapping(value = "/pay/stat",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN')")
    public JSONObject payStat(ReceivePayParamsDto dto) {
        JSONObject jsonObject = new JSONObject();
        try {
            List<Map<String, Object>> mapList = statData(dto, (short)1);
            jsonObject.put("code", 0);
            jsonObject.put("message", "");
            jsonObject.put("data", JSONArray.toJSON(mapList==null?new Object():mapList));
        } catch (Exception e) {
            jsonObject.put("code", -1);
            jsonObject.put("message",e.getMessage());
        }
        return jsonObject;
    }



    /***
     * 统计数据
     */
    private List<Map<String,Object>>  statData(ReceivePayParamsDto dto, short isReceivable) {
        Company company = SecurityInfoGetter.geUserCompRel().getCompany();
        Map paramMap = new HashMap<String,String>();
        paramMap.put("companyId", company.getCompId());
        paramMap.put("isDeleted", 0);
        paramMap.put("type",0);//取运输的费用类型 运输是0 ，仓储是1
        paramMap.put("isReceivable", isReceivable); //应收0，应付1
        List<FeeProperty> feePropertyList = financeRpcService.selectByCondition(paramMap);
        if (feePropertyList!=null && feePropertyList.size()>0) {
            StringBuffer sb_1 = new StringBuffer();
            if (dto.getGroupId()>0) {//业务组
                dto.setGroupIds(dto.getGroupId().toString());
            } else {
                List<Group> groupList = SecurityInfoGetter.groups();
                for(int i=0;i<groupList.size();i++) {
                    Group group = groupList.get(i);
                    sb_1.append(group.getGroupId());
                    if(i!=groupList.size()-1){
                        sb_1.append(",");
                    }
                }
                dto.setGroupIds(sb_1.toString());
            }
            StringBuffer sb_2 = new StringBuffer();
            for (int i=0; i<feePropertyList.size();i++) {
                FeeProperty feeProperty = feePropertyList.get(i);
                sb_2.append("IFNULL(sum(case pro_id when "+feeProperty.getProId()+" then money end),0) as '"+feeProperty.getName()+"' ");
                if(i!=feePropertyList.size()-1){
                    sb_2.append(",");
                }
            }
            dto.setStatCols(sb_2.toString());
            dto.setIsDeleted((short)0);
            dto.setCompanyId(company.getCompId());
            return iFeeFlowService.receivePayStat(dto);
        }
        return null;
    }



    @ApiOperation("利润统计")
    @RequestMapping(value = "/profit/stat",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN')")
    public PageBaseDto profitStat(ProfitStatParamsDto dto) {
        Company company = SecurityInfoGetter.geUserCompRel().getCompany();
        List<Group> groupList = SecurityInfoGetter.groups();
        StringBuffer sb_1 = new StringBuffer();
        for(int i=0;i<groupList.size();i++) {
            Group group = groupList.get(i);
            sb_1.append(group.getGroupId());
            if(i!=groupList.size()-1){
                sb_1.append(",");
            }
        }
        dto.setCompanyId(company.getCompId());
        dto.setIsDeleted((short)0);
        dto.setGroupIds(sb_1.toString());
        PageInfo pg = iFeeFlowService.profitStat(dto);
        PageBaseDto pg_result = new PageBaseDto(pg.getList(), pg.getTotal());
        return pg_result;
    }


    @ApiOperation("利润统计")
    @RequestMapping(value = "g",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN')")
    public ProfitStatResult1Dto profitStatTotal(ProfitStatParamsDto dto) {
        Company company = SecurityInfoGetter.geUserCompRel().getCompany();
        List<Group> groupList = SecurityInfoGetter.groups();
        StringBuffer sb_1 = new StringBuffer();
        for(int i=0;i<groupList.size();i++) {
            Group group = groupList.get(i);
            sb_1.append(group.getGroupId());
            if(i!=groupList.size()-1){
                sb_1.append(",");
            }
        }
        dto.setPageNo(1);
        dto.setPageSize(0);
        dto.setCompanyId(company.getCompId());
        dto.setIsDeleted((short)0);
        dto.setGroupIds(sb_1.toString());
        PageInfo pg = iFeeFlowService.profitStat(dto);
        ProfitStatResult1Dto dto1 = new ProfitStatResult1Dto();
        if(pg.getTotal()>0) {
            List<ProfitStatResultDto> list = pg.getList();
            float receiveMoneyTotal = 0,payMoneyTotal=0,profitMoneyTotal=0;
            for(ProfitStatResultDto obj :list) {
                if(obj.getPayMoney()!=null) payMoneyTotal+=obj.getPayMoney();
                if(obj.getReceiveMoney()!=null) receiveMoneyTotal+=obj.getReceiveMoney();
            }
            dto1.setPayMoneyTotal(payMoneyTotal);
            dto1.setReceiveMoneyTotal(receiveMoneyTotal);
            dto1.setProfitMoneyTotal(receiveMoneyTotal-payMoneyTotal);
        }
        return dto1;
    }


}
