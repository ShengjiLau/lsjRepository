package com.lcdt.driver.wechat.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.driver.dto.PageBaseDto;
import com.lcdt.traffic.dto.DriverWaybillParamsDto;
import com.lcdt.traffic.dto.OwnCompany4SnatchRdto;
import com.lcdt.traffic.dto.SnatchOfferDto;
import com.lcdt.traffic.dto.SnathBill4WaittingPdto;
import com.lcdt.traffic.model.SnatchGoods;
import com.lcdt.traffic.service.IPlanRpcService4Wechat;
import com.lcdt.userinfo.model.User;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by yangbinq on 2018/3/19.
 * Desciption: 运输入计划API
 */

@RestController
@RequestMapping("/plan")
public class PlanApi {

   @Reference
   private IPlanRpcService4Wechat iPlanRpcService4Wechat;

   @ApiOperation("竞价--企业组")
   @RequestMapping(value = "/driver/companyGroupList", method = RequestMethod.GET)
   public List<OwnCompany4SnatchRdto>  companyGroupList() {
      User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      List<OwnCompany4SnatchRdto> ownCompany4SnatchRdtoList = iPlanRpcService4Wechat.ownCompanyList(loginUser.getUserId());
      return ownCompany4SnatchRdtoList;
   }

   @ApiOperation("竞价--待抢")
   @RequestMapping(value = "/driver/waittingSnatchList", method = RequestMethod.GET)
   public PageBaseDto  waittingSnatchList(SnathBill4WaittingPdto dto) {
      PageInfo pg = iPlanRpcService4Wechat.snatchBill4WaittingList(dto);
      PageBaseDto pageBaseDto = new PageBaseDto(pg.getList(), pg.getTotal());
      return pageBaseDto;
   }


   @ApiOperation("竞价--已抢")
   @RequestMapping(value = "/driver/compleateSnatchList", method = RequestMethod.GET)
   public PageBaseDto  compleateSnatchList(SnathBill4WaittingPdto dto) {
      PageInfo pg = iPlanRpcService4Wechat.snatchBill4CompleteList(dto);
      PageBaseDto pageBaseDto = new PageBaseDto(pg.getList(), pg.getTotal());
      return pageBaseDto;
   }

   @ApiOperation("竞价--报价")
   @RequestMapping(value = "/driver/driverOffer", method = RequestMethod.POST)
   public JSONObject driverOffer(@RequestBody SnatchOfferDto dto) {
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
      } else {
         message = "操作失败，请重试！";
      }
      jsonObject.put("message",message);
      jsonObject.put("code",code);
      return jsonObject;
   }



}
