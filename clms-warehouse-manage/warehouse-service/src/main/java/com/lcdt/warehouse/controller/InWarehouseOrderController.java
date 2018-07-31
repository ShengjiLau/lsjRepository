package com.lcdt.warehouse.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.warehouse.dto.*;
import com.lcdt.warehouse.entity.InWarehouseOrder;
import com.lcdt.warehouse.entity.InorderGoodsInfo;
import com.lcdt.warehouse.service.InWarehouseOrderService;
import com.lcdt.warehouse.utils.DateUtils;
import com.lcdt.warehouse.utils.GroupIdsUtil;
import com.lcdt.warehouse.utils.InplanUtil;
import com.lcdt.warehouse.vo.ConstantVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author code generate
 * @since 2018-05-07
 */
@RestController
@RequestMapping("/wh/in")
@Api(value = "入库单api", description = "入库单操作")
public class InWarehouseOrderController {

    @Autowired
    InWarehouseOrderService inWarehouseOrderService;

    @ApiOperation("入库单新增")
    @PostMapping(value = "/order")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_in_order_add')")
    public JSONObject inWarehouseOrder(@RequestBody InWarehouseOrderDto params) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User user = SecurityInfoGetter.getUser();
        params.setCompanyId(companyId);
        params.setCreateId(user.getUserId());
        params.setCreateName(user.getRealName());
        params.setCreateDate(new Date());
        int result = 0;
        if (params.getOperationType() == 0) {
            result = inWarehouseOrderService.addInWarehouseOrder(params);
        } else if (params.getOperationType() == 1) {
            result = inWarehouseOrderService.addAndStorageInWarehouseOrder(params);
        }

