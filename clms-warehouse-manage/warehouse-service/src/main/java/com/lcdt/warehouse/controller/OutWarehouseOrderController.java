package com.lcdt.warehouse.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.userinfo.model.User;
import com.lcdt.warehouse.dto.*;
import com.lcdt.warehouse.service.OutWarehouseOrderService;
import com.lcdt.warehouse.utils.DateUtils;
import com.lcdt.warehouse.utils.GroupIdsUtil;
import com.lcdt.warehouse.utils.OutplanUtil;
import com.lcdt.warehouse.vo.ConstantVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

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
 * @since 2018-05-25
 */
@RestController
@RequestMapping("/wh/out")
@Api(value = "出库单api", description = "出库单操作")
public class OutWarehouseOrderController {

    @Autowired
    OutWarehouseOrderService outWarehouseOrderService;

    @ApiOperation("出库单新增")
    @PostMapping(value = "/order")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_out_order_add')")
    public JSONObject addOutWarehouseOrder(@RequestBody OutWhOrderDto params){
        Long companyId = SecurityInfoGetter.getCompanyId();
        User user = SecurityInfoGetter.getUser();
        params.setCompanyId(companyId);
        params.setCreateId(user.getUserId());
        params.setCreateName(user.getRealName());
        int result=outWarehouseOrderService.addOutWarehouseOrder(params);
        JSONObject jsonObject=new JSONObject();
        if(result>0){
            jsonObject.put("code",0);
            jsonObject.put("message","新增成功");
        }else{
            jsonObject.put("code",-1);
            jsonObject.put("message","新增失败");
        }

        return jsonObject;
    }


    @ApiOperation("出库单列表")
    @GetMapping(value = "/order")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_out_order_search')")
    public PageBaseDto outWarehouseOrderList(OutWhOrderSearchDto params) {
        return getPageBaseDto(params);
    }


    @ApiOperation("出库单详细")
    @GetMapping(value = "/order/{outorderId}")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_out_order_search')")
    public OutWhOrderDto outWarehouseOrderDetail(@PathVariable Long outorderId) {
        OutWhOrderDto outWhOrderDto = new OutWhOrderDto();
        outWhOrderDto = outWarehouseOrderService.queryOutWarehouseOrder(SecurityInfoGetter.getCompanyId(), outorderId);
        return outWhOrderDto;
    }

    @ApiOperation("出库单出库")
    @PatchMapping(value = "/order/outbound")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_out_order_outbound')")
    public JSONObject outbound(@RequestBody OutWhOrderOutboundParamsDto params) {
        ModifyOutOrderStatusParamsDto statusParams = new ModifyOutOrderStatusParamsDto();
        User user = SecurityInfoGetter.getUser();
        statusParams.setOutorderId(params.getOutorderId());
        statusParams.setUpdateId(user.getUserId());
        statusParams.setUpdateName(user.getRealName());
//        statusParams.setOrderStatus(ConstantVO.OUT_ORDER_STATUS_HAVE_OUTBOUND);
        statusParams.setCompanyId(SecurityInfoGetter.getCompanyId());
        statusParams.setOutboundTime(params.getOutboundTime());

        boolean result = outWarehouseOrderService.outbound(statusParams, params.getOutOrderGoodsInfoList());
        JSONObject jsonObject = new JSONObject();
        if (result) {
            jsonObject.put("code", 0);
            jsonObject.put("message", "出库成功");
        } else {
            jsonObject.put("code", -1);
            jsonObject.put("message", "出库失败");
        }
        return jsonObject;
    }

    @ApiOperation("出库单取消")
    @PatchMapping(value = "/order/cancel/{outorderId}")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_out_order_cancel')")
    public JSONObject cancelOutbound(@PathVariable long outorderId) {
        ModifyOutOrderStatusParamsDto params = new ModifyOutOrderStatusParamsDto();
        User user = SecurityInfoGetter.getUser();
        params.setOutorderId(outorderId);
        params.setUpdateId(user.getUserId());
        params.setUpdateName(user.getRealName());
        params.setOrderStatus(ConstantVO.OUT_ORDER_STATUS_HAVE_CANCEL);
        params.setCompanyId(SecurityInfoGetter.getCompanyId());

        boolean result = outWarehouseOrderService.modifyOutOrderStatus(params);
        JSONObject jsonObject = new JSONObject();
        if (result) {
            jsonObject.put("code", 0);
            jsonObject.put("message", "取消出库单成功");
        } else {
            jsonObject.put("code", -1);
            jsonObject.put("message", "取消出库单失败");
        }
        return jsonObject;
    }

