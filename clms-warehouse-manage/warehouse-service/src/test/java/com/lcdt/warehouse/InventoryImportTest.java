package com.lcdt.warehouse;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class InventoryImportTest {


    @Test
    public void testOpenUrl() throws IOException {
        XSSFWorkbook sheets = new XSSFWorkbook((new URL("\n" +
                "https://clms-dtd.oss-cn-beijing.aliyuncs.com/temp_excel/%E5%BA%93%E5%AD%98%E5%AF%BC%E5%85%A5%E6%A8%A1%E6%9D%BF.xlsx")).openStream());// 读取excel模板
        final XSSFSheet sheetAt = sheets.getSheetAt(0);
        final XSSFRow row = sheetAt.getRow(0);
        row.cellIterator().forEachRemaining(cell ->
        {
            System.out.println(cell.getStringCellValue());
        });
    }


}
