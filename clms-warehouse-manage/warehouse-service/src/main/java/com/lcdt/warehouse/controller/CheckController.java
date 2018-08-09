package com.lcdt.warehouse.controller;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.warehouse.dto.*;
import com.lcdt.warehouse.entity.TCheck;
import com.lcdt.warehouse.entity.TCheckItem;
import com.lcdt.warehouse.service.CheckService;
import com.lcdt.warehouse.utils.DateUtils;
import com.lcdt.warehouse.utils.InplanUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.tl.commons.util.DateUtility;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/15.
 */
@RestController
@RequestMapping("/checkapi")
public class CheckController {

    Logger logger = LoggerFactory.getLogger(CheckController.class);
    @Autowired
    private CheckService checkService;

    @ApiOperation("盘库列表")
    @RequestMapping(value = "/checkList", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_check_search')")
    public PageBaseDto checkList(CheckParamDto checkDto) {
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        Long userId = SecurityInfoGetter.getUser().getUserId(); //获取用户id
        String userName = SecurityInfoGetter.getUser().getRealName();   //获取用户姓名

        checkDto.setCompanyId(companyId);

        List<CheckListDto> checkList = checkService.selectList(checkDto);
        PageBaseDto pageBaseDto = new PageBaseDto(checkList, checkList.size());
        return pageBaseDto;
    }

    @ApiOperation("根据id读盘库记录")
    @RequestMapping(value = "/findCheckById", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_check_search') or hasAuthority('wh_check_complete')")
    public JSONObject findCheckById(@RequestParam Long checkId) {
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        Long userId = SecurityInfoGetter.getUser().getUserId(); //获取用户id
        String userName = SecurityInfoGetter.getUser().getRealName();   //获取用户姓名
        CheckParamDto checkDto = new CheckParamDto();
        checkDto.setCompanyId(companyId);
        checkDto.setCheckId(checkId);
        List<CheckListDto> checkList = checkService.selectList(checkDto);
        JSONObject jsonObject = new JSONObject();

        if (CollectionUtils.isNotEmpty(checkList)){
            CheckListDto dto  = checkList.get(0);
            dto.setCompleteName(userName);
            jsonObject.put("data", dto);
        }
//        jsonObject.put("userName",userName);
        jsonObject.put("code", 0);
        jsonObject.put("message", "读取成功！");
        return jsonObject;
    }

    @ApiOperation("取消盘库")
    @RequestMapping(value = "/cancelCheck/{checkId}", method = RequestMethod.PATCH)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_check_cancel')")
    public JSONObject cancelCheck(@PathVariable Long checkId) {
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        Long userId = SecurityInfoGetter.getUser().getUserId(); //获取用户id
        String userName = SecurityInfoGetter.getUser().getRealName();   //获取用户姓名
        CheckParamDto checkDto = new CheckParamDto();
        checkDto.setCompanyId(companyId);
        checkDto.setCheckId(checkId);
        checkDto.setUpdateDate(new Date());
        checkDto.setUpdateId(userId);
        checkDto.setUpdateName(userName);
        checkDto.setCheckStatus((Integer) CheckStatusEnum.cancel.getValue());
        int result = checkService.cancelCheck(checkDto);

        JSONObject jsonObject = new JSONObject();
        String message = "";
        int code = -1;
        if (result > 0) {
            code = 0;
            message = "取消成功！";
        } else {
            message = "取消失败，请重试！";
        }
        jsonObject.put("message", message);
        jsonObject.put("code", code);
        return jsonObject;
    }

    @ApiOperation("保存盘库单和明细")
    @RequestMapping(value = "/saveCheckAndItems", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_check_create')")
    public JSONObject saveCheckAndItems(@Validated CheckSaveDto checkSaveDto, BindingResult bindingResult) {
//        if(1==1) {
//            throw new RuntimeException("异常测试");
//        }
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        Long userId = SecurityInfoGetter.getUser().getUserId(); //获取用户id
        String userName = SecurityInfoGetter.getUser().getRealName();   //获取用户姓名
        TCheck check = new TCheck();
        BeanUtils.copyProperties(checkSaveDto, check);
        check.setCheckStatus((Integer) CheckStatusEnum.watting.getValue());
        check.setIsDeleted(0);
        check.setCreateDate(new Date());
        check.setCreateId(userId);
        check.setCreateName(userName);
        check.setCompanyId(companyId);
        List<Map<String, Object>> items = checkSaveDto.getItems();
        System.out.println("check.getWarehouseId:"+check.getWarehouseId());
        System.out.println("check.getWarehouseName:"+check.getWarehouseName());
        boolean result = checkService.insertCheckAndItems(check, items);

        JSONObject jsonObject = new JSONObject();
        String message = "";
        int code = -1;
        if (result) {
            code = 0;
            message = "保存成功！";
        } else {
            message = "保存失败，请重试！";
        }
        jsonObject.put("message", message);
        jsonObject.put("code", code);
        jsonObject.put("data", checkSaveDto);
        return jsonObject;
    }
    @ApiOperation("保存盘库单和明细")
    @RequestMapping(value = "/updateAttachment", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_check_create')")
    public JSONObject updateAttachment(@Validated CheckSaveDto checkSaveDto) {
        System.out.println("checkSaveDto:"+checkSaveDto);
        JSONObject jsonObject = new JSONObject();
        String message = "附件更新失败";
        boolean result = false;
        int code = -1;
         TCheck check = checkService.selectById(checkSaveDto.getCheckId());
        if(check==null){
            jsonObject.put("message", message);
            jsonObject.put("code", code);
            return jsonObject;
        }
        check.setAttachment(checkSaveDto.getAttachment());
        result  = checkService.updateAllColumnById(check);
        if(result){
            code=0;
            message="附件更新成功";
        }
        jsonObject.put("message", message);
        jsonObject.put("code", code);
        return jsonObject;
    }

    @ApiOperation("保存并完成盘库")
    @RequestMapping(value = "/updateCheckAndComplete", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_check_complete')")
    public JSONObject updateCheckAndComplete(@Validated CheckSaveDto checkSaveDto) {
        JSONObject jsonObject = new JSONObject();
        String message = "";
        boolean result = true;
        int code = -1;
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        Long userId = SecurityInfoGetter.getUser().getUserId(); //获取用户id
        String userName = SecurityInfoGetter.getUser().getRealName();   //获取用户姓名

        TCheck check = new TCheck();
        BeanUtils.copyProperties(checkSaveDto,check);
        check.setCompleteDate(DateUtils.string2Date_safe(checkSaveDto.getCompleteDate(),"yyyy-MM-dd"));
        check.setCheckStatus((Integer) CheckStatusEnum.completed.getValue());
        check.setUpdateDate(new Date());
        check.setUpdateName(userName);
        check.setUpdateId(userId);

        List<Map<String, Object>> items = checkSaveDto.getItems();
        try {
            checkService.completeCheckAndItems(check, items);
        }catch (RuntimeException e){
            e.printStackTrace();
            message = e.getLocalizedMessage();
            jsonObject.put("message", message);
            jsonObject.put("code", code);
            return jsonObject;
        }

        if (result) {
            code = 0;
            message = "保存成功！";
        } else {
            message = "保存失败，请重试！";
        }
        jsonObject.put("message", message);
        jsonObject.put("code", code);
        return jsonObject;
    }


    @ApiOperation("首页待盘库数量")
    @RequestMapping(value = "/findWaittingChecks", method = RequestMethod.GET)
    public JSONObject findWaittingChecks() {
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        Long userId = SecurityInfoGetter.getUser().getUserId(); //获取用户id
        String userName = SecurityInfoGetter.getUser().getRealName();   //获取用户姓名
        CheckParamDto checkDto = new CheckParamDto();
        checkDto.setCompanyId(companyId);
        checkDto.setCheckStatus((Integer) CheckStatusEnum.watting.getValue());
        int num = checkService.selectWattingNum(checkDto);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("checkNums", num);

//        jsonObject.put("userName",userName);
        jsonObject.put("code", 0);
        jsonObject.put("message", "读取成功！");
        return jsonObject;
    }


    @ApiOperation("导出盘库")
    @RequestMapping(value = "/exportCheck",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_check_search')")
    public void exportCheck(@ApiParam(value = "盘库单ID",required = true) @RequestParam Long checkId,
                                  HttpServletResponse response) throws IOException {
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        Long userId = SecurityInfoGetter.getUser().getUserId(); //获取用户id
        String userName = SecurityInfoGetter.getUser().getRealName();   //获取用户姓名
        CheckParamDto checkDto = new CheckParamDto();
        checkDto.setCompanyId(companyId);
        checkDto.setCheckId(checkId);
        List<CheckListDto> checkList = checkService.selectList(checkDto);
        JSONObject jsonObject = new JSONObject();
        CheckListDto dto = new CheckListDto();
        if (CollectionUtils.isNotEmpty(checkList)) {
            dto = checkList.get(0);
        }


        //File fi = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "templates/入库计划.xlsx");
        ClassPathResource resource = new ClassPathResource("/templates/盘库单.xlsx");
        if (resource.exists()) {
            response.reset();
            XSSFWorkbook wb = null;
            try {
                wb = new XSSFWorkbook(resource.getInputStream());    // 读取excel模板
                XSSFSheet sheet = wb.getSheetAt(0);  // 读取了模板内所有sheet内容
                XSSFRow row = sheet.getRow(0);
                XSSFCell cell = row.getCell(0);
                cell.setCellValue("盘库单 - " + dto.getCheckNum());

                row = sheet.getRow(2);
                cell = row.getCell(11);
                cell.setCellValue(dto.getWarehouseName() == null ? "" : dto.getWarehouseName());//仓库名称

                cell = row.getCell(36);
                cell.setCellValue(dto.getGroupName() == null ? "" : dto.getGroupName());//所属项目

                row = sheet.getRow(3);
                cell = row.getCell(11);
                cell.setCellValue(dto.getCustomerName() == null ? "" : dto.getCustomerName());//客户名称

                row = sheet.getRow(4);
                cell = row.getCell(11);
                cell.setCellValue(dto.getCompleteName() == null ? "" : dto.getCompleteName());//盘库人员

                row = sheet.getRow(4);
                cell = row.getCell(36);
                cell.setCellValue(dto.getCompleteDate() == null ? "" : DateUtility.date2String(dto.getCompleteDate(),"yyyy-MM-dd HH:mm"));//盘库时间

                row = sheet.getRow(5);
                cell = row.getCell(11);
                cell.setCellValue(dto.getRemark() == null ? "" : dto.getRemark());//备注

                if (null != dto.getItemList() && dto.getItemList().size() > 0) {
                    int rows = 8, i = 1;
                    for (TCheckItem item : dto.getItemList()) {
                        row = sheet.getRow(rows);
                        row.getCell(0).setCellValue(i++);
                        row.getCell(2).setCellValue(item.getGoodsName());
                        row.getCell(11).setCellValue(item.getGoodsCode());
                        row.getCell(17).setCellValue(item.getGoodsBarcode());
                        row.getCell(23).setCellValue(item.getGoodsUnit());
                        row.getCell(28).setCellValue(item.getGoodsBatch());
                        row.getCell(33).setCellValue(item.getStorageLocationCode());
                        row.getCell(39).setCellValue(item.getInvertoryAmount());
                        row.getCell(43).setCellValue(item.getCheckAmount()!=null?item.getCheckAmount().toString():"");
                        row.getCell(48).setCellValue(item.getDifferentAmount()!=null?item.getDifferentAmount().toString():"");
                        rows++;
                    }
                }
                String fileName = "盘库单-" + dto.getCheckNum() + ".xlsx";
                response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes("gbk"), "iso-8859-1") + "\"");
                response.setContentType("application/octet-stream;charset=UTF-8");
                OutputStream ouputStream = response.getOutputStream();
                wb.write(ouputStream);
                ouputStream.flush();
                ouputStream.close();

            } catch (Exception e) {
                logger.error("导出excel出现异常:", e);
            } finally {
                wb.close();
            }
        }
    }
}
