package com.lcdt.warehouse.controller.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.pay.rpc.CompanyServiceCountService;
import com.lcdt.userinfo.model.User;
import com.lcdt.util.ClmsBeanUtil;
import com.lcdt.warehouse.dto.AllotDto;
import com.lcdt.warehouse.entity.AllotProduct;
import com.lcdt.warehouse.service.AllotService;
import com.lcdt.warehouse.utils.DateToStringUtils;
import com.lcdt.warehouse.utils.JSONResponseUtil;
import com.lcdt.warehouse.utils.ResponseMessage;
import com.lcdt.warehouse.utils.SheetUtils;
import com.lcdt.warehouse.utils.StreamUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by liz on 2018/5/8.
 */
@RestController
@RequestMapping("/api/allot")
@Api(value = "调拨api", description = "调拨操作")
@Slf4j
public class AllotApi {
    @Autowired
    private AllotService allotService;
    @Reference
    CompanyServiceCountService companyServiceCountService;

    @ApiOperation(value = "调拨单列表", notes = "调拨单列表数据")
    @GetMapping("/allotList")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('allot_get')")
    public ResponseMessage allotList(@Validated AllotDto dto,
                                     @ApiParam(value = "页码",required = true, defaultValue = "1") @RequestParam Integer pageNo,
                                     @ApiParam(value = "每页显示条数",required = true, defaultValue = "10") @RequestParam Integer pageSize) {
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        dto.setCompanyId(companyId);
        dto.setIsDeleted((short)0);

        Map map= ClmsBeanUtil.beanToMap(dto);
        map.put("pageNo", pageNo);
        map.put("pageSize", pageSize);
        Page<AllotDto> listPageInfo = allotService.allotDtoList(map);
        return JSONResponseUtil.success(listPageInfo);
    }

    @ApiOperation("新增")
    @RequestMapping(value = "/addAllot", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('allot_add')")
    public JSONObject addAllot(@Validated @RequestBody AllotDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
//        int storageServiceCount = companyServiceCountService.companyProductCount(companyId, "storage_service");
//        if(storageServiceCount > 0) {
            User user = SecurityInfoGetter.getUser();
            dto.setAllotStatus((short) 1);//在途
            dto.setCreateId(user.getUserId());
            dto.setCreateName(user.getRealName());
            dto.setCreateTime(new Date());
            dto.setCompanyId(companyId);
            dto.setIsDeleted((short) 0);

            boolean result = allotService.addAllot(dto);
            if (result) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code", 0);
                jsonObject.put("message", "添加成功");
                return jsonObject;
            } else {
                throw new RuntimeException("添加失败");
            }
//        }else {
//            throw new RuntimeException("仓单服务余额不足");
//        }
    }

