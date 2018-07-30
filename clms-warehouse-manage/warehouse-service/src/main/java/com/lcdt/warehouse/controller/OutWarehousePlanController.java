package com.lcdt.warehouse.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.userinfo.model.Group;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.warehouse.dto.*;
import com.lcdt.warehouse.entity.InWarehousePlan;
import com.lcdt.warehouse.entity.OutOrderGoodsInfo;
import com.lcdt.warehouse.entity.OutWarehousePlan;
import com.lcdt.warehouse.service.OutOrderGoodsInfoService;
import com.lcdt.warehouse.service.OutWarehousePlanService;
import com.lcdt.warehouse.utils.DateUtils;
import com.lcdt.warehouse.utils.GroupIdsUtil;
import com.lcdt.warehouse.utils.InplanUtil;
import com.lcdt.warehouse.utils.OutplanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author code generate
 * @since 2018-05-07
 * @Desciption: 仓储出库计划API
 */
@RestController
@RequestMapping("/out/plan")
@Api(value = "仓储出库计划API",description = "仓储出库计划API接口")
public class OutWarehousePlanController {
    private static Logger logger = LoggerFactory.getLogger(InWarehousePlanController.class);

    @Autowired
    private OutWarehousePlanService outWarehousePlanService;

    @Autowired
    private OutOrderGoodsInfoService outOrderGoodsInfoService;



    @ApiOperation("出库计划列表")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN')  or hasAuthority('wh_out_plan_search')")
    public PageBaseDto inPlanList(@Validated OutWhPlanSearchParamsDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        dto.setCompanyId(companyId);
        if (dto.getCreateBegin()!=null && dto.getCreateBegin()>0) {
            dto.setCreateBeginStr(DateUtils.stampToDate(dto.getCreateBegin()));
        }
        if (dto.getCreateBegin()!=null && dto.getCreateEnd()>0) {
            dto.setCreateEndStr(DateUtils.stampToDate(dto.getCreateEnd()));
        }
        if(!StringUtils.isEmpty(dto.getPlanStatus()) && dto.getPlanStatus().equals("00")) {
            dto.setPlanStatus(null); //查询所有
        }
        dto.setGroupIds(GroupIdsUtil.getOwnGroupIds(dto.getGroupId()));
//        if(StringUtils.isEmpty(dto.getGroupId())) {
//            StringBuffer sb = new StringBuffer();
//            List<Group> groupList = SecurityInfoGetter.groups();
//            if(groupList!=null && groupList.size()>0) {
//                for(int i=0;i<groupList.size();i++) {
//                    Group group = groupList.get(i);
//                    sb.append(group.getGroupId()+",");
//                }
//                dto.setGroupIds(sb.toString().substring(0,sb.toString().length()-1));
//            }
//        } else {
//            if (dto.getGroupId()>0) {
//                dto.setGroupIds(dto.getGroupId().toString());
//            }
//        }
        Page pg = outWarehousePlanService.outWarehousePlanList(dto,new Page<OutWarehousePlan>(dto.getPageNo(), dto.getPageSize()));
        List<InWarehousePlan> inWarehousePlanList = pg.getRecords();
        PageBaseDto result = new PageBaseDto(inWarehousePlanList,pg.getTotal());
        return result;
    }


    @ApiOperation("计划详细")
    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_out_plan_search')")
    public OutWhPlanDto detail(@ApiParam(value = "计划ID",required = true) @RequestParam Long outPlanId,
                             @ApiParam(value = "是否加载配仓",required = true) @RequestParam boolean flag) {
        UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
        JSONObject jsonObject = new JSONObject();
        OutWhPlanDto outWhPlanDto = outWarehousePlanService.outWhPlanDetail(outPlanId,flag, userCompRel,false);
        if(outWhPlanDto!=null) {
            List<OutWhPlanGoodsDto> outWhPlanGoodsDtoList = outWhPlanDto.getOutWhPlanGoodsDtoList();
            if (outWhPlanGoodsDtoList!=null && outWhPlanGoodsDtoList.size()>0) {
                for (OutWhPlanGoodsDto dto : outWhPlanGoodsDtoList)  {
                    OutOrderGoodsInfo outOrderGoodsInfo = outOrderGoodsInfoService.queryOutboundQuantity(userCompRel.getCompId(),outPlanId,dto.getRelationId());
                    if (outOrderGoodsInfo != null) {
                        dto.setOutboundQuantity(outOrderGoodsInfo.getOutboundQuantity());

                    }
                }
            }
        }
        return outWhPlanDto;
    }



    @ApiOperation("计划保存")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_out_plan_add')")
    public JSONObject add(@RequestBody OutWhPlanDto outWhPlanDto) {
        UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
        String msg = "新建失败！";
        OutWarehousePlan outWarehousePlan = null;
        JSONObject jsonObject = new JSONObject();
        try {
            outWarehousePlan = outWarehousePlanService.outWhPlanAdd(outWhPlanDto, userCompRel);
        } catch (RuntimeException e) {
            msg = e.getMessage();
            logger.error(e.getMessage());
        }
        jsonObject.put("code", outWarehousePlan==null? -1:0);
        jsonObject.put("message", outWarehousePlan==null? msg:"新建成功！");
        return jsonObject;
    }


