package com.lcdt.warehouse.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.warehouse.dto.InWhPlanDto;
import com.lcdt.warehouse.dto.InWhPlanGoodsDto;
import com.lcdt.warehouse.dto.InWhPlanSearchParamsDto;
import com.lcdt.warehouse.dto.PageBaseDto;
import com.lcdt.warehouse.entity.InWarehousePlan;
import com.lcdt.warehouse.service.InWarehousePlanService;
import com.lcdt.warehouse.utils.DateUtils;
import com.lcdt.warehouse.utils.GroupIdsUtil;
import com.lcdt.warehouse.utils.InplanUtil;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;
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

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by yangbinq on 2018/05/08.
 * Desciption: 仓储入库计划API
 */
@RestController
@RequestMapping("/in/plan")
@Api(value = "仓储入库计划API",description = "仓储入库计划API接口")
public class InWarehousePlanController {

    private static Logger logger = LoggerFactory.getLogger(InWarehousePlanController.class);

    @Autowired
    private InWarehousePlanService inWarehousePlanService;

    @ApiOperation("入库计划列表")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_in_plan_search')")
    public PageBaseDto inPlanList(@Validated InWhPlanSearchParamsDto dto) {
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
        Page pg = inWarehousePlanService.inWarehousePlanList(dto,new Page<InWarehousePlan>(dto.getPageNo(), dto.getPageSize()));
        List<InWarehousePlan> inWarehousePlanList = pg.getRecords();
        PageBaseDto result = new PageBaseDto(inWarehousePlanList,pg.getTotal());
        return result;
    }