    @ApiOperation("出库配仓信息，计划用")
    @GetMapping(value = "/order/distribution/records")
    public PageBaseDto queryDisOutRecords(@RequestParam Long outPlanId) {
        PageBaseDto pageBaseDto = new PageBaseDto(outWarehouseOrderService.queryOutOrderDisRecords(SecurityInfoGetter.getCompanyId(), outPlanId));
        return pageBaseDto;
    }

    @ApiOperation("概览出库单已完成数量")
    @GetMapping(value = "/outWarehouseBillNum")
    public JSONObject outWarehouseNum(OutWhOrderSearchDto params) {
        params.setCompanyId(SecurityInfoGetter.getCompanyId());
        String [] inOrderStatus = {"2"};
        params.setOrderStatus(inOrderStatus);

        JSONObject jo =  new JSONObject();
        jo.put("code", 0);
        jo.put("data",outWarehouseOrderService.selectOutWarehouseNum(params));

        return jo;
    }


    @ApiOperation("首页出库单数量")
    @GetMapping(value = "/outWarehouseBillNum4Index")
    public JSONObject outWarehouseNum4Index(OutWhOrderSearchDto params) {
        params.setCompanyId(SecurityInfoGetter.getCompanyId());
        String [] inOrderStatus = {"2","1"};
        params.setOrderStatus(inOrderStatus);

        JSONObject jo =  new JSONObject();
        jo.put("code", 0);
        jo.put("data",outWarehouseOrderService.selectOutWarehouseNum(params));

        return jo;
    }

    @ApiOperation("概览出库单待出库")
    @GetMapping(value = "/order/wait")
    public PageBaseDto outWarehouseOrderWaiteOutbound(OutWhOrderSearchDto params) {
        return getPageBaseDto(params);
    }

    private PageBaseDto getPageBaseDto(OutWhOrderSearchDto params){
        params.setGroupIds(GroupIdsUtil.getOwnGroupIds(params.getGroupId()))
                .setCompanyId(SecurityInfoGetter.getCompanyId());
        Page<OutWhOrderDto> inWarehouseOrderPage = outWarehouseOrderService.queryOutWarehouseOrderList(params);
        return new PageBaseDto(inWarehouseOrderPage.getRecords(), inWarehouseOrderPage.getTotal());
    }



