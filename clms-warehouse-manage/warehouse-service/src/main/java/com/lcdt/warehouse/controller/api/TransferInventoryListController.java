package com.lcdt.warehouse.controller.api;


import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.lcdt.util.ResponseJsonUtils;
import com.lcdt.warehouse.controller.exception.ShiftInventoryException;
import com.lcdt.warehouse.dto.PageBaseDto;
import com.lcdt.warehouse.dto.TransferInventoryListDTO;
import com.lcdt.warehouse.dto.TransferListDTO;
import com.lcdt.warehouse.entity.TransferGoodsDO;
import com.lcdt.warehouse.service.TransferInventoryListService;
import com.lcdt.warehouse.utils.CloseStream;
import com.lcdt.warehouse.utils.DateToStringUtils;
import com.lcdt.warehouse.utils.SheetUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Sheng-ji Lau
 * @date 2018年8月1日
 * @version
 * @Description: TODO 
 */
@RestController
@RequestMapping("/api/transfer")
@Api(description = "库存转换单api")
@Slf4j
public class TransferInventoryListController {
	
	@Autowired
	private TransferInventoryListService transferInventoryListService;
	
	@PostMapping("/add")
	@ApiOperation(value = "创建库存转换单")
	@PreAuthorize(value = "hasRole('ROLE_SYS_ADMIN') or hasAuthority('transfer_inventory_add')")
	public JSONObject insertTransferInventoryList(@RequestBody TransferInventoryListDTO transferInventoryListDTO) {
		int result = transferInventoryListService.insertTransferInventoryList(transferInventoryListDTO);
		String message = null;
		if (result > 0) {
			message = "创建成功";
			return ResponseJsonUtils.successResponseJsonWithoutData(message);
		}else {
			message = "创建库存转换单时出现异常！";
			throw new ShiftInventoryException(message);
		}
	}
	
	
	@PostMapping("/complete")
	@ApiOperation(value = "完成库存转换单")
	@PreAuthorize(value = "hasRole('ROLE_SYS_ADMIN') or hasAuthority('transfer_inventory_complete')")
	public JSONObject complementTransferInventoryList(@RequestBody TransferInventoryListDTO transferInventoryListDTO) {
		int result = transferInventoryListService.completeTransferInventoryList(transferInventoryListDTO);
		String message = null;
		if (result > 0) {
			message = "操作成功";
			return ResponseJsonUtils.successResponseJsonWithoutData(message);
		}else {
			message = "完成库存转换单时出现异常！";
			throw new ShiftInventoryException(message);
		}
	}
	
	
	@PostMapping("/list")
	@ApiOperation(value = "查询库存转换单列表")
	@PreAuthorize(value = "hasRole('ROLE_SYS_ADMIN') or hasAuthority('transfer_inventory_get')")
	public JSONObject getTransferInventoryLists(TransferListDTO transferListDTO) {
		PageBaseDto<TransferInventoryListDTO> pageBaseDto = transferInventoryListService.getTransferInventoryListDTOList(transferListDTO);
		String message = "库存转换单列表";
		return ResponseJsonUtils.successResponseJson(pageBaseDto, message);
	}
	
	
	@PostMapping("/detail")
	@ApiOperation(value = "查询库存转换单详情")
	@PreAuthorize(value = "hasRole('ROLE_SYS_ADMIN') or hasAuthority('transfer_inventory_get')")
	public JSONObject getTransferInventoryDetail(Long transferId) {
		TransferInventoryListDTO transferInventoryListDTO = transferInventoryListService.getTransferInventoryListDTODetail(transferId);
		String message = null;
		if (null != transferInventoryListDTO) {
			message = "库存转换单详情";
			return ResponseJsonUtils.successResponseJson(transferInventoryListDTO, message);
		}else {
			message = "查询详情失败！";
			throw new RuntimeException(message);
		}
	}
	
	
	@PostMapping("/remove")
	@ApiOperation(value = "取消库存转换单")
	@PreAuthorize(value = "hasRole('ROLE_SYS_ADMIN') or hasAuthority('transfer_inventory_remove')")
	public JSONObject cancelTransferInventoryList(Long transferId) {
		int result = transferInventoryListService.updateTransferStatus(transferId);
		String message = null;
		if (result > 0) {
			message = "操作成功";
			return ResponseJsonUtils.successResponseJsonWithoutData(message);
		}else {
			message = "取消库存转换单时出现异常！";
			throw new ShiftInventoryException(message);
		}
	}
	
	
	@GetMapping("/export")
	@ApiOperation(value = "导出库存转换单")
	@PreAuthorize(value = "hasRole('ROLE_SYS_ADMIN') or hasAuthority('transfer_inventory_export')")
	public void exportXSSFile(Long transferId, HttpServletResponse response)  {
		TransferInventoryListDTO transferInventoryListDTO = transferInventoryListService.getTransferInventoryListDTODetail(transferId);
		ClassPathResource resource = new ClassPathResource("/templates/transfer_inventory_list.xlsx");
		if (resource.exists()) {
			response.reset();
			XSSFWorkbook xwb = null;
			InputStream inputStream = null;
			OutputStream ouputStream = null;
			try {
				inputStream = resource.getInputStream();
				 xwb = (XSSFWorkbook) WorkbookFactory.create(inputStream); // 读取excel模板
				 
				 SheetUtils SheetUtils = new SheetUtils(xwb, 0);
				 SheetUtils.setCell(0, 0, "商品转换单-" + transferInventoryListDTO.getListSerialNo());
				 SheetUtils.setCell(4, 2, transferInventoryListDTO.getGroupName());
				 SheetUtils.setCell(4, 6, transferInventoryListDTO.getCustomerName());
				 SheetUtils.setCell(5, 2, transferInventoryListDTO.getWarehouseName());
				 SheetUtils.setCell(5, 6, transferInventoryListDTO.getCreateUser());
				 SheetUtils.setCell(6, 2, DateToStringUtils.ConvertDateToString(transferInventoryListDTO.getGmtCreate()));
				 SheetUtils.setCell(6, 6, DateToStringUtils.ConvertDateToString(transferInventoryListDTO.getGmtComplete()));
				 SheetUtils.setCell(7, 2, transferInventoryListDTO.getRemark());
				 
				 List<TransferGoodsDO> transferGoodsDOList = transferInventoryListDTO.getTransferGoodsDOList();
				 if (null != transferGoodsDOList && !transferGoodsDOList.isEmpty()) {
					 int materialGoodsRow = 11, productGoodsRow = 29;
					 for (int i = 0; i < transferGoodsDOList.size(); i++) {
						 TransferGoodsDO transferGoodsDO = transferGoodsDOList.get(i);
						 if (0 == transferGoodsDO.getIsMaterial()) {
							 SheetUtils SheetGoodsUtils = new SheetUtils(xwb, 0, materialGoodsRow);
							 setGoodsCell(SheetGoodsUtils, transferGoodsDO);
							 materialGoodsRow ++;
						 }else {
							 SheetUtils SheetGoodsUtils = new SheetUtils(xwb, 0, productGoodsRow); 
							 setGoodsCell(SheetGoodsUtils, transferGoodsDO);
							 productGoodsRow ++;
						 }
					 }
				 }
				 
				 String fileName = "商品转换单.xlsx";
				 response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes("utf-8"),"iso-8859-1") + "\"");
	             response.setContentType("application/octet-stream; charset=UTF-8");
	             ouputStream = response.getOutputStream();
	             xwb.write(ouputStream);
				
			}catch(Exception e) {
				e.printStackTrace();
				log.debug("导出Excel表出现异常：" + e.getMessage());
			}finally {
				CloseStream.close(xwb);
				CloseStream.close(ouputStream);
				CloseStream.close(inputStream);
			}
		}else {
			throw new RuntimeException("Excel模板文件路径异常!");
		}
	}
	
	
	private void setGoodsCell(SheetUtils SheetGoodsUtils, TransferGoodsDO transferGoodsDO) {
		 SheetGoodsUtils.setCell(0, transferGoodsDO.getGoodsName());
		 SheetGoodsUtils.setCell(1, transferGoodsDO.getGoodsCode());
		 SheetGoodsUtils.setCell(2, transferGoodsDO.getGoodsBarcode());
		 SheetGoodsUtils.setCell(3, transferGoodsDO.getGoodsSpec());
		 SheetGoodsUtils.setCell(4, transferGoodsDO.getGoodsUnit());
		 SheetGoodsUtils.setCell(5, transferGoodsDO.getGoodsBatch());
		 SheetGoodsUtils.setCell(6, transferGoodsDO.getWhLocCode());
		 SheetGoodsUtils.setCell(7, transferGoodsDO.getTransferNum().doubleValue());
		 SheetGoodsUtils.setCell(8, transferGoodsDO.getRemark());
	}
	
	
	

}