    @ApiOperation("发布")
    @RequestMapping(value = "/publish",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_in_plan_publish')")
    public JSONObject publish(@ApiParam(value = "计划ID",required = true) @RequestParam Long planId) {
        UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
        InWarehousePlan obj = new InWarehousePlan();
        obj.setPlanId(planId);
        JSONObject jsonObject = new JSONObject();
        boolean flag = false;
        String msg = "发布失败！";
        try {
            flag = inWarehousePlanService.inWhPlanPublish(obj, userCompRel);
        } catch (RuntimeException e) {
            msg = e.getMessage();
            logger.error(e.getMessage());
        }
        jsonObject.put("code", flag==true? 0:-1);
        jsonObject.put("message", flag==true? "发布成功！":msg);
        return jsonObject;
    }


    @ApiOperation("取消")
    @RequestMapping(value = "/cancel",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_in_plan_cancel')")
    public JSONObject cancel(@ApiParam(value = "计划ID",required = true) @RequestParam Long planId) {
        UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
        InWarehousePlan obj = new InWarehousePlan();
        obj.setPlanId(planId);
        JSONObject jsonObject = new JSONObject();
        boolean flag = false;
        String msg = "取消失败！";
        try {
            flag = inWarehousePlanService.inWhPlanCancel(obj, userCompRel);
        } catch (RuntimeException e) {
            msg = e.getMessage();
            logger.error(e.getMessage());
        }
        jsonObject.put("code", flag==true? 0:-1);
        jsonObject.put("message", flag==true? "取消成功！":msg);
        return jsonObject;
    }



    @ApiOperation("完成")
    @RequestMapping(value = "/complete",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_in_plan_complete')")
    public JSONObject complete(@ApiParam(value = "计划ID",required = true) @RequestParam Long planId) {
        UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
        InWarehousePlan obj = new InWarehousePlan();
        obj.setPlanId(planId);
        JSONObject jsonObject = new JSONObject();
        boolean flag = false;
        String msg = "操作失败！";
        try {
            flag = inWarehousePlanService.inWhPlanComplete(obj, userCompRel);
        } catch (RuntimeException e) {
            msg = e.getMessage();
            logger.error(e.getMessage());
        }
        jsonObject.put("code", flag==true? 0:-1);
        jsonObject.put("message", flag==true? "操作成功！":msg);
        return jsonObject;
    }



    @ApiOperation("计划保存")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_in_plan_add')")
    public JSONObject add(@RequestBody InWhPlanDto inWhPlanAddParamsDto) {
        UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
        String msg = "新建失败！";
        InWarehousePlan inWarehousePlan = null;
        JSONObject jsonObject = new JSONObject();
        try {
            inWarehousePlan = inWarehousePlanService.inWhPlanAdd(inWhPlanAddParamsDto, userCompRel);
        } catch (RuntimeException e) {
            msg = e.getMessage();
            logger.error(e.getMessage());
        }
        jsonObject.put("code", inWarehousePlan==null? -1:0);
        jsonObject.put("message", inWarehousePlan==null? msg:"新建成功！");
        return jsonObject;
    }



    @ApiOperation("计划详细")
    @RequestMapping(value = "/detail",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_in_plan_search')")
    public JSONObject detail(@ApiParam(value = "计划ID",required = true) @RequestParam Long planId,
                             @ApiParam(value = "是否加载配仓",required = true) @RequestParam boolean flag,
                             @ApiParam(value = "加载配仓完成后，是否显示",required = true) @RequestParam boolean flag1) {
        UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
        JSONObject jsonObject = new JSONObject();
        InWhPlanDto inWhPlanDto = inWarehousePlanService.inWhPlanDetail(planId,flag, userCompRel,true,flag1);
        jsonObject.put("data",inWhPlanDto);
        jsonObject.put("code", 0);
        return jsonObject;
    }



    @ApiOperation("计划编辑")
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_in_plan_edit')")
    public JSONObject edit(@RequestBody InWhPlanDto inWhPlanAddParamsDto) {
        UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
        String msg = "编辑失败！";
        boolean flag = false;
        JSONObject jsonObject = new JSONObject();
        try {
            flag = inWarehousePlanService.inWhPlanEdit(inWhPlanAddParamsDto, userCompRel);
        } catch (RuntimeException e) {
            msg = e.getMessage();
            logger.error(e.getMessage());
        }
        jsonObject.put("code", flag==true? 0:-1);
        jsonObject.put("message", flag==true? "编辑成功！":msg);
        return jsonObject;
    }


    @ApiOperation("计划配仓")
    @RequestMapping(value = "/distributeWh",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_in_plan_dis')")
    public JSONObject distributeWh(@RequestBody InWhPlanDto inWhPlanAddParamsDto) {
        UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
        String msg = "配仓失败！";
        boolean flag = false;
        JSONObject jsonObject = new JSONObject();
        try {
            flag = inWarehousePlanService.distributeWh(inWhPlanAddParamsDto, userCompRel);
        } catch (RuntimeException e) {
            msg = e.getMessage();
            logger.error(e.getMessage());
        }
        jsonObject.put("code", flag==true? 0:-1);
        jsonObject.put("message", flag==true? "配仓成功！":msg);
        return jsonObject;
    }



    @ApiOperation("入库计划导出")
    @RequestMapping(value = "/exportInplan",method = RequestMethod.GET)
    public void exportInplan(@ApiParam(value = "计划ID",required = true) @RequestParam Long planId,
                                   HttpServletResponse response) throws IOException {
        UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
        InWhPlanDto inWhPlanDto = inWarehousePlanService.inWhPlanDetail(planId, true, userCompRel, true, false);
        File fi = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "templates/入库计划.xlsx");
        if (fi.exists()) {
            OutputStream os = response.getOutputStream();
            response.reset();
            XSSFWorkbook wb = null;
            try {
                wb = new XSSFWorkbook(new FileInputStream(fi));    // 读取excel模板
                XSSFSheet sheet = wb.getSheetAt(0);  // 读取了模板内所有sheet内容
                XSSFRow row = sheet.getRow(0);
                XSSFCell cell = row.getCell(0);
                cell.setCellValue("入库计划-"+inWhPlanDto.getPlanNo());

                row = sheet.getRow(2);
                cell= row.getCell(11);
                cell.setCellValue(inWhPlanDto.getGroupName()==null?"":inWhPlanDto.getGroupName());//所属项目

                row = sheet.getRow(3);
                cell = row.getCell(11);
                cell.setCellValue(inWhPlanDto.getContractNo()==null?"":inWhPlanDto.getContractNo());//合同单号

                row = sheet.getRow(3);
                cell = row.getCell(36);
                cell.setCellValue(inWhPlanDto.getCustomerPurchaseNo()==null?"":inWhPlanDto.getCustomerPurchaseNo());//采购单号

                row = sheet.getRow(5);
                cell = row.getCell(11);
                cell.setCellValue(inWhPlanDto.getCustomerName()==null?"":inWhPlanDto.getCustomerName());//客户名称


                row = sheet.getRow(6);
                cell = row.getCell(11);
                cell.setCellValue(inWhPlanDto.getCustomerContactName()==null?"":inWhPlanDto.getCustomerContactName());//联系人

                row = sheet.getRow(6);
                cell = row.getCell(36);
                cell.setCellValue(inWhPlanDto.getCustomerContactPhone()==null?"":inWhPlanDto.getCustomerContactPhone());//联系电话

                row = sheet.getRow(8);
                cell = row.getCell(11);
                cell.setCellValue(inWhPlanDto.getWarehouseName()==null?"":inWhPlanDto.getWarehouseName());//入库仓库

                row = sheet.getRow(8);
                cell = row.getCell(36);
                cell.setCellValue(inWhPlanDto.getStorageType()==null?"": InplanUtil.convertStorageType(inWhPlanDto.getStorageType()));//入库类型

                row = sheet.getRow(9);
                cell = row.getCell(11);
                cell.setCellValue(inWhPlanDto.getStoragePlanTime()==null?"":inWhPlanDto.getStoragePlanTime());//计划日期

                row = sheet.getRow(10);
                cell = row.getCell(11);
                cell.setCellValue(inWhPlanDto.getStorageRemark()==null?"":inWhPlanDto.getStorageRemark());//备注

                row = sheet.getRow(12);
                cell = row.getCell(11);
                cell.setCellValue(inWhPlanDto.getDeliverymanName()==null?"":inWhPlanDto.getDeliverymanName());//送货单位

                row = sheet.getRow(12);
                cell = row.getCell(36);
                cell.setCellValue(inWhPlanDto.getDeliverymanLinkman()==null?"":inWhPlanDto.getDeliverymanLinkman());//送货人

                row = sheet.getRow(13);
                cell = row.getCell(11);
                cell.setCellValue(inWhPlanDto.getDeliverymanPhone()==null?"":inWhPlanDto.getDeliverymanPhone());//送货人电话

                row = sheet.getRow(13);
                cell = row.getCell(36);
                cell.setCellValue(inWhPlanDto.getDeliverymanCar()==null?"":inWhPlanDto.getDeliverymanCar());//送货人车辆

                List<InWhPlanGoodsDto> inWhPlanGoodsDtoList = inWhPlanDto.getInWhPlanGoodsDtoList();
                if (null!=inWhPlanGoodsDtoList && inWhPlanGoodsDtoList.size()>0) {
                    int rows = 16,i=1;
                    for (InWhPlanGoodsDto dto : inWhPlanGoodsDtoList) {
                         row = sheet.getRow(rows);
                         row.getCell(0).setCellValue(i++);
                        row.getCell(2).setCellValue("商品名称");
                        row.getCell(11).setCellValue("商品编码");
                        row.getCell(19).setCellValue("商品条码");

                        row.getCell(26).setCellValue("计量单位");
                        row.getCell(31).setCellValue("计划数量");
                        row.getCell(36).setCellValue("已入库数量");
                        row.getCell(42).setCellValue("待配数量");
                        row.getCell(47).setCellValue("入库单价");
                        rows++;



                    }
                }
                String fileName ="入库计划.xlsx";
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


    public static String encodeFilename(String filename, HttpServletResponse request) {
        /**
         * 获取客户端浏览器和操作系统信息
         * 在IE浏览器中得到的是：User-Agent=Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; Maxthon; Alexa Toolbar)
         * 在Firefox中得到的是：User-Agent=Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.7.10) Gecko/20050717 Firefox/1.0.6
         */
        String agent = request.getHeader("USER-AGENT");
        try {
            if ((agent != null) && (-1 != agent.indexOf("MSIE"))) {
                String newFileName = URLEncoder.encode(filename, "UTF-8");
                newFileName = StringUtils.replace(newFileName, "+", "%20");
                if (newFileName.length() > 150) {
                    newFileName = new String(filename.getBytes("GB2312"), "ISO8859-1");
                    newFileName = StringUtils.replace(newFileName, " ", "%20");
                }
                return newFileName;
            }
            if ((agent != null) && (-1 != agent.indexOf("Mozilla")))
                return MimeUtility.encodeText(filename, "UTF-8", "B");

            return filename;
        } catch (Exception ex) {
            return filename;
        }
    }



}

