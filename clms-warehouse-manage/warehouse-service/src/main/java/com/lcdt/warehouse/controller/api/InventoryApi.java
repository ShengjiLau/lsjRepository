package com.lcdt.warehouse.controller.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.warehouse.dto.ImportInventoryDto;
import com.lcdt.warehouse.dto.InventoryQueryDto;
import com.lcdt.warehouse.entity.Inventory;
import com.lcdt.warehouse.service.InventoryService;
import com.lcdt.warehouse.utils.JSONResponseUtil;
import com.lcdt.warehouse.utils.ResponseMessage;
import com.lcdt.warehouse.utils.excel.ExcelReader;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import sun.net.www.http.HttpClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


@RestController
@RequestMapping("/api/inventory")
@Api(value = "库存api", description = "库存")
public class InventoryApi {

    @Autowired
    InventoryService inventoryService;

    private Logger logger = LoggerFactory.getLogger(InventoryApi.class);


    @PostMapping("/list")
    @ApiOperation("库存明细列表")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('inventory_list_search')")
    public ResponseMessage inventoryList(InventoryQueryDto queryDto){
        logger.debug("query inventory list querydto:{}",queryDto);
        Long loginCompanyId = SecurityInfoGetter.getCompanyId();
        queryDto.setCompanyId(loginCompanyId);
        Page<Inventory> page = inventoryService.queryInventoryPage(queryDto, loginCompanyId);
        return JSONResponseUtil.success(page);
    }

    @PostMapping("/all")
    @ApiOperation("查询指定商品在 仓库中的库存")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('inventory_list_search')")
    public ResponseMessage inventoryList(Long warehouseId, Long goodsId) {
        Long loginCompanyId = SecurityInfoGetter.getCompanyId();
        List<Inventory> inventories = inventoryService.queryAllInventory(loginCompanyId,warehouseId, goodsId);
        return JSONResponseUtil.success(inventories);
    }


    @PostMapping("/price/update")
    @ApiOperation("修改库存成本价")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('update_price')")
    public ResponseMessage modifyInventoryPrice(Long inventoryId,Double newprice) {
        return JSONResponseUtil.success(inventoryService.modifyInventoryPrice(inventoryId, newprice));
    }

    @PostMapping("/costremark/update")
    @ApiOperation("修改备注")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('update_remark')")
    public ResponseMessage modifyInventoryRemark(Long inventoryId, String remark) {
        return JSONResponseUtil.success(inventoryService.modifyInventoryRemark(inventoryId, remark));
    }


    @PostMapping("/inventory/import")
    @ApiOperation("修改备注")
    public ResponseMessage importInventorylist(String fileUrl,Long customerId,Long wareHouseId){
        //download file
        XSSFWorkbook sheets = null;
        try {

            ArrayList<ImportInventoryDto> importInventoryDtos = new ArrayList<>();

            sheets = new XSSFWorkbook(doanloadFile(fileUrl));// 读取excel模板
            //只读取第一个sheet
            XSSFSheet firstSheet = sheets.getSheetAt(0);
            ExcelReader.readSheet(firstSheet, new ExcelReader.CellReadCallback() {
                @Override
                public void onCellRead(Cell cell) {
                    int rowNum = cell.getRowIndex();
                    if (rowNum == 0) {
                        return;
                    }
                    int columnIndex = cell.getColumnIndex();
                    ImportInventoryDto importInventoryDto;
                    if (rowNum - 1 > importInventoryDtos.size()) {
                        importInventoryDto = new ImportInventoryDto();
                        importInventoryDto.setCompanyId(SecurityInfoGetter.getCompanyId());
                        importInventoryDto.setUserId(SecurityInfoGetter.getUser().getUserId());
                        importInventoryDto.setWareHouseId(wareHouseId);
                        importInventoryDto.setCustomerId(customerId);
                        importInventoryDtos.add(importInventoryDto);
                    }
                    else{
                        importInventoryDto = importInventoryDtos.get(rowNum - 1);
                    }
                        switch (columnIndex) {
                            case 0:
                                importInventoryDto.setGoodName(cell.getStringCellValue());
                                break;
                            case 1:
                                importInventoryDto.setGoodSpec(cell.getStringCellValue());
                                break;
                            case 2:
                                importInventoryDto.setGoodcode(cell.getStringCellValue());
                                break;
                            case 3:
                                importInventoryDto.setBatch(cell.getStringCellValue());
                                break;
                            case 4:
                                importInventoryDto.setStorageLocationCode(cell.getStringCellValue());
                                break;
                            case 5:
                                importInventoryDto.setNum(cell.getStringCellValue());
                                break;
                            case 6:
                                importInventoryDto.setCostPrice(cell.getStringCellValue());
                                break;
                        }

                    }

                }
            );

            inventoryService.importInventory(importInventoryDtos);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (sheets != null) {
                try {
                    sheets.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    private InputStream doanloadFile(String url) {
        try {
            final URL fileUrl = new URL(url);
            return fileUrl.openStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };
    }
}