    @ApiOperation("计划编辑")
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_out_plan_edit')")
    public JSONObject edit(@RequestBody OutWhPlanDto outWhPlanDto) {
        UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
        String msg = "编辑失败！";
        boolean flag = false;
        JSONObject jsonObject = new JSONObject();
        try {
            flag = outWarehousePlanService.outWhPlanEdit(outWhPlanDto, userCompRel);
        } catch (RuntimeException e) {
            msg = e.getMessage();
            logger.error(e.getMessage());
        }
        jsonObject.put("code", flag==true? 0:-1);
        jsonObject.put("message", flag==true? "编辑成功！":msg);
        return jsonObject;
    }




    @ApiOperation("发布")
    @RequestMapping(value = "/publish",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_out_plan_publish')")
    public JSONObject publish(@ApiParam(value = "计划ID",required = true) @RequestParam Long planOutId) {
        UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
        OutWarehousePlan obj = new OutWarehousePlan();
        obj.setOutplanId(planOutId);
        JSONObject jsonObject = new JSONObject();
        boolean flag = false;
        String msg = "发布失败！";
        try {
            flag = outWarehousePlanService.outWhPlanPublish(obj, userCompRel);
        } catch (RuntimeException e) {
            msg = e.getMessage();
            logger.error(e.getMessage());
        }
        jsonObject.put("code", flag==true? 0:-1);
        jsonObject.put("message", flag==true? "发布成功 ！":msg);
        return jsonObject;
    }




    @ApiOperation("完成")
    @RequestMapping(value = "/complete",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_out_plan_complete')")
    public JSONObject complete(@ApiParam(value = "计划ID",required = true) @RequestParam Long planOutId) {
        UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
        OutWarehousePlan obj = new OutWarehousePlan();
        obj.setOutplanId(planOutId);
        JSONObject jsonObject = new JSONObject();
        boolean flag = false;
        String msg = "操作失败！";
        try {
            flag = outWarehousePlanService.outWhPlanComplete(obj, userCompRel);
        } catch (RuntimeException e) {
            msg = e.getMessage();
            logger.error(e.getMessage());
        }
        jsonObject.put("code", flag==true? 0:-1);
        jsonObject.put("message", flag==true? "操作成功！":msg);
        return jsonObject;
    }


    @ApiOperation("取消")
    @RequestMapping(value = "/cancel",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_out_plan_cancel')")
    public JSONObject cancel(@ApiParam(value = "计划ID",required = true) @RequestParam Long planOutId) {
        UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
        OutWarehousePlan obj = new OutWarehousePlan();
        obj.setOutplanId(planOutId);
        JSONObject jsonObject = new JSONObject();
        boolean flag = false;
        String msg = "取消失败！";
        try {
            flag = outWarehousePlanService.outWhPlanCancel(obj, userCompRel);
        } catch (RuntimeException e) {
            msg = e.getMessage();
            logger.error(e.getMessage());
        }
        jsonObject.put("code", flag==true? 0:-1);
        jsonObject.put("message", flag==true? "取消成功！":msg);
        return jsonObject;
    }


    @ApiOperation("计划配仓")
    @RequestMapping(value = "/distributeWh",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_out_plan_dis')")
    public JSONObject distributeWh(@RequestBody OutWhPlanDto outWhPlanDto) {
        UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
        String msg = "配仓失败！";
        boolean flag = false;
        JSONObject jsonObject = new JSONObject();
        try {
           flag = outWarehousePlanService.distributeWh(outWhPlanDto, userCompRel);
        } catch (RuntimeException e) {
            msg = e.getMessage();
            logger.error(e.getMessage());
        }
        jsonObject.put("code", flag==true? 0:-1);
        jsonObject.put("message", flag==true? "配仓成功！":msg);
        return jsonObject;
    }




    @ApiOperation("出库计划导出")
    @RequestMapping(value = "/exportOutplan",method = RequestMethod.GET)
    public void exportOutplan(@ApiParam(value = "计划ID",required = true) @RequestParam Long outPlanId,
                             HttpServletResponse response) throws IOException {
        UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
        OutWhPlanDto outWhPlanDto = outWarehousePlanService.outWhPlanDetail(outPlanId,true, userCompRel,false);
        if(outWhPlanDto!=null) {

            File fi = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "templates/出库计划.xlsx");
            if (fi.exists()) {
                OutputStream os = response.getOutputStream();
                response.reset();
                XSSFWorkbook wb = null;
                try {
                    wb = new XSSFWorkbook(new FileInputStream(fi));    // 读取excel模板
                    XSSFSheet sheet = wb.getSheetAt(0);  // 读取了模板内所有sheet内容
                    XSSFRow row = sheet.getRow(0);
                    XSSFCell cell = row.getCell(0);
                    cell.setCellValue("出库计划-"+outWhPlanDto.getPlanNo());

                    row = sheet.getRow(2);
                    cell= row.getCell(2);
                    cell.setCellValue(outWhPlanDto.getGroupName()==null?"所属项目":outWhPlanDto.getGroupName());//所属项目

                    row = sheet.getRow(4);
                    cell = row.getCell(2);
                    cell.setCellValue(outWhPlanDto.getCustomerName()==null?"客户名称":outWhPlanDto.getCustomerName());//客户名称

                    row = sheet.getRow(5);
                    cell = row.getCell(2);
                    cell.setCellValue(outWhPlanDto.getCustomerContactName()==null?"联系人":outWhPlanDto.getCustomerContactName());//联系人

                    row = sheet.getRow(5);
                    cell = row.getCell(9);
                    cell.setCellValue(outWhPlanDto.getCustomerContactPhone()==null?"联系电话":outWhPlanDto.getCustomerContactPhone());//联系电话

                    row = sheet.getRow(7);
                    cell = row.getCell(2);
                    cell.setCellValue(outWhPlanDto.getWarehouseName()==null?"":outWhPlanDto.getWarehouseName());//仓库

                    row = sheet.getRow(7);
                    cell = row.getCell(9);
                    cell.setCellValue(outWhPlanDto.getStorageType()==null?"": OutplanUtil.convertStorageType(outWhPlanDto.getStorageType()));//出库类型

                    row = sheet.getRow(8);
                    cell = row.getCell(2);
                    cell.setCellValue(outWhPlanDto.getStoragePlanTime()==null?"计划日期":outWhPlanDto.getStoragePlanTime());//计划日期

                    row = sheet.getRow(9);
                    cell = row.getCell(2);
                    cell.setCellValue(outWhPlanDto.getStorageRemark()==null?"":outWhPlanDto.getStorageRemark());//备注

                    row = sheet.getRow(11);
                    cell = row.getCell(2);
                    cell.setCellValue(outWhPlanDto.getPickupUnit()==null?"提货单位":outWhPlanDto.getPickupUnit());//提货单位

                    row = sheet.getRow(12);
                    cell = row.getCell(2);
                    cell.setCellValue(outWhPlanDto.getPickupLinkman()==null?"提货人":outWhPlanDto.getPickupLinkman());//提货人

                    row = sheet.getRow(12);
                    cell = row.getCell(9);
                    cell.setCellValue(outWhPlanDto.getPickupIdentiycard()==null?"提货人身份证号":outWhPlanDto.getPickupIdentiycard());//提货人身份证号

                    row = sheet.getRow(13);
                    cell = row.getCell(2);
                    cell.setCellValue(outWhPlanDto.getPickupPhone()==null?"提货人电话":outWhPlanDto.getPickupPhone());//提货人电话


                    row = sheet.getRow(13);
                    cell = row.getCell(9);
                    cell.setCellValue(outWhPlanDto.getPickupCar()==null?"提货人车辆":outWhPlanDto.getPickupCar());//提货人车辆

                    List<OutWhPlanGoodsDto> outWhPlanGoodsDtoList = outWhPlanDto.getOutWhPlanGoodsDtoList();
                    if (outWhPlanGoodsDtoList!=null && outWhPlanGoodsDtoList.size()>0) {

                        int rows = 16,i=1;
                        for (OutWhPlanGoodsDto dto : outWhPlanGoodsDtoList)  {

                            row = sheet.getRow(rows);
                            OutOrderGoodsInfo outOrderGoodsInfo = outOrderGoodsInfoService.queryOutboundQuantity(userCompRel.getCompId(),outPlanId,dto.getRelationId());
                            if (outOrderGoodsInfo != null) {
                                dto.setOutboundQuantity(outOrderGoodsInfo.getOutboundQuantity());
                                row.getCell(10).setCellValue(dto.getOutboundQuantity());
                            }
                            row.getCell(0).setCellValue(i++);
                            row.getCell(1).setCellValue(dto.getGoodsName());
                            row.getCell(3).setCellValue(dto.getGoodsCode());
                            row.getCell(5).setCellValue(dto.getGoodsBarcode());
                            row.getCell(6).setCellValue(dto.getUnit());
                            row.getCell(7).setCellValue(dto.getPlanGoodsNum());
                            row.getCell(8).setCellValue(dto.getOutOderGoodsNum());
                            row.getCell(9).setCellValue(dto.getRemainGoodsNum());
                            row.getCell(11).setCellValue(dto.getOutHousePrice());
                            rows++;
                        }

                    }
                    String fileName ="出库计划.xlsx";
                    response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes("gbk"),"iso-8859-1") + "\"");
                    response.setContentType("application/octet-stream;charset=UTF-8");
                    OutputStream ouputStream = response.getOutputStream();
                    wb.write(ouputStream);
                    ouputStream.flush();
                    ouputStream.close();
                } catch (Exception e) {
                    logger.error("导出excel出现异常:", e);
                }

            }








        }

    }


}

