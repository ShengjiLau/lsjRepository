package com.lcdt.driver.wechat.api.plan;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.clms.security.helper.TokenSecurityInfoGetter;
import com.lcdt.driver.dto.PageBaseDto;
import com.lcdt.traffic.dto.*;
import com.lcdt.traffic.model.SnatchGoods;
import com.lcdt.traffic.service.IPlanRpcService4Wechat;
import com.lcdt.userinfo.model.Group;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangbinq on 2018/3/19.
 * Desciption: 运输入计划API
 */

@RestController
@RequestMapping("/plan")
public class PlanApi {

   @com.alibaba.dubbo.config.annotation.Reference(check = false)
   private IPlanRpcService4Wechat iPlanRpcService4Wechat;

   @ApiOperation("竞价--企业组")
   @RequestMapping(value = "/driver/companyGroupList", method = RequestMethod.GET)
   public JSONObject companyGroupList() {
      User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      SnathBill4WaittingPdto dto = new SnathBill4WaittingPdto();
      dto.setDriverId(loginUser.getUserId());
      List<OwnCompany4SnatchRdto> ownCompany4SnatchRdtoList = iPlanRpcService4Wechat.ownCompanyList(dto);
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("message","");
      jsonObject.put("code",0);
      jsonObject.put("data",ownCompany4SnatchRdtoList);
      return jsonObject;
   }

   @ApiOperation("竞价--待抢")
   @RequestMapping(value = "/driver/waittingSnatchList", method = RequestMethod.GET)
   public PageBaseDto  waittingSnatchList(SnathBill4WaittingPdto dto) {
      User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      dto.setDriverId(loginUser.getUserId());
      PageInfo pg = iPlanRpcService4Wechat.snatchBill4WaittingList(dto);
      PageBaseDto pageBaseDto = new PageBaseDto(pg.getList(), pg.getTotal());
      return pageBaseDto;
   }


   @ApiOperation("竞价--已抢")
   @RequestMapping(value = "/driver/compleateSnatchList", method = RequestMethod.GET)
   public PageBaseDto  compleateSnatchList(SnathBill4WaittingPdto dto) {
      User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      dto.setDriverId(loginUser.getUserId());
      PageInfo pg = iPlanRpcService4Wechat.snatchBill4CompleteList(dto);
      PageBaseDto pageBaseDto = new PageBaseDto(pg.getList(), pg.getTotal());
      return pageBaseDto;
   }

    @ApiOperation("竞价--报价")
    @RequestMapping(value = "/driver/driverOffer", method = RequestMethod.POST)
    public JSONObject driverOffer(SnatchOfferDto dto) {
            User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            SnatchGoods snatchGoods = new SnatchGoods();
            snatchGoods.setOfferId(loginUser.getUserId());
            snatchGoods.setOfferName(loginUser.getRealName());
            snatchGoods.setCreateId(loginUser.getUserId());
            snatchGoods.setCreateName(loginUser.getRealName());
            snatchGoods.setUpdateId(loginUser.getUserId());
            snatchGoods.setUpdateName(loginUser.getRealName());
            snatchGoods.setOfferPhone(loginUser.getPhone()); //抢单人电话
            // snatchGoods.setCompanyId(companyId);
            snatchGoods.setPlanCompanyId(dto.getCompanyId());//计划企业ID
            int flag = iPlanRpcService4Wechat.driverOffer(dto,snatchGoods);
            JSONObject jsonObject = new JSONObject();
            String message = null;
            int code = -1;
            if (flag>0) {
            code = 0;
                message = "抢单成功！";
            } else {
                message = "操作失败，请重试！";
            }
            jsonObject.put("message",message);
            jsonObject.put("code",code);
            return jsonObject;
   }



    @ApiOperation("我的计划-直派、竞价列表")
    @RequestMapping(value = "/carrier/planList",method = RequestMethod.GET)
    public PageBaseDto planList(Plan4CarrierParamsDto dto) {
        UserCompRel userCompRel = TokenSecurityInfoGetter.getUserCompRel();
        StringBuffer sb = new StringBuffer();
        List<Group> groupList = TokenSecurityInfoGetter.getUserCompRel().getGroups();
        if(groupList!=null && groupList.size()>0) {
            sb.append("(");
            for(int i=0;i<groupList.size();i++) {
                Group group = groupList.get(i);
                sb.append(" find_in_set('"+group.getGroupId()+"',group_id)");
                if(i!=groupList.size()-1){
                    sb.append(" or ");
                }
            }
            sb.append(")");
        }

        Long companyId = userCompRel.getCompany().getCompId();
        Map map = new HashMap<String,String>();
        map.put("orderDesc",dto.getOrderDesc());
        map.put("orderFiled",dto.getOrderFiled());
        map.put("planStatus",dto.getPlanStatus());//计划状态
        map.put("sendOrderType",dto.getSendOrderType());//计划类型
        map.put("customerName",dto.getCustomerName()); //客户名称
        map.put("companyId", companyId);
        map.put("page_no", dto.getPageNo());
        map.put("page_size", dto.getPageSize());

        if (!sb.toString().isEmpty()) {
            map.put("groupIds", sb.toString());
        }
        PageInfo pg = iPlanRpcService4Wechat.wayBillPlanList(map);
        PageBaseDto pageBaseDto = new PageBaseDto(pg.getList(), pg.getTotal());
        return pageBaseDto;
    }




    @ApiOperation("直接-派单")
    @RequestMapping(value = "/carrier/splitGoods4Direct",method = RequestMethod.POST)
    public JSONObject splitGoods4Direct(SplitGoodsParamsDto dto) {
        UserCompRel userCompRel = TokenSecurityInfoGetter.getUserCompRel();
        int flag = iPlanRpcService4Wechat.splitGoods4Direct(dto,userCompRel);
        JSONObject jsonObject = new JSONObject();
        String message = null;
        int code = -1;
        if (flag==1) {
            code = 0;
        } else {
            message = "操作失败，请重试！";
        }
        jsonObject.put("message",message);
        jsonObject.put("code",code);
        return jsonObject;
    }



    @ApiOperation("竞价-派单")
    @RequestMapping(value = "/carrier/splitGoods4Bidding",method = RequestMethod.POST)
    public JSONObject splitGoods4Bidding(BindingSplitParamsDto dto) {
        UserCompRel userCompRel = TokenSecurityInfoGetter.getUserCompRel();
        int flag = iPlanRpcService4Wechat.splitGoods4Bidding(dto,userCompRel);
        JSONObject jsonObject = new JSONObject();
        String message = null;
        int code = -1;
        if (flag==1) {
            code = 0;
        } else {
            message = "操作失败，请重试！";
        }
        jsonObject.put("message",message);
        jsonObject.put("code",code);
        return jsonObject;
    }



}
