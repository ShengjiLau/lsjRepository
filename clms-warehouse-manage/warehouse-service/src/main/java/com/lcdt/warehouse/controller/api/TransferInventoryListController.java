package com.lcdt.warehouse.controller.api;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
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
	public void exportXSSFile(Long transferId, HttpServletResponse response) throws Exception, InvalidFormatException {
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
				 XSSFSheet sheet = xwb.getSheetAt(0);
				 XSSFRow row = sheet.getRow(0);
				 XSSFCell cell = row.getCell(0);
				 cell.setCellValue("商品转换单-" + transferInventoryListDTO.getListSerialNo());
				
				 row = sheet.getRow(4);
				 cell = row.getCell(2);
				 cell.setCellValue(transferInventoryListDTO.getGroupName());
				 cell = row.getCell(6);
				 cell.setCellValue(transferInventoryListDTO.getCustomerName());
				 
				 row = sheet.getRow(5);
				 cell = row.getCell(2);
				 cell.setCellValue(transferInventoryListDTO.getWarehouseName());
				 cell = row.getCell(6);
				 cell.setCellValue(transferInventoryListDTO.getCreateUser());
				 
				 row = sheet.getRow(6);
				 cell = row.getCell(2);
				 cell.setCellValue(transferInventoryListDTO.getGmtCreate().toGMTString());
				 cell = row.getCell(6);
				 cell.setCellValue(transferInventoryListDTO.getGmtComplete().toGMTString());
				 
				 row = sheet.getRow(7);
				 cell = row.getCell(2);
				 cell.setCellValue(transferInventoryListDTO.getRemark());
				 
				 List<TransferGoodsDO> transferGoodsDOList = transferInventoryListDTO.getTransferGoodsDOList();
				 if (null != transferGoodsDOList && !transferGoodsDOList.isEmpty()) {
					 int materialGoodsRow = 11,productGoodsRow = 29;
					 for (int i = 0; i < transferGoodsDOList.size(); i++) {
						 TransferGoodsDO transferGoodsDO = transferGoodsDOList.get(i);
						 if (0 == transferGoodsDO.getIsMaterial()) {
							 row = sheet.getRow(materialGoodsRow); 
							 cell = row.getCell(0);
							 cell.setCellValue(transferGoodsDO.getGoodsName());
							 cell = row.getCell(1);
							 cell.setCellValue(transferGoodsDO.getGoodsCode());
							 cell = row.getCell(2);
							 cell.setCellValue(transferGoodsDO.getGoodsBarcode());
							 cell = row.getCell(3);
							 cell.setCellValue(transferGoodsDO.getGoodsSpec());
							 cell = row.getCell(4);
							 cell.setCellValue(transferGoodsDO.getGoodsUnit());
							 cell = row.getCell(5);
							 cell.setCellValue(transferGoodsDO.getGoodsBatch());
							 cell = row.getCell(6);
							 cell.setCellValue(transferGoodsDO.getWhLocCode());
							 cell = row.getCell(7);
							 cell.setCellValue(transferGoodsDO.getTransferNum().doubleValue());
							 cell = row.getCell(8);
							 cell.setCellValue(transferGoodsDO.getRemark());
							 materialGoodsRow++;
						 }else {
							 row = sheet.getRow(productGoodsRow); 
							 cell = row.getCell(0);
							 cell.setCellValue(transferGoodsDO.getGoodsName());
							 cell = row.getCell(1);
							 cell.setCellValue(transferGoodsDO.getGoodsCode());
							 cell = row.getCell(2);
							 cell.setCellValue(transferGoodsDO.getGoodsBarcode());
							 cell = row.getCell(3);
							 cell.setCellValue(transferGoodsDO.getGoodsSpec());
							 cell = row.getCell(4);
							 cell.setCellValue(transferGoodsDO.getGoodsUnit());
							 cell = row.getCell(5);
							 cell.setCellValue(transferGoodsDO.getGoodsBatch());
							 cell = row.getCell(6);
							 cell.setCellValue(transferGoodsDO.getWhLocCode());
							 cell = row.getCell(7);
							 cell.setCellValue(transferGoodsDO.getTransferNum().doubleValue());
							 cell = row.getCell(8);
							 cell.setCellValue(transferGoodsDO.getRemark());
							 productGoodsRow++;
						 }
					 }
				 }
				 
				 String fileName = "商品转换单.xlsx";
				 response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes("utf-8"),"iso-8859-1") + "\"");
	             response.setContentType("application/octet-stream;charset=UTF-8");
	             ouputStream = response.getOutputStream();
	             xwb.write(ouputStream);
				
			}catch(IOException e) {
				e.printStackTrace();
				log.debug("导出Excel表出现异常：" + e.getMessage());
			}finally {
				if (null !=  xwb) {
					try {
						xwb.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (null != ouputStream) {
					try {
						ouputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (null != inputStream) {
					try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			}
		}else {
			throw new RuntimeException("文件路径异常!");
		}
	}
	
	
	
	
	
	

}