    @ApiOperation("编辑")
    @RequestMapping(value = "/modifyAllot", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('allot_modify')")
    public JSONObject modifyAllot(@Validated @RequestBody AllotDto dto) {
        boolean result = allotService.modifyAllot(dto);
        if (result) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "修改成功");
            return jsonObject;
        } else {
            throw new RuntimeException("修改失败");
        }
    }

    @ApiOperation("取消")
    @RequestMapping(value = "/cancelAllot", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('allot_delete')")
    public JSONObject cancelAllot(@ApiParam(value = "调拨单id",required = true) @RequestParam Long allotId) {
        boolean result = allotService.cancelAllot(allotId);
        if (result) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "取消成功");
            return jsonObject;
        } else {
            throw new RuntimeException("取消失败");
        }
    }

    @ApiOperation("详情")
    @RequestMapping(value = "/allotDetail", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('allot_get')")
    public JSONObject allotDetail(@ApiParam(value = "调拨单id",required = true) @RequestParam Long allotId) {
        AllotDto dto = allotService.getAllotInfo(allotId);
        if (dto != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data", dto);
            jsonObject.put("code", 0);
            jsonObject.put("message", "调拨单详情");
            return jsonObject;
        } else {
            throw new RuntimeException("获取失败");
        }
    }

    @ApiOperation("入库")
    @RequestMapping(value = "/allotPutInStorage", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('allot_put_in_storage')")
    public JSONObject allotPutInStorage(@Validated @RequestBody AllotDto dto) {
        boolean result = allotService.allotPutInStorage(dto);
        if (result) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "入库成功");
            return jsonObject;
        } else {
            throw new RuntimeException("入库失败");
        }
    }
    
    
    @ApiOperation(value = "调拨单列表-无权限", notes = "调拨单列表数据")
    @GetMapping("/list")
    public ResponseMessage allotListWithoutAuthorize(@Validated AllotDto dto,
                                     @ApiParam(value = "页码",required = true, defaultValue = "1") @RequestParam Integer pageNo,
                                     @ApiParam(value = "每页显示条数",required = true, defaultValue = "10") @RequestParam Integer pageSize) {
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        dto.setCompanyId(companyId);
        dto.setIsDeleted((short)0);

        Map map= ClmsBeanUtil.beanToMap(dto);
        map.put("pageNo", pageNo);
        map.put("pageSize", pageSize);
        Page<AllotDto> listPageInfo = allotService.allotDtoList(map);
        return JSONResponseUtil.success(listPageInfo);
    }
    
    
    @ApiOperation("导出")
    @RequestMapping(value = "/export/{allotId}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('allot_export')")
    public void exportAllot(@PathVariable Long allotId, HttpServletResponse response) {
    	AllotDto dto = allotService.getAllotInfo(allotId);
    	ClassPathResource resource = new ClassPathResource("/templates/allot_order.xlsx");
    	if (resource.exists()) {
    		response.reset();
    		XSSFWorkbook xwb = null;
			InputStream inputStream = null;
			OutputStream ouputStream = null;
			try {
				inputStream = resource.getInputStream();
				xwb = (XSSFWorkbook) WorkbookFactory.create(inputStream);
				
				SheetUtils SheetUtils = new SheetUtils(xwb, 0);
				SheetUtils.setCell(0, 0, "调拨单-" + dto.getAllotCode());
				SheetUtils.setCell(4, 2, allotService.getGroupName(dto.getGroupId()));
				SheetUtils.setCell(4, 6, DateToStringUtils.ConvertDateToString(dto.getCreateTime()));
				SheetUtils.setCell(5, 2, dto.getCustomerName());
				SheetUtils.setCell(5, 6, dto.getContactName());
				SheetUtils.setCell(6, 2, dto.getPhoneNum());
				SheetUtils.setCell(7, 2, dto.getWarehouseInName());	
				SheetUtils.setCell(7, 6, DateToStringUtils.ConvertDateToString(dto.getAllotInTime()));
				SheetUtils.setCell(8, 2, dto.getWarehouseOutName());	
				SheetUtils.setCell(8, 6, DateToStringUtils.ConvertDateToString(dto.getAllotOutTime()));	
				SheetUtils.setCell(9, 2, dto.getOperator());	
				SheetUtils.setCell(10, 2, dto.getRemark());	
				 
				 List<AllotProduct> allotProductList = dto.getAllotProductList();
				 int goodsRow = 14;
				 for (int i = 0; i < allotProductList.size(); i++) {
					 AllotProduct allotProduct = allotProductList.get(i);
					 SheetUtils SheetUtilsGoods = new SheetUtils(xwb, 0, goodsRow);
					 SheetUtilsGoods.setCell(0, allotProduct.getName());
					 SheetUtilsGoods.setCell(1, allotProduct.getCode());		 
					 SheetUtilsGoods.setCell(2, allotProduct.getBarCode()); 
					 SheetUtilsGoods.setCell(3, allotProduct.getSpec());
					 SheetUtilsGoods.setCell(4, allotProduct.getUnit());
					 SheetUtilsGoods.setCell(5, allotProduct.getBatchNum());
					 SheetUtilsGoods.setCell(6, allotProduct.getWarehouseLocCode());
					 SheetUtilsGoods.setCell(7, allotProduct.getAllotNum().doubleValue());
					 SheetUtilsGoods.setCell(8, allotProduct.getRemark());
					 goodsRow ++;
				 }
				 
				 String fileName = "调拨单" + dto.getAllotCode() + ".xlsx";
				 ouputStream = StreamUtils.getOutputStream(response, fileName);
	             xwb.write(ouputStream);				
			}catch(Exception e) {
				e.printStackTrace();
				log.debug("导出出现异常：" + e.getMessage());
			}finally {
				StreamUtils.closeStream(xwb);
				StreamUtils.closeStream(inputStream);
				StreamUtils.closeStream(ouputStream);
			}
    	}
    }
    
    
    
    
    
    
}