        JSONObject jsonObject = new JSONObject();
        if (result > 0) {
            jsonObject.put("code", 0);
            jsonObject.put("message", "添加成功");
        } else {
            jsonObject.put("code", -1);
            jsonObject.put("message", "添加失败");
        }
        return jsonObject;
    }

    @ApiOperation("入库单列表")
    @GetMapping(value = "/order")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_in_order_search')")
    public PageBaseDto inWarehouseOrderList(InWarehouseOrderSearchParamsDto params) {
        return getPageBaseDto(params);
    }

    @ApiOperation("入库单详细")
    @GetMapping(value = "/order/{inorderId}")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_in_order_search')")
    public InWarehouseOrderDetailDto inWarehouseOrderDetail(@PathVariable long inorderId) {
        return inWarehouseOrderService.queryInWarehouseOrder(SecurityInfoGetter.getCompanyId(), inorderId);
    }

    @ApiOperation("入库单入库")
    @PatchMapping(value = "/order/storage")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_in_order_storage')")
    public JSONObject inStorage(@RequestBody InWarehouseOrderStorageParamsDto params) {
        ModifyInOrderStatusParamsDto statusParams = new ModifyInOrderStatusParamsDto();
        User user = SecurityInfoGetter.getUser();
        statusParams.setInorderId(params.getInorderId());
        statusParams.setUpdateId(user.getUserId());
        statusParams.setUpdateName(user.getRealName());
        statusParams.setInOrderStatus(ConstantVO.IN_ORDER_STATUS_HAVE_STORAGE);
        statusParams.setCompanyId(SecurityInfoGetter.getCompanyId());
        statusParams.setStorageTime(params.getStorageTime());

        boolean result = inWarehouseOrderService.storage(statusParams, params.getGoodsInfoDtoList());
        JSONObject jsonObject = new JSONObject();
        if (result) {
            jsonObject.put("code", 0);
            jsonObject.put("message", "入库成功");
        } else {
            jsonObject.put("code", -1);
            jsonObject.put("message", "入库失败");
        }
        return jsonObject;
    }

    @ApiOperation("入库单取消")
    @PatchMapping(value = "/order/cancel/{inorderId}")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_in_order_cancel')")
    public JSONObject cancelStorage(@PathVariable long inorderId) {
        ModifyInOrderStatusParamsDto params = new ModifyInOrderStatusParamsDto();
        User user = SecurityInfoGetter.getUser();
        params.setInorderId(inorderId);
        params.setUpdateId(user.getUserId());
        params.setUpdateName(user.getRealName());
        params.setInOrderStatus(ConstantVO.IN_ORDER_STATUS_HAVE_CANCEL);
        params.setCompanyId(SecurityInfoGetter.getCompanyId());

        boolean result = inWarehouseOrderService.modifyInOrderStatus(params);
        JSONObject jsonObject = new JSONObject();
        if (result) {
            jsonObject.put("code", 0);
            jsonObject.put("message", "取消入库单成功");
        } else {
            jsonObject.put("code", -1);
            jsonObject.put("message", "取消入库单失败");
        }
        return jsonObject;
    }

    @ApiOperation("配仓信息，计划用")
    @RequestMapping(value = "/order/distribution/records", method = RequestMethod.GET)
    public PageBaseDto queryDisRecords(@RequestParam Long planId) {
        PageBaseDto pageBaseDto = new PageBaseDto(inWarehouseOrderService.queryDisRecords(SecurityInfoGetter.getCompanyId(), planId));
        return pageBaseDto;
    }


    @ApiOperation("计划下的入库单")
    @GetMapping(value = "/plan/order")
    public PageBaseDto inWarehouseOrderListOfPlan(InWarehouseOrderSearchParamsDto params) {
        params.setCompanyId(SecurityInfoGetter.getCompanyId());
        Page<InWarehouseOrderDto> inWarehouseOrderPage = inWarehouseOrderService.queryInWarehouseOrderListOfPlan(params);
        PageBaseDto pageBaseDto = new PageBaseDto(inWarehouseOrderPage.getRecords(), inWarehouseOrderPage.getTotal());
        return pageBaseDto;
    }


    @ApiOperation("概览入库单已完成数量")
    @GetMapping(value = "/inWarehouseBillNum")
    public JSONObject inWarehouseNum(InWarehouseOrderSearchParamsDto params) {
        params.setCompanyId(SecurityInfoGetter.getCompanyId());
        String[] inOrderStatus = {"2"};
        params.setInOrderStatus(inOrderStatus);

        JSONObject jo = new JSONObject();
        jo.put("code", 0);
        jo.put("data", inWarehouseOrderService.selectInWarehouseNum(params));

        return jo;
    }


    @ApiOperation("概览入库单已完成数量")
    @GetMapping(value = "/inWarehouseBillNum4Index")
    public JSONObject inWarehouseBillNum4Index(InWarehouseOrderSearchParamsDto params) {
        params.setCompanyId(SecurityInfoGetter.getCompanyId());
        String[] inOrderStatus = {"2", "1"};
        params.setInOrderStatus(inOrderStatus);

        JSONObject jo = new JSONObject();
        jo.put("code", 0);
        jo.put("data", inWarehouseOrderService.selectInWarehouseNum(params));

        return jo;
    }

    @ApiOperation("概览入库单待入库数量")
    @GetMapping(value = "/order/wait")
    public PageBaseDto inWarehouseOrderWaiteStorage(InWarehouseOrderSearchParamsDto params){
        return getPageBaseDto(params);
    }


    @ApiOperation("入库单导出")
    @GetMapping(value = "/export/{inorderId}")
    public void export(@PathVariable Long inorderId,HttpServletResponse response) throws IOException {
        InWarehouseOrderDto orderDeatil = inWarehouseOrderService.queryInWarehouseOrderDetail(SecurityInfoGetter.getCompanyId(),inorderId);
        File fi = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "templates/入库单.xlsx");
        if (fi.exists()) {
            response.reset();
            XSSFWorkbook wb = null;
            try {
                wb = new XSSFWorkbook(new FileInputStream(fi));    // 读取excel模板
                XSSFSheet sheet = wb.getSheetAt(0);  // 读取了模板内所有sheet内容
                XSSFRow row = sheet.getRow(0);
                XSSFCell cell = row.getCell(0);
                cell.setCellValue("入库单-"+orderDeatil.getInOrderCode());

                row = sheet.getRow(2);
                cell= row.getCell(11);
                cell.setCellValue(orderDeatil.getCustomerName()==null?"":orderDeatil.getCustomerName());//客户名称

                row = sheet.getRow(3);
                cell = row.getCell(11);
                cell.setCellValue(orderDeatil.getCustomerContactName()==null?"":orderDeatil.getCustomerContactName());//联系人

                row = sheet.getRow(3);
                cell = row.getCell(39);
                cell.setCellValue(orderDeatil.getCustomerContactPhone()==null?"":orderDeatil.getCustomerContactPhone());//联系电话

                row = sheet.getRow(4);
                cell = row.getCell(11);
                cell.setCellValue(orderDeatil.getCreateDate()==null?"": DateUtils.date2String(orderDeatil.getCreateDate(),"yyyy-MM-dd hh:mm:ss"));//创建时间

                row = sheet.getRow(6);
                cell = row.getCell(11);
                cell.setCellValue(orderDeatil.getWarehouseName()==null?"":orderDeatil.getWarehouseName());//入库仓库

                row = sheet.getRow(6);
                cell = row.getCell(39);
                cell.setCellValue(orderDeatil.getStorageType()==null?"": InplanUtil.convertStorageType(orderDeatil.getStorageType()));//入库类型

                row = sheet.getRow(7);
                cell = row.getCell(11);
                cell.setCellValue(orderDeatil.getStoragePlanTime()==null ? "" : DateUtils.date2String(orderDeatil.getStoragePlanTime(),"yyyy-MM-dd hh:mm:ss"));//计划入库日期

                row = sheet.getRow(7);
                cell = row.getCell(39);
                cell.setCellValue(orderDeatil.getStorageTime()==null ? "" : DateUtils.date2String(orderDeatil.getStorageTime(),"yyyy-MM-dd hh:mm:ss"));//实际入库日期

                row = sheet.getRow(8);
                cell = row.getCell(11);
                cell.setCellValue(orderDeatil.getStorageRemark()==null?"":orderDeatil.getStorageRemark());//备注

                row = sheet.getRow(10);
                cell = row.getCell(11);
                cell.setCellValue(orderDeatil.getDeliverymanName()==null?"":orderDeatil.getDeliverymanName());//送货单位

                row = sheet.getRow(10);
                cell = row.getCell(39);
                cell.setCellValue(orderDeatil.getDeliverymanLinkman()==null?"":orderDeatil.getDeliverymanLinkman());//送货人

                row = sheet.getRow(11);
                cell = row.getCell(11);
                cell.setCellValue(orderDeatil.getDeliverymanPhone()==null?"":orderDeatil.getDeliverymanPhone());//送货人电话

                row = sheet.getRow(11);
                cell = row.getCell(39);
                cell.setCellValue(orderDeatil.getDeliverymanCar()==null?"":orderDeatil.getDeliverymanCar());//送货人车辆

                List<InorderGoodsInfoDto> inorderGoodsInfoDtoList = orderDeatil.getGoodsInfoDtoList();
                if (null != inorderGoodsInfoDtoList && !inorderGoodsInfoDtoList.isEmpty()) {
                    int rows = 14,i=1;
                    for (InorderGoodsInfoDto dto : inorderGoodsInfoDtoList) {
                        row = sheet.getRow(rows);
                        row.getCell(0).setCellValue(i++);
                        row.getCell(2).setCellValue(dto.getGoodsName());
                        row.getCell(11).setCellValue(dto.getGoodsCode());
                        row.getCell(16).setCellValue(dto.getGoodsBarcode());
                        row.getCell(21).setCellValue(dto.getUnit());
                        row.getCell(26).setCellValue(dto.getReceivalbeAmount());
                        row.getCell(31).setCellValue(dto.getInHouseAmount());
                        row.getCell(36).setCellValue(dto.getDamage());
                        row.getCell(42).setCellValue(dto.getBatch());
                        row.getCell(47).setCellValue(dto.getStorageLocationCode());
                        rows++;
                    }

                }
                String fileName ="入库单.xlsx";
                response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes("gbk"),"iso-8859-1") + "\"");
                response.setContentType("application/octet-stream;charset=UTF-8");
                OutputStream ouputStream = response.getOutputStream();
                wb.write(ouputStream);
                ouputStream.flush();
                ouputStream.close();

            } catch (Exception e) {
//                logger.error("导出excel出现异常:", e);
            }finally {

            }

        }
    }

    /**
     * 提取列表，和待入库概览
     * @param params
     * @return
     */
    private PageBaseDto getPageBaseDto(InWarehouseOrderSearchParamsDto params){
        params.setGroupIds(GroupIdsUtil.getOwnGroupIds(params.getGroupId()))
                .setCompanyId(SecurityInfoGetter.getCompanyId());
        Page<InWarehouseOrderDto> inWarehouseOrderPage = inWarehouseOrderService.queryInWarehouseOrderList(params);
        return new PageBaseDto(inWarehouseOrderPage.getRecords(), inWarehouseOrderPage.getTotal());
    }




}