    @ApiOperation("出库单导出")
    @GetMapping(value = "/export/{outorderId}")
    public void exportOutWarehouseOrderDetail(@PathVariable Long outorderId,HttpServletResponse response) throws IOException {
        OutWhOrderDto outWhOrderDto = outWarehouseOrderService.queryOutWarehouseOrder(SecurityInfoGetter.getCompanyId(), outorderId);
        ClassPathResource resource = new ClassPathResource("/templates/out_warehouse_order.xlsx");
        if (resource.exists()) {
            response.reset();
            XSSFWorkbook wb = null;
            try {
                wb = new XSSFWorkbook(resource.getInputStream());    // 读取excel模板
                XSSFSheet sheet = wb.getSheetAt(0);  // 读取了模板内所有sheet内容
                XSSFRow row = sheet.getRow(0);
                XSSFCell cell = row.getCell(0);
                cell.setCellValue("出库单-" + outWhOrderDto.getOutorderNo());

                row = sheet.getRow(2);
                cell = row.getCell(11);
                cell.setCellValue(outWhOrderDto.getCustomerName() == null ? "" : outWhOrderDto.getCustomerName());//客户名称

                row = sheet.getRow(3);
                cell = row.getCell(11);
                cell.setCellValue(outWhOrderDto.getCustomerContactName() == null ? "" : outWhOrderDto.getCustomerContactName());//联系人

                row = sheet.getRow(3);
                cell = row.getCell(37);
                cell.setCellValue(outWhOrderDto.getCustomerContactPhone() == null ? "" : outWhOrderDto.getCustomerContactPhone());//联系电话

                row = sheet.getRow(4);
                cell = row.getCell(11);
                cell.setCellValue(outWhOrderDto.getCreateDate() == null ? "" : DateUtils.date2String(outWhOrderDto.getCreateDate(),"yyyy-MM-dd hh:mm:ss"));//创建时间

                row = sheet.getRow(6);
                cell = row.getCell(11);
                cell.setCellValue(outWhOrderDto.getWarehouseName() == null ? "" : outWhOrderDto.getWarehouseName());//仓库

                row = sheet.getRow(6);
                cell = row.getCell(37);
                cell.setCellValue(outWhOrderDto.getOutboundType() == null ? "" : OutplanUtil.convertStorageType(outWhOrderDto.getOutboundType()));//出库类型

                row = sheet.getRow(7);
                cell = row.getCell(11);
                cell.setCellValue(outWhOrderDto.getOutboundPlanTime() == null ? "" : DateUtils.date2String(outWhOrderDto.getOutboundPlanTime(),"yyyy-MM-dd hh:mm:ss"));//计划日期                row = sheet.getRow(7);

                row = sheet.getRow(7);
                cell = row.getCell(37);
                cell.setCellValue(outWhOrderDto.getOutboundTime() == null ? "" : DateUtils.date2String(outWhOrderDto.getOutboundTime(),"yyyy-MM-dd hh:mm:ss"));//实际日期

                row = sheet.getRow(8);
                cell = row.getCell(11);
                cell.setCellValue(outWhOrderDto.getOutPlanRemark() == null ? "" : outWhOrderDto.getOutPlanRemark());//计划备注

                row = sheet.getRow(9);
                cell = row.getCell(11);
                cell.setCellValue(outWhOrderDto.getOutboundRemark() == null ? "" : outWhOrderDto.getOutboundRemark());//计划备注

                row = sheet.getRow(11);
                cell = row.getCell(11);
                cell.setCellValue(outWhOrderDto.getPickupUnit() == null ? "" : outWhOrderDto.getPickupUnit());//提货单位

                row = sheet.getRow(12);
                cell = row.getCell(11);
                cell.setCellValue(outWhOrderDto.getPickupLinkman() == null ? "" : outWhOrderDto.getPickupLinkman());//提货人

                row = sheet.getRow(12);
                cell = row.getCell(37);
                cell.setCellValue(outWhOrderDto.getPickupIdentiycard() == null ? "" : outWhOrderDto.getPickupIdentiycard());//提货人身份证号

                row = sheet.getRow(13);
                cell = row.getCell(11);
                cell.setCellValue(outWhOrderDto.getPickupPhone() == null ? "" : outWhOrderDto.getPickupPhone());//提货人电话


                row = sheet.getRow(13);
                cell = row.getCell(37);
                cell.setCellValue(outWhOrderDto.getPickupVehicleNum() == null ? "" : outWhOrderDto.getPickupVehicleNum());//提货人车辆

                List<OutOrderGoodsInfoDto> outOrderGoodsInfoList = outWhOrderDto.getOutOrderGoodsInfoList();
                if (outOrderGoodsInfoList != null && outOrderGoodsInfoList.size() > 0) {

                    int rows = 16, i = 1;
                    for (OutOrderGoodsInfoDto dto : outOrderGoodsInfoList) {

                        row = sheet.getRow(rows);
                        row.getCell(0).setCellValue(i++);
                        row.getCell(2).setCellValue(dto.getGoodsName());
                        row.getCell(11).setCellValue(dto.getGoodsCode());
                        row.getCell(16).setCellValue(dto.getGoodsBarCode());
                        row.getCell(21).setCellValue(dto.getUnit());
                        row.getCell(25).setCellValue(dto.getOutboundPrice());
                        row.getCell(29).setCellValue(dto.getBatch());
                        row.getCell(34).setCellValue(dto.getStorageLocationCode());
                        row.getCell(40).setCellValue(dto.getGoodsNum());
                        row.getCell(45).setCellValue(dto.getOutboundQuantity());
                        row.getCell(50).setCellValue(dto.getGoodsNum()-dto.getOutboundQuantity());
                        rows++;
                    }

                }
                String fileName = "出库单.xlsx";
                response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes("gbk"), "iso-8859-1") + "\"");
                response.setContentType("application/octet-stream;charset=UTF-8");
                OutputStream ouputStream = response.getOutputStream();
                wb.write(ouputStream);
                ouputStream.flush();
                ouputStream.close();

            } catch (Exception e) {
//                logger.error("导出excel出现异常:", e);
            }

        }
    }

}

